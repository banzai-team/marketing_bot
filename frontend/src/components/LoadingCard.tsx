import React from 'react';

const LoadingCard: React.FC = () => (
    <div className="py-20 px-2 flex justify-center items-center">
        <span className="text-primary text-xl align-middle mr-4">Идет загрузка</span>
        <span className="loading loading-dots loading-lg text-primary" />
    </div>
);

export default LoadingCard;
