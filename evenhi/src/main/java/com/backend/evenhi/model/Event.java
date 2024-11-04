package com.backend.evenhi.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "events")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_id")
    private Long id;

    @NotBlank
    @NotNull
    @Column(name = "name_event")
    private String nameEvent;

    @Column(name = "path_image")
    private String pathImage;

    @NotBlank
    @NotNull
    @Column(name = "zipcode")
    private String zipcode;

    @Column(name = "district")
    private String district; /*bairro*/

    @Column(name = "street")
    private String street; /*rua*/

    @NotBlank
    @NotNull
    @Column(name = "state")
    private String state; /*estado*/

    @NotBlank
    @NotNull
    @Column(name = "city")
    private String city; /*cidade*/

    @NotBlank
    @NotNull
    @Column(name = "country")
    private String country; /*pais*/

    @Column(name = "subscribers")
    private Integer subscribers; /*numeros de inscritos*/

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE})
    @JoinColumn(name = "id", referencedColumnName = "id")
    private User user;
}
