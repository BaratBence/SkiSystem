package com.itsupport.skibackend.services;

import com.itsupport.elevator.models.Elevator;

import com.itsupport.skibackend.models.ElevatorApplicationModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;


@Service
public class ElevatorCommunicationHandler {

    @Autowired
    private RestTemplate restTemplate;
    /*
    public ElevatorCommunicationHandler(RestTemplateBuilder restTemplateBuilder){
        this.restTemplate = restTemplateBuilder
                .setConnectTimeout(Duration.ofSeconds(10))
                .setReadTimeout(Duration.ofSeconds(10))
                .build();
    }
     */

    public Elevator registerNewElevator(ElevatorApplicationModel elevatorApplicationModel){
        UriComponents url = UriComponentsBuilder.newInstance().scheme("https").host(elevatorApplicationModel.getAddress()).path("api/register").build();
        Map<String, String> payLoad = new HashMap<>();
        payLoad.put("ID", elevatorApplicationModel.getId().toString());
        payLoad.put("Address", "localhost:8443");
        payLoad.put("MaxUtil", "0.9");
        HttpEntity<Map<String, String>> entity = new HttpEntity<>(payLoad, null);
        try {
            return this.restTemplate.exchange(url.toString(), HttpMethod.POST, entity, Elevator.class).getBody();
        }catch (HttpStatusCodeException ex){
            // raw http status code e.g `404`
            System.out.println(ex.getRawStatusCode());
            // http status code e.g. `404 NOT_FOUND`
            System.out.println(ex.getStatusCode().toString());
            // get response body
            System.out.println(ex.getResponseBodyAsString());
            // get http headers
            HttpHeaders headers= ex.getResponseHeaders();

            return null;
        }
    }

    public Elevator getElevatorStatus(String address){
        UriComponents url = UriComponentsBuilder.newInstance().scheme("https").host(address).path("api/status").build();
        try {
            return this.restTemplate.getForObject(url.toString(), Elevator.class);
        }catch (HttpStatusCodeException ex){
            // raw http status code e.g `404`
            System.out.println(ex.getRawStatusCode());
            // http status code e.g. `404 NOT_FOUND`
            System.out.println(ex.getStatusCode().toString());
            // get response body
            System.out.println(ex.getResponseBodyAsString());
            // get http headers
            HttpHeaders headers= ex.getResponseHeaders();

            return null;
        }
    }

    public Elevator turnOffElevator(String address){
        UriComponents url = UriComponentsBuilder.newInstance().scheme("https").host(address).path("api/turnOff").build();
        try {
            return this.restTemplate.exchange(url.toString(), HttpMethod.PUT, null, Elevator.class).getBody();
        }catch (HttpStatusCodeException ex){
            // raw http status code e.g `404`
            System.out.println(ex.getRawStatusCode());
            // http status code e.g. `404 NOT_FOUND`
            System.out.println(ex.getStatusCode().toString());
            // get response body
            System.out.println(ex.getResponseBodyAsString());
            // get http headers
            HttpHeaders headers= ex.getResponseHeaders();

            return null;
        }
    }

    public Elevator turnOnElevator(String address){
        UriComponents url = UriComponentsBuilder.newInstance().scheme("https").host(address).path("api/turnOn").build();
        try {
            return this.restTemplate.exchange(url.toString(), HttpMethod.PUT, null, Elevator.class).getBody();
        }catch (HttpStatusCodeException ex){
            // raw http status code e.g `404`
            System.out.println(ex.getRawStatusCode());
            // http status code e.g. `404 NOT_FOUND`
            System.out.println(ex.getStatusCode().toString());
            // get response body
            System.out.println(ex.getResponseBodyAsString());
            // get http headers
            HttpHeaders headers= ex.getResponseHeaders();

            return null;
        }
    }
}
