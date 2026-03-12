package com.isamarques.autominer.service;

import com.isamarques.autominer.client.CambridgeScraper;
import com.isamarques.autominer.client.LangeekClient;
import com.isamarques.autominer.dto.LangeekResponseDTO;
import com.isamarques.autominer.entity.Sentence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DictionaryService {
    private static final Logger logger = LoggerFactory.getLogger(DictionaryService.class);
    private final CambridgeScraper scraper;
    private final LangeekClient langeekClient;

    public DictionaryService(CambridgeScraper scraper, LangeekClient langeekClient){
        this.scraper = scraper;
        this.langeekClient = langeekClient;
    }

    public Object searchWordsToMine(List<Sentence> sentencesList){
        for(Sentence sentence : sentencesList){
            String word = sentence.getWordToMine();
            logger.info("Processando palavra: {}", word);
            List<String> definitions = lookupWord(word);
            List<String> imageUrls = lookupWordImages(word.toLowerCase().trim());
            if(imageUrls != null && !imageUrls.isEmpty()){
                sentence.setImageMeaningUrl(imageUrls);
            }
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

    public List<String> lookupWordImages(String word){
        try {
            logger.info("Iniciando busca de imagem para a palavra: {}", word);
            List<String> imageUrls = new ArrayList<>();

            String imageFromCambridge = scraper.fetchImageUrls(word);

            if(imageFromCambridge != null){
                imageUrls.add(imageFromCambridge);
            }

            List<LangeekResponseDTO> imagesLangeek = langeekClient.getWordImage(word);

            if(imagesLangeek != null && !imagesLangeek.isEmpty()){
                imagesLangeek.forEach(image -> {
                    if(image != null && image.getTranslation() != null && image.getTranslation().getWordPhoto() != null){
                        imageUrls.add(image.getTranslation().getWordPhoto().getPhoto());
                    }
                });
            }

            return imageUrls.isEmpty() ? null : imageUrls;
        }catch (Exception e){
            logger.error("Erro ao realizar scraping de imagens para a palavra {}: {}", word, e.getMessage());
            return null;
        }
    }
}
