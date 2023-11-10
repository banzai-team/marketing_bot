INSERT INTO dialog (dialog_id, created_at)
VALUES (1, CURRENT_TIMESTAMP),
       (2, CURRENT_TIMESTAMP);
INSERT INTO model_request (model_request_id, dialog_id, is_operator, additional_text)
VALUES (1, 1, true, 'hello'),
       (2, 1, true, 'jo'),
       (3, 2, true, 'b');
INSERT INTO model_response (model_response_id, dialog_evaluation, offer_purchase, model_request_id)
VALUES (1, 0.5, true, 1);
INSERT INTO model_request_message (model_request_id, ordinal_number, content)
VALUES (1, 0, 'Hello'),
       (1, 1, 'What is your name?'),
       (1, 2, 'John');
INSERT INTO stop_topic (model_response_id, content)
VALUES (1, 'f*ck');
INSERT INTO user_feedback (user_id, model_response_id, is_correct, created_at, applied_at, status)
VALUES (99, 1, false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'PENDING'),
       (101, 1, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'APPLIED');
