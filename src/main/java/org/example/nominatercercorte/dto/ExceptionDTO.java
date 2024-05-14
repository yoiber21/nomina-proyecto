package org.example.nominatercercorte.dto;


public record ExceptionDTO(
    String message,
    String className,
    Integer status
) {
}