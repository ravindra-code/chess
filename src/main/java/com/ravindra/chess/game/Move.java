package com.ravindra.chess.game;

public class Move {

    private Tile source;
    private Tile destination;
    private Color color;
    private boolean isCastleMove;

    public Color getColor() {
        return color;
    }

    public Move(){
        this.isCastleMove = false;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Move(Tile source, Tile destination) {
        this.source = source;
        this.destination = destination;
        this.isCastleMove = false;
    }

    public Move(Tile source, Tile destination, boolean isCastleMove) {
        this.source = source;
        this.destination = destination;
        this.isCastleMove = true;
    }

    public Tile getSource() {
        return source;
    }

    public void setSource(Tile source) {
        this.source = source;
    }

    public Tile getDestination() {
        return destination;
    }

    public void setDestination(Tile destination) {
        this.destination = destination;
    }

    public boolean isCastleMove() {
        return isCastleMove;
    }

    public void setCastleMove(boolean castleMove) {
        isCastleMove = castleMove;
    }
}
