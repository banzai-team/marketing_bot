package banz.ai.marketing.bot.modelinterceptor.dto;

import banz.ai.marketing.bot.commons.ModelRequest;
import banz.ai.marketing.bot.commons.ModelResponse;
import lombok.Data;

import java.util.Date;

@Data
public class MqMessage {

    private ModelRequest modelRequest;
    private ModelResponse modelResponse;
    private Date madeAt;
    private String meta;
}
