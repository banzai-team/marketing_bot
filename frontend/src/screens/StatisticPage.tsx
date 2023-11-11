import React from 'react';
import {Head} from '~/components/shared/Head';
import PieChart from "~/components/PieChart";

const StatisticPage: React.FC = () => {
    const data = [
        { name: '👍', value: 5, color: "#4b6afd"},
        { name: '👎', value: 3, color: "#e3e1fe"},
        { name: 'Не оценено', value: 1, color: "#7d90b2"},
    ];

    const data2 = [
        { name: '5', value: 5, color: "#17A049"},
        { name: '4', value: 3, color: "#28C260"},
        { name: '3', value: 1, color: "#5CD488"},
        { name: '2', value: 0, color: "#84E3A7"},
        { name: '1', value: 0, color: "#A9F2C4"},
        { name: '0', value: 2, color: "#DDD8D8"},
        { name: '-1', value: 1, color: "#FFB4B4"},
        { name: '-2', value: 0, color: "#ED8888"},
        { name: '-3', value: 1, color: "#E94F4F"},
        { name: '-4', value: 0, color: "#D61D1D"},
        { name: '-5', value: 1, color: "#B40000"},
    ];

    return (
        <>
            <Head title="Статистика" />
            <div className="page-title">Статистика</div>
            <div className="grid grid-cols-2 gap-4 mt-6 max-sm:grid-cols-1">
                <div className="card bg-base-100 shadow-xl p-4 h-80">
                    <div className="text-lg font-bold text-primary mb-4">Оценка настроения диалогов</div>
                    <PieChart data={data2} />
                </div>
                <div className="card bg-base-100 shadow-xl p-4 ">
                    <div className="text-lg font-bold text-primary mb-4">Оцнка диалогов</div>
                    <PieChart data={data} />
                </div>
            </div>

        </>
    );
};

export default StatisticPage;
