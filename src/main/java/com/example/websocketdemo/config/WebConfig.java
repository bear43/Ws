package com.example.websocketdemo.config;

import com.example.websocketdemo.converter.ConverterWrapper;
import com.example.websocketdemo.converter.channel.ChannelConverter;
import com.example.websocketdemo.converter.channel.ChannelDTOToChannelConverter;
import com.example.websocketdemo.converter.channel.ChannelToChannelDTOConverter;
import com.example.websocketdemo.converter.message.MessageConverter;
import com.example.websocketdemo.converter.message.MessageDTOToMessageConverter;
import com.example.websocketdemo.converter.message.MessageToMessageDTOConverter;
import com.example.websocketdemo.converter.user.UserConverter;
import com.example.websocketdemo.converter.user.UserDTOToUserConverter;
import com.example.websocketdemo.model.Channel;
import com.example.websocketdemo.model.Message;
import com.example.websocketdemo.model.User;
import com.example.websocketdemo.model.dto.ChannelDTO;
import com.example.websocketdemo.model.dto.MessageDTO;
import com.example.websocketdemo.model.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Lazy;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.PostConstruct;

@Configuration
@ComponentScan("com.example.websocketdemo.converter")
public class WebConfig implements WebMvcConfigurer
{

    @Autowired
    @Lazy
    private ConverterWrapper<Message, MessageDTO> messageConverter;

    @Autowired
    @Lazy
    private FormatterRegistry registry;

    @Autowired
    @Lazy
    private ConverterWrapper<User, UserDTO> userConverter;

    @Autowired
    @Lazy
    private ConverterWrapper<Channel, ChannelDTO> channelConverter;

    @Override
    public void addFormatters(FormatterRegistry registry) {
    }

    @PostConstruct
    private void afterConstruct() {
        registry.addConverter(messageConverter.getEntityToDTOInstance());
        registry.addConverter(messageConverter.getDTOToEntityInstance());
        registry.addConverter(channelConverter.getEntityToDTOInstance());
        registry.addConverter(channelConverter.getDTOToEntityInstance());
        registry.addConverter(userConverter.getEntityToDTOInstance());
        registry.addConverter(userConverter.getDTOToEntityInstance());
    }
}
