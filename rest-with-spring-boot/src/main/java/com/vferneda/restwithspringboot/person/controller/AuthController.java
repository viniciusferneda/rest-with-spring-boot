package com.vferneda.restwithspringboot.person.controller;

import com.vferneda.restwithspringboot.person.repository.UserRepository;
import com.vferneda.restwithspringboot.security.AccountCredentialsVO;
import com.vferneda.restwithspringboot.security.jwt.JwtTokenProvider;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private UserRepository userRepository;

    @ApiOperation(value = "Authenticate a user by credentials")
    @PostMapping(value = "/signin",
            produces = {"application/json", "application/xml", "application/x-yaml"},
            consumes = {"application/json", "application/xml", "application/x-yaml"})
    public ResponseEntity<?> signin(@RequestBody AccountCredentialsVO data) {
        try {
            var userName = data.getUserName();
            var password = data.getPassword();

            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userName, password));

            var user = userRepository.findByUserName(userName);

            var token = "";

            if (user == null) {
                throw new UsernameNotFoundException("Username " + userName + " not found!");
            } else {
                token = jwtTokenProvider.createToken(userName, user.getRoles());
            }

            final Map<Object, Object> model = new HashMap<>();
            model.put("userName", userName);
            model.put("token", token);

            return ok(model);
        } catch (AuthenticationException exc) {
            throw new BadCredentialsException("Invalid username/password supplied!");
        }
    }
}
