package com.example.websocketdemo.model.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class GeneralRemovableAuthoredDTO extends GeneralRemovableDTO {
    protected UserDTO author;
}
