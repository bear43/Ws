package com.example.websocketdemo.util.converter;

import org.thymeleaf.util.ArrayUtils;

import java.util.Arrays;
import java.util.Optional;

import static java.util.Spliterators.spliterator;

public class Converter {
    public static byte[] convert(Byte[] array) throws Exception {
        if(array == null) throw new NullPointerException();
        byte[] byteArray = new byte[array.length];
        for (int i = 0; i < array.length; i++) {
            byteArray[i] = array[i];
        }
        //System.arraycopy(array, 0, byteArray, 0, array.length);
        return byteArray;
    }

    public static Byte[] convert(byte[] array) throws Exception {
        Byte[] byteArray = new Byte[array.length];
        for (int i = 0; i < array.length; i++) {
            byteArray[i] = array[i];
        }
        //System.arraycopy(array, 0, byteArray, 0, array.length);
        return byteArray;
    }
}
