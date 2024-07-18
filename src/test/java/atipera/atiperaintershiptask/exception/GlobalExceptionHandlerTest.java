package atipera.atiperaintershiptask.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import feign.FeignException;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

class GlobalExceptionHandlerTest {

        @Test
        void testHandleNotFoundError() {
                // Arrange
                GlobalExceptionHandler globalExceptionHandler = new GlobalExceptionHandler();

                // Act
                ResponseEntity<Object> actualHandleNotFoundErrorResult = globalExceptionHandler
                        .handleNotFoundError(new UnauthorizedException("An error occurred"));

                // Assert
                Object body = actualHandleNotFoundErrorResult.getBody();
                assertTrue(body instanceof CommonExceptionTemplate);
                HttpStatusCode statusCode = actualHandleNotFoundErrorResult.getStatusCode();
                assertTrue(statusCode instanceof HttpStatus);
                assertEquals("An error occurred", ((CommonExceptionTemplate) body).getMessage());
                assertEquals(401, ((CommonExceptionTemplate) body).getStatus().intValue());
                assertEquals(404, actualHandleNotFoundErrorResult.getStatusCodeValue());
                assertEquals(HttpStatus.NOT_FOUND, statusCode);
                assertTrue(actualHandleNotFoundErrorResult.hasBody());
                assertTrue(actualHandleNotFoundErrorResult.getHeaders().isEmpty());
        }

        @Test
        void testHandleNotFoundException() {
                // Arrange
                GlobalExceptionHandler globalExceptionHandler = new GlobalExceptionHandler();

                // Act
                ResponseEntity<Object> actualHandleNotFoundExceptionResult = globalExceptionHandler
                        .handleNotFoundException(new NotFoundException("An error occurred"));

                // Assert
                Object body = actualHandleNotFoundExceptionResult.getBody();
                assertTrue(body instanceof CommonExceptionTemplate);
                HttpStatusCode statusCode = actualHandleNotFoundExceptionResult.getStatusCode();
                assertTrue(statusCode instanceof HttpStatus);
                assertEquals("An error occurred", ((CommonExceptionTemplate) body).getMessage());
                assertEquals(404, ((CommonExceptionTemplate) body).getStatus().intValue());
                assertEquals(404, actualHandleNotFoundExceptionResult.getStatusCodeValue());
                assertEquals(HttpStatus.NOT_FOUND, statusCode);
                assertTrue(actualHandleNotFoundExceptionResult.hasBody());
                assertTrue(actualHandleNotFoundExceptionResult.getHeaders().isEmpty());
        }

        @Test
        void testHandleRateLimitException() {
                // Arrange
                GlobalExceptionHandler globalExceptionHandler = new GlobalExceptionHandler();

                // Act
                ResponseEntity<Object> actualHandleRateLimitExceptionResult = globalExceptionHandler
                        .handleRateLimitException(new RateLimitException("An error occurred"));

                // Assert
                Object body = actualHandleRateLimitExceptionResult.getBody();
                assertTrue(body instanceof CommonExceptionTemplate);
                HttpStatusCode statusCode = actualHandleRateLimitExceptionResult.getStatusCode();
                assertTrue(statusCode instanceof HttpStatus);
                assertEquals("An error occurred", ((CommonExceptionTemplate) body).getMessage());
                assertEquals(403, ((CommonExceptionTemplate) body).getStatus().intValue());
                assertEquals(404, actualHandleRateLimitExceptionResult.getStatusCodeValue());
                assertEquals(HttpStatus.NOT_FOUND, statusCode);
                assertTrue(actualHandleRateLimitExceptionResult.hasBody());
                assertTrue(actualHandleRateLimitExceptionResult.getHeaders().isEmpty());
        }
}
