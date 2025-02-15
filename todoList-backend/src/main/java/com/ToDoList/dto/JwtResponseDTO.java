package com.ToDoList.dto;

import com.ToDoList.model.User;
import lombok.*;

@NoArgsConstructor
public class JwtResponseDTO {
    private String accessToken;
    private UserDTO userDTO;
    private User user;



    public JwtResponseDTO(String accessToken, UserDTO userDTO, User user) {
        this.accessToken = accessToken;
        this.userDTO = userDTO;
        this.user = user;
    }

    // Add this new constructor for error responses
    public JwtResponseDTO(String errorMessage, UserDTO userDTO) {
        this.accessToken = errorMessage;
        this.userDTO = userDTO;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public UserDTO getUserDTO() {
        return userDTO;
    }

    public void setUserDTO(UserDTO userDTO) {
        this.userDTO = userDTO;
    }
}