package banz.ai.marketing.bot.commons;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiError {

    private int code;
    private String path;
    private Date timestamp;
    private String message;
}
