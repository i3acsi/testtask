package com.interview.testtask.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "orders")
@Data
public class Order {
    @JsonView(Views.SimpleView.class)
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    @JsonView(Views.SimpleView.class)
    private String name;
    @JsonView(Views.SimpleView.class)
    private String description;

    @JsonView(Views.SimpleView.class)
    @Column(name = "client_name")
    private String clientName;

    @JsonView(Views.SimpleView.class)
    @Column(name = "client_telephone")
    private String clientTelephone;

    @JsonView(Views.CommonView.class)
    @Column(name = "created_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "YYYY-MM-dd")
    private LocalDate createdDate;

    @JsonView(Views.CommonView.class)
    @Column(name = "created_time")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    private LocalTime createdTime;

    @JsonView(Views.CommonView.class)
    @Column(name = "updated_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "YYYY-MM-dd")
    private LocalDate updatedDate;

    @JsonView(Views.CommonView.class)
    @Column(name = "updated_time")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    private LocalTime updatedTime;

    @JsonView(Views.SimpleView.class)
    private boolean active;

    @JsonView(Views.SimpleView.class)
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "holder_id")
    private User holder;

    @JsonView(Views.SimpleView.class)
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "courier_id")
    private User courier;

    @JsonView(Views.CommonView.class)
    private String history;
}
