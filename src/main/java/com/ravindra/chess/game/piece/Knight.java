package com.ravindra.chess.game.piece;

import com.ravindra.chess.game.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Knight extends Piece {

    Logger log = LoggerFactory.getLogger(this.getClass().getName());

    public Knight(Status status, Color color) {
        this.status = status;
        this.color = color;
    }

    @Override
    public boolean isValidMove(Board board, Move move) {

        log.debug("##Knight:: isValidMove:: Validate if the move is valid for Knight..");
        if (move.getDestination().isValid(move.getDestination().getX(), move.getDestination().getY())) {

            if ((Math.abs(move.getDestination().getX() - move.getSource().getX()) == 1 &&
                    Math.abs(move.getDestination().getY() - move.getSource().getY()) == 2) ||
                    (Math.abs(move.getDestination().getX() - move.getSource().getX()) == 2 &&
                            Math.abs(move.getDestination().getY() - move.getSource().getY()) == 1)) {

                if (board.isTileEmpty
                        (Tile.getTile(move.getDestination().getX(),
                        move.getDestination().getY()))) {
                    return true;
                } else {
                    Piece piece = board.getPieceAtTileLocation(Tile.getTile(move.getDestination().getX(),
                            move.getDestination().getY())).getPiece();
                    if (!this.getColor().equals(piece.getColor())) {
                        return true;
                    }
                }

            }
        }
        log.debug("##Knight:: isValidMove:: Move not valid..");
        return false;
    }

    @Override
    public List<Tile> getAllPossibleMoves(Board board, Position position) {
        log.debug("##Knight:: getAllPossibleMoves:: Find all possible moves for the knight..");
        List<Tile> moves = new ArrayList<>();
        Arrays.stream(Tile.values())
                .forEach(e -> {
            Move move = new Move(Tile.getTile(position.getX(), position.getY()),
                    Tile.getTile(e.getX(), e.getY()));
            if (isValidMove(board, move)) {
                moves.add(e);
            }
        });
        return moves;
    }

    @Override
    public boolean isPathBlocked(Board board, Move move) {
        return isValidMove(board, move);
    }

    @Override
    public List<Tile> tracePath(Move move) {
        return null;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return new Knight(this.getStatus(), this.getColor());
    }
}
