import React from 'react';
import { Outlet, Navigate } from "react-router-dom";
import loginImage from '../images/login_page_image.png'
import { useAuth } from "~/components/contexts/UserContext";
// import Header from '~/components/Header';

const Layout: React.FC = () => {
  const auth = useAuth();

  if (auth.isAuthenticated) {
    return <Navigate to="/"/>
  }

  return (
    // <div className="h-screen w-screen flex flex-row">
    //     <div className="flex-1 h-full flex flex-col justify-center items-center">
    //         <img src="logo.svg" className="pt-6 pl-8 absolute top-0 left-0" />
    //         <Outlet />
    //     </div>
    //     <div className="flex-1 bg-[url('letter.png')] bg-center bg-cover max-sm:hidden" />
    //
    // </div>

    <div className="h-screen w-screen flex items-center overflow-hidden">
      <div
        className="flex flex-col justify-center items-center p-6 min-w-100 h-screen relative basis-full sm:basis-5/12 sm:items-end">
        <img src="/logo.png" className="pt-4 ml-6 absolute top-0 left-0" width={130}/>
        <div className="text-center py-20 px-10 sm: w-90">
          <Outlet/>
        </div>
        <div className="w-96 h-36 bg-primary rounded-full absolute -rotate-45 -bottom-10 -left-36"/>
        <div className="w-96 h-32 bg-primary-content rounded-full absolute -rotate-45 -bottom-10 -left-32"/>
      </div>
      <div
        className="flex-1 sm:flex flex-col justify-center items-center h-screen basis-7/12 bg-gradient-to-r from-base-100 from-45% to-primary-content to-45%">
        <img src={loginImage} alt="Login image" className="m-auto pb-28"/>
      </div>
    </div>
  );
};

export default Layout;
