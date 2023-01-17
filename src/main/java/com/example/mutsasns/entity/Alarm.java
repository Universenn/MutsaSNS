package com.example.mutsasns.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class Alarm extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private AlarmType alarmType;

    @ManyToOne
    @JoinColumn(name = "user_userId")
    private User user;


    private Long fromUserId;
    private Long targetId;
    private String text;

    public Alarm(AlarmType alarmType, User user, Long fromUserId, Long targetId, String text) {
        this.alarmType = alarmType;
        this.user = user;
        this.fromUserId = fromUserId;
        this.targetId = targetId;
        this.text = text;
    }
}
