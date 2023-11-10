import axios from "axios";
import {config} from '~/config/config';

export type SendMessagePayload = {
  messages: any;
  isOperator: boolean;
  id: number;
  text: string;
};

export type SendFeedbackPayload = {
  id: string;
  feedback: boolean;
};

export function sendMessage(payload: SendMessagePayload) {
  const params = {
    is_operator: payload.isOperator,
    messages: payload.messages,
    id_sequence: payload.id,
    text: payload.text,
  }

  return axios.post(`${config.apiUrl}/api/model/evaluate`, params, {
    headers: {
      'Content-Type': `application/json`,
      'Authorization': `Basic ${btoa('user:12345')}`,
    },
  });
}

export function getDialogs() {
  return axios.get(`${config.apiUrl}/dialogs`, {
    headers: {
      'Content-Type': `application/json`,
      'Authorization': `Basic ${btoa('user:12345')}`,
    },
  });
}

export function sendFeedback(payload: SendFeedbackPayload) {
  const params = {
    id: payload.id,
    feedback: payload.feedback,
  }

  return axios.post(`${config.apiUrl}/api/feedback`, params, {
    headers: {
      'Content-Type': `application/json`,
      'Authorization': `Basic ${btoa('user:12345')}`,
    },
  });
}

