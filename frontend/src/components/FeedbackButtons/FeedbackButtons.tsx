import React from 'react';
import "./FeedbackButtons.css";
import {useMutation} from 'react-query';
import {sendFeedback} from '~/domain/api';

type FeedbackButtonsProps = {
    chatIds: string | Array<string>;
    bigSize?: boolean;
    cleanChatIds?: (chatIds: Array<string>) => void;
}

const FeedbackButtons: React.FC<FeedbackButtonsProps> = ({ chatIds, cleanChatIds, bigSize }) => {
    const send = useMutation(sendFeedback);
    
    const sendFeedbackRequest = (chatIds: string | Array<string>, feedback: boolean): void => {
        if (Array.isArray(chatIds)) {
            chatIds.forEach(id => send.mutate({id, feedback}));
            cleanChatIds && cleanChatIds([]);
        } else {
            send.mutate({id: chatIds, feedback});
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
