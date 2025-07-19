package com.zadanie2.test;

import com.zadanie2.dto.GitHubAllRepoList;
import com.zadanie2.proxy.GithubProxy;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class GitHubControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @MockBean
    private GithubProxy githubProxy;

    @Test
    void shouldReturnAllReposForUser() {
        String username = "Rwyszynski";

        String repoResponseJson = """
        [
          {
            "name": "repo1",
            "owner": {
              "login": "Rwyszynski"
            },
            "fork": false
          }
        ]
        """;

        String branchesResponseJson = """
        [
          {
            "name": "main",
            "commit": {
              "sha": "abc123"
            }
          }
        ]
        """;

        when(githubProxy.getAllRepos(username)).thenReturn(repoResponseJson);
        when(githubProxy.getAllBranches(username, "repo1")).thenReturn(branchesResponseJson);

        ResponseEntity<GitHubAllRepoList> response = testRestTemplate.exchange(
                "http://localhost:" + port + "/repo/" + username,
                org.springframework.http.HttpMethod.GET,
                null,
                GitHubAllRepoList.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        GitHubAllRepoList body = response.getBody();
        assertThat(body).isNotNull();

        var firstRepo = body.fullObject().get(0);
        assertThat(firstRepo.name()).isEqualTo("repo1");
        assertThat(firstRepo.owner().login()).isEqualTo("Rwyszynski");

        assertThat(firstRepo.branch()).hasSize(1);
        assertThat(firstRepo.branch().get(0).commit()).isNotNull();
    }
}
