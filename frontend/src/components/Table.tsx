import React from 'react';
import {Column, flexRender, getCoreRowModel, useReactTable} from '@tanstack/react-table'

type TableProps = {
    data: any[],
    columns: Array<any | Column<Record<string, unknown>>>;
    renderSubComponent: (arg: any) => React.ReactNode;
}

const Table: React.FC<TableProps> = ({data, columns, renderSubComponent}) => {
    const [expanded, setExpanded] = React.useState({})

    const table = useReactTable<any>({
        data,
        columns,
        state: {
            expanded,
        },
        onExpandedChange: setExpanded,
        getSubRows: row => row.subRows,
        getCoreRowModel: getCoreRowModel(),
        debugTable: true,
    })

    return (
        <table className="table">
            <thead>
            {table.getHeaderGroups().map(headerGroup => (
                <tr key={headerGroup.id}>
                    {headerGroup.headers.map(header => (
                        <th key={header.id}>
                            {header.isPlaceholder
                                ? null
                                : flexRender(
                                    header.column.columnDef.header,
                                    header.getContext()
                                )}
                        </th>
                    ))}
                </tr>
            ))}
            </thead>
            <tbody>
            {table.getRowModel().rows.map(row => {
                return (
                    <>
                        <tr key={row.id}>
                            {row.getVisibleCells().map(cell => {
                                return (
                                    <td key={cell.id}>
                                        {flexRender(
                                            cell.column.columnDef.cell,
                                            cell.getContext()
                                        )}
                                    </td>
                                )
                            })}
                        </tr>
                        {row.getIsExpanded() && renderSubComponent && (
                            <tr>
                                {/* 2nd row is a custom 1 cell row */}
                                <td colSpan={row.getVisibleCells().length}>
                                    {renderSubComponent({row})}
                                </td>
                            </tr>
                        )}
                    </>
                )
            })}
            </tbody>
        </table>
    );
};

export default Table;
