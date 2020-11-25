package com.ravindra.chess.game.piece;

import com.ravindra.chess.game.Board;
import com.ravindra.chess.game.Color;
import com.ravindra.chess.game.Move;
import com.ravindra.chess.game.Tile;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Rook extends Piece {

    Logger log = LoggerFactory.getLogger(this.getClass().getName());

    public Rook() {
    }

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

        log.debug("##Rook:: isValidMove:: Validate if the move is valid..");
        if (move.getDestination().isValid(move.getDestination().getX(), move.getDestination().getY())) {

            if (move.getSource().getX() == move.getDestination().getX() ||
                    move.getSource().getY() == move.getDestination().getY()) {


                if (!board.isTileEmpty(move.getDestination()) &&
                        board.getPieceAtTileLocation(move.getDestination()).getPiece().getColor()
                                ==board.getPieceAtTileLocation(move.getSource()).getPiece().getColor()){
                    return false;
                }
                if (!isPathBlocked(board, move) || move.isCastleMove()) {
                    return true;

                }
            }
        }
        return false;

    }

    @Override
    public boolean isPathBlocked(Board board, Move move) {

        log.debug("##Rook:: isPathBlocked:: Validate if the path is blocked..");
        List<Tile> path = tracePath(move);
        for (Tile tile : path) {
            if (!board.isTileEmpty(tile)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public List<Tile> tracePath(Move move) {
        log.debug("##Rook:: tracePath:: Trace Path for Rook's movement..");
        List<Tile> path = new ArrayList<>();
        if (move.getDestination().getX() == move.getSource().getX()) {
            int min = Math.min(move.getDestination().getY(), move.getSource().getY());
            int max = Math.max(move.getDestination().getY(), move.getSource().getY());
            for (int i = min + 1; i < max; i++) {
                path.add(Tile.getTile(move.getDestination().getX(), i));
            }
        } else if (move.getDestination().getY() == move.getDestination().getY()) {
            int min = Math.min(move.getDestination().getX(), move.getSource().getX());
            int max = Math.max(move.getDestination().getX(), move.getSource().getX());
            for (int i = min + 1; i < max; i++) {
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
