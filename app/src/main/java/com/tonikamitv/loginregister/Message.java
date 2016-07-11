package com.tonikamitv.loginregister;

import java.io.Serializable;

/**
 * Created by tylweiss on 12/16/2015.
 */
public class Message implements Serializable {
    String username, message;

    public Message(String username, String message ){

        this.username = username;
        this.message = message;
    }


}
