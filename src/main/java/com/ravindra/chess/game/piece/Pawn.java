package com.ravindra.chess.game.piece;

import com.ravindra.chess.game.*;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Pawn extends Piece {

    Logger log = LoggerFactory.getLogger(this.getClass().getName());

    public Pawn(Status status, Color color) {
        this.status = status;
        this.color = color;
    }

    public Pawn() {
    }

    @Override
    public boolean isValidMove(Board board, Move move) {

        log.debug("##Pawn:: isValidMove:: Validate if the move is valid for Pawn..");
        boolean validMove = false;
        if (move.getDestination().isValid(move.getDestination().getX(), move.getDestination().getY())) {
            int diffY = move.getDestination().getY() - move.getSource().getY();
            int diffX = move.getDestination().getX() - move.getSource().getX();
            Position position = board.getPieceAtTileLocation(move.getSource());
            if (this.getColor().equals(Color.WHITE)) {
                if (Math.abs(diffX) == 1 && diffY==1) {
                    validMove = isAttackMove(board, move);
                } else if (diffX == 0) {
                    if (diffY == 2) {
                        validMove = ((board.isTileEmpty(move.getDestination())) &&
                                (board.isTileEmpty(Tile.getTile(
                                        move.getDestination().getX(), move.getDestination().getY() + 1)) &&
                                        (position.getPiece().getCounter() == 0)

                                ));

                    } else if (diffY == 1) {
                        validMove = board.isTileEmpty(move.getDestination()) ? true : false;
                    }
                }

            } else {
                if (Math.abs(diffX) == 1 && diffY==-1) {
                    validMove = isAttackMove(board, move);
                } else if (diffX == 0) {
                    if (diffY == -2) {
                        if (Tile.isValid(move.getDestination().getX(), move.getDestination().getY() - 1)) {
                            validMove = ((board.isTileEmpty(move.getDestination())) &&
                                    (board.isTileEmpty(Tile.getTile(
                                            move.getDestination().getX(), move.getDestination().getY() - 1
                                    ))) && (position.getPiece().getCounter() == 0));
                        } else {
                            validMove = false;
                        }

                    } else if (diffY == -1) {
                        validMove = board.isTileEmpty(move.getDestination()) ? true : false;
                    }
                }

            }
        }

        log.debug("##Pawn:: isValidMove:: Is move valid for Pawn:: "+ validMove);
        return validMove;

    }

    public boolean isAttackMove(Board board, Move move) {

        log.debug("##Pawn:: isAttackMove:: Validate if the move is attack move.. ");
        if (!board.isTileEmpty(move.getDestination())) {
            return this.getColor().equals(board.getPosition(move.getDestination()).getPiece().getColor()) ? false : true;
        }
        return false;
    }

    @Override
    public boolean isPathBlocked(Board board, Move move) {
        return !isValidMove(board, move);
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return new Pawn(this.getStatus(), this.getColor());
    }

    @Override
    public List<Tile> tracePath(Move move) {
        return null;
    }

}
