package com.cwpark.petmap.petmap.data.response;

import com.cwpark.petmap.petmap.data.enums.ErrorCode;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.validation.BindingResult;

@Getter
@AllArgsConstructor
@Builder
public class ErrorResponse<T> {

  private int status;

  private String divisionCode;

  private String resultMsg;

  private List<FieldError> errors;

  private String reason;

  protected ErrorResponse(final ErrorCode code) {
    this.resultMsg = code.getMessage();
    this.status = code.getStatus();
    this.divisionCode = code.getDivisionCode();
    this.errors = new ArrayList<>();
  }

  protected ErrorResponse(final ErrorCode code, final String reason) {
    this.resultMsg = code.getMessage();
    this.status = code.getStatus();
    this.divisionCode = code.getDivisionCode();
    this.reason = reason;
  }

  protected ErrorResponse(final ErrorCode code, final List<FieldError> errors) {
    this.resultMsg = code.getMessage();
    this.status = code.getStatus();
    this.errors = errors;
    this.divisionCode = code.getDivisionCode();
  }

  public static ErrorResponse of(final ErrorCode code, final BindingResult bindingResult) {
    return new ErrorResponse(code, FieldError.of(bindingResult));
  }

  public static ErrorResponse of(final ErrorCode code) {
    return new ErrorResponse(code);
  }

  public static ErrorResponse of(final ErrorCode code, final String reason) {
    return new ErrorResponse(code, reason);
  }

  @Getter
  @AllArgsConstructor
  @Builder
  public static class FieldError {
    private final String field;
    private final String value;
    private final String reason;

    public static List<FieldError> of(final String field, final String value, final String reason) {
      List<FieldError> fieldErrors = new ArrayList<>();
      fieldErrors.add(new FieldError(field, value, reason));
      return fieldErrors;
    }

    private static List<FieldError> of(final BindingResult bindingResult) {
      final List<org.springframework.validation.FieldError> fieldErrors = bindingResult.getFieldErrors();
      return fieldErrors.stream()
          .map(error -> new FieldError(
              error.getField(),
              error.getRejectedValue() == null ? "" : error.getRejectedValue().toString(),
              error.getDefaultMessage()))
          .collect(Collectors.toList());
    }
  }

}
