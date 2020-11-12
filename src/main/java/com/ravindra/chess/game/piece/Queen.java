package com.ravindra.chess.game.piece;

import com.ravindra.chess.game.Board;
import com.ravindra.chess.game.Color;
import com.ravindra.chess.game.Move;
import com.ravindra.chess.game.Tile;

import java.util.List;

public class Queen extends Piece{

    public Queen(Status status, Color color) {
        this.status = status;
        this.color = color;
    }

    @Override
    public boolean isValidMove(Board board, Move move) {
        if (move.getDestination().isValid(move.getDestination().getX(), move.getDestination().getY())) {
            if ((Math.abs(move.getDestination().getX() - move.getSource().getX()) ==
                    Math.abs(move.getDestination().getY() - move.getSource().getX())) ||
                    move.getSource().getX() == move.getDestination().getX() ||
                    move.getSource().getY() == move.getDestination().getY()) {

                if (!isPathBlocked(board, move)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public List<Tile> tracePath(Move move) {
        return null;
    }

    @Override
    public boolean isPathBlocked(Board board, Move move) {
        return new Bishop().isPathBlocked(board, move) || new Rook().isPathBlocked(board, move);
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return new Queen(this.getStatus(), this.getColor());
    }

}
