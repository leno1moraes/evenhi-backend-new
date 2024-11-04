package com.backend.evenhi.service;

import com.backend.evenhi.dto.UserDTO;
import com.backend.evenhi.model.User;
import com.backend.evenhi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

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
        return userRepository.save(user);
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

}
