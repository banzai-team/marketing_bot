import React from 'react';
import { NavLink } from "react-router-dom";
import { useAuth } from "~/components/contexts/UserContext";
import {ChartPieIcon, HomeIcon, ArrowRightOnRectangleIcon, UsersIcon, UserIcon} from '@heroicons/react/24/outline'

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
    const username = auth.user?.profile?.preferred_username;

    return (
        <div className="drawer-side">
            <label htmlFor={idSidebar} aria-label="close sidebar" className="drawer-overlay" />

            <ul className="menu p-0 py-4 w-48 shadow-lg min-h-full bg-base-100 justify-between">
                <img src="/logo.png" className="ml-6 absolute left-0 top-4" width={130} />
                <li className="pt-10">
                    <NavLink
                        to={Routes.ROOT}
                        className="px-6 py-4 font-bold"
                        onClick={onSelect}
                    >
                        <HomeIcon className="h-5 w-5" /> История
                    </NavLink>
                    <NavLink
                        to={Routes.STATISTIC}
                        className="px-6 py-4 font-bold"
                        onClick={onSelect}
                    >
                        <ChartPieIcon className="h-5 w-5" /> Статистика
                    </NavLink>
                    <a
                        href=""
                        target="_blank"
                        className="px-6 py-4 font-bold"
                    >
                        <UsersIcon className="h-5 w-5" /> Пользователи
                    </a>
                </li>
                
                <li>
                    <NavLink
                        to={Routes.PROFILE}
                        className="px-6 py-4 font-bold avatar"
                        onClick={onSelect}
                    >
                        <div className="w-5 rounded-full ring ring-offset-base-100 ring-base-300 ring-offset-2 mr-2">
                            <UserIcon />
                        </div>
                        {username}
                    </NavLink>
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
