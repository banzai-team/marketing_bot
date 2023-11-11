import React from 'react';
import {Head} from '~/components/shared/Head';

const StatisticPage: React.FC = () => {
    return (
        <>
            <Head title="Статистика" />
            <div className="page-title">Статистика</div>
            <div className="grid grid-cols-2 gap-4 mt-6 grid-rows-3 grid-flow-col" />

        </>
    );
};

export default StatisticPage;
