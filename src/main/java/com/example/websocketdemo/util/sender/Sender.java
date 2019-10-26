package com.example.websocketdemo.util.sender;

import com.example.websocketdemo.model.dto.GeneralRemovableAuthoredDTO;
import lombok.Data;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.security.Principal;

@Data
public class Sender {

    private SimpMessagingTemplate simpMessagingTemplate;

    public Sender(SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    public void sendToUserAndAll(String route, Principal principal, GeneralRemovableAuthoredDTO object) {
        boolean isAuthor = object.getAuthor().getMe();
        object.getAuthor().setMe(false);
        simpMessagingTemplate.convertAndSend(
                route,
                object);
        object.getAuthor().setMe(isAuthor);
        simpMessagingTemplate.convertAndSendToUser(
                principal.getName(),
                route,
                object);

    }

}
