package com.recruitment.requestcounter;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Entity
@ToString
@EqualsAndHashCode
@Table(name = "request_counter")
public class RequestCounterEntity {
    @Id
    @Column(unique = true, nullable = false)
    private String login;

    private long requestCount;

    public RequestCounterEntity(String login, long requestCount) {
        this.login = login;
        this.requestCount = requestCount;
    }

    public RequestCounterEntity() {}
}
