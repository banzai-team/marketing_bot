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
 * ApiError
 */

@JsonTypeName("apiError")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2023-11-04T21:45:58.716213200+07:00[Asia/Novosibirsk]")
public class ApiError {

  private String error;

  private Integer code;

  public ApiError error(String error) {
    this.error = error;
    return this;
  }

  /**
   * A brief description of the error
   * @return error
  */
  
  @Schema(name = "error", description = "A brief description of the error", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("error")
  public String getError() {
    return error;
  }

  public void setError(String error) {
    this.error = error;
  }

  public ApiError code(Integer code) {
    this.code = code;
    return this;
  }

  /**
   * Error code indicating the specific error
   * @return code
  */
  
  @Schema(name = "code", description = "Error code indicating the specific error", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("code")
  public Integer getCode() {
    return code;
  }

  public void setCode(Integer code) {
    this.code = code;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ApiError apiError = (ApiError) o;
    return Objects.equals(this.error, apiError.error) &&
        Objects.equals(this.code, apiError.code);
  }

  @Override
  public int hashCode() {
    return Objects.hash(error, code);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ApiError {\n");
    sb.append("    error: ").append(toIndentedString(error)).append("\n");
    sb.append("    code: ").append(toIndentedString(code)).append("\n");
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

