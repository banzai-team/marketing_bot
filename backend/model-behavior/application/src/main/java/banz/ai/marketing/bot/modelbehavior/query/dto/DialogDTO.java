package banz.ai.marketing.bot.modelbehavior.query.dto;

import banz.ai.marketing.bot.modelbehavior.behavior.entity.Dialog;
import banz.ai.marketing.bot.modelbehavior.behavior.entity.ModelRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DialogDTO {

    private long dialogId;
    private Date createdAt;
    private List<Long> requestIds;

    public static DialogDTO project(Dialog dialog) {
        return DialogDTO.builder()
                .dialogId(dialog.getId())
                .createdAt(dialog.getCreatedAt())
                .requestIds(dialog.getModelRequests().stream().map(ModelRequest::getId).collect(Collectors.toList()))
                .build();
    }
}
