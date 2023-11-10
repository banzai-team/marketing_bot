import React from 'react';
import { Head } from '~/components/shared/Head';
import {useNavigate, useParams} from "react-router-dom";
import { Routes } from "~/components/router/Router";

const Login: React.FC = () => {
    const navigate = useNavigate();
    const { id } = useParams();

    return (
        <>
            <Head title={`Conversation #${id}`} />
            <div className="page-title">{`Диалог #${id}`}</div>
        </>
    );
};

export default Login;
