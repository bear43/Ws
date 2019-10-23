package com.example.websocketdemo.config;

import com.example.websocketdemo.converter.channel.ChannelDTOToChannelConverter;
import com.example.websocketdemo.converter.channel.ChannelToChannelDTOConverter;
import com.example.websocketdemo.converter.message.MessageDTOToMessageConverter;
import com.example.websocketdemo.converter.message.MessageToMessageDTOConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer
{

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new MessageToMessageDTOConverter());
        registry.addConverter(new MessageDTOToMessageConverter());
        registry.addConverter(new ChannelToChannelDTOConverter());
        registry.addConverter(new ChannelDTOToChannelConverter());
    }
}
