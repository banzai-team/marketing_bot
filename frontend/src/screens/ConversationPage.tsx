import React from 'react';
import {Head} from '~/components/shared/Head';
import {Link, useParams} from "react-router-dom";
import {Routes} from "~/components/router/Router";
import Chat from "~/components/Chat/Chat";
import {ArrowLongLeftIcon, PlusSmallIcon} from '@heroicons/react/24/outline'
import OfferPurchase from "~/components/OfferPurchase";
import PositiveNegativeFeedback from "~/components/PositiveNegativeFeedback/PositiveNegativeFeedback";
import FeedbackButtons from "~/components/FeedbackButtons/FeedbackButtons";
import {useQuery} from 'react-query';
import {getDialog} from '~/domain/api';
import {useAuth} from 'react-oidc-context';
import LoadingCard from '~/components/LoadingCard';
import { dialogByIdKey } from "~/domain/keys";

const Conversation: React.FC = () => {
    const {id = ""} = useParams();
    const auth = useAuth();

    const {data: dialog, isLoading} = useQuery(dialogByIdKey(id), () => getDialog(auth.user?.access_token!, id));
    const data = dialog?.data?.request;
    const responseData = data?.response;

    return (
        <>
            <Head title={`Conversation #${id}`} />
            <div className="page-title">{`Диалог #${id}`}</div>

            <Link to={Routes.ROOT} className="link link-hover text-sm opacity-30">
                <ArrowLongLeftIcon className="h-5 w-5 inline mr-1" />На главную
            </Link>
            {
                isLoading
                    ? (
                        <LoadingCard />
                    )
                    : dialog?.data && !dialog.data.empty ? (
                        <div className="grid grid-cols-2 gap-4 mt-6 grid-rows-3 grid-flow-col max-sm:grid-cols-1 items-start">
                            <div className="card bg-base-100 shadow-xl p-4 row-span-3 max-sm:row-span-1">
                                <Chat data={data.messages} />
                            </div>
                            <div className="grid grid-cols-1 gap-4">
                                <div className="card bg-base-100 shadow-xl p-4 col-span-2 max-sm:col-span-1">
                                    <div className="text-lg font-bold text-primary mb-4">Входные данные</div>
                                    <div className="flex mb-4">
                                        <div className="font-bold mr-4">Оператор:</div>
                                        <OfferPurchase hasOffer={data.operator} />
                                    </div>
                                    <div className="flex mb-4">
                                        <div className="font-bold mr-4">ID Диалога:</div>
                                        <div>{data.dialogId}</div>
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
                                        <OfferPurchase hasOffer={responseData?.offerPurchase} />
                                    </div>
                                    <div className="flex mb-4">
                                        <div className="font-bold mr-4">Оценка настроения:</div>
                                        <PositiveNegativeFeedback point={responseData?.dialogEvaluation} />
                                    </div>
                                    <div className="flex mb-12">
                                        <div className="font-bold mr-4">Затронутые стоп-темы:</div>
                                        <div>
                                            {responseData?.stopTopics?.length > 0 ? (
                                                responseData.stopTopics.map((theme: string, key: number) => (
                                                    <div key={key}>{theme}</div>
                                                ))
                                            ) : "-"}
                                        </div>
                                    </div>
                                    <div className="flex justify-center">
                                        <FeedbackButtons chatIds={id} bigSize feedback={responseData?.feedback} />
                                    </div>
                                </div>
                            </div>
                        </div>
                    ) : <LoadingCard text="Нет данных для отображения"/>
            }
        </>
    );
};

export default Conversation;

