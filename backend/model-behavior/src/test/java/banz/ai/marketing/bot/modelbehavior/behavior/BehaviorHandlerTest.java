package banz.ai.marketing.bot.modelbehavior.behavior;

import banz.ai.marketing.bot.commons.ModelBehaviorDTO;
import banz.ai.marketing.bot.commons.ModelRequest;
import banz.ai.marketing.bot.commons.ModelResponse;
import banz.ai.marketing.bot.modelbehavior.behavior.exception.InvalidModelBehaviorException;
import banz.ai.marketing.bot.modelbehavior.behavior.repository.DialogRepository;
import banz.ai.marketing.bot.modelbehavior.behavior.repository.ModelRequestRepository;
import banz.ai.marketing.bot.modelbehavior.behavior.repository.ModelResponseRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class BehaviorHandlerTest {

    DialogRepository dialogRepository =  Mockito.mock(DialogRepository.class);
    ModelRequestRepository modelRequestRepository =  Mockito.mock(ModelRequestRepository.class);
    ModelResponseRepository modelResponseRepository =  Mockito.mock(ModelResponseRepository.class);
    BehaviorHandler underTest = new BehaviorHandler(
        dialogRepository,
            modelRequestRepository,
            modelResponseRepository
    );

    @Test
    void shouldThrowInvalidModelBehaviorExceptionBecauseRequestIsEmpty() {
        var mb = new ModelBehaviorDTO();
        mb.setCapturedAt(new Date());
        mb.setModelResponse(new ModelResponse());

        var exception = assertThrows(InvalidModelBehaviorException.class, () -> underTest.handleBehavior(mb));
        assertTrue(exception.getMessage().toLowerCase().contains("request is empty"));
    }

    @Test
    void shouldThrowInvalidModelBehaviorExceptionBecauseMessageListIsEmpty() {
        var mb = new ModelBehaviorDTO();
        var mreq = new ModelRequest();
        mreq.setDialogId(1L);
        mreq.setMessages(new ArrayList<>());
        mb.setCapturedAt(new Date());
        mb.setModelRequest(mreq);
        mb.setModelResponse(new ModelResponse());

        var exception = assertThrows(InvalidModelBehaviorException.class, () -> underTest.handleBehavior(mb));
        assertTrue(exception.getMessage().toLowerCase().contains("at least one message"));
    }
}