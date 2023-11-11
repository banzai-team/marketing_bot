import React from 'react';
import {Cell, Legend, Pie, PieChart, ResponsiveContainer} from "recharts";

type CustomPieChartProps = {
    data: Array<{name: string, value: number, color?: string}>;
}

const CustomPieChart: React.FC<CustomPieChartProps> = ({data}) => {
    const COLORS = ['#0088FE', '#00C49F', '#FFBB28', '#FF8042'];
    const RADIAN = Math.PI / 180;

    const RenderLegend = (props) => {
        const { payload } = props;

        return (
            <ul>
                {
                    payload.map((entry, index) => {
                        const color = entry.color ? entry.color : COLORS[index % COLORS.length];
                        return (
                            <li key={`item-${index}`}>
                                <div className="h-3 w-3 mr-2 inline-block" style={{ background: color}} />
                                <span className="text-sm inline-block" style={{ color: color}} >
                                    {entry.value}
                                </span>
                            </li>
                        );
                    })
                }
            </ul>
        );
    }

    const renderCustomizedLabel = ({ cx, cy, midAngle, innerRadius, outerRadius, percent, index }) => {
        const radius = innerRadius + (outerRadius - innerRadius) * 0.5;
        const x = cx + radius * Math.cos(-midAngle * RADIAN);
        const y = cy + radius * Math.sin(-midAngle * RADIAN);

        if (percent === 0) return "";

        return (
            <text x={x} y={y} fill="white" textAnchor={x > cx ? 'start' : 'end'} className="text-sm" dominantBaseline="central">
                {`${(percent * 100).toFixed(0)}%`}
            </text>
        );
    };
    return (
        <ResponsiveContainer width="100%" height="100%">
            <PieChart width={400} height={430}>
                <Legend content={<RenderLegend />} verticalAlign="middle" align="left" width={100} />
                {/*<Legend verticalAlign="middle" align="left" height={36} />*/}
                <Pie
                    data={data}
                    cx="50%"
                    cy="50%"
                    labelLine={false}
                    label={renderCustomizedLabel}
                    outerRadius={120}
                    innerRadius={60}
                    fill="#8884d8"
                    dataKey="value"

                >
                    {data.map((entry, index) => (
                        <Cell
                            strokeWidth={2}
                            key={`cell-${index}`}
                            fill={entry.color ? entry.color : COLORS[index % COLORS.length]}
                            name={entry.name}
                        />
                    ))}
                </Pie>
            </PieChart>
        </ResponsiveContainer>
    );
};

export default CustomPieChart;
