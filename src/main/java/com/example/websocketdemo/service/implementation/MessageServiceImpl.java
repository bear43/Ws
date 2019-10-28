package com.example.websocketdemo.service.implementation;

import com.example.websocketdemo.model.Message;
import com.example.websocketdemo.model.dto.MessageDTO;
import com.example.websocketdemo.repository.MessageRepository;
import com.example.websocketdemo.service.MessageService;
import com.example.websocketdemo.service.UserService;
import org.springframework.core.convert.ConversionService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;

    private final ConversionService conversionService;

    private final UserService userService;

    public MessageServiceImpl(MessageRepository messageRepository, ConversionService conversionService, UserService userService) {
        this.messageRepository = messageRepository;
        this.conversionService = conversionService;
        this.userService = userService;
    }

    private void emptyChecker(String text) throws Exception {
        text = text.trim();
        if(text.isEmpty()) throw new Exception("Empty message are not allowed");

    }

    private void emptyChecker(Byte[] data) throws  Exception{
        if(data.length == 0) throw new Exception("Cannot save empty data");
    }


    @Override
    public MessageDTO createMessage(MessageDTO messageDTO) throws Exception {
        emptyChecker(messageDTO.getData());
        Message message = conversionService.convert(messageDTO, Message.class);
        message.setAuthor(userService.getCurrentUser());
        messageRepository.save(message);
        return convertToDTO(message);
    }

    @Override
    @PreAuthorize("@messageRepository.hasPermission(#messageDTO.getId(), @userServiceImpl.getCurrentUser().getId())")
    public MessageDTO editMessage(MessageDTO messageDTO) throws Exception {
        emptyChecker(messageDTO.getData());
        if(!messageRepository.existsById(messageDTO.getId()))  throw new Exception("Cannot find message with id " + messageDTO.getId());
        Message message = conversionService.convert(messageDTO, Message.class);
        messageRepository.save(message);
        return convertToDTO(message);
        /*return permissionChecker.doPermissionActionTyped(this, userService.getCurrentUser().getId(), messageDTO.getId(), () -> {
        });*/
    }

    @Override
    @PreAuthorize("@messageRepository.hasPermission(#id, @userServiceImpl.getCurrentUser().getId())")
    public void removeMessage(long id) throws Exception {
        messageRepository.deleteById(id);
        /*permissionChecker.doPermissionActionVoid(this, userService.getCurrentUser().getId(), id, () -> {
            messageRepository.deleteById(id);
        });*/
    }

    @Override
    public MessageDTO convertToDTO(Message message) {
        return conversionService.convert(message, MessageDTO.class);
    }

    @Override
    public Message convertToEntity(MessageDTO messageDTO) {
        return conversionService.convert(messageDTO, Message.class);
    }

    @Override
    public List<Message> readAll() {
        return StreamSupport.stream(messageRepository.findAll().spliterator(), true).collect(Collectors.toList());
    }

    @Override
    public List<MessageDTO> readAllByChannel(Long id) {
        return messageRepository.findAllByChannelId(id).stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public boolean hasPermission(long userId, long messageId) {
        return messageRepository.hasPermission(messageId, userId);
    }
}
