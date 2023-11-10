import React, {lazy, Suspense} from 'react';
import {BrowserRouter, Outlet, RouteObject, useRoutes} from 'react-router-dom';

import Layout from "~/components/Layout";
import MainLayout from "~/components/MainLayout";
import Chats from '~/components/Chats';

const Loading = () => (
    <div className="w-full h-full flex justify-center items-center">
        <span className="loading loading-dots loading-lg text-primary"/>
    </div>
);

const IndexScreen = lazy(() => import('~/screens/Index'));
const Page404Screen = lazy(() => import('~/screens/404'));
const LoginScreen = lazy(() => import('~/screens/LoginPage'));
const ConversationScreen = lazy(() => import('~/screens/ConversationPage'));

export const Routes = {
    ROOT: "/",
    LOGIN: "login",
    CHATS: "chats",
    CONVERSATION: "conversation",
};

export const Router = () => {
    return (
        <BrowserRouter>
            <InnerRouter />
        </BrowserRouter>
    );
};

const InnerRouter = () => {
    const routes: RouteObject[] = [
        {
            element: <Layout />,
            children: [
                {
                    path: Routes.LOGIN,
                    element: <LoginScreen />,
                },
            ],
        },
        {
            path: '/',
            element: <MainLayout />,
            children: [
                {
                    index: true,
                    element: <IndexScreen />,
                },
                {
                    path: Routes.LOGIN,
                    element: <LoginScreen />,
                },
                {
                    path: `${Routes.CONVERSATION}/:id`,
                    element: <ConversationScreen />,
                },
                {
                    path: '*',
                    element: <Page404Screen />,
                },
                {
                    path: 'chats',
                    element: <Chats />,
                },
            ],
        },
    ];
    const element = useRoutes(routes);
    return (
        <Suspense fallback={<Loading />}>{element}</Suspense>
    );
};


//             element: <div className="w-full h-full flex justify-center items-center"><Outlet /></div>,