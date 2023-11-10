import React from 'react';
import FeedbackButtons from "~/components/FeedbackButtons/FeedbackButtons";

type FeedbackPanelProps = {
    chatIds: Array<string>;
    cleanChatIds: (chatIds: Array<string>) => void;
}

const FeedbackPanel: React.FC<FeedbackPanelProps> = ({ chatIds, cleanChatIds }) => {
    return (
        <div
            className="absolute bottom-2 left-20 right-20 bg-secondary-content text-white rounded-full card p-4 flex flex-row justify-center items-center z-10">
            <div className="mr-4">Оценить выделенные диалоги</div>
            <FeedbackButtons chatIds={chatIds} cleanChatIds={cleanChatIds} />
        </div>

    );
};

export default FeedbackPanel;
