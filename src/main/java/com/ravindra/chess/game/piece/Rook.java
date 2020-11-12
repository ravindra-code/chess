package com.ravindra.chess.game.piece;

import com.ravindra.chess.game.Board;
import com.ravindra.chess.game.Color;
import com.ravindra.chess.game.Move;
import com.ravindra.chess.game.Tile;

import java.util.ArrayList;
import java.util.List;

public class Rook extends Piece{

    public Rook(){}

    public Rook(Status status, Color color) {
        this.status = status;
        this.color = color;
    }

    public Rook(Status status, Color color, int counter) {
        this.status = status;
        this.color = color;
        this.counter = counter;
    }

    @Override
    public boolean isValidMove(Board board, Move move) {

        if (move.getDestination().isValid(move.getDestination().getX(), move.getDestination().getY())) {

            if (move.getSource().getX() == move.getDestination().getX() ||
                    move.getSource().getY() == move.getDestination().getY()) {

                if (!isPathBlocked(board, move) || move.isCastleMove()) {
                    return true;

                }
            }
        }
        return false;

    }

    @Override
    public boolean isPathBlocked(Board board, Move move) {

        List<Tile> path = tracePath(move);
        for (Tile tile: path){
            if (!board.isTileEmpty(tile)){
                return true;
            }
        }
        return false;
    }

    @Override
    public List<Tile> tracePath(Move move) {
        List<Tile> path = new ArrayList<>();
        if (move.getDestination().getX()==move.getSource().getX()){
            int min = Math.min(move.getDestination().getY(),move.getSource().getY());
            int max = Math.max(move.getDestination().getY(),move.getSource().getY());
            for (int i=min+1; i<max; i++ ){
                path.add(Tile.getTile(move.getDestination().getX(), i));
            }
        }
        else if (move.getDestination().getY()==move.getDestination().getY()){
            int min = Math.min(move.getDestination().getX(),move.getSource().getX());
            int max = Math.max(move.getDestination().getX(),move.getSource().getX());
            for (int i=min+1; i<max; i++ ){
                path.add(Tile.getTile(i, move.getDestination().getY()));
            }
        }
        return path;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return new Rook(this.getStatus(), this.getColor());
    }


}
