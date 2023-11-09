import React from 'react';
import {FormikValues, useFormik} from 'formik';
import {useMutation} from 'react-query';
import {sendMessage} from '~/domain/api';

const Chats: React.FC = () => {
    const send = useMutation(sendMessage);

    if (send.isLoading) {
        console.log('loading');
    }

    const formik: FormikValues = useFormik<{
        message: string,
        isOperator: boolean,
        id: number,
    }>({
        initialValues: {
            message: "",
            isOperator: true,
            id: 0,
        },
        /*onSubmit: async (values) => formik.resetForm(),*/
        onSubmit: async (values) => {
            send.mutate({ messages: ['Hello'] });
        }
    });
    
    const isOperator = formik.getFieldProps("isOperator");

    return <div className="h-screen flex items-center justify-center">
        <form onSubmit={formik.handleSubmit} className="bg-base-100 border border-gray-200 shadow py-20 px-10 w-4/5 md:w-2/4">
            <textarea
                rows="4"
                className="block p-2.5 mb-2.5 w-full text-gray-900 bg-gray-50 border border-gray-300 rounded-sm focus:ring-blue-500 focus:border-blue-500"
                placeholder="Сообщение..."
                {...formik.getFieldProps("message")}
            />
            <div className="flex items-center mb-4">
                <input type="number" placeholder="id" className="input input-bordered w-28 mr-3 bg-gray-50" {...formik.getFieldProps("id")}/>
                <input type="checkbox" {...isOperator} checked={isOperator.value} className="w-4 h-4 mr-1.5 cursor-pointer"/>
                <label className="font-medium mr-2.5">Operator</label>
            </div>
            <button type="submit" className="btn btn-block mt-7 btn-primary">
                Отправить
            </button>
        </form>
    </div>;
};

export default Chats;
