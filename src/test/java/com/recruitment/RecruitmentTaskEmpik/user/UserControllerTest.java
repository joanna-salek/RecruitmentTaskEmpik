package com.recruitment.user;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.ZonedDateTime;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @MockBean private UserService service;
    @Autowired MockMvc mvc;

    /*
        Test GET "/users/{login}"
          - Mock UserService getUser(String login) to return example user entity
          - Validate Json GET response with expected user
    */
    @Test
    public void getUserByLogin() throws Exception {
        // given
        User octocat =
                new User(
                        2,
                        "octocat",
                        "Octocat",
                        "user",
                        "https://avatars.githubusercontent.com/u/583231?v=4",
                        ZonedDateTime.parse("2011-01-25T18:44:36Z"),
                        2.4F);

        // when
        when(service.getUser("octocat")).thenReturn(octocat);

        // then
        mvc.perform(MockMvcRequestBuilders.get("/users/octocat").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(2))
                .andExpect(jsonPath("$.login").value("octocat"))
                .andExpect(jsonPath("$.name").value("Octocat"))
                .andExpect(jsonPath("$.type").value("user"))
                .andExpect(
                        jsonPath("$.avatarUrl")
                                .value("https://avatars.githubusercontent.com/u/583231?v=4"))
                .andExpect(jsonPath("$.createdAt").value("2011-01-25T18:44:36Z"))
                .andExpect(jsonPath("$.calculations").value(2.4));
    }
}
