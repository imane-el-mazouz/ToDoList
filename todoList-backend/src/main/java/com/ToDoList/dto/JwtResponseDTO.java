package com.ToDoList.dto;

import com.ToDoList.model.User;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JwtResponseDTO {
    private String accessToken;
    private UserDTO userDTO ;

    private User user ;

    public JwtResponseDTO(String error, Object o) {
    }
}
