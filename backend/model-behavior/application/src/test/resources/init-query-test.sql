INSERT INTO dialog (dialog_id, created_at)
VALUES (1, CURRENT_TIMESTAMP),
       (2, CURRENT_TIMESTAMP);
INSERT INTO model_request (model_request_id, dialog_id, is_operator, additional_text, performed_at)
VALUES ('c6c0206e-7322-4293-8727-cfafd4edc977', 1, true, 'hello', '2023-11-10 11:30:00 +00:00'),
       ('266169b3-d398-4e26-b67e-1e80fdcbc88e', 1, true, 'jo', '2023-11-09 12:30:00 +00:00'),
       ('fb33957a-6583-429a-b682-78fa7389f155', 2, true, 'b', '2023-11-11 12:00:00 +00:00');
INSERT INTO model_response (model_response_id, dialog_evaluation, offer_purchase, model_request_id, feedback)
VALUES ('0bb9df12-49fe-407e-9325-ea84dcab69f9', 0.5, true, 'c6c0206e-7322-4293-8727-cfafd4edc977', -4),
       ('7996006c-c676-479e-b7b9-4e9953479bf8', 0.2, true, 'fb33957a-6583-429a-b682-78fa7389f155', 2);
INSERT INTO model_request_message (model_request_id, ordinal_number, content)
VALUES ('c6c0206e-7322-4293-8727-cfafd4edc977', 0, 'Hello'),
       ('c6c0206e-7322-4293-8727-cfafd4edc977', 1, 'What is your name?'),
       ('c6c0206e-7322-4293-8727-cfafd4edc977', 2, 'John');
INSERT INTO stop_topic (model_response_id, content)
VALUES ('0bb9df12-49fe-407e-9325-ea84dcab69f9', 'f*ck');
