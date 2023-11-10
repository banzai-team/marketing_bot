import React from 'react';
import { NavLink } from "react-router-dom";
import {ChatBubbleLeftRightIcon, HomeIcon, ArrowRightOnRectangleIcon} from '@heroicons/react/24/outline'

import {Routes} from "~/components/router/Router";

type SidebarProps = {
    idSidebar: string,
    onSelect: () => void
}

const Sidebar: React.FC<SidebarProps> = ({
    idSidebar,
    onSelect
}) => {
    return (
        <div className="drawer-side">
            <label htmlFor={idSidebar} aria-label="close sidebar" className="drawer-overlay" />

            <ul className="menu p-0 py-6 w-48 shadow-lg min-h-full bg-base-100">
                <img src="logo.svg" className="pb-4 ml-6"  width={150} />
                <li>

                    <NavLink
                        to={Routes.ROOT}
                        className="px-6 py-4 font-bold"
                        onClick={onSelect}
                    >
                        <HomeIcon className="h-5 w-5" /> Home
                    </NavLink>
                </li>
                <li>
                    <NavLink
                        to={Routes.CHATS}
                        className="px-6 py-4 font-bold"
                        onClick={onSelect}
                    >
                        <ChatBubbleLeftRightIcon className="h-5 w-5" /> Chats
                    </NavLink>
                </li>

                <div className="divider" />

                <li>
                    <NavLink
                        to="/login"
                        className="px-6 py-4 font-bold"
                    >
                        <ArrowRightOnRectangleIcon className="h-5 w-5" /> Logout
                    </NavLink>
                </li>
            </ul>
        </div>
    );
};

export default Sidebar;
