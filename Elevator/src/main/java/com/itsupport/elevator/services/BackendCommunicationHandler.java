package com.itsupport.elevator.services;

import com.itsupport.elevator.models.Elevator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class BackendCommunicationHandler {

    @Autowired
    private  RestTemplate restTemplate;

    public void notifyBackend(String backendAddress, Elevator elevator) {
        UriComponents url = UriComponentsBuilder.newInstance()
                .scheme("https")
                .host(backendAddress)
                .path("/api/callback/{id}/exceptional")
                .build();
        try {
            HttpHeaders headers = new HttpHeaders();
            HttpEntity<Elevator> entity = new HttpEntity<>(elevator, headers);
            this.restTemplate.put(url.toString(), entity, ElevatorHandler.ID);
        }catch (HttpStatusCodeException ex){
            // raw http status code e.g `404`
            System.out.println(ex.getRawStatusCode());
            // http status code e.g. `404 NOT_FOUND`
            System.out.println(ex.getStatusCode());
            // get response body
            System.out.println(ex.getResponseBodyAsString());
        }
    }
}
