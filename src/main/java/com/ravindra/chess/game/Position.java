package com.ravindra.chess.game;

import com.ravindra.chess.game.piece.Piece;

public class Position implements Cloneable {

    private Piece piece;
    private int x;
    private int y;

    public Position() {

    }

    public Position(int x, int y, Piece piece) {
        this.piece = piece;
        this.x = x;
        this.y = y;
    }

    public Piece getPiece() {
        return piece;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return new Position(this.getX(), this.getY(), (Piece) this.getPiece().clone());
    }
}
