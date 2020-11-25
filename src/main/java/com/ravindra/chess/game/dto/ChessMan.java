package com.ravindra.chess.game.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class ChessMan {

    @NotNull(message = "Color not specified.")
    private String color;
    @NotNull(message = "Piece name not specified.")
    private String name;
    @NotNull(message = "xCoordinate cannot be null.")
    @Min(value = 0, message = "Invalid coordinate value. ")
    @Max(value = 7, message = "Invalid coordinate value. ")
    private int xCoordinate;
    @NotNull(message = "yCoordinate cannot be null.")
    @Min(value = 0, message = "Invalid coordinate value. ")
    @Max(value = 7, message = "Invalid coordinate value. ")
    private int yCoordinate;
    @NotNull(message = "counter cannot be null.")
    private int counter;

    public ChessMan() {
    }

    public ChessMan(String color, String name, int xCoordinate, int yCoordinate, int counter) {
        this.color = color;
        this.name = name;
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
        this.counter = counter;
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getxCoordinate() {
        return xCoordinate;
    }

    public void setxCoordinate(int xCoordinate) {
        this.xCoordinate = xCoordinate;
    }

    public int getyCoordinate() {
        return yCoordinate;
    }

    public void setyCoordinate(int yCoordinate) {
        this.yCoordinate = yCoordinate;
    }
}
