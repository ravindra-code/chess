package com.ravindra.chess.game.strategies;

import com.ravindra.chess.game.Board;
import com.ravindra.chess.game.Move;
import com.ravindra.chess.game.Position;
import com.ravindra.chess.game.Tile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Castling {
    Logger log = LoggerFactory.getLogger(this.getClass().getName());

    public boolean isMoveApplicable(Board board, Position rook, Position king) {

        log.debug("##Castling:: isMoveApplicable:: validate if castling is applicable..");
        if (rook.getPiece().getCounter() == 0 && king.getPiece().getCounter() == 0 &&
                isPathClear(board, king, rook)) {

            return true;
        } else {
            log.error("Castle strategy not applicable!!!");
        }
        return false;

    }

    public boolean isPathClear(Board board, Position king, Position rook) {
        log.debug("##Castling:: isPathClear:: Validate if the path is clear for castling..");
        int[][] threatMatrix = board.calculateThreatMatrix(board, king.getPiece().getColor());
        if (king.getX() < rook.getX()) {
            for (int i = king.getX() + 1; i <= king.getX() + 2; i++) {
                if (threatMatrix[i][king.getY()] == 1 ||
                        !board.isTileEmpty(Tile.getTile(i, king.getY()))) {
                    return false;
                }
            }
        } else {
            for (int i = king.getX(); i <= king.getX() - 2; i--) {
                if (threatMatrix[i][king.getY()] == 1 ||
                        !board.isTileEmpty(Tile.getTile(i, king.getY()))) {
                    return false;
                }
            }
        }
        return true;
    }


    public Board initiateStrategy(Board board, Position rook, Position king) {

        log.debug("##Castling:: initiateStrategy: Castle strategy initiated..");
        if (isMoveApplicable(board, rook, king)) {
            if (king.getX() < rook.getX()) {
                board.changePositions(new Move(Tile.getTile(king.getX(), king.getY()),
                        Tile.getTile(king.getX() + 2, king.getY()), true), king.getPiece());
                board.changePositions(new Move(Tile.getTile(rook.getX(), rook.getY()),
                        Tile.getTile(rook.getX() - 2, rook.getY()), true), rook.getPiece());
            } else {
                board.changePositions(new Move(Tile.getTile(king.getX(), king.getY()),
                        Tile.getTile(king.getX() - 2, king.getY()), true), king.getPiece());
                board.changePositions(new Move(Tile.getTile(rook.getX(), rook.getY()),
                        Tile.getTile(rook.getX() + 3, rook.getY()), true), rook.getPiece());
            }
        }
        return board;

    }
}
