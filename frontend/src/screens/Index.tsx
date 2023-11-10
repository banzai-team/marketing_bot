import React from 'react';
import {Head} from '~/components/shared/Head';
import Table from "~/components/Table";
import {createColumnHelper, Row} from "@tanstack/react-table";
import {PlusSmallIcon, UserCircleIcon} from '@heroicons/react/24/solid'

import ExpandedButton from "~/components/ExpandedButton";
import PositiveNegativeFeedback from "~/components/PositiveNegativeFeedback/PositiveNegativeFeedback";
import FeedbackButtons from "~/components/FeedbackButtons/FeedbackButtons";


const Index: React.FC = () => {
    const data = [
        {
            id: "1",
            firstName: "Test 1",
            lastName: "Test 11",
            age: "111",
            feedback: "-5",
            subRows: [{
                messages: [
                    "Где информация о вкладе 13% in",
                    "Hello! out",
                    "Hello! 2 in",
                    "Hello! 3 out",
                    "Hello! 4 in",
                ]
            }]
        },
        {
            id: "2",
            firstName: "Test 2",
            lastName: "Test 22",
            age: "222",
            feedback: "-4",
            subRows: [{
                messages: [
                    "Hello! out",
                    "Hello! 2 in",
                    "Hello! 3 out",
                    "Hello! 4 in",
                ]
            }]
        },
        {
            id: "3",
            firstName: "Test 3",
            lastName: "Test 33",
            feedback: "-3",
            age: "333",
        },
        {
            id: "3",
            firstName: "Test 3",
            lastName: "Test 33",
            feedback: "-2",
            age: "333",
        },
        {
            id: "3",
            firstName: "Test 3",
            lastName: "Test 33",
            feedback: "-1",
            age: "333",
        },
        {
            id: "3",
            firstName: "Test 3",
            lastName: "Test 33",
            feedback: "0",
            age: "333",
        },
        {
            id: "3",
            firstName: "Test 3",
            lastName: "Test 33",
            feedback: "1",
            age: "333",
        },
        {
            id: "3",
            firstName: "Test 3",
            lastName: "Test 33",
            feedback: "2",
            age: "333",
        },
        {
            id: "3",
            firstName: "Test 3",
            lastName: "Test 33",
            feedback: "3",
            age: "333",
        },
        {
            id: "3",
            firstName: "Test 3",
            lastName: "Test 33",
            feedback: "4",
            age: "333",
        },
        {
            id: "3",
            firstName: "Test 3",
            lastName: "Test 33",
            feedback: "5",
            age: "333",
        }
    ];

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
                        onChange={table.getToggleAllRowsSelectedHandler()}
                        checked={table.getIsAllRowsSelected()}
                        className="checkbox checkbox-xs checkbox-primary align-middle"
                    />
                </div>
            ),
            cell: ({row, getValue}) => (
                <div className="flex justify-between items-center">
                    <ExpandedButton onClick={row.getToggleExpandedHandler()} isExpanded={row.getIsExpanded()} />
                    <input type="checkbox" checked={row.getIsSelected()} onChange={row.getToggleSelectedHandler()} className="checkbox checkbox-xs checkbox-primary" />
                </div>
            ),
        }),
        columnHelper.accessor('firstName', {
            cell: info => info.renderValue(),
        }),
        columnHelper.accessor("feedback", {
            cell: info => <PositiveNegativeFeedback point={info.renderValue()} />,
            header: "Oценка настроения",
        }),
        columnHelper.accessor('age', {
            header: "Оценка",
            cell: info => <FeedbackButtons />,
        })
    ]

    // TODO: add type
    const renderSubComponent = ({row}: { row: Row<any> }) => {
        const data = row.original.subRows[0].messages;

        if (!data) {
            return null;
        }

        return (
            <div>
                {data.map((message, key) => {
                    if (message.trim().endsWith("in")) {
                        return (
                            <div className="chat chat-start" key={`message-${key}`}>
                                <div className="chat-image avatar">
                                    <div className="w-12 rounded-full bg-base-100">
                                        <UserCircleIcon className="h-12 w-12" />
                                    </div>
                                </div>
                                <div className="chat-header">
                                    User
                                </div>
                                <div className="chat-bubble">
                                    {message.substring(0, message.lastIndexOf('in'))}
                                </div>
                            </div>
                        )
                    }

                    return (
                        <div className="chat chat-end" key={`message-${key}`}>
                            <div className="chat-image avatar">
                                <div className="w-10 rounded-full">
                                    <img src="gazprombank-1.svg" />
                                </div>
                            </div>
                            <div className="chat-header">
                                Operator
                            </div>
                            <div className="chat-bubble chat-bubble-primary">
                                {message.substring(0, message.lastIndexOf('out'))}
                            </div>
                        </div>
                    )
                })}
            </div>
        )
    }

  return (
      <>
          <Head title="Main page" />
          <div className="flex justify-between">
              <div className="page-title">Home Page</div>

              <button className="btn btn-sm btn-outline btn-primary">
                  <PlusSmallIcon className="h-5 w-5" />
                  add new
              </button>
          </div>
          <div className="card bg-base-100 shadow-xl overflow-hidden mt-6">
              <Table data={data} columns={columns} renderSubComponent={renderSubComponent} />
          </div>
      </>
  );
}

export default Index;
