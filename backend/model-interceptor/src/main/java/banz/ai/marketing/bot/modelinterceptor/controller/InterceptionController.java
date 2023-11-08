package banz.ai.marketing.bot.modelinterceptor.controller;

import banz.ai.marketing.bot.commons.ApiError;
import banz.ai.marketing.bot.commons.ModelRequestDTO;
import banz.ai.marketing.bot.commons.ModelResponseDTO;
import banz.ai.marketing.bot.modelinterceptor.core.ModelCallWrapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("api/model")
@RequiredArgsConstructor
public class InterceptionController {

    private final ModelCallWrapper wrapper;

    @PostMapping("evaluate")
    public ResponseEntity<ModelResponseDTO> doInterceptModelCall(@RequestBody ModelRequestDTO modelRequest) {
        return ResponseEntity.ok().body(wrapper.wrap(modelRequest));
    }

    @ExceptionHandler()
    private ResponseEntity<?> handleException(HttpServletRequest request, Exception exception) {
        return ResponseEntity.internalServerError().body(new ApiError(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                request.getRequestURI(),
                new Date(),
                exception.getMessage()));
    }
}
