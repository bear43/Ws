package com.example.websocketdemo.converter.user;

import com.example.websocketdemo.converter.ConverterWrapper;
import com.example.websocketdemo.model.User;
import com.example.websocketdemo.model.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UserConverter implements ConverterWrapper<User, UserDTO> {

    @Autowired
    private UserToUserDTOConverter userToUserDTOConverter;

    @Autowired
    private UserDTOToUserConverter userDTOToUserConverter;

    @Override
    public Converter<User, UserDTO> getEntityToDTOInstance() {
        return userToUserDTOConverter;
    }

    @Override
    public Converter<UserDTO, User> getDTOToEntityInstance() {
        return userDTOToUserConverter;
    }
}
