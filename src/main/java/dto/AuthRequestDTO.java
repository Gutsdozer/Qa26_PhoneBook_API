package dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder

public class AuthRequestDTO {

 //   {
   //     "username": "string",
  //          "password": "q5\\P8Mjj-dfCz\\?w;i\\{=9yhYxpg&Lp.K':9&g,@0OECyS6p"
 //   }

    private String username;
    private String password;
}
