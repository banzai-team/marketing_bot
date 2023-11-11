import axios from "axios";
import { config } from '~/config/config';

export type SendMessagePayload = {
  messages: any;
  isOperator: boolean;
  id: number;
  text: string;
};

export type SendFeedbackPayload = {
  userId: string;
  modelResponseId: string;
  isCorrect: boolean;
};

export function sendMessage(payload: SendMessagePayload, token: string) {
  const params = {
    is_operator: payload.isOperator,
    messages: payload.messages,
    id_sequence: payload.id,
    text: payload.text,
  }

  return axios.post(`${config.apiUrl}/api/model/evaluate`, params, {
    headers: {
      'Content-Type': `application/json`,
      // Authorization: `Bearer ${token}`,
      'Authorization': `Basic ${btoa('test:1234')}`,
    },
  });
}

export function getDialogs(token: string) {
  return axios.get(`${config.apiUrl}/api/model/query/model-request`, {
    headers: {
      'Content-Type': `application/json`,
      // Authorization: `Bearer ${token}`,
      'Authorization': `Basic ${btoa('test:1234')}`,

    },
  });
}

export function sendFeedback(payload: SendFeedbackPayload, token: string) {
  return axios.post(`${config.apiUrl}/api/feedback`, payload, {
    headers: {
      'Content-Type': `application/json`,
      // Authorization: `Bearer ${token}`,
      'Authorization': `Basic ${btoa('test:1234')}`,


    },
  });
}

