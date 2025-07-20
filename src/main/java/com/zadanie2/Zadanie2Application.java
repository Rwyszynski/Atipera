package com.zadanie2;

import com.zadanie2.controller.GitHubController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Zadanie2Application {

    @Autowired
    GitHubController gitHubController;

    public static void main(String[] args) {
        SpringApplication.run(Zadanie2Application.class, args);
    }
/*
    @EventListener(ApplicationReadyEvent.class)
    public void makeRequestToSongifyEndpoint() {
        FullObject responseAll = gitHubController.retreiveAllRepos("kalqa");
        System.out.println(responseAll);
    }

 */
}
