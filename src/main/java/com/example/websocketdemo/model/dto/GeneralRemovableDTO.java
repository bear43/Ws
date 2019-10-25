package com.example.websocketdemo.model.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class GeneralRemovableDTO extends GeneralDTO {
    protected Boolean removed;
}
