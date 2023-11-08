package banz.ai.marketing.bot.modelbehavior.controller;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/dialogs")
@RequiredArgsConstructor
public class DialogQueryController {

    @GetMapping(params = { "page", "size"})
    public ResponseEntity<Page<?>> listDialogs(@RequestParam("page") int page,
                                               @RequestParam("size") int size) {
        throw new NotImplementedException();
    }

    @GetMapping("{dialogId}")
    public ResponseEntity<?> getDialogById(@PathVariable long dialogId) {

        throw new NotImplementedException();
    }


    @GetMapping("{dialogId}/requests/{requestId}")
    public ResponseEntity<?> getDialogById(@PathVariable("dialogId") long dialogId,
                                           @PathVariable("requestId") long requestId) {

        throw new NotImplementedException();
    }
}
