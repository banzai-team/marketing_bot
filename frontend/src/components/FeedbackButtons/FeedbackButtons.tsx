import React from 'react';
import "./FeedbackButtons.css";

type FeedbackButtonsProps = {}

const FeedbackButtons: React.FC<FeedbackButtonsProps> = () => {
    return (
        <>
            <button className="btn btn-sm btn-ghost mr-1 text-2xl feedback-btn">
                👍
            </button>
            <button className="btn btn-sm btn-ghost text-2xl feedback-btn">
                👎
            </button>
        </>

    );
};

export default FeedbackButtons;
