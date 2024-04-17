package opp.mic.payroll.controller;

import opp.mic.payroll.model.AuthRequest;
import opp.mic.payroll.model.LoginResponse;
import opp.mic.payroll.model.RefreshToken;
import opp.mic.payroll.service.RefreshTokenService;
import opp.mic.payroll.service.UserAuthTokenService;
import opp.mic.payroll.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private AuthenticationManager authManager;
    private UserService userService;
    private RefreshTokenService refreshTokenService;
    private UserAuthTokenService authTokenService;

    public AuthController(AuthenticationManager authManager, UserService userService,
                          RefreshTokenService refreshTokenService, UserAuthTokenService authTokenService) {
        this.authManager = authManager;
        this.userService = userService;
        this.refreshTokenService = refreshTokenService;
        this.authTokenService = authTokenService;
    }

    @GetMapping
    public String hello(){
        return "hello";
    }
    @PostMapping(value = "/login")
    public ResponseEntity<LoginResponse> token(@RequestBody AuthRequest authRequest){
        try{
            Authentication authentication =
                    authManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.username(),authRequest.password()));
            if(authentication.isAuthenticated()){
                RefreshToken refreshToken = refreshTokenService.createToken(authentication.getName());
                LoginResponse loginResponse = LoginResponse.builder()
                        .appUser(userService.getUser(authentication.getName()))
                        .accessToken(authTokenService.token(authentication))
                        .token(refreshToken.getToken())
                        .build();
                return ResponseEntity.ok().body(loginResponse);
            }
            else{
                throw new AuthenticationServiceException("Could not authenticate user");
            }
        }catch(Exception e){
            throw  new AuthenticationServiceException(e.getMessage());
        }
    }

    @DeleteMapping("/logout")
    public RefreshToken logout(@RequestParam("token") String token){
        return refreshTokenService.removeToken(token);
    }
}
