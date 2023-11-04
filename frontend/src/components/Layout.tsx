import React from 'react';
import {Outlet} from "react-router-dom";
// import Header from '~/components/Header';

const Layout: React.FC = () => {
    return (
        // <div className="h-screen w-screen flex flex-row">
        //     <div className="flex-1 h-full flex flex-col justify-center items-center">
        //         <img src="logo.svg" className="pt-6 pl-8 absolute top-0 left-0" />
        //         <Outlet />
        //     </div>
        //     <div className="flex-1 bg-[url('letter.png')] bg-center bg-cover max-sm:hidden" />
        //
        // </div>

        <div className="h-screen w-screen flex flex-col justify-center items-center bg-gradient-to-b from-primary to-primary-content">
            <div className="h-screen w-screen absolute top-0 left-0 bg-[url('pattern.svg')] opacity-70 bg-cover" />
            <img src="logo.svg" className="pt-6 pl-8 absolute top-0 left-0" />
            <div className="flex flex-1 flex-col justify-center items-center p-6 min-w-100">
                <div className="card bg-base-100 shadow-xl py-20 px-10 sm: w-90">
                    <Outlet />
                </div>
            </div>
        </div>
    );
};

export default Layout;
