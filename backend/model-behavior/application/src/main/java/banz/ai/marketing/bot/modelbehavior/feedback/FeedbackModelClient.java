package banz.ai.marketing.bot.modelbehavior.feedback;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "model", url = "${model.url}")
public interface FeedbackModelClient {

    @PostMapping("/invoke-relearning")
    public ResponseEntity<?> invokeModelRelearning(String doIt);
}
