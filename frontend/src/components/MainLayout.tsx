import React from 'react';
import { Outlet} from "react-router-dom";
import { Bars3Icon, XMarkIcon } from '@heroicons/react/24/solid'

import Sidebar from "~/components/Sidebar";


const MainLayout: React.FC = () => {
    return (
        <div className="w-full h-full ">
            <div className="drawer lg:drawer-open md:drawer-open">
                <input id="my-drawer-2" type="checkbox" className="drawer-toggle" />
                <div className="drawer-content ">
                    <label htmlFor="my-drawer-2" className=" btn swap btn-square swap-rotate drawer-button lg:hidden md:hidden">
                        <input type="checkbox" />
                            <Bars3Icon className="text-base-content swap-off h-8 w-8" />
                            <XMarkIcon className="text-base-content swap-on fill-current h-8 w-8" />
                    </label>
                    <div className="py-4 px-6 bg-base-100">
                        <Outlet />
                    </div>
                </div>
                <Sidebar />
            </div>
        </div>
    );
};

export default MainLayout;
