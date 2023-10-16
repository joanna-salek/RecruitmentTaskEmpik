package com.recruitment.github;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.ZonedDateTime;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.client.WebClient;

@ExtendWith(MockitoExtension.class)
public class GithubServiceTest {
    private MockWebServer mockWebServer;

    private GithubService githubService;

    @BeforeEach
    void setUp() throws Exception {
        mockWebServer = new MockWebServer();
        mockWebServer.start();

        githubService =
                new GithubService(
                        WebClient.builder().baseUrl(mockWebServer.url("/").toString()).build());
    }

    @AfterEach
    void tearDown() throws Exception {
        mockWebServer.shutdown();
    }

    @Test
    void getUser() throws InterruptedException {
        // given
        MockResponse mockResponse =
                new MockResponse()
                        .setHeader("content-type", "application/json")
                        .setResponseCode(200)
                        .setBody(
                                """
                        {
                        \t"id": 2,
                        \t"login": "octocat",
                        \t"name": "Octocat",
                        \t"type": "user",
                        \t"avatar_url": "https://avatars.githubusercontent.com/u/583231?v=4",
                        \t"created_at": "2011-01-25T18:44:36Z",
                        \t"public_repos": 2,
                        \t"followers": 10
                        }""");
        mockWebServer.enqueue(mockResponse);

        // when
        GithubUser user = githubService.getUser("octocat");
        RecordedRequest recordedRequest = mockWebServer.takeRequest();

        // then
        assertEquals(
                new GithubUser(
                        2,
                        "octocat",
                        "Octocat",
                        "user",
                        "https://avatars.githubusercontent.com/u/583231?v=4",
                        ZonedDateTime.parse("2011-01-25T18:44:36Z"),
                        2,
                        10),
                user);
        assertEquals("GET", recordedRequest.getMethod());
        assertEquals("/users/octocat", recordedRequest.getPath());
    }
}
