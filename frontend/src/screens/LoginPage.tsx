import React from 'react';
import { Head } from '~/components/shared/Head';
import { useNavigate } from "react-router-dom";
import { Routes } from "~/components/router/Router";

const Login: React.FC = () => {
    const navigate = useNavigate();

    return (
        <>
            <Head title="Login" />
            <h1 className="mb-6">Добро пожаловать!</h1>
            <form>
                <label className="label">
                    <span className="label-text text-secondary">Логин</span>
                </label>
                <input type="text" placeholder="email" className="input input-bordered w-full max-w-xs" />

                <label className="label">
                    <span className="label-text text-secondary">Пароль</span>
                </label>
                <input type="password" placeholder="password" className="input input-bordered w-full max-w-xs" />
                <button className="btn btn-block mt-10 btn-primary" onClick={() => navigate(Routes.ROOT)}>
                    Войти
                </button>
            </form>
        </>
    );
};

export default Login;
