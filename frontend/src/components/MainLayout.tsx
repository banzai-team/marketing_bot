import React, {useState} from 'react';
import { Outlet} from "react-router-dom";

import Sidebar from "~/components/Sidebar";
import Header from "~/components/Header";


const MainLayout: React.FC = () => {
    const idSidebar = "my-drawer-2";
    const [isDrawerOpen, setDrawerOpen] = useState(false);
    const toggle = () => setDrawerOpen(!isDrawerOpen);

    return (
        <div className="w-full h-full bg-neutral-content overflow-auto">
            <div className="drawer lg:drawer-open md:drawer-open">
                <input
                    id={idSidebar}
                    type="checkbox"
                    className="drawer-toggle"
                    checked={isDrawerOpen}
                    onChange={toggle}
                />
                <div className="drawer-content relative">
                    <Header idSidebar={idSidebar} />
                    <div className="py-4 px-6 relative h-full">
                        <Outlet />
                    </div>
                </div>
                <Sidebar idSidebar={idSidebar} onSelect={toggle} />
            </div>
        </div>
    );
};

export default MainLayout;
