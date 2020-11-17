package com.ravindra.chess.game.piece;

import com.ravindra.chess.game.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class Piece implements Cloneable{

    public int counter;
    protected Status status;
    protected Color color;
    public Tile previousPosition;

    public Piece() {
        this.status = Status.ALIVE;
        this.counter = 0;
    }

    public Status getStatus() {
        return status;
    }

    public abstract boolean isValidMove(Board board, Move move);

    public abstract boolean isPathBlocked(Board board, Move move);

    public List<Tile> getAllPossibleMoves(Board board, Position position){
        ArrayList<Tile> possibleMoves = new ArrayList<>();

        Arrays.stream(Tile.values()).forEach(e->{
            Move move = new Move(Tile.getTile(position.getX(), position.getY()),
                    Tile.getTile(e.getX(), e.getY()));
            if (isValidMove(board, move)){
                possibleMoves.add(e);
            }
        });
        return possibleMoves;
    }

    public Tile getPreviousPosition() {
        return previousPosition;
    }

    public void setPreviousPosition(Tile previousPosition) {
        this.previousPosition = previousPosition;
    }

    public abstract List<Tile> tracePath(Move move);

    public synchronized void setCounter(int counter) {
        this.counter = counter;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public synchronized void increment() {
        counter++;
    }

    public synchronized int getCounter() {
        return counter;
    }

    @Override
    public abstract Object clone() throws CloneNotSupportedException;
}
