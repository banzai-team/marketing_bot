import React from 'react';
import {UserIcon} from '@heroicons/react/24/outline';

const Avatar: React.FC<{className?: string}> = ({ className = "w-5 mr-2" }) => {
    return (
        <div className="avatar">
            <div className={`rounded-full ring ring-offset-base-100 ring-base-300 ring-offset-2 ${className}`}>
                <UserIcon />
            </div>
        </div>
    );
};

export default Avatar;