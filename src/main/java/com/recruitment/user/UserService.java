package com.recruitment.user;

import com.recruitment.github.GithubService;
import com.recruitment.github.GithubUser;
import com.recruitment.requestcounter.RequestCounterEntity;
import com.recruitment.requestcounter.RequestCounterRepository;
import jakarta.transaction.Transactional;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final GithubService githubService;
    private final RequestCounterRepository requestCounterRepository;

    public UserService(
            GithubService githubService, RequestCounterRepository requestCounterRepository) {
        this.githubService = githubService;
        this.requestCounterRepository = requestCounterRepository;
    }

    public User getUser(String login) {
        // assuming that every time request is made incrementing should be done
        // even if it is not successful (clarification needed)
        incrementRequestCount(login);

        GithubUser githubUser = githubService.getUser(login);
        float calculations = performCalculations(githubUser.publicRepos(), githubUser.followers());
        return new User(
                githubUser.id(),
                githubUser.login(),
                githubUser.name(),
                githubUser.type(),
                githubUser.avatarUrl(),
                githubUser.createdAt(),
                calculations);
    }

    @Transactional
    private void incrementRequestCount(String login) {
        Optional<RequestCounterEntity> record = requestCounterRepository.findByLogin(login);
        record.ifPresentOrElse(
                requestCounterEntity -> {
                    requestCounterEntity.setRequestCount(
                            requestCounterEntity.getRequestCount() + 1);
                    requestCounterRepository.save(requestCounterEntity);
                },
                () -> requestCounterRepository.save(new RequestCounterEntity(login, 1)));
    }

    private float performCalculations(int publicRepos, long followers) {
        if (followers == 0) {
            throw new IllegalArgumentException(
                    "Divide by zero exception - number of followers can't be zero for calculation"
                            + " purposes");
        }
        return 6F / followers * (2 + publicRepos);
    }
}
