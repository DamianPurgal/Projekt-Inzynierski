package pl.damian.demor.DTO.JWT;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RefreshJWTRequest {
    private String refreshToken;
}
