import React from 'react';
import { Head } from '~/components/shared/Head';
import Table from "~/components/Table";
import {createColumnHelper, Row} from "@tanstack/react-table";

import ExpandedButton from "~/components/ExpandedButton";


const Index: React.FC = () => {
    const data = [
        {
            firstName: "Test 1",
            lastName: "Test 11",
            age: "111",
            subRows: [{
                message: "Expanded data"
            }]
        },
        {
            firstName: "Test 2",
            lastName: "Test 22",
            age: "222",
            subRows: [{
                message: "Expanded data"
            }]
        },
        {
            firstName: "Test 3",
            lastName: "Test 33",
            age: "333",
            subRows: [{
                message: "Expanded data"
            }]
        }
    ];

    // TODO: add type
    const columnHelper = createColumnHelper<any>();

    const columns = [
        columnHelper.accessor('firstName', {
            cell: ({row, getValue}) => (
                <>
                    <ExpandedButton onClick={row.getToggleExpandedHandler()} isExpanded={row.getIsExpanded()}/>
                    {getValue()}
                </>
            ),
        }),
        columnHelper.accessor(row => row.lastName, {
            id: 'lastName',
            cell: info => <i>{info.getValue()}</i>,
            header: () => <span>Last Name</span>,
        }),
        columnHelper.accessor('age', {
            header: () => 'Age',
            cell: info => info.renderValue(),
        })
    ]

    // TODO: add type
    const renderSubComponent = ({row}: { row: Row<any> }) => {
        return (
            <pre style={{fontSize: '10px'}}>
      <code>{JSON.stringify(row.original, null, 2)}</code>
    </pre>
        )
    }

  return (
    <>
      <Head title="Main page" />
      <div className="page-title">Home Page</div>
        <div className="card bg-base-100 shadow-xl overflow-hidden mt-6">
            <Table data={data} columns={columns} renderSubComponent={renderSubComponent} />
        </div>

    </>
  );
}

export default Index;
