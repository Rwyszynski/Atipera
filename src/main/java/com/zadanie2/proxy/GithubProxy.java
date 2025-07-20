package com.zadanie2.proxy;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Log4j2
@Component
public class GithubProxy {

    @Autowired
    RestTemplate restTemplate;

    private static final Logger log = LogManager.getLogger(GithubProxy.class);

    @Value("https://api.github.com")
    String url;

    public String getAllRepos(String username) {
        UriComponentsBuilder builder = UriComponentsBuilder
                .newInstance()
                .scheme("https")
                .host("api.github.com")
                .path("/users/" + username + "/repos");
        try {
            ResponseEntity<String> response = restTemplate.exchange(
                    builder.build().toUri(),
                    HttpMethod.GET,
                    null,
                    String.class
            );
            String json = response.getBody();
            ObjectMapper mapper = new ObjectMapper();

            return json;
        } catch (IllegalArgumentException e) {
            log.error("Użytkownik nie istnieje");
            return null;
        }
    }

    public String getAllBranches(String username, String repo) {
        UriComponentsBuilder builder = UriComponentsBuilder
                .newInstance()
                .scheme("https")
                .host("api.github.com")
                .path("/repos/" + username + "/" + repo + "/branches");
        try {
            ResponseEntity<String> response = restTemplate.exchange(
                    builder.build().toUri(),
                    HttpMethod.GET,
                    null,
                    String.class
            );
            String json = response.getBody();
            ObjectMapper mapper = new ObjectMapper();

            return json;
        } catch (IllegalArgumentException e) {
            log.error("Użytkownik nie istnieje");
            return null;
        }
    }

}