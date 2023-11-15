import React, { useMemo } from 'react';
import { Head } from '~/components/shared/Head';
import Table from "~/components/Table";
import { createColumnHelper, Row } from "@tanstack/react-table";
import { PlusSmallIcon } from '@heroicons/react/24/outline'
import { Link, useNavigate } from "react-router-dom";
import { useQuery } from "react-query";

import ExpandedButton from "~/components/ExpandedButton";
import PositiveNegativeFeedback from "~/components/PositiveNegativeFeedback/PositiveNegativeFeedback";
import FeedbackButtons from "~/components/FeedbackButtons/FeedbackButtons";
import { cutUnknownDirection } from "~/utils/MessagesUtils";
import Chat from "~/components/Chat/Chat";
import OfferPurchase from "~/components/OfferPurchase";
import { Routes } from "~/components/router/Router";
import FeedbackPanel from "~/components/FeedbackPanel";
import LoadingCard from "~/components/LoadingCard";
import { getDialogs } from "~/domain/api";
import { dialogKey } from "~/domain/keys";
import { useAuth } from "react-oidc-context";

const Index: React.FC = () => {
  const [rowSelection, setRowSelection] = React.useState<string[]>([]);
  const navigate = useNavigate();
  const onRowClick = (id: string) => navigate(`${Routes.CONVERSATION}/${id}`);

  // const isLoading = false;
  const auth = useAuth();

  const { data: dialogs, isLoading } = useQuery([dialogKey], () => getDialogs(auth.user?.access_token!));

  const data = useMemo(() => dialogs?.data?.content.map(
    (item: any) => ({ ...item.request, subRows: [true] })
  ) || null, [dialogs]);


  // TODO: add type
  const columnHelper = createColumnHelper<any>();

  const columns = [
    columnHelper.accessor('id', {
      // TODO: fix first column size
      size: 85,
      header: ({ table }) => (
        <div className="flex justify-end">
          <input
            type="checkbox"
            onChange={() => (
              rowSelection.length === data.length
                ? setRowSelection([])
                : setRowSelection(data.map((item: { id: string }) => item.id))
            )}
            checked={rowSelection.length === data.length}
            className="checkbox checkbox-xs checkbox-primary align-middle"
          />
        </div>
      ),
      cell: ({ row, getValue }) => (
        <div className="flex justify-between items-center" onClick={(e) => e.stopPropagation()}>
          <ExpandedButton onClick={row.getToggleExpandedHandler()} isExpanded={row.getIsExpanded()}/>
          <input
            type="checkbox"
            checked={rowSelection.includes(getValue())}
            onChange={(e) => {
              e.stopPropagation();
              e.preventDefault();
              rowSelection.includes(getValue())
                ? setRowSelection(rowSelection.filter(item => item !== getValue()))
                : setRowSelection([...rowSelection, getValue()])
            }}
            className="checkbox checkbox-xs checkbox-primary z-2"
          />
        </div>
      ),
    }),
    columnHelper.accessor('messages', {
      header: "Чат",
      cell: info => {
        const messages = info.renderValue();
        if (!messages) {
          return "-";
        }

        return (
          <div className="truncate w-80">
            {cutUnknownDirection(messages[messages.length - 1])}
          </div>
        );
      }
    }),
    columnHelper.accessor('response.stopTopics', {
      header: "Кол-во стоп-тем",
      cell: info => info.renderValue() ? info.renderValue().length : "0"
    }),
    columnHelper.accessor('response.offerPurchase', {
      header: "Предложение",
      cell: info => <OfferPurchase hasOffer={info.renderValue()}/>
    }),
    columnHelper.accessor("response.dialogEvaluation", {
      header: "Oценка настроения",
      cell: info => <PositiveNegativeFeedback point={info.renderValue()}/>,
    }),
    columnHelper.accessor('response', {
      header: "Оценка",
      cell: info => <FeedbackButtons chatIds={info.renderValue().id} allFeedbacks={info.renderValue()?.feedbacks}/>,
    })
  ]

  // TODO: add type
  const renderSubComponent = ({ row }: { row: Row<any> }) => {

    const messages = row.original.messages;
    if (!messages) {
      return null;
    }

    return (
      <div className="grid grid-cols-2 gap-4 p-3">
        <div className="flex">
          <Chat data={messages}/>
          <div className="divider divider-horizontal"/>
        </div>
        <div>
          <div className="flex mb-4">
            <div className="font-bold mr-4">Cтоп-темы:</div>
            <div>
              {row.original.response.stopTopics && row.original.response.stopTopics.length ? (
                row.original.response.stopTopics.map((theme: string, key: number) => (
                  <div key={key}>{theme}</div>
                ))
              ) : "-"}
            </div>
          </div>
          <div className="flex mb-4">
            <div className="font-bold mr-4">ID Диалога:</div>
            <div>{row.original.dialogId}</div>
          </div>
          <div className="flex mb-4">
            <div className="font-bold mr-4">Оператор:</div>
            <OfferPurchase hasOffer={row.original.operator}/>
          </div>
          <div className="flex mb-4">
            <div className="font-bold mr-4">Вспомогательный текст:</div>
            <div>{row.original.text}</div>
          </div>
        </div>
      </div>
    )
  }

  return (
    <>
      <Head title="Main page" />
      {
        rowSelection.length ? <FeedbackPanel chatIds={rowSelection} cleanChatIds={setRowSelection}/> : null
      }
      <div className="flex justify-between">
        <div className="page-title">История вызовов</div>

        <Link to={`${Routes.CONVERSATION}/${Routes.CREATE}`} className="btn btn-sm btn-outline btn-primary">
          <PlusSmallIcon className="h-5 w-5"/>
          Добавить
        </Link>
      </div>
      <div className="card bg-base-100 shadow-xl overflow-hidden mt-6 mb-10">
        {
          isLoading
            ? (
              <LoadingCard />
            ) : dialogs?.data && !dialogs.data.empty && !!data ? (
              <Table
                onRowClick={onRowClick}
                data={data}
                columns={columns}
                renderSubComponent={renderSubComponent}
              />
            ) : <LoadingCard text="Нет данных для отображения"/>
        }
      </div>
    </>
  );
}

export default Index;
