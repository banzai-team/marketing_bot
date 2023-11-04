import React from 'react';
import { NavLink } from "react-router-dom";
import {HeartIcon, HomeIcon, ArrowRightOnRectangleIcon} from '@heroicons/react/24/solid'

import {Routes} from "~/components/router/Router";


const Sidebar: React.FC = () => {
    return (
        <div className="drawer-side">
            <label htmlFor="my-drawer-2" aria-label="close sidebar" className="drawer-overlay" />

            <ul className="menu p-0 py-6 w-60 shadow-lg min-h-full bg-base-100">
                <img src="logo.svg" className="pb-4" />
                <li>

                    <NavLink
                        to={Routes.ROOT}
                        className="px-6 py-4 font-bold"
                    >
                        <HomeIcon className="h-5 w-5" /> Home
                    </NavLink>
                </li>
                <li>
                    <NavLink
                        to="/test"
                        className="px-6 py-4 font-bold"
                    >
                        <HeartIcon className="h-5 w-5" /> Test
                    </NavLink>
                </li>

                <div className="divider"></div>

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
