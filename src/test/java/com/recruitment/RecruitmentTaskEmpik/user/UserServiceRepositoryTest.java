package com.recruitment.user;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.recruitment.github.GithubService;
import com.recruitment.github.GithubUser;
import com.recruitment.requestcounter.RequestCounterEntity;
import com.recruitment.requestcounter.RequestCounterRepository;
import java.time.ZonedDateTime;
import java.util.Optional;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

@DataJpaTest
@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserServiceRepositoryTest {

    @Autowired private RequestCounterRepository requestCounterRepository;

    UserService userService;

    @MockBean GithubService githubService;

    @Test
    public void incrementAfterGetUserTest() {
        // given
        GithubUser michaelGithubUser =
                new GithubUser(
                        1,
                        "michael234",
                        "Michael",
                        "user",
                        "https://avatars.githubusercontent.com/u/5832?v=4",
                        ZonedDateTime.parse("2012-01-25T18:44:36Z"),
                        2,
                        10);
        userService = new UserService(githubService, requestCounterRepository);

        // when
        Mockito.when(githubService.getUser(michaelGithubUser.login()))
                .thenReturn(michaelGithubUser);

        // getting user 7 times to increment count of requests in DB
        int counter = 7;
        for (int i = 0; i < counter; i++) {
            userService.getUser(michaelGithubUser.login());
        }
        Optional<RequestCounterEntity> michael =
                requestCounterRepository.findByLogin(michaelGithubUser.login());

        // then
        assertTrue(michael.isPresent());
        assertEquals(michael.get().getRequestCount(), counter);
        assertEquals(michael.get().getLogin(), michaelGithubUser.login());
    }

    @Test
    public void repositoryOperationsTest() {

        // given
        RequestCounterEntity james = new RequestCounterEntity("James", 6);

        // when
        requestCounterRepository.save(james);
        Optional<RequestCounterEntity> jamesRecord =
                requestCounterRepository.findByLogin(james.getLogin());

        // then
        assertEquals(jamesRecord.get().getLogin(), "James");
        assertEquals(jamesRecord.get().getRequestCount(), 6);
    }
}
