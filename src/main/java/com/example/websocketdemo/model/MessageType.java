package com.example.websocketdemo.model;

import com.example.websocketdemo.util.converter.Converter;
import javassist.bytecode.ByteArray;
import org.thymeleaf.util.ArrayUtils;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public enum MessageType {
    TEXT,
    VOICE;
    public <T> T resolveData(Byte[] byteArray) throws Exception {
        switch(this) {
            case TEXT:
                return (T)new String(Converter.convert(byteArray));
            case VOICE:
                return (T)byteArray;
                default:
                    throw new Exception("Unknown message type");
        }
    }
}
