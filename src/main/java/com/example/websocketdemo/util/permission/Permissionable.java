package com.example.websocketdemo.util.permission;

public interface Permissionable {
    boolean hasPermission(long userId, long entityId);
}
