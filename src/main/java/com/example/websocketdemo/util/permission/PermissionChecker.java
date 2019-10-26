package com.example.websocketdemo.util.permission;

import com.example.websocketdemo.util.FunctionWrappers;

public class PermissionChecker {

    private final String noPermissionExceptionText;

    public PermissionChecker(String noPermissionExceptionText) {
        this.noPermissionExceptionText = noPermissionExceptionText;
    }

    public void doPermissionActionVoid(Permissionable checker, long userId, long entityId, FunctionWrappers.functionWrapperVoid function) throws Exception {
        if(checker.hasPermission(userId, entityId))
            function.apply();
        else
            throw new Exception(noPermissionExceptionText);
    }

    public <T> T doPermissionActionTyped(Permissionable checker, long userId, long entityId, FunctionWrappers.functionWrapperTyped<T> function) throws Exception {
        if(checker.hasPermission(userId, entityId))
            return function.apply();
        else
            throw new Exception(noPermissionExceptionText);
    }
}
