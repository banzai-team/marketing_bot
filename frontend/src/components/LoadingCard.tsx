import React from 'react';

const LoadingCard: React.FC<{text?: string}> = ({ text }) => (
    <div className="py-20 px-2 flex justify-center items-center">
        {
            text ? ( <span className="text-primary text-xl align-middle mr-4">{text}а</span> ) : (
                <>
                    <span className="text-primary text-xl align-middle mr-4">Идет загрузка</span>
                    <span className="loading loading-dots loading-lg text-primary" />
                </>
            )
        }
    </div>
);

export default LoadingCard;
