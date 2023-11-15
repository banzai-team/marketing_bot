INSERT INTO dialog (dialog_id, created_at)
VALUES (1, CURRENT_TIMESTAMP),
       (2, CURRENT_TIMESTAMP);
INSERT INTO model_request (model_request_id, dialog_id, is_operator, additional_text, performed_at)
VALUES (1, 1, true, 'hello', CURRENT_TIMESTAMP),
       (2, 1, true, 'jo', CURRENT_TIMESTAMP),
       (3, 2, true, 'b', CURRENT_TIMESTAMP);
INSERT INTO model_response (model_response_id, dialog_evaluation, offer_purchase, model_request_id, feedback)
VALUES (1, 0.5, true, 1, -4),
       (3, 0.2, true, 3, 2);
INSERT INTO model_request_message (model_request_id, ordinal_number, content)
VALUES (1, 0, 'Hello'),
       (1, 1, 'What is your name?'),
       (1, 2, 'John');
INSERT INTO stop_topic (model_response_id, content)
VALUES (1, 'f*ck');