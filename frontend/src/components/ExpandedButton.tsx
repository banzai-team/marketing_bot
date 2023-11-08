import React from 'react';
import {ChevronDownIcon, ChevronRightIcon} from '@heroicons/react/24/solid'

type ExpandedButtonProps = {
    onClick: () => void,
    isExpanded: boolean
}

const ExpandedButton: React.FC<ExpandedButtonProps> = ({onClick, isExpanded}) => {
    return (
        <button
            onClick={onClick}
            className="btn btn-xs btn-ghost mr-2"
        >
            {/* TODO: add transition */}
            {isExpanded ? <ChevronDownIcon className="h-3 w-3" /> : <ChevronRightIcon className="h-3 w-3" />}
        </button>

    );
};

export default ExpandedButton;
