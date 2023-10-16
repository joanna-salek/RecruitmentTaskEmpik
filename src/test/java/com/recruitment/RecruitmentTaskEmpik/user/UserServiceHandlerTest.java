package com.recruitment.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.recruitment.github.GithubService;
import com.recruitment.github.GithubUser;
import com.recruitment.requestcounter.RequestCounterRepository;
import java.time.ZonedDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
public class UserServiceHandlerTest {

    @MockBean UserService userService;

    @MockBean GithubService githubService;

    @MockBean RequestCounterRepository requestCounterRepository;

    @BeforeEach
    void setUp() throws Exception {
        userService = new UserService(githubService, requestCounterRepository);
    }

    @Test
    void getCorrectUserTest() {
        // given
        Mockito.when(githubService.getUser("octocat"))
                .thenReturn(
                        new GithubUser(
                                2,
                                "octocat",
                                "Octocat",
                                "user",
                                "https://avatars.githubusercontent.com/u/583231?v=4",
                                ZonedDateTime.parse("2011-01-25T18:44:36Z"),
                                2,
                                10));

        // when
        User user = userService.getUser("octocat");

        // then
        assertEquals(
                new User(
                        2,
                        "octocat",
                        "Octocat",
                        "user",
                        "https://avatars.githubusercontent.com/u/583231?v=4",
                        ZonedDateTime.parse("2011-01-25T18:44:36Z"),
                        2.4F),
                user);
    }

    @Test
    void getNotCorrectUserTest() {
        // given user with number of followers = 0
        Mockito.when(githubService.getUser("octocat"))
                .thenReturn(
                        new GithubUser(
                                2,
                                "octocat",
                                "Octocat",
                                "user",
                                "https://avatars.githubusercontent.com/u/583231?v=4",
                                ZonedDateTime.parse("2011-01-25T18:44:36Z"),
                                2,
                                0));

        // then
        Exception exception =
                assertThrows(
                        IllegalArgumentException.class,
                        () -> {
                            userService.getUser("octocat");
                        });

        String expectedMessage =
                "Divide by zero exception - number of followers can't be zero for calculation"
                        + " purposes";
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);
    }
}
