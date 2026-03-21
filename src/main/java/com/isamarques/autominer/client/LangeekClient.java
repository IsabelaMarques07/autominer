package com.isamarques.autominer.client;

import com.isamarques.autominer.configuration.FeignConfig;
import com.isamarques.autominer.dto.LangeekResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "langeekClient", url = "https://api.langeek.co/v1/cs/en/word/", configuration = FeignConfig.class)
public interface LangeekClient {

    @GetMapping("")
    List<LangeekResponseDTO> getWordImage(@RequestParam("term") String word);
}
