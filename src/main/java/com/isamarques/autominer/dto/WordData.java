package com.isamarques.autominer.dto;

import java.util.List;

public record WordData(List<String> definitions, String imageUrl) {

    public WordData {
        definitions = (definitions != null) ? definitions : List.of();
    }
}
