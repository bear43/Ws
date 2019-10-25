package com.example.websocketdemo.model.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class UserDTO extends GeneralDTO {

    private String username;

    /* Is user author(owner) of current element? */
    private Boolean me;

    public UserDTO(Long id, String username, Boolean me) {
        this.id = id;
        this.username = username;
        this.me = me;
    }

    public UserDTO(Long id, String username) {
        this(id, username, false);
    }

    public UserDTO(String username) {
        this(null, username);
    }

    public UserDTO() {}

    public void isAuthor(Long id) {
        if(id == null)
            this.me = false;
        else
            this.me = this.id.equals(id);
    }
}
