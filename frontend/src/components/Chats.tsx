import React from 'react';
import {useFormik} from 'formik';

const Chats: React.FC = () => {
    const formik = useFormik<{
        message: string,
    }>({
        initialValues: {
            message: "",
        },
        onSubmit: async (values) => formik.resetForm(),
       
    });
    
    return <div className="h-screen flex items-center justify-center">
        <form onSubmit={formik.handleSubmit} className="bg-base-100 border border-gray-200 shadow py-20 px-10 w-2/4">
            <textarea
                rows="4"
                className="block p-2.5 w-full text-gray-900 bg-gray-50 border border-gray-300 rounded-sm focus:ring-blue-500 focus:border-blue-500"
                placeholder="Сообщение..."
                {...formik.getFieldProps("message")}
            />
            <button type="submit" className="btn btn-block mt-10 btn-primary">
                Отправить
            </button>
        </form>
    </div>;
};

export default Chats;