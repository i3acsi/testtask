package com.interview.cdekTask.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "orders")
@Getter
@Setter
public class Order {
    @JsonView(Views.CourierView.class)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @JsonView(Views.CourierView.class)
    @Column(name = "name")
    private String name;

    @JsonView(Views.CourierView.class)
    @Column(name = "description")
    private String description;

    @JsonView(Views.CourierView.class)
    @Column(name = "client_name")
    private String clientName;

    @JsonView(Views.CourierView.class)
    @Column(name = "client_telephone")
    private String clientTelephone;

    @JsonView(Views.OperatorView.class)
    @Column(name = "created")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "YYYY-MM-dd HH:mm:ss")
    private LocalDateTime created;

    @JsonView(Views.OperatorView.class)
    @Column(name = "updated")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "YYYY-MM-dd HH:mm:ss")
    private LocalDateTime updated;

    @JsonView(Views.CourierView.class)
    @Column(name = "complete")
    private boolean complete = false;

    @JsonView(Views.CourierView.class)
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "holder_id")
    private User holder;

    @JsonView(Views.OperatorView.class)
    @Column(name = "history")
    private String history;
}
