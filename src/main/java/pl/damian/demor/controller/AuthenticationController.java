package pl.damian.demor.controller;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.damian.demor.DTO.AppUser.RegisterAppUserDTO;
import pl.damian.demor.DTO.JWT.RefreshJWTRequest;
import pl.damian.demor.exception.security.JWT.JwtRefreshTokenNotValidException;
import pl.damian.demor.security.configuration.JwtConfiguration;
import pl.damian.demor.security.response.authentication.AuthenticationResponse;
import pl.damian.demor.service.definition.AppUserService;

import javax.crypto.SecretKey;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Date;

@RestController
@RequestMapping("api/auth")
public class AuthenticationController {

    private final SecretKey secretKeyAccessToken;

    private final SecretKey secretKeyRefreshToken;

    private final AppUserService appUserService;

    private final JwtConfiguration jwtConfiguration;

    public AuthenticationController(@Qualifier("secretKeyAccessToken") SecretKey secretKeyAccessToken,
                              @Qualifier("secretKeyRefreshToken") SecretKey secretKeyRefreshToken,
                              AppUserService appUserService,
                              JwtConfiguration jwtConfiguration) {
        this.secretKeyAccessToken = secretKeyAccessToken;
        this.secretKeyRefreshToken = secretKeyRefreshToken;
        this.appUserService = appUserService;
        this.jwtConfiguration = jwtConfiguration;
    }

    @PostMapping("/register")
    @PreAuthorize("permitAll()")
    public void registerUser(@RequestBody @Valid RegisterAppUserDTO registerAppUserDTO){
        appUserService.registerUser(registerAppUserDTO);
    }

    @PostMapping("/refreshToken")
    @PreAuthorize("permitAll()")
    public AuthenticationResponse refreshJWT(@RequestBody @NotNull RefreshJWTRequest request){

        try{
            Jws<Claims> claimsJws = Jwts.parserBuilder()
                    .setSigningKey(secretKeyRefreshToken)
                    .build()
                    .parseClaimsJws(request.getRefreshToken());

            Claims body = claimsJws.getBody();
            String username = body.getSubject();

            UserDetails user = appUserService.loadUserByUsername(username);

            String accessToken = Jwts.builder()
                    .setSubject(user.getUsername())
                    .claim("authorities", user.getAuthorities())
                    .setIssuedAt(new Date())
                    .setExpiration(java.sql.Date.valueOf(LocalDate.now().plusDays(jwtConfiguration.getAccessTokenExpirationTimeInDays())))
                    .signWith(secretKeyAccessToken)
                    .compact();

            return new AuthenticationResponse(
                    accessToken,
                    request.getRefreshToken()
            );

        }catch(Exception exception){
            throw new JwtRefreshTokenNotValidException();
        }
    }
}
