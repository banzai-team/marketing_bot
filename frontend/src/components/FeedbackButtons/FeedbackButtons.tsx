import React from 'react';
import {HandThumbDownIcon, HandThumbUpIcon} from '@heroicons/react/24/solid'
import "./FeedbackButtons.css";

type FeedbackButtonsProps = {}

const FeedbackButtons: React.FC<FeedbackButtonsProps> = () => {
    return (
        <>
            <button className="btn btn-sm btn-ghost mr-4">
                <HandThumbUpIcon className="h-5 w-5" />
            </button>
            <button className="btn btn-sm btn-ghost">
                <HandThumbDownIcon className="h-5 w-5" />
            </button>
        </>

    );
};

export default FeedbackButtons;
