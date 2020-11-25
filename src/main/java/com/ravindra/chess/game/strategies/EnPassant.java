package com.ravindra.chess.game.strategies;

import com.ravindra.chess.game.Board;
import com.ravindra.chess.game.Move;
import com.ravindra.chess.game.Position;
import com.ravindra.chess.game.Tile;
import com.ravindra.chess.game.exception.InvalidStrategyException;
import com.ravindra.chess.game.piece.Pawn;
import com.ravindra.chess.game.piece.Piece;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EnPassant {

    Logger log = LoggerFactory.getLogger(this.getClass().getName());

    public boolean isMoveApplicable(Board board, Move move, Piece attacker, Piece attacked) {

        log.debug("##Enpassant:: isMoveApplicable:: Validate if EnPassant move is applicable..");
        if (attacker instanceof Pawn && attacked instanceof Pawn) {
            if ((Math.abs(move.getDestination().getX() - move.getSource().getX()) == 1 &&
                    Math.abs(move.getDestination().getY() - move.getSource().getY()) == 1) &&
                    board.isTileEmpty(move.getDestination())) {

                if (Math.abs(attacked.getPreviousPosition().getY() - move.getSource().getY()) == 2 &&
                        !board.isTileEmpty(Tile.getTile(move.getDestination().getX(), move.getSource().getY())) &&
                        board.getPosition(Tile.getTile(move.getDestination().getX(), move.getSource().getY())).getPiece() instanceof Pawn &&
                        board.getPosition(Tile.getTile(move.getDestination().getX(), move.getSource().getY())).getPiece().getColor()
                                != board.getPosition(move.getSource()).getPiece().getColor()) {
                    return true;
                }
            }
        }
        log.debug("##Enpassant:: isMoveApplicable:: Enpassant not applicable..");
        return false;
    }

    public Board initiateStrategy(Board board, Move move, Piece attacker, Piece attacked) {

        log.debug("##Enpassant:: initiateStrategy:: Initiate En passant strategy...");
        if (isMoveApplicable(board, move, attacker, attacked)) {
            board.setPosition(new Position(move.getDestination().getX(), move.getDestination().getY(), attacker));
            board.vacate(move.getSource().getX(), move.getSource().getY());
            attacker.increment();
            board.vacate(move.getDestination().getX(), move.getSource().getY());
        }else{
            log.error("##EnPassant:: initiateStrategy:: EnPassant not applicable..");
            throw new InvalidStrategyException("Enpassant not applicable..");
        }
        return board;
    }
}
