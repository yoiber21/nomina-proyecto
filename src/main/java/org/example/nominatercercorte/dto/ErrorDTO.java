package org.example.nominatercercorte.dto;

import java.util.List;

public record ErrorDTO (
    String explanation,
    List<String> messages
){
}
