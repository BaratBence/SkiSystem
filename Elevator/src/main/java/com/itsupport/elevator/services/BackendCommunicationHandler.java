package com.itsupport.elevator.services;

import com.itsupport.elevator.elevator.Elevator;
import com.itsupport.elevator.elevator.ElevatorHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.Duration;
import java.util.Collections;

public class BackendCommunicationHandler {

    @Autowired
    private  RestTemplate restTemplate;

    public BackendCommunicationHandler(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void notifyBackend(String backendAddress, Elevator elevator) {
        UriComponents url = UriComponentsBuilder.newInstance()
                .scheme("https")
                .host(backendAddress)
                .path("/api/callback/{id}/exceptional")
                .build();
        try {
            HttpHeaders headers = new HttpHeaders();
            //headers.setContentType(MediaType.APPLICATION_JSON);
            //headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            HttpEntity<Elevator> entity = new HttpEntity<>(elevator, headers);
            this.restTemplate.put(url.toString(), entity, ElevatorHandler.ID);
        }catch (HttpStatusCodeException ex){
            // raw http status code e.g `404`
            System.out.println(ex.getRawStatusCode());
            // http status code e.g. `404 NOT_FOUND`
            System.out.println(ex.getStatusCode().toString());
            // get response body
            System.out.println(ex.getResponseBodyAsString());
            // get http headers
            HttpHeaders headers= ex.getResponseHeaders();
        }
    }
}
