import React from 'react';
import {Bars3Icon, XMarkIcon} from '@heroicons/react/24/solid'

type HeaderProps = {
    idSidebar: string
}

const Header: React.FC<HeaderProps> = ({ idSidebar }) => {
    return (
        <div className="navbar bg-base-100  lg:hidden md:hidden">
            <div className="flex-none">
                <label htmlFor={idSidebar} className=" btn btn-ghost swap btn-square swap-rotate drawer-button">
                    <input type="checkbox" />
                    <Bars3Icon className="text-base-content swap-off h-6 w-6" />
                    <XMarkIcon className="text-base-content swap-on fill-current h-6 w-6" />
                </label>
            </div>
            <div className="flex-1">
                <img src="/logo.svg" className="ml-6" width={150} />
            </div>
        </div>

    );
};

export default Header;
