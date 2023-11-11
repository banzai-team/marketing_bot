import React from 'react';
import { FormikValues, useFormik } from 'formik';
import { useMutation } from 'react-query';
import { sendMessage, SendMessagePayload } from '~/domain/api';
import { Head } from '~/components/shared/Head';
import * as Yup from "yup";
import { useAuth } from "react-oidc-context";
import { useNavigate } from "react-router-dom";
import { Routes } from "~/components/router/Router";

const validationMessage = "Обязательное поле";

const validationSchema = Yup.object({
  message: Yup.string().required(validationMessage),
  text: Yup.string().required(validationMessage),
});

const Chats: React.FC = () => {
  const auth = useAuth();
  const navigate = useNavigate();

  const send = useMutation((payload: SendMessagePayload) => sendMessage(payload, auth.user?.access_token!));

  const formik: FormikValues = useFormik<{
    message: string,
    isOperator: boolean,
    id: number,
    text: string;
  }>({
    initialValues: {
      message: "",
      isOperator: true,
      id: 0,
      text: ""
    },
    // onSubmit: async (values) => formik.resetForm(),
    onSubmit: async (values) => {
      await send.mutateAsync({ messages: values.message.split("\n"), isOperator: values.isOperator, id: values.id, text: values.text }, {
        onSuccess: () => {
          navigate("/");
        }
      });
    },
    validationSchema,
  });

  const isOperator = formik.getFieldProps("isOperator");

  const messageError = (formik.touched?.message && formik.errors?.message) ? "textarea-error" : "";
  const textError = (formik.touched?.text && formik.errors?.text) ? "input-error" : "";

  return <>
    <Head title="Создание диалога"/>
    <div className="page-title">Создание диалога</div>
    <div className="flex items-center justify-center mt-6">
      <form onSubmit={formik.handleSubmit} className="card bg-base-100 shadow-xl overflow-hidden p-4 w-4/5 md:w-4/6">

        <div className="form-control  mb-4">
          <label className="label">
            <span>Диалог для оценки</span>
            {messageError && <span className="text-error label-text-alt">{formik.errors.message}</span>}
          </label>
          <textarea
            rows="4"
            className={`textarea textarea-bordered h-60 block p-2.5 w-full text-gray-900 bg-gray-50 border rounded-sm focus:ring-blue-500 focus:border-blue-500 ${messageError}`}
            placeholder="Введите сообщения"
            {...formik.getFieldProps("message")}
          />
          <label className="label justify-start items-start pb-0">
            <div className="label-text-alt mr-2 ">
              Сообщения вводятся c указанием направления (in - пользователь; out - помощник банка)
            </div>
          </label>
          <label className="label justify-start items-start">
            <div className="label-text-alt mr-2 ">
              Пример:
            </div>
            <div className="label-text-alt ">
              Где информация о вкладе 13% <span className="text-primary font-bold">in</span>
              <br />
              Добрый день! Я – виртуальный помощник ... <span className="text-primary font-bold">out</span>
            </div>
          </label>
        </div>
        <div className="form-control">
          <label className="label">
            <span>Текст</span>
            {textError && <span className="text-error label-text-alt">{formik.errors.message}</span>}
          </label>
          <input
            type="text"
            className={`input input-bordered w-full bg-gray-50 mb-4 ${textError}`}
            placeholder="Введите текст"
            {...formik.getFieldProps("text")}
          />
        </div>
        <div className="flex items-center mb-4">
          <div className="form-control">
            <label className="label">
              Число
            </label>
            <input type="number" placeholder="id"
                   className="input input-bordered input-sm w-28 mr-3 bg-gray-50" {...formik.getFieldProps("id")}/>
          </div>
          <input type="checkbox" {...isOperator} checked={isOperator.value}
                 className="mt-6 w-4 h-4 mr-1.5 cursor-pointer"/>
          <label className="font-medium mr-2.5 mt-6">Operator</label>
        </div>
        <button type="submit" className={`btn btn-block mt-7 btn-primary`} disabled={formik.isSubmitting}>
          {!formik.isSubmitting ? "Отправить" : <span className="loading loading-spinner"></span>}
        </button>
      </form>
    </div>
  </>;
};

export default Chats;
