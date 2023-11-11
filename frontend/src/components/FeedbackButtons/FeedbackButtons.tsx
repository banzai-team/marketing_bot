import React from 'react';
import "./FeedbackButtons.css";
import { useMutation, useQueryClient } from 'react-query';
import { sendFeedback, SendFeedbackPayload } from '~/domain/api';
import { useAuth } from "react-oidc-context";

type FeedbackButtonsProps = {
  chatIds: string | Array<string>;
  bigSize?: boolean;
  cleanChatIds?: (chatIds: Array<string>) => void;
}

const FeedbackButtons: React.FC<FeedbackButtonsProps> = ({ chatIds, cleanChatIds, bigSize }) => {
  const auth = useAuth();

  const queryClient = useQueryClient()

  const send = useMutation((payload: SendFeedbackPayload) => sendFeedback(payload, auth.user?.access_token!), {
    onSuccess: (_, initialParams) => {
      // queryClient.setQueryData([dialogByIdKey(initialParams.modelResponseId)], {  });
    },
  });

  const sendFeedbackRequest = async (chatIds: string | Array<string>, feedback: boolean) => {
    if (Array.isArray(chatIds)) {
      await Promise.all(chatIds.map(id => send.mutateAsync({ userId: auth.user?.profile.nickname, modelResponseId: Number.parseInt(id), isCorrect: feedback })));
      cleanChatIds && cleanChatIds([]);
      queryClient.invalidateQueries(dialogKey)
    } else {
      await send.mutateAsync({ userId: auth.user?.profile.nickname, modelResponseId: Number.parseInt(chatIds), isCorrect: feedback });

      queryClient.invalidateQueries(dialogKey);
    }
  };

  return (
    <>
      <button
        className={`btn btn-ghost mr-1 feedback-btn ${bigSize ? "text-5xl btn-lg" : "text-2xl btn-sm"}`}
        onClick={(e) => {
          e.stopPropagation();
          e.preventDefault();
          sendFeedbackRequest(chatIds, true);
        }}
      >
        👍
      </button>
      <button
        className={`btn btn-ghost feedback-btn ${bigSize ? "text-5xl btn-lg" : "text-2xl btn-sm"}`}
        onClick={(e) => {
          e.stopPropagation();
          e.preventDefault();
          sendFeedbackRequest(chatIds, false);
        }}
      >
        👎
      </button>
    </>

  );
};

export default FeedbackButtons;
