package com.ravindra.chess.game.piece;

import com.ravindra.chess.game.Board;
import com.ravindra.chess.game.Color;
import com.ravindra.chess.game.Move;
import com.ravindra.chess.game.Tile;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Queen extends Piece {

    Logger log = LoggerFactory.getLogger(this.getClass().getName());

    Bishop bishop;

    public Queen(Status status, Color color) {
        this.status = status;
        this.color = color;
    }

    @Override
    public boolean isValidMove(Board board, Move move) {
        log.debug("##Queen:: isValidMove:: Validate if the move is valid..");
        if (move.getDestination().isValid(move.getDestination().getX(), move.getDestination().getY())) {
            if ((Math.abs(move.getDestination().getX() - move.getSource().getX()) ==
                    Math.abs(move.getDestination().getY() - move.getSource().getY())) ||
                    (move.getSource().getX() == move.getDestination().getX()) ||
                    (move.getSource().getY() == move.getDestination().getY())) {


                if (!board.isTileEmpty(move.getDestination()) &&
                        board.getPieceAtTileLocation(move.getDestination()).getPiece().getColor()
                                ==board.getPieceAtTileLocation(move.getSource()).getPiece().getColor()){
                    return false;
                }
                if (!isPathBlocked(board, move)) {
                    log.debug("##Queen:: isValidMove:: Valid Move..");
                    return true;
                }
            }
        }
        log.debug("##Queen:: isValidMove:: Invalid Move..");
        return false;
    }

    @Override
    public List<Tile> tracePath(Move move) {
        return null;
    }

    @Override
    public boolean isPathBlocked(Board board, Move move) {
        log.debug("##Queen:: isPathBlocked:: Validate if the path is blocked..");
        return new Bishop().isPathBlocked(board, move) || new Rook().isPathBlocked(board, move);
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return new Queen(this.getStatus(), this.getColor());
    }

}
