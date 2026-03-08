package com.isamarques.autominer.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class PayloadData {
    @JsonProperty
    List<Sentence> sentences;
}
