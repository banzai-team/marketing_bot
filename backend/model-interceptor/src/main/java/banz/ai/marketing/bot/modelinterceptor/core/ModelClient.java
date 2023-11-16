package banz.ai.marketing.bot.modelinterceptor.core;

import banz.ai.marketing.bot.commons.ModelRequestDTO;
import banz.ai.marketing.bot.commons.ModelResponseDTO;
import banz.ai.marketing.bot.modelinterceptor.dto.ModelRequest;
import banz.ai.marketing.bot.modelinterceptor.dto.ModelResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "model", url = "${model.url}")
public interface ModelClient {

    @PostMapping("/base_process")
    ModelResponse makeModelRequest(ModelRequest modelRequest);

}
