package com.interview.cdekTask.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
@Getter
@Setter
public class Order extends AbstractEntity{
    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "client_name")
    private String clientName;

    @Column(name = "client_telephone")
    private String clientTelephone;

    @Column(name = "created")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "YYYY-MM-dd HH:mm:ss")
    private LocalDateTime created;

    @Column(name = "updated")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "YYYY-MM-dd HH:mm:ss")
    private LocalDateTime updated;

    @Column(name = "complete")
    private boolean complete = false;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "holder_id")
    private User holder;

    @Column(name = "history")
    private String history;
}
