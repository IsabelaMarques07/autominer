package com.isamarques.autominer.client;

import com.isamarques.autominer.dto.WordData;

import java.io.IOException;

public interface DictionaryScraper {
    WordData fetchWordData(String word) throws IOException;
}
