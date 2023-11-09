import axios from "axios";
import {config} from '~/config/config';

export type SendMessagePayload = {
  messages: any;
};

export function sendMessage(payload: SendMessagePayload) {
  const form = new FormData();

  form.append("is_operator", 'true');
  form.append("messages", payload.messages);
  form.append("id_sequence", '88');
  form.append("files", 'Test text');

  return axios.post(`${config.apiUrl}/api/model/evaluate-application/json`, form, {
    headers: {
      'Content-Type': `multipart/form-data;`,
    },
  });
}
