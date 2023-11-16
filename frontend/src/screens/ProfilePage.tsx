import React from 'react';
import {Head} from '~/components/shared/Head';
import {useAuth} from '~/components/contexts/UserContext';
import Avatar from '~/components/Avatar/Avatar';

const ProfilePage: React.FC = () => {
    const auth = useAuth();
    const username = auth.user?.profile?.preferred_username;
    
    return (
        <>
            <Head title="Профиль" />
            <div className="page-title">Профиль</div>
            <div className="mt-6 card bg-base-100 shadow-xl p-4 flex flex-row">
                <Avatar className="w-28 mr-8" />
                <div className="flex">
                    <div className="font-bold mr-4">Имя:</div>
                    {username}
                </div>
            </div>
        </>
    );
};

export default ProfilePage;