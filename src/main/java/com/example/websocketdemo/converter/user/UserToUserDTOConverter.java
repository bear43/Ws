package com.example.websocketdemo.converter.user;

import com.example.websocketdemo.model.User;
import com.example.websocketdemo.model.dto.UserDTO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UserToUserDTOConverter implements Converter<User, UserDTO> {

    @Override
    public UserDTO convert(User user) {
        return new UserDTO(user.getId(), user.getUsername());
    }
}
