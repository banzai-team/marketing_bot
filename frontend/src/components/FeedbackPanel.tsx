import React from 'react';
import {Bars3Icon, XMarkIcon} from '@heroicons/react/24/solid'
import FeedbackButtons from "~/components/FeedbackButtons/FeedbackButtons";

type HeaderProps = {
    idSidebar: string;
    chatIds: Array<string>;
}

const Header: React.FC<HeaderProps> = ({ idSidebar, chatIds }) => {
    return (
        <div
            className="absolute bottom-2 left-20 right-20 bg-secondary-content text-white rounded-full card p-4 flex flex-row justify-center items-center z-10">
            <div className="mr-4">Оценить выделенные диалоги</div>
            <FeedbackButtons chatIds={chatIds}/>
        </div>

    );
};

export default Header;
