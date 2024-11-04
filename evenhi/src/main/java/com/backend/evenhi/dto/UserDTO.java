package com.backend.evenhi.dto;

import com.backend.evenhi.model.TypeDocument;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private Long userId;
    private String userName;
    private String email;
    private String password;
    private TypeDocument typeDocument;
    private String document;
}
