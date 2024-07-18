/**
 * Created by Denys Durbalov
 * Date of creation: 7/17/24
 * Project name: Atipera-Intership-Task
 * email: den.dyrbalov25@gmail.com or s28680@pjwstk.edu.pl
 */

package atipera.atiperaintershiptask.exception;

import feign.FeignException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

        @ExceptionHandler(FeignException.TooManyRequests.class)
        public ResponseEntity<Object> handleTooManyRequestsError(FeignException.TooManyRequests error) {
                return new ResponseEntity<>(getDefaultErrorResponse(error), HttpStatus.NOT_FOUND);
        }

        @ExceptionHandler(FeignException.NotFound.class)
        public ResponseEntity<Object> handleNotFoundError(FeignException.NotFound error) {
                return new ResponseEntity<>(getDefaultErrorResponse(error), HttpStatus.NOT_FOUND);
        }

        @ExceptionHandler(FeignException.InternalServerError.class)
        public ResponseEntity<Object> handleInternalServerError(FeignException.InternalServerError error) {
                return new ResponseEntity<>(getDefaultErrorResponse(error), HttpStatus.NOT_FOUND);
        }

        @ExceptionHandler(FeignException.class)
        public ResponseEntity<Object> handleFeignException(FeignException error) {
                return new ResponseEntity<>(getDefaultErrorResponse(error), HttpStatus.valueOf(error.status()));
        }

        @ExceptionHandler(RateLimitException.class)
        public ResponseEntity<Object> handleRateLimitException(RateLimitException error) {
                return new ResponseEntity<>(getDefaultErrorResponse(error, 403), HttpStatus.NOT_FOUND);
        }

        @ExceptionHandler(UnauthorizedException.class)
        public ResponseEntity<Object> handleNotFoundError(UnauthorizedException error) {
                return new ResponseEntity<>(getDefaultErrorResponse(error, 401), HttpStatus.NOT_FOUND);
        }

        @ExceptionHandler(NotFoundException.class)
        public ResponseEntity<Object> handleNotFoundException(NotFoundException error) {
                return new ResponseEntity<>(getDefaultErrorResponse(error, 404), HttpStatus.NOT_FOUND);
        }

        private static CommonExceptionTemplate getDefaultErrorResponse(FeignException error) {
                return CommonExceptionTemplate.builder()
                        .status(error.status())
                        .message(error.getMessage())
                        .build();
        }

        private static CommonExceptionTemplate getDefaultErrorResponse(Throwable error, int statusCode) {
                return CommonExceptionTemplate.builder()
                        .status(statusCode)
                        .message(error.getMessage())
                        .build();
        }
}
