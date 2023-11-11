import React from 'react';
import { NavLink } from "react-router-dom";
import { useAuth } from "~/components/contexts/UserContext";
import {ChartPieIcon, HomeIcon, ArrowRightOnRectangleIcon} from '@heroicons/react/24/outline'

import {Routes} from "~/components/router/Router";

type SidebarProps = {
    idSidebar: string,
    onSelect: () => void
}

const Sidebar: React.FC<SidebarProps> = ({
    idSidebar,
    onSelect
}) => {

    const auth = useAuth();

    return (
        <div className="drawer-side">
            <label htmlFor={idSidebar} aria-label="close sidebar" className="drawer-overlay" />

            <ul className="menu p-0 py-4 w-48 shadow-lg min-h-full bg-base-100">
                <img src="/logo.png" className="pb-4 ml-6"  width={130} />
                <li>

                    <NavLink
                        to={Routes.ROOT}
                        className="px-6 py-4 font-bold"
                        onClick={onSelect}
                    >
                        <HomeIcon className="h-5 w-5" /> История
                    </NavLink>
                </li>
                <li>
                    <NavLink
                        to={Routes.STATISTIC}
                        className="px-6 py-4 font-bold"
                        onClick={onSelect}
                    >
                        <ChartPieIcon className="h-5 w-5" /> Статистика
                    </NavLink>
                </li>

                <div className="divider" />

                <li>
                    <span
                        onClick={() => void auth.removeUser()}
                        className="px-6 py-4 font-bold"
                    >
                        <ArrowRightOnRectangleIcon className="h-5 w-5" /> Выйти
                    </span>
                </li>
            </ul>
        </div>
    );
};

export default Sidebar;
