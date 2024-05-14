package org.example.nominatercercorte.exception;

public class BadAuthenticationException extends RuntimeException {
  public BadAuthenticationException(String message) {
    super(message);
  }
}
