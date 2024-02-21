package com.example.kbackend.controller

import com.example.kbackend.model.exception.AbstractApiException
import com.example.kbackend.model.exception.ApiException
import com.example.kbackend.model.exception.NotFoundException
import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import kotlin.math.log

@ControllerAdvice
class ExceptionResolver {
    @ExceptionHandler(Exception::class)
    fun resolveException(
        request: HttpServletRequest,
        response: HttpServletResponse,
        exception: Exception,
    ) {
        println("*****************************************************")
        println(exception)
        println("*****************************************************")
        val exceptionToResponse = toApiError(exception)
        val objectMapper = ObjectMapper().findAndRegisterModules()
        response.contentType = MediaType.APPLICATION_JSON.toString()
        response.status = exceptionToResponse.status.value()
        response.characterEncoding = "UTF-8"
        response.writer.write(objectMapper.writeValueAsString(exceptionToResponse))
    }

    private fun toApiError(exception: Exception): AbstractApiException {
        if (exception is AbstractApiException) return exception
        if (exception is MethodArgumentNotValidException) {
            var message = ""
            for (error in exception.fieldErrors) {
                message += "${error.field}: ${error.defaultMessage};\n"
            }
            return ApiException(message = message, status = HttpStatus.BAD_REQUEST)
        }
        return ApiException(message = exception.message.orEmpty())
    }

}