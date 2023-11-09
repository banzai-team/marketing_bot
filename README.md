# Marketing bot

## API
* ### `POST /api/model/evaluate`
    Endpoint used to run the model.
* ### `POST /api/feedback/`
    Allows user to send a feedback for a specific model response which can be used to train model. 
    Currently only negative feedback will be used. 
* ### `GET /dialogs`
    Lists dialogs
* ### `GET /dialogs/:dialogId`
    Brief information about dialog: creation date and collection of requests performed for the dialog.
* ### `GET /dialogs/:dialogId/requests/:requestId`
    Provides complete model request information (dialogId, messages, operator flag, text) and model response information (dialog evaluation, stop topics and offer purchase flag).
* ### `POST /users/invite`
    Invites a user using specified email


## Diagrams

### Microservices

```mermaid
flowchart TD
client(["Client"])
api_gateway["API gateway"]
auth["Auth service"]
auth_db[("Auth DB")]
model_api["Model"]
model_interceptor["Model interceptor"]
model_behavior["Model behavior"]
model_behavior_db[("Model 
behaviour db")]
rmq>RabbitMQ]
notification_service["Notification service"]
user_feedback["User feedback"]
feendback_db[("Feedback db")]


client-->|Request Headers...
Authorization: Basic login:password|api_gateway
api_gateway-.Authorizes request.->auth
auth-->auth_db
api_gateway--"Intercepts and watches 
for model's input/output"-->model_interceptor
api_gateway--"User sends feedback
in order to correct model's behavour"-->user_feedback
model_interceptor---->model_api
user_feedback--"Feedback will be processed asynchronosly"--->rmq
user_feedback-->feendback_db
model_interceptor-."Sends captured behavior".->rmq
rmq-->model_behavior
rmq---->notification_service
model_behavior --> model_behavior_db
model_behavior--"Applies negative user feedback"--->model_api

style client fill:#33F
style model_api fill:#900
style auth fill:#090
style model_interceptor fill:#090
style api_gateway fill:#090
style model_behavior fill:#090
style notification_service fill:#090
style user_feedback fill:#090
style auth_db fill:#555
style model_behavior_db fill:#555
style feendback_db fill:#555
```

### Domain ER diagram 

```mermaid
erDiagram

dialog {
    int dialog_id
    date created_at
    string meta
}

model_request_message {
    int message_id
    int model_request_id
    int ordinal_number
    string content
}

user_feedback {
    int feedback_id
    int user_id
    int model_response_id
    boolean is_correct
}

model_request {
    int request_id
    int dialog_id
    boolean is_operator
    string text
}

model_response {
    int response_id
    int dialog_evaluation
    boolean offer_purchase
    int request_id
}

stop_theme {
    int model_response
    string content
}

model_request ||--|{ model_request_message: contains
model_response ||--|| user_feedback: given
model_request }|--|| dialog: refers
model_request ||--|| model_response: corresponds
model_response ||--o{ stop_theme: "may contain"
```
