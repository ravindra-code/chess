package com.ravindra.chess.game.piece;

import com.ravindra.chess.game.*;

import java.util.List;

public class Pawn extends Piece{

    public Pawn(Status status, Color color) {
        this.status = status;
        this.color = color;
    }

    public Pawn(){}

    @Override
    public boolean isValidMove(Board board, Move move) {

        boolean validMove = false;
        if (move.getDestination().isValid(move.getDestination().getX(), move.getDestination().getY())) {
            int diffY = move.getDestination().getY() - move.getSource().getY();
            int diffX = move.getDestination().getX() - move.getSource().getX();
            Position position = board.getPieceAtTileLocation(move.getSource());
            if (this.getColor().equals(Color.WHITE)) {
                if (Math.abs(diffX) == 1) {
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
                if (Math.abs(diffX) == 1) {
                    validMove = isAttackMove(board, move);
                } else if (diffX == 0) {
                    if (diffY == -2) {
                        if (Tile.isValid(move.getDestination().getX(), move.getDestination().getY() - 1)){
                            validMove = ((board.isTileEmpty(move.getDestination())) &&
                                    (board.isTileEmpty(Tile.getTile(
                                            move.getDestination().getX(), move.getDestination().getY() - 1
                                    ))) && (position.getPiece().getCounter() == 0));
                        }else{
                            validMove = false;
                        }

                    } else if (diffY == -1) {
                        validMove = board.isTileEmpty(move.getDestination()) ? true : false;
                    }
                }

            }
        }

        return validMove;

    }

    public boolean isAttackMove(Board board, Move move){

        if (!board.isTileEmpty( move.getDestination())){
            return this.getColor().equals(board.getPosition(move.getDestination()).getPiece().getColor())?false:true;
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
