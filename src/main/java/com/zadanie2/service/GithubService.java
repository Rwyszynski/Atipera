package com.zadanie2.service;

import com.zadanie2.entity.Branch;
import com.zadanie2.dto.FullObject;
import com.zadanie2.dto.GitHubAllRepoList;
import com.zadanie2.dto.GitHubResult;
import com.zadanie2.entity.GithubMapper;
import com.zadanie2.exception.UserNotFoundException;
import com.zadanie2.proxy.GithubProxy;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

@Service
public class GithubService {

    GithubProxy githubProxy;
    GithubMapper githubMapper;

    public GithubService(GithubProxy githubProxy, GithubMapper githubMapper) {
        this.githubProxy = githubProxy;
        this.githubMapper = githubMapper;
    }

    public List<GitHubResult> fetchAllRepos(String username) {
        try {
            String json = String.valueOf(githubProxy.getAllRepos(username));
            return githubMapper.mapJsonToGitHubResultList(json)
                    .stream()
                    .filter(gitHubResult -> !gitHubResult.fork())
                    .toList();
        } catch (HttpClientErrorException ex) {
            throw new UserNotFoundException("User: " + username + " not found");
        }
    }

    public List<Branch> fetchAllBranches(String username, String repo) {
        String json = githubProxy.getAllBranches(username, repo);
        return githubMapper.mapGitHubResultListToBranchList(json);
    }

    public GitHubAllRepoList getAllRepos(String username) {
        List<FullObject> resultList = new ArrayList<>();
        List<GitHubResult> gitHubResultList = fetchAllRepos(username);

        ExecutorService executor = Executors.newFixedThreadPool(Math.min(gitHubResultList.size(), 10));
        List<Future<FullObject>> futures = new ArrayList<>();

        for (GitHubResult gitHubResult : gitHubResultList) {
            futures.add(executor.submit(() -> {
                List<Branch> branchSource = fetchAllBranches(gitHubResult.owner().login(), gitHubResult.name());
                return new FullObject(gitHubResult.name(), gitHubResult.owner(), branchSource);
            }));
        }

        for (Future<FullObject> future : futures) {
            try {
                FullObject single = future.get();
                resultList.add(single);
            } catch (InterruptedException | ExecutionException e) {

                e.printStackTrace();
            }
        }

        executor.shutdown();
        return new GitHubAllRepoList(resultList);
    }
}