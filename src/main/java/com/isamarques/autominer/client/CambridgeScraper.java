package com.isamarques.autominer.client;

import com.isamarques.autominer.dto.WordData;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Slf4j
@Component
public class CambridgeScraper implements DictionaryScraper {

    private static final String BASE_URL        = "https://dictionary.cambridge.org/dictionary/english/";
    private static final String USER_AGENT      = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36";
    private static final String ACCEPT_LANGUAGE = "en-US,en;q=0.5";
    private static final String DEF_SELECTOR    = ".def.ddef_d.db";
    private static final String IMG_SELECTOR    = ".dimg_i";

    @Override
    public WordData fetchWordData(String word) throws IOException {
        log.debug("Conectando ao Cambridge Dictionary para: '{}'", word);
        Document doc = connectTo(word);
        return new WordData(extractDefinitions(doc), extractImageUrl(doc));
    }

    private List<String> extractDefinitions(Document doc) {
        List<String> definitions = doc.select(DEF_SELECTOR).eachText();
        if (definitions.isEmpty()) {
            log.debug("Nenhuma definição encontrada no documento.");
        }
        return definitions;
    }

    private String extractImageUrl(Document doc) {
        Element imgElement = doc.select(IMG_SELECTOR).first();
        if (imgElement == null) {
            log.debug("Nenhuma imagem encontrada no documento.");
            return null;
        }
        return imgElement.absUrl("src");
    }

    private Document connectTo(String word) throws IOException {
        return Jsoup.connect(BASE_URL + word)
                .userAgent(USER_AGENT)
                .header("Accept-Language", ACCEPT_LANGUAGE)
                .get();
    }
}
