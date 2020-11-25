package com.ravindra.chess.game.exception;

public class InvalidStrategyException extends RuntimeException{

    private static final String msg = "Strategy not applicable";

    public InvalidStrategyException(String message){
        super(msg + message);
    }
}
