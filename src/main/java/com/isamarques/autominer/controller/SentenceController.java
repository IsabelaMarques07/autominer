package com.isamarques.autominer.controller;

import com.isamarques.autominer.dto.Sentence;
import com.isamarques.autominer.service.DictionaryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping
@CrossOrigin(origins = "http://localhost:3000")
public class SentenceController {

    private final DictionaryService dictionaryService;

    public SentenceController(DictionaryService dictionaryService) {
        this.dictionaryService = dictionaryService;
    }

    @PostMapping("/sentence")
    public ResponseEntity<List<Sentence>> search(@RequestBody List<Sentence> sentences) {
        log.info("Recebida requisição /sentence com {} palavra(s)", sentences.size());
        List<Sentence> result = dictionaryService.searchWordsToMine(sentences);
        return ResponseEntity.ok(result);
    }
}
