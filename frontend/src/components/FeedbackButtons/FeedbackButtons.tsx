import React from 'react';
import "./FeedbackButtons.css";
import {useMutation} from 'react-query';
import {sendFeedback} from '~/domain/api';

type FeedbackButtonsProps = {
    chatIds: string | Array<string>;
    cleanChatIds?: (chatIds: Array<string>) => void;
}

const FeedbackButtons: React.FC<FeedbackButtonsProps> = ({ chatIds, cleanChatIds }) => {
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
                className="btn btn-sm btn-ghost mr-1 text-2xl feedback-btn"
                onClick={() => sendFeedbackRequest(chatIds, true)}
            >
                👍
            </button>
            <button
                className="btn btn-sm btn-ghost text-2xl feedback-btn"
                onClick={() => sendFeedbackRequest(chatIds, false)}
            >
                👎
            </button>
        </>

    );
};

export default FeedbackButtons;
