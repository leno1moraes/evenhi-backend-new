package com.backend.evenhi.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class EventDTO {
    private Long id;
    private String nameEvent;
    private String pathImage;
    private String zipcode;
    private String district;
    private String street;
    private String state;
    private String city;
    private String country;
    private Integer subscribers;
    private Long userId;
    private Integer status;
    private Date date;
}
