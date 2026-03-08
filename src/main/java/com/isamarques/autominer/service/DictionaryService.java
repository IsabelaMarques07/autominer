package com.isamarques.autominer.service;

import com.isamarques.autominer.client.CambridgeScraper;
import com.isamarques.autominer.entity.Sentence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DictionaryService {
    private static final Logger logger = LoggerFactory.getLogger(DictionaryService.class);
    private final CambridgeScraper scraper;

    public DictionaryService(CambridgeScraper scraper){
        this.scraper = scraper;
    }

    public Object searchWordsToMine(List<Sentence> sentencesList){
        for(Sentence sentence : sentencesList){
            String word = sentence.getWordToMine();
            logger.info("Processando palavra: {}", word);
            List<String> definitions = lookupWord(word);
            sentence.setDefinition(definitions);
        }
        return sentencesList;
    }

    public List<String> lookupWord(String word) {
        try{
            logger.info("Iniciando busca para a palavra: {}", word);
            List<String> definition = scraper.fetchDefinition(word.toLowerCase().trim());

            if(definition == null || definition.isEmpty()){
                logger.warn("Definição não encontrada para a palavra: {}", word);
                return null;
            }
            return definition;
        }catch (Exception e){
            logger.error("Erro ao realizar scraping da palavra {}: {}", word, e.getMessage());
            return null;
        }
    }
}
