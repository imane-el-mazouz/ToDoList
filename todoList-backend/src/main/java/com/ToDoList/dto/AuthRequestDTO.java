package com.ToDoList.dto;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class AuthRequestDTO{
   private String email ;
   private String password ;

}
