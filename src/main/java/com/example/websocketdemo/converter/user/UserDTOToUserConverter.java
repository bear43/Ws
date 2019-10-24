package com.example.websocketdemo.converter.user;

import com.example.websocketdemo.model.User;
import com.example.websocketdemo.model.dto.UserDTO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UserDTOToUserConverter implements Converter<UserDTO, User> {

    @Override
    public User convert(UserDTO userDTO) {
        User user = new User(userDTO.getUsername(), "");
        user.setId(userDTO.getId());
        return user;
    }
}
