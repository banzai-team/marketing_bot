package banz.ai.marketing.bot.commons;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ModelRequestDTO {

    private String uuid;
    private long dialogId;
    private List<String> messages;
    private boolean isOperator;
    private String text;
}
