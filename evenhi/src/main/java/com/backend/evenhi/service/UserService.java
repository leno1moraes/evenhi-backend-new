package com.backend.evenhi.service;

import com.backend.evenhi.config.jwt.JwtUtils;
import com.backend.evenhi.dto.UserDTO;
import com.backend.evenhi.model.LoginResponse;
import com.backend.evenhi.model.User;
import com.backend.evenhi.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.lang.Collections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtils jwtUtils;

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

//    private final UserRepository userRepository;
//    private final AuthenticationManager authenticationManager;
//    private final JwtUtils jwtUtils;
//    public UserService(UserRepository userRepository,
//                       AuthenticationManager authenticationManager, JwtUtils jwtUtils) {
//        this.userRepository = userRepository;
//        this.authenticationManager = authenticationManager;
//        this.jwtUtils = jwtUtils;
//    }

    public List<User> listAll() {
        return userRepository.findAll();
    }

    public Optional<User> findById(Long id){
        return userRepository.findById(id);
    }

    public UserDTO findByIdNew(Long id){
        User user = userRepository.findById(id).orElseThrow();
        UserDTO userDTO = new UserDTO();
        userDTO.setUserName(user.getUserName());
        userDTO.setEmail(user.getEmail());
        userDTO.setDocument(user.getDocument());
        userDTO.setTypeDocument(user.getTypeDocument());
        return userDTO;
    }

    public User createUser(User user) {
        return userRepository.save(saveUser(user));
    }

    public User updateUser(Long Id, User user){
        return userRepository.findById(Id)
                .map(userIn -> {
                    userIn.setUserName(user.getUserName());
                    userIn.setPassword(user.getPassword());
                    userIn.setEmail(user.getEmail());
                    userIn.setDocument(user.getDocument());
                    userIn.setTypeDocument(user.getTypeDocument());
                    return userRepository.save(userIn);
                }).orElseThrow(() -> new RuntimeException("0"));
    }

    public void deleteUser(Long Id){
        userRepository.deleteById(Id);
    }

    public User saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public ResponseEntity<?> authenticate(String email, String password) {
        LoginResponse response;
        Optional<User> user = userRepository.findByEmail(email);

        try {
            if (user.isPresent() && passwordEncoder.matches(password, user.get().getPassword())){
                Collection<? extends GrantedAuthority> authorities =
                        AuthorityUtils.createAuthorityList("ROLE_USER");
                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(email, password, authorities);
                Authentication authentication = authenticationManager.authenticate(authenticationToken);
                SecurityContextHolder.getContext().setAuthentication(authentication);
                String jwtToken = jwtUtils.generateTokenFromUsernameOrEmail(email);

                logger.info("#############################################");
                logger.info("##########  AUTENTICADO sucesso ! ###########");
                logger.info("#############################################");
                logger.info("Dados da Autenticação: " + authenticationToken);
                logger.info("Token de Autenticação gerado: " + jwtToken);

                response = new LoginResponse(
                        user.get().getUserName(),
                        user.get().getEmail(),
                        jwtToken,
                        null);
                return ResponseEntity.ok(response);
            }
            return ResponseEntity.notFound().build();

        } catch (Exception exception) {
            Map<String, Object> map = new HashMap<>();
            map.put("message", "Bad credentials");
            map.put("status", false);
            return ResponseEntity.notFound().build();
        }
    }

}
