import React from 'react';
import {Column, flexRender, getCoreRowModel, useReactTable} from '@tanstack/react-table'

type TableProps = {
    data: any[],
    columns: Array<any | Column<Record<string, unknown>>>;
    renderSubComponent: (arg: any) => React.ReactNode;
    onRowClick?: (id: string) => void;
}

const Table: React.FC<TableProps> = ({data, columns, renderSubComponent, onRowClick}) => {
    const [expanded, setExpanded] = React.useState({})

    const table = useReactTable<any>({
        // defaultColumn: {
        //     size: 100,
        // },
        data,
        columns,
        state: {
            expanded,
        },
        onExpandedChange: setExpanded,
        getSubRows: row => row.id,
        getCoreRowModel: getCoreRowModel(),
        debugTable: true,
    })


    return (
        <table className="table">
            <thead>
            {table.getHeaderGroups().map(headerGroup => (
                <tr key={headerGroup.id}>
                    {headerGroup.headers.map(header => (
                        <th
                            style={header.getSize ? {
                                width: `${header.getSize()}px` || undefined,
                                maxWidth: `${header.getSize()}px` || undefined,
                                // width: `${header.column.columnDef.meta.width}px` || undefined,
                            } : {}}
                            key={header.id}
                        >
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
                    <React.Fragment key={`row-${row.id}`}>
                        <tr
                            key={row.id}
                            onClick={() => onRowClick ? onRowClick(row.original.id) : null}
                            className={`${onRowClick ? "row-action" : ""}`}
                        >
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
                            <tr className="shadow-inner">
                                {/* 2nd row is a custom 1 cell row */}
                                <td colSpan={row.getVisibleCells().length}>
                                    {renderSubComponent({row})}
                                </td>
                            </tr>
                        )}
                    </React.Fragment>
                )
            })}
            </tbody>
        </table>
    );
};

export default Table;
