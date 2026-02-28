package com.basic.sensor2browser.dto;

import com.basic.sensor2browser.model.EnumRole;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder                                                    // Lombok builder pattern, resolving the "Constructor Nightmare"
/**
 * DTO used to expose user information and JWT tokens
 * to clients without leaking sensitive internal entity details.
 */
public class UserDto {

    private Long id;
    private String userName;
    private String email;
    @ToString.Exclude                                       // Precision (field level): Prevents passwords from being printed
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)  // Hide password in responses
    private String password;
    private String userProfileImage;
    private EnumRole role;
    private String token;
    private String refreshToken;
    private Long expirationTime;
    private String message;

}
