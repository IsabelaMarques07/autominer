package com.isamarques.autominer.service;

import com.isamarques.autominer.client.DictionaryScraper;
import com.isamarques.autominer.client.LangeekClient;
import com.isamarques.autominer.dto.LangeekResponseDTO;
import com.isamarques.autominer.dto.WordData;
import com.isamarques.autominer.dto.Sentence;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class DictionaryService {

    private final DictionaryScraper scraper;
    private final LangeekClient langeekClient;

    public DictionaryService(DictionaryScraper scraper, LangeekClient langeekClient) {
        this.scraper = scraper;
        this.langeekClient = langeekClient;
    }

    public List<Sentence> searchWordsToMine(List<Sentence> sentences) {
        for (Sentence sentence : sentences) {
            String word = sentence.getWordToMine().toLowerCase().trim();
            log.info("Processando palavra: '{}'", word);

            WordData wordData = fetchFromCambridge(word);

            if (!wordData.definitions().isEmpty()) {
                sentence.setDefinition(wordData.definitions());
            }

            List<String> imageUrls = gatherImages(word, wordData.imageUrl());
            if (!imageUrls.isEmpty()) {
                sentence.setImageMeaningUrl(imageUrls);
            }
        }
        return sentences;
    }

    private WordData fetchFromCambridge(String word) {
        try {
            return scraper.fetchWordData(word);
        } catch (IOException e) {
            log.error("Erro ao buscar dados do Cambridge para '{}': {}", word, e.getMessage());
            return new WordData(List.of(), null);
        }
    }

    private List<String> gatherImages(String word, String cambridgeImageUrl) {
        List<String> imageUrls = new ArrayList<>();

        if (cambridgeImageUrl != null) {
            imageUrls.add(cambridgeImageUrl);
        }

        try {
            List<LangeekResponseDTO> langeekImages = langeekClient.getWordImage(word);
            if (langeekImages != null) {
                langeekImages.stream()
                        .filter(img -> img != null
                                && img.getTranslation() != null
                                && img.getTranslation().getWordPhoto() != null)
                        .map(img -> img.getTranslation().getWordPhoto().getPhoto())
                        .forEach(imageUrls::add);
            }
        } catch (Exception e) {
            log.error("Erro ao buscar imagens do Langeek para '{}': {}", word, e.getMessage());
        }

        return imageUrls;
    }
}
