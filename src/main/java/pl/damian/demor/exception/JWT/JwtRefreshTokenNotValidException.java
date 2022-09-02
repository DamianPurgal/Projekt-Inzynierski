package pl.damian.demor.exception.JWT;

import org.springframework.http.HttpStatus;
import pl.damian.demor.exception.BusinessException;

public class JwtRefreshTokenNotValidException extends BusinessException {

    public JwtRefreshTokenNotValidException() { super(HttpStatus.BAD_REQUEST, "Refresh token not valid"); }

    public JwtRefreshTokenNotValidException(String message) { super(HttpStatus.BAD_REQUEST, message); }
}
