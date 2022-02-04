package org.world_time_api_new.exception;

public class WrongAreaException extends RuntimeException{
    public WrongAreaException(String message, Exception e){ super(message, e);}
}
