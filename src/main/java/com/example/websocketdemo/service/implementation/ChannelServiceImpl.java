package com.example.websocketdemo.service.implementation;

import com.example.websocketdemo.model.Channel;
import com.example.websocketdemo.model.User;
import com.example.websocketdemo.model.dto.ChannelDTO;
import com.example.websocketdemo.repository.ChannelRepository;
import com.example.websocketdemo.service.ChannelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ChannelServiceImpl implements ChannelService {

    @Autowired
    private ChannelRepository channelRepository;

    @Autowired
    private ConversionService conversionService;

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
    public ChannelDTO editChannel(ChannelDTO channel) throws Exception {
        duplicateChecker(channel.getTitle(), channelRepository.getTitleById(channel.getId()));
        Channel channelInstance = channelRepository.save(convertToEntity(channel));
        return convertToDTO(channelInstance);
    }

    @Override
    public void removeChannel(ChannelDTO channel) throws Exception {
        channelRepository.delete(convertToEntity(channel));
    }

    @Override
    public List<Channel> readAll() {
        List<Channel> channelList = new ArrayList<>();
        channelRepository.findAll().forEach(channelList::add);
        return channelList;
    }

    @Override
    public ChannelDTO convertToDTO(Channel channel) throws Exception {
        return conversionService.convert(channel, ChannelDTO.class);
    }

    @Override
    public Channel convertToEntity(ChannelDTO channelDTO) throws Exception {
        return conversionService.convert(channelDTO, Channel.class);
    }
}
