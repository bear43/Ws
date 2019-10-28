package com.example.websocketdemo.service.implementation;

import com.example.websocketdemo.model.Channel;
import com.example.websocketdemo.model.User;
import com.example.websocketdemo.model.dto.ChannelDTO;
import com.example.websocketdemo.repository.ChannelRepository;
import com.example.websocketdemo.service.ChannelService;
import com.example.websocketdemo.service.UserService;
import org.apache.tomcat.util.security.PermissionCheck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import javax.servlet.annotation.ServletSecurity;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ChannelServiceImpl implements ChannelService {

    @Autowired
    private ChannelRepository channelRepository;

    @Autowired
    private ConversionService conversionService;

    @Autowired
    private UserService userService;

    private void duplicateChecker(String title, String oldTitle) throws Exception {
        boolean existsWithThisTitle = channelRepository.existsByTitle(title);
        boolean currentTitleAreTheSameAsNewTitle = existsWithThisTitle && (oldTitle != null && oldTitle.equals(title));
        if(currentTitleAreTheSameAsNewTitle) throw new Exception("Current title are the same as new title");
        if(existsWithThisTitle) throw new Exception("Channel already defined");
    }

    @Override
    public ChannelDTO createChannel(String title, User author) throws Exception {
        duplicateChecker(title, null);
        Channel channel = new Channel(title, author);
        channelRepository.save(channel);
        return convertToDTO(channel);
    }

    @Override
    @PreAuthorize("@channelRepository.hasPermission(@userServiceImpl.getCurrentUser().getId(), #channel.getId())")
    public ChannelDTO editChannel(ChannelDTO channel) throws Exception {
        duplicateChecker(channel.getTitle(), channelRepository.getTitleById(channel.getId()));
        Channel channelInstance = channelRepository.save(convertToEntity(channel));
        return convertToDTO(channelInstance);
        /*return  permissionChecker.doPermissionActionTyped(this, userService.getCurrentUser().getId(), channel.getId(), () -> {
        });*/
    }

    @Override
    @PreAuthorize("@channelRepository.hasPermission(@userServiceImpl.getCurrentUser().getId(), #id)")
    public void removeChannel(long id) throws Exception {
        channelRepository.deleteById(id);
        /*permissionChecker.doPermissionActionVoid(this, userService.getCurrentUser().getId(), id, () -> {
            channelRepository.deleteById(id);
            //channelRepository.delete(convertToEntity(channel));//TODO: WHY THAT DOESN'T WORK? Does that work like that 'cause messageList are null when it calls em.remove? The difference in only that move
        });*/
    }

    @Override
    public List<Channel> readAll() {
        return StreamSupport.stream(channelRepository.findAll().spliterator(), true).collect(Collectors.toList());
    }

    @Override
    public ChannelDTO convertToDTO(Channel channel) {
        return conversionService.convert(channel, ChannelDTO.class);
    }

    @Override
    public Channel convertToEntity(ChannelDTO channelDTO) {
        return conversionService.convert(channelDTO, Channel.class);
    }

    public boolean hasPermission(long userId, long entityId) {
        return channelRepository.hasPermission(userId, entityId);
    }
}
