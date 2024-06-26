package com.web.ddajait.config.error;

import static org.springframework.http.HttpStatus.FORBIDDEN;

import org.springframework.core.NestedExceptionUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.web.ddajait.config.auth.AuthenticationTypes;
import com.web.ddajait.config.constant.CommonError;
import com.web.ddajait.config.constant.MemberError;
import com.web.ddajait.config.error.custom.DuplicateMemberException;
import com.web.ddajait.config.error.custom.NotFoundMemberException;
import com.web.ddajait.config.jwt.InvalidPasswordException;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;

/*
 * 
 * 
 */
@Slf4j
@RestControllerAdvice
public class ExceptionHandlerAdvice {

        // Exception에 대한 예외처리
        @ExceptionHandler(Exception.class)
        public ResponseEntity handleException(Exception e) {
                // NestedExceptionUtils.getMostSpecificCause() -> 가장 구체적인 원인, 즉 가장 근본 원인을 찾아서 반환
                log.error("[Exception] cause: {} , message: {}", NestedExceptionUtils.getMostSpecificCause(e),
                                e.getMessage());
                ErrorCode errorCode = CommonError.INTERNAL_SERVER_ERROR;
                ErrorResponse errorResponse = ErrorResponse.of(errorCode.getHttpStatus(), errorCode.getCode(),
                                errorCode.getMessage());
                return ResponseEntity.status(errorCode.getHttpStatus()).body(errorResponse);
        }

        @ExceptionHandler(AuthenticationException.class)
        public ResponseEntity authException(InvalidPasswordException e) {
                log.error("[Exception] cause: {} , message: {}", NestedExceptionUtils.getMostSpecificCause(e),
                                e.getMessage());
                ErrorCode errorCode = AuthenticationTypes.BadCredentialsException;
                ErrorResponse errorResponse = ErrorResponse.of(errorCode.getHttpStatus(), errorCode.getCode(),
                                errorCode.getMessage());
                return ResponseEntity.status(errorCode.getHttpStatus()).body(errorResponse);
        }

        // RuntimeException에 대한 예외처리
        @ExceptionHandler(RestApiException.class)
        public ResponseEntity handleSystemException(RestApiException e) {
                log.error("[SystemException] cause: {}, message: {}", NestedExceptionUtils.getMostSpecificCause(e),
                                e.getMessage());
                ErrorCode errorCode = e.getErrorCode();
                ErrorResponse errorResponse = ErrorResponse.of(errorCode.getHttpStatus(), errorCode.getCode(),
                                errorCode.getMessage());
                return ResponseEntity.status(errorCode.getHttpStatus()).body(errorResponse);
        }

        // 메소드가 잘못되었거나 부적합한 인수를 전달했을 경우 -> 필수 파라미터 없을 때
        @ExceptionHandler(IllegalArgumentException.class)
        public ResponseEntity handleIllegalArgumentException(IllegalArgumentException e) {
                log.error("[IlleagalArgumentException] cause: {} , message: {}",
                                NestedExceptionUtils.getMostSpecificCause(e),
                                e.getMessage());
                ErrorCode errorCode = CommonError.ILLEGAL_ARGUMENT_ERROR;
                ErrorResponse errorResponse = ErrorResponse.of(errorCode.getHttpStatus(), errorCode.getCode(),
                                errorCode.getMessage());
                return ResponseEntity.status(errorCode.getHttpStatus()).body(errorResponse);
        }

