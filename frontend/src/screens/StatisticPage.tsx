import React, {useMemo} from 'react';
import {Head} from '~/components/shared/Head';
import PieChart from "~/components/PieChart";
import {useQuery} from "react-query";
import {dialogKey} from "~/domain/keys";
import {getDialogs} from "~/domain/api";
import {useAuth} from "react-oidc-context";
import LoadingCard from "~/components/LoadingCard";

type dataItem = {
    request : {
        response: {
            feedback: -1 | 0 | 1;
            dialogEvaluation: number;
        }
    }
}
const StatisticPage: React.FC = () => {
    const auth = useAuth();
    const { data: dialogs, isLoading } = useQuery([dialogKey], () => getDialogs(auth.user?.access_token!));

    const feedbackStat = useMemo(() => {
        const feedback: Array<number> =
            dialogs?.data?.content?.map((i: dataItem)  => i ? Math.round(i.request.response.feedback) : null);

        const dataFeedback: any = feedback?.reduce((a: any, b) => {
            a[b] = (a[b] || 0) + 1;
            return a;
        }, {});

        return [
            { name: '👍', value: dataFeedback?.["1"], color: "#4b6afd"},
            { name: '👎', value: dataFeedback?.["-1"], color: "#e3e1fe"},
            { name: 'Не оценено', value: dataFeedback?.["0"], color: "#7d90b2"},
        ];
    }, [dialogs]);

    const evaluationStat = useMemo(() => {
        const evaluation: Array<number> = dialogs?.data?.content?.map((i: dataItem) => i ? Math.round(i.request.response.dialogEvaluation) : null);
        const dataFeedback: any = evaluation?.reduce((a: any, b) => {
            a[b] = (a[b] || 0) + 1;
            return a;
        }, {});

        return [
            { name: '5', value: dataFeedback?.["5"], color: "#17A049"},
            { name: '4', value: dataFeedback?.["4"], color: "#28C260"},
            { name: '3', value: dataFeedback?.["3"], color: "#5CD488"},
            { name: '2', value: dataFeedback?.["2"], color: "#84E3A7"},
            { name: '1', value: dataFeedback?.["1"], color: "#A9F2C4"},
            { name: '0', value: dataFeedback?.["0"], color: "#DDD8D8"},
            { name: '-1', value: dataFeedback?.["-1"], color: "#FFB4B4"},
            { name: '-2', value: dataFeedback?.["-2"], color: "#ED8888"},
            { name: '-3', value: dataFeedback?.["-3"], color: "#E94F4F"},
            { name: '-4', value: dataFeedback?.["-4"], color: "#D61D1D"},
            { name: '-5', value: dataFeedback?.["-5"], color: "#B40000"},
        ];
    }, [dialogs]);

    return (
        <>
            <Head title="Статистика" />
            <div className="page-title">Статистика</div>

            <div className="grid grid-cols-2 gap-4 mt-6 max-sm:grid-cols-1">
                <div className="card bg-base-100 shadow-xl p-4 h-80">
                    <div className="text-lg font-bold text-primary mb-4">Оценка настроения диалогов</div>
                    {
                        isLoading ? <LoadingCard /> : <PieChart data={evaluationStat} />
                    }
                </div>
                <div className="card bg-base-100 shadow-xl p-4 ">
                    <div className="text-lg font-bold text-primary mb-4">Оцнка диалогов</div>
                    {
                        isLoading ? <LoadingCard /> : <PieChart data={feedbackStat} />
                    }
                </div>
            </div>

        </>
    );
};

export default StatisticPage;
