package com.isamarques.autominer.controller;

import com.isamarques.autominer.entity.Sentence;
import com.isamarques.autominer.service.DictionaryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping()
@CrossOrigin(origins = "http://localhost:3000")
public class SentenceController {

    @Autowired
    private DictionaryService dictionaryService;

    @PostMapping("/sentence")
    public Object search(@RequestBody List<Sentence> sentences){
        var definitions = dictionaryService.searchWordsToMine(sentences);
        return ResponseEntity.ok(definitions);
    }

    @PostMapping("/generate")
    public Object generate(@RequestBody List<Sentence> sentences){
        return sentences;
    }


}
