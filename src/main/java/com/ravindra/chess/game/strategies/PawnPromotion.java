package com.ravindra.chess.game.strategies;

import com.ravindra.chess.game.Board;
import com.ravindra.chess.game.Position;
import com.ravindra.chess.game.exception.InvalidStrategyException;
import com.ravindra.chess.game.piece.King;
import com.ravindra.chess.game.piece.Pawn;
import com.ravindra.chess.game.piece.Piece;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PawnPromotion {

    Logger log = LoggerFactory.getLogger(this.getClass().getName());

    public boolean isMoveApplicable(Position position) {
        log.debug("##PawnPromotion:: isMoveApplicable:: Validate if the Pawn Promotion move is applicable..");
        Piece piece = position.getPiece();
        if (null != piece && piece instanceof Pawn) {
            if (position.getY() == 0 || position.getY() == 7) {
                return true;
            }

        }
        log.debug("##PawnPromotion:: isMoveApplicable:: Invalid Pawn Promotion move.");
        return false;

    }

    public Board initiateStrategy(Board board, Position position, Piece toPromote) {

        log.debug("##PawnPromoton:: initiateStrategy:: Initiate PawnPromotion Strategy..");
        if (isMoveApplicable(position) && validatePromotionPiece(position.getPiece(), toPromote)) {
            // move the promoted chess piece to the desired location.
            position.setPiece(toPromote);
            board.setPosition(position);

        } else {
            log.error("##PawnPromotion:: initiateStrategy:: Pawn Promotion strategy is not applicable..");
            throw new InvalidStrategyException("Strategy not applicable..");
        }
        return board;
    }

    public boolean validatePromotionPiece(Piece oldPiece, Piece toPromote) {
        log.debug("##PawnPromotion:: validatePromotionPiece:: Validate if the piece to be promoted is valid or not..");
        if (null == toPromote) {
            return false;
        }
        if (oldPiece.getColor() != toPromote.getColor()) {
            return false;
        }
        if (toPromote instanceof Pawn || toPromote instanceof King) {
            return false;
        }
        log.debug("##PawnPromotion:: validatePromotionPiece:: Piece to be promoted is valid..");
        return true;
    }
}