        // @Valid 유효성 검사에서 예외가 발생했을 때 -> requestbody에 잘못 들어왔을 때
        @ExceptionHandler(MethodArgumentNotValidException.class)
        public ResponseEntity handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
                log.error("[MethodArgumentNotValidException] cause: {}, message: {}",
                                NestedExceptionUtils.getMostSpecificCause(e), e.getMessage());
                ErrorCode errorCode = CommonError.INVALID_ARGUMENT_ERROR;
                ErrorResponse errorResponse = ErrorResponse.of(errorCode.getHttpStatus(),
                                errorCode.getCode(),
                                errorCode.getMessage(),
                                e.getBindingResult());
                return ResponseEntity.status(errorCode.getHttpStatus()).body(errorResponse);
        }

        // @Validated 유효성 검사에서 예외가 발생했을 때
        @ExceptionHandler(ConstraintViolationException.class)
        public ResponseEntity handleMethodArgumentNotValidException(ConstraintViolationException e) {
                log.error("[MethodArgumentNotValidException] cause: {}, message: {}",
                                NestedExceptionUtils.getMostSpecificCause(e), e.getMessage());
                ErrorCode errorCode = CommonError.INVALID_ARGUMENT_ERROR;
                ErrorResponse errorResponse = ErrorResponse.of(errorCode.getHttpStatus(),
                                errorCode.getCode(),
                                errorCode.getMessage());
                return ResponseEntity.status(errorCode.getHttpStatus()).body(errorResponse);
        }

        // 잘못된 포맷 요청 -> Json으로 안보내다던지
        @ExceptionHandler(HttpMessageNotReadableException.class)
        public ResponseEntity handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
                log.error("[HttpMessageNotReadableException] cause: {}, message: {}",
                                NestedExceptionUtils.getMostSpecificCause(e), e.getMessage());
                ErrorCode errorCode = CommonError.INVALID_FORMAT_ERROR;
                ErrorResponse errorResponse = ErrorResponse.of(errorCode.getHttpStatus(), errorCode.getCode(),
                                errorCode.getMessage());
                return ResponseEntity.status(errorCode.getHttpStatus()).body(errorResponse);
        }

        @ExceptionHandler(EntityNotFoundException.class)
        public ResponseEntity handleEntityNotFoundException(EntityNotFoundException e) {
                log.error("[EntityNotFoundException] cause:{}, message: {}",
                                NestedExceptionUtils.getMostSpecificCause(e),
                                e.getMessage());
                ErrorCode errorCode = MemberError.MEMBER_NOT_FOUND_ERROR;
                ErrorResponse errorResponse = ErrorResponse.of(errorCode.getHttpStatus(), errorCode.getCode(),
                                errorCode.getMessage());
                return ResponseEntity.status(errorCode.getHttpStatus()).body(errorResponse);
        }

        // Custom
        // 중복 회원 예외처리
        @ExceptionHandler(DuplicateMemberException.class)
        public ResponseEntity handleHttpClientErrorException(DuplicateMemberException e) {
                log.error("[DuplicateMemberException : Conflict] cause: {}, message: {}",
                                NestedExceptionUtils.getMostSpecificCause(e), e.getMessage());
                ErrorCode errorCode = MemberError.MEMBER_ID_ALREADY_EXISTS_ERROR;
                ErrorResponse errorResponse = ErrorResponse.of(errorCode.getHttpStatus(), errorCode.getCode(),
                                e.getMessage() + " " + errorCode.getMessage());
                return ResponseEntity.status(errorCode.getHttpStatus()).body(errorResponse);
        }

        // 존재하지 않는 회원 처리
        @ResponseStatus(FORBIDDEN)
        @ExceptionHandler({ NotFoundMemberException.class, AccessDeniedException.class })
        public ResponseEntity AccessForbiddenException(NotFoundMemberException e) {
                log.error("[NotFoundMemberException : FORBIDDEN] cause: {}, message: {}",
                                NestedExceptionUtils.getMostSpecificCause(e), e.getMessage());
                ErrorCode errorCode = MemberError.MEMBER_NOT_FOUND_ERROR;
                ErrorResponse errorResponse = ErrorResponse.of(errorCode.getHttpStatus(), errorCode.getCode(),
                                e.getMessage() + " " + errorCode.getMessage());
                return ResponseEntity.status(errorCode.getHttpStatus()).body(errorResponse);
        }
}
