package com.ravindra.chess.game.piece;

import com.ravindra.chess.game.Board;
import com.ravindra.chess.game.Color;
import com.ravindra.chess.game.Move;
import com.ravindra.chess.game.Tile;

import java.util.ArrayList;
import java.util.List;

public class Bishop extends Piece{

    public Bishop(){}

    public Bishop(Status status, Color color) {
        this.status = status;
        this.color = color;
    }

    @Override
    public boolean isValidMove(Board board, Move move) {
        if (move.getDestination().isValid(move.getDestination().getX(), move.getDestination().getY())) {

            if (Math.abs(move.getDestination().getX() - move.getSource().getY()) ==
                    Math.abs(move.getDestination().getY() - move.getSource().getX())) {

                if (!isPathBlocked(board, move)) {
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
        int sourceX = move.getSource().getX();
        int sourceY = move.getSource().getY();
        int destinationX = move.getDestination().getX();
        int destinationY = move.getDestination().getY();
        if (sourceX<destinationX && sourceY<destinationY){
            sourceX++; sourceY++;
            while (sourceX<destinationX && sourceY<destinationY){
                path.add(Tile.getTile(sourceX, sourceY));
                sourceX++;
                sourceY++;
            }
        }
        else if (sourceX<destinationX && sourceY>destinationY){
            while (sourceX<destinationX && sourceY>destinationY){
                path.add(Tile.getTile(sourceX, sourceY));
                sourceX++;
                sourceY--;
            }
        }
        else if (sourceX>destinationX && sourceY < destinationY){
            while (sourceX>destinationX && sourceY<destinationY){
                path.add(Tile.getTile(sourceX, sourceY));
                sourceX--;
                sourceY++;
            }
        }
        else {
            while (sourceX>destinationX && sourceY>destinationY){
                path.add(Tile.getTile(sourceX, sourceY));
                sourceX--;
                sourceY--;
            }
        }
        return path;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        Piece clone = new Bishop();
        clone.setStatus(this.getStatus());
        clone.setColor(this.getColor());
        return clone;
    }
}
