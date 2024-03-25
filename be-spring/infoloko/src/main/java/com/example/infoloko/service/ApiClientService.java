package com.example.infoloko.service;

import com.example.infoloko.model.InfoLoko;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ApiClientService {

    private static RestTemplate restTemplate;


    public ApiClientService(RestTemplate restTemplate) {
        ApiClientService.restTemplate = restTemplate;
    }

    public static void callAPI(InfoLoko infoLoko) {
        //Initial State Api
        String apiUrl = "http://localhost:8080/api/send";

        // Membuat header untuk POST
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Membuat entitas HTTP dengan data InfoLokomotif yang akan dikirimkan
        HttpEntity<InfoLoko> requestEntity = new HttpEntity<>(infoLoko, headers);

        // Melakukan permintaan POST ke API
        String response = restTemplate.postForObject(apiUrl, requestEntity, String.class);
    }
}
