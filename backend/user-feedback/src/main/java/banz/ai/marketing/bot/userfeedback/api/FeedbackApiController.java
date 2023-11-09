package banz.ai.marketing.bot.userfeedback.api;

import banz.ai.marketing.bot.commons.UserFeedbackDTO;
import jakarta.annotation.Generated;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2023-11-04T21:45:58.716213200+07:00[Asia/Novosibirsk]")
@Controller
@RequestMapping("${openapi.userFeedback.base-path:}")
public class FeedbackApiController implements FeedbackApi {

  @Override
  public ResponseEntity<Void> postModelFeedback(UserFeedbackDTO userFeedbackDTO) {
    return FeedbackApi.super.postModelFeedback(userFeedbackDTO);
  }
}
