package com.example.websocketdemo.util;

public class FunctionWrappers {
    @FunctionalInterface
    public interface functionWrapperVoid {
        void apply() throws Exception;
    }

    @FunctionalInterface
    public interface functionWrapperTyped<T> {
        T apply() throws Exception;
    }
}
