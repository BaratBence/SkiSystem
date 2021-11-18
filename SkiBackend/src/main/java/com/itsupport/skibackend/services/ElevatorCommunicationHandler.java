package com.itsupport.skibackend.services;

import com.itsupport.elevator.models.Elevator;

import com.itsupport.skibackend.models.ElevatorApplicationModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.context.ServletWebServerApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;


@Service
public class ElevatorCommunicationHandler {
    @Autowired
    private ServletWebServerApplicationContext server;

    @Autowired
    private Environment env;

    @Autowired
    private RestTemplate restTemplate;

    public Elevator registerNewElevator(ElevatorApplicationModel elevatorApplicationModel){
        UriComponents url = UriComponentsBuilder.newInstance().scheme("https").host(elevatorApplicationModel.getAddress()).path("api/register").build();
        Map<String, String> payLoad = new HashMap<>();
        try {
            String backendAddress = InetAddress.getLocalHost().getHostAddress() + ":" + server.getWebServer().getPort();
            payLoad.put("ID", elevatorApplicationModel.getId().toString());
            payLoad.put("Address", "localhost:8443");
            payLoad.put("MaxUtil", env.getProperty("com.itsupport.MaxUtil"));
            HttpEntity<Map<String, String>> entity = new HttpEntity<>(payLoad, null);
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
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return null;
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
