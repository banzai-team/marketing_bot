INSERT INTO dialog (dialog_id, created_at)
VALUES (1, CURRENT_TIMESTAMP),
       (2, CURRENT_TIMESTAMP);
INSERT INTO model_request (model_request_id, dialog_id, is_operator, additional_text)
VALUES (1, 1, true, 'hello'),
       (2, 1, true, 'jo'),
       (3, 2, true, 'b')
       ;
INSERT INTO model_response (dialog_evaluation, offer_purchase, model_request_id)
VALUES (0.5, true, 1);
-- INSERT INTO model_request_message
-- VALUES;
-- INSERT INTO stop_topic
-- VALUES;
-- INSERT INTO user_feedback
-- VALUES;
