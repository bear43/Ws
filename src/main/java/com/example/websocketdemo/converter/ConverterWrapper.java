package com.example.websocketdemo.converter;

import org.springframework.core.convert.converter.Converter;

public interface ConverterWrapper<Type1, Type2> {
    Converter<Type1, Type2> getEntityToDTOInstance();
    Converter<Type2, Type1> getDTOToEntityInstance();
}
