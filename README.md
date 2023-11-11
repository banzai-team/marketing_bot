# Summary

Чтобы запустить данное решение локально, необходимо вызвать следующие команду
### docker-compose
`make up`

### kubernetes
TODO: need to release
```
helm repo add gazprom-mrk https://gazprom-charts.obs.ru-moscow-1.hc.sbercloud.ru
helm install gazprom-mrkt gazprom-mrkt/gazprom-mrkt
```

# Компоненты системы

- backend:
  - Сервис, отвечающий за статистику вызова и фидбэк модели [model-behavior](backend/model-behavior)
  - Сервис, отвечающий за вызов модели [model-interceptor](backend/model-interceptor)
  - Единый gateway осуществлящий аутентификацию [api-gateway](backend/api-gateway)
- ml:
  - Вся ml модель [ml-service](ml)
- frontend:
  - Бэкоффис [frontend](frontend)

# Marketing bot

## API
* ### `POST /api/model/evaluate`
    Endpoint used to run the model.
* ### `POST /api/feedback/`
    Allows user to send a feedback for a specific model response which can be used to train model. 
    Currently only negative feedback will be used. 
* ### `GET /api/model/query/model-request?page=:pageNum&size=:pageSize&dialogId=:dialogId`
    Lists all requests with responses. :dialogId may be used to select request for specific dialog.
* ### `GET /api/model/query/model-request/:requestId`
    Provides complete model request information (dialogId, messages, operator flag, text) and model response information (dialog evaluation, stop topics and offer purchase flag).

## Diagrams

### Microservices

```mermaid
%%{
    init: {
    'theme': 'base',
    'themeVariables': {
      'primaryColor': '#BB2528',
      'primaryTextColor': '#fff',
      'primaryBorderColor': '#7C0000',
      'lineColor': '#F8B229',
      'secondaryColor': '#006100',
      'tertiaryColor': '#fff',
      'background': '#fff'
    }
  }
}%%
flowchart TD
client(["Client"])
api_gateway["API gateway"]
auth["Keycloak"]
model_api["Model"]
model_interceptor["Model interceptor"]
model_behavior["Model behavior"]
model_behavior_db[("Model 
behaviour db")]
rmq>RabbitMQ]


client-->|Request Headers...
Authorization: Basic login:password|api_gateway
api_gateway-.Authorizes request.->auth
api_gateway--"Intercepts and watches 
for model's input/output"-->model_interceptor
api_gateway-."User sends feedback
in order to correct model's behavour".->rmq
model_interceptor<-."Captures request and response".->model_api
model_interceptor-."Sends captured behavior".->rmq
rmq-->model_behavior
model_behavior --> model_behavior_db
model_behavior--"Applies negative user feedback"--->model_api

style client fill:#33F
style model_api fill:#900
style auth fill:#090
style model_interceptor fill:#090
style api_gateway fill:#090
style model_behavior fill:#090
style model_behavior_db fill:#555
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
