package com.isamarques.autominer.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class WordPhotoDTO {
    private String photo; // Esta é a URL que você quer
}