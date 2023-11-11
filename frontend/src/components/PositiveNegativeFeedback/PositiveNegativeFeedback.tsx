import React from 'react';
import {ArrowDownIcon, ArrowUpIcon} from '@heroicons/react/24/solid'
import "./PositiveNegativeFeedback.css";

type PositiveNegativeFeedbackFeedbackProps = {
    point: number;
}

const PositiveNegativeFeedback: React.FC<PositiveNegativeFeedbackFeedbackProps> = ({ point }) => {
    return (
        <div className={`badge rounded-full feedback-badge feedback${point}`}>
            {
                point < 0
                    ? <ArrowDownIcon className="h-3 w-3 mr-1" />
                    : point > 0
                        ? <ArrowUpIcon className="h-3 w-3 mr-1" />
                        : null
            }
            {point}
        </div>

    );
};

export default PositiveNegativeFeedback;
