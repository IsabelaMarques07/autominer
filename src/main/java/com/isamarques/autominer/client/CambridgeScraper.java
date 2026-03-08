package com.isamarques.autominer.client;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class CambridgeScraper {
    private static final String URL_BASE = "https://dictionary.cambridge.org/dictionary/english/";

    public List<String> fetchDefinition(String word) throws IOException {
        Document doc = Jsoup.connect(URL_BASE + word)
                .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36")
                .header("Accept-Language", "en-US,en;q=0.5")
                .get();

        List<String> definitions = doc.select(".def.ddef_d.db").eachText();

        if (!definitions.isEmpty()) {
            return definitions;
        } else {
            return null;
        }
    }
}
