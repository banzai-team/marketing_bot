import React from 'react';
import {ArrowDownIcon, ArrowUpIcon} from '@heroicons/react/24/solid'
import "./PositiveNegativeFeedback.css";

type PositiveNegativeFeedbackFeedbackProps = {
    point: number;
}

const PositiveNegativeFeedback: React.FC<PositiveNegativeFeedbackFeedbackProps> = ({ point }) => {
    const val = Math.round(point);
    return (
        <div className={`badge rounded-full feedback-badge feedback${val}`}>
            {
                val < 0
                    ? <ArrowDownIcon className="h-3 w-3 mr-1" />
                    : val > 0
                        ? <ArrowUpIcon className="h-3 w-3 mr-1" />
                        : null
            }
            {val}
        </div>

    );
};

export default PositiveNegativeFeedback;
