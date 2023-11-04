package banz.ai.marketing.bot.userfeedback.dto;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonTypeName;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * PostModelFeedbackRequest
 */

@JsonTypeName("postModelFeedback_request")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2023-11-04T21:45:58.716213200+07:00[Asia/Novosibirsk]")
public class PostModelFeedbackRequest {

  private Boolean feedback;

  private Long userId;

  public PostModelFeedbackRequest feedback(Boolean feedback) {
    this.feedback = feedback;
    return this;
  }

  /**
   * Get feedback
   * @return feedback
  */
  
  @Schema(name = "feedback", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("feedback")
  public Boolean getFeedback() {
    return feedback;
  }

  public void setFeedback(Boolean feedback) {
    this.feedback = feedback;
  }

  public PostModelFeedbackRequest userId(Long userId) {
    this.userId = userId;
    return this;
  }

  /**
   * Get userId
   * @return userId
  */
  
  @Schema(name = "userId", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("userId")
  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PostModelFeedbackRequest postModelFeedbackRequest = (PostModelFeedbackRequest) o;
    return Objects.equals(this.feedback, postModelFeedbackRequest.feedback) &&
        Objects.equals(this.userId, postModelFeedbackRequest.userId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(feedback, userId);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PostModelFeedbackRequest {\n");
    sb.append("    feedback: ").append(toIndentedString(feedback)).append("\n");
    sb.append("    userId: ").append(toIndentedString(userId)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

