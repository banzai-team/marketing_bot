import React, { lazy, Suspense } from 'react';
import { BrowserRouter, RouteObject, useRoutes } from 'react-router-dom';
import { useAuth } from "react-oidc-context";

import Layout from "~/components/Layout";
import MainLayout from "~/components/MainLayout";
import LoadingCard from "~/components/LoadingCard";

const Loading = () => (
    <div className="w-full h-full flex justify-center items-center">
        <span className="loading loading-dots loading-lg text-primary"/>
    </div>
);

const IndexScreen = lazy(() => import('~/screens/Index'));
const Page404Screen = lazy(() => import('~/screens/404'));
const LoginScreen = lazy(() => import('~/screens/LoginPage'));
const ConversationScreen = lazy(() => import('~/screens/ConversationPage'));
const Chats = lazy(() => import('~/screens/Chats'));
const StatisticScreen = lazy(() => import('~/screens/StatisticPage'));

export const Routes = {
    ROOT: "/",
    LOGIN: "login",
    CONVERSATION: "conversation",
    CREATE: "create",
    STATISTIC: "statistic",
};

export const Router = () => {
    // const auth = useAuth();
    // const [hasTriedSignin, setHasTriedSignin] = React.useState(false);
    //
    // // automatically sign-in
    // React.useEffect(() => {
    //     if (!hasAuthParams() &&
    //       !auth.isAuthenticated && !auth.activeNavigator && !auth.isLoading &&
    //       !hasTriedSignin
    //     ) {
    //         auth.signinRedirect();
    //         setHasTriedSignin(true);
    //     }
    // }, [auth, hasTriedSignin]);
    const auth = useAuth();

    if (auth.isLoading) {
        return <LoadingCard />
    }
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
                    path: `${Routes.CONVERSATION}/${Routes.CREATE}`,
                    element: <Chats />,
                },
                {
                    path: Routes.STATISTIC,
                    element: <StatisticScreen />,
                },
                {
                    path: '*',
                    element: <Page404Screen />,
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