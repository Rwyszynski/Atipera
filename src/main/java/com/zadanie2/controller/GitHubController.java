package com.zadanie2.controller;

import com.zadanie2.dto.GitHubAllRepoList;
import com.zadanie2.proxy.GithubProxy;
import com.zadanie2.service.GithubService;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
@RequestMapping("/repo")
public class GitHubController {

    GithubProxy githubProxy;
    GithubService githubService;

    public GitHubController(GithubProxy githubProxy, GithubService githubService) {
        this.githubProxy = githubProxy;
        this.githubService = githubService;
    }

    @GetMapping("/{name}")
    public ResponseEntity<GitHubAllRepoList> retreiveAllRepos(@PathVariable String name) {
        GitHubAllRepoList allRepos = githubService.getAllRepos(name);
        return ResponseEntity.ok(allRepos);
    }
}
