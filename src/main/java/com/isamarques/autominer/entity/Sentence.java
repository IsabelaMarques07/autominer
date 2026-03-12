package com.isamarques.autominer.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Sentence {

    @JsonProperty
    private String fullSentence;

    @JsonProperty
    private String wordToMine;

    @JsonProperty
    private List<String> definition = new ArrayList<>();

    @JsonProperty
    private String selectedDef;

    @JsonProperty
    private List<String> imageMeaningUrl;
}
