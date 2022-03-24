package app.controllers;

import app.configuration.captcha.CaptchaSettings;
import app.configuration.captcha.GoogleResponse;
import app.configuration.security.JwtTokenUtil;
import app.constants.Roles;
import app.entities.DTOs.User.LoginDTO;
import app.entities.DTOs.User.RegisterDTO;
import app.entities.DTOs.User.ShortUser;
import app.entities.RoleEntity;
import app.entities.UserEntity;
import app.mapper.UserMapper;
import app.repositories.RoleRepository;
import app.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.client.RestOperations;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final UserMapper userMapper;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    private final CaptchaSettings captchaSettings;
    private final RestOperations restTemplate;
    private final String RECAPTCHA_URL_TEMPLATE = "https://www.google.com/recaptcha/api/siteverify?secret=%s&response=%s";

    @PostMapping("/login")
    public ResponseEntity<String> authenticateUser(@RequestBody LoginDTO loginDTO) {
        try {
            String url = String.format(RECAPTCHA_URL_TEMPLATE, captchaSettings.getKey(), loginDTO.getToken());
            try {
                final GoogleResponse googleResponse = restTemplate.getForObject(url, GoogleResponse.class);
                ;
                if (!googleResponse.isSuccess()) {
                    throw new Exception("reCaptcha was not successfully validated");
                }
            }
            catch (Exception rce) {
                String str = rce.getMessage();
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(str);
            }
            Authentication authenticate = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(
                            loginDTO.getEmail(),
                            loginDTO.getPassword()));

            User user = (User) authenticate.getPrincipal();

            UserEntity dbUser = userRepository
                    .findByEmail(user.getUsername());

            String token = jwtTokenUtil.generateAccessToken(dbUser);
            return ResponseEntity.ok()
                    .header(HttpHeaders.AUTHORIZATION)
                    .body(token);
        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody RegisterDTO registerDTO) {
        String url = String.format(RECAPTCHA_URL_TEMPLATE, captchaSettings.getKey(), registerDTO.getToken());
        try {
            final GoogleResponse googleResponse = restTemplate.getForObject(url, GoogleResponse.class);
            ;
            if (!googleResponse.isSuccess()) {
                throw new Exception("reCaptcha was not successfully validated");
            }
        }
        catch (Exception rce) {
            String str = rce.getMessage();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(str);
        }

        if (userRepository.existsByEmail(registerDTO.getEmail())) {
            return new ResponseEntity<String>("Email is already taken!", HttpStatus.CONFLICT);
        }


        // create user object
        UserEntity user = new UserEntity();
        user.setEmail(registerDTO.getEmail());
        user.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
        user.setUsername(registerDTO.getUsername());

        List<RoleEntity> roles = new ArrayList<>();
        roles.add(roleRepository.findByName(Roles.Admin));
        user.setRoles(roles);

        userRepository.save(user);

        UserEntity dbUser = userRepository
                .findByEmail(user.getEmail());

        String token = jwtTokenUtil.generateAccessToken(dbUser);
        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION)
                .body(token);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logoutUser() {
        String token = jwtTokenUtil.generateLogoutToken();
        return ResponseEntity.ok().body(token);
    }
}
