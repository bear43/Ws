package com.example.websocketdemo.model;

import javassist.bytecode.ByteArray;
import org.thymeleaf.util.ArrayUtils;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public enum MessageType {
    TEXT,
    VOICE
}
