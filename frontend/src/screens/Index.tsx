import React from 'react';
import {Head} from '~/components/shared/Head';
import Table from "~/components/Table";
import {createColumnHelper, Row} from "@tanstack/react-table";
import {PlusSmallIcon} from '@heroicons/react/24/solid'

import ExpandedButton from "~/components/ExpandedButton";


const Index: React.FC = () => {
    const data = [
        {
            id: "1",
            firstName: "Test 1",
            lastName: "Test 11",
            age: "111",
            subRows: [{
                message: "Expanded data"
            }]
        },
        {
            id: "2",
            firstName: "Test 2",
            lastName: "Test 22",
            age: "222",
            subRows: [{
                message: "Expanded data"
            }]
        },
        {
            id: "3",
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
          <div className="flex justify-between">
              <div className="page-title">Home Page</div>

              <button className="btn btn-sm btn-outline btn-primary">
                  <PlusSmallIcon className="h-5 w-5" />
                  add new
              </button>
          </div>
          <div className="card bg-base-100 shadow-xl overflow-hidden mt-6">
              <Table data={data} columns={columns} renderSubComponent={renderSubComponent}/>
          </div>
      </>
  );
}

export default Index;
