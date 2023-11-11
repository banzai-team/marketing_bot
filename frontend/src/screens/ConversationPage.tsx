import React from 'react';
import {Head} from '~/components/shared/Head';
import {Link, useParams} from "react-router-dom";
import {Routes} from "~/components/router/Router";
import Chat from "~/components/Chat/Chat";
import {ArrowLongLeftIcon, PlusSmallIcon} from '@heroicons/react/24/outline'
import OfferPurchase from "~/components/OfferPurchase";
import PositiveNegativeFeedback from "~/components/PositiveNegativeFeedback/PositiveNegativeFeedback";
import FeedbackButtons from "~/components/FeedbackButtons/FeedbackButtons";

const Conversation: React.FC = () => {
    const {id = ""} = useParams();

    const data = {
        messages: [
            "Где информация о вкладе 13% in",
            "Hello! out",
            "Hello! 2 in",
            "Hello! 3 out",
            "Hello! 4 in"
        ],
        text: "Custom text",
        id_sequence: "456",
        is_operator: true,
        feedback: 4,
        offer_purchase: true,
        stop_theme: ["test", "test2"],
    }

    return (
        <>
            <Head title={`Conversation #${id}`} />
            <div className="page-title">{`Диалог #${id}`}</div>

            <Link to={Routes.ROOT} className="link link-hover text-sm opacity-30">
                <ArrowLongLeftIcon className="h-5 w-5 inline mr-1" />На главную
            </Link>
            <div className="grid grid-cols-2 gap-4 mt-6 grid-rows-3 grid-flow-col max-sm:grid-cols-1">
                <div className="card bg-base-100 shadow-xl p-4 row-span-3 max-sm:row-span-1">
                    <Chat data={data.messages} />
                </div>

                <div className="card bg-base-100 shadow-xl p-4 col-span-2 max-sm:col-span-1">
                    <div className="flex mb-4">
                        <div className="font-bold mr-4">Оператор:</div>
                        <OfferPurchase hasOffer={data.is_operator} />
                    </div>
                    <div className="flex mb-4">
                        <div className="font-bold mr-4">Число:</div>
                        <div>{data.id_sequence}</div>
                    </div>
                    <div className="flex mb-4">
                        <div className="font-bold mr-4">Вспомогательный текст:</div>
                        <div>{data.text}</div>
                    </div>
                </div>

                <div className="card bg-base-100 shadow-xl p-4 row-span-2 col-span-2 max-sm:row-span-1 max-sm:col-span-1">
                    <div className="text-lg font-bold text-primary mb-4">Результат</div>
                    <div className="flex mb-4">
                        <div className="font-bold mr-4">Было ли сделано предложение:</div>
                        <OfferPurchase hasOffer={data.offer_purchase} />
                    </div>
                    <div className="flex mb-4">
                        <div className="font-bold mr-4">Оценка настроения:</div>
                        <PositiveNegativeFeedback point={data.feedback} />
                    </div>
                    <div className="flex mb-12">
                        <div className="font-bold mr-4">Затронутые стоп-темы:</div>
                        <div>
                            {data.stop_theme ? (
                                data.stop_theme.map((theme: string, key: number) => (
                                    <div key={key}>{theme}</div>
                                ))
                            ) : "-"}
                        </div>
                    </div>
                    <div className="flex justify-center">
                        <FeedbackButtons chatIds={id} bigSize />
                    </div>
                </div>
            </div>
        </>
);
};

export default Conversation;
