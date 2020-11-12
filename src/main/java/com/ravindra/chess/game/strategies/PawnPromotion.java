package com.ravindra.chess.game.strategies;

import com.ravindra.chess.game.Board;
import com.ravindra.chess.game.Position;
import com.ravindra.chess.game.piece.King;
import com.ravindra.chess.game.piece.Pawn;
import com.ravindra.chess.game.piece.Piece;

public class PawnPromotion{


    public boolean isMoveApplicable(Position position) {
        Piece piece = position.getPiece();
        if (null!=piece && piece instanceof Pawn){
            if (position.getY()==0 || position.getY()==7){
                return true;
            }

        }
        return false;

    }

    public Board initiateStrategy(Board board, Position position, Piece toPromote) {

        if (isMoveApplicable(position) && validatePromotionPiece(position.getPiece(), toPromote)){
            // move the promoted chess piece to the desired location.
            position.setPiece(toPromote);
            board.setPosition(position);

        }else{
            System.out.println("Pawn Promotion strategy is not applicable!!!");
        }
        return board;
    }

    public boolean validatePromotionPiece(Piece oldPiece, Piece toPromote){
        if (null==toPromote){
            return false;
        }
        if (oldPiece.getColor()!=toPromote.getColor()){
            return false;
        }
        if (toPromote instanceof Pawn || toPromote instanceof King){
            return false;
        }
        return true;
    }
}
