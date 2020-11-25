package com.ravindra.chess.game.exception;

public class InvalidMoveException  extends RuntimeException{

    private static final String msg = "Move not Valid";

    public InvalidMoveException(String message){
        super(msg + message);
    }
}
