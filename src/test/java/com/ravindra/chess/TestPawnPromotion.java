package com.ravindra.chess;

import com.ravindra.chess.game.Board;
import com.ravindra.chess.game.Color;
import com.ravindra.chess.game.Position;
import com.ravindra.chess.game.dto.ChessMan;
import com.ravindra.chess.game.factory.PieceFactory;
import com.ravindra.chess.game.piece.Pieces;
import com.ravindra.chess.game.strategies.PawnPromotion;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class TestPawnPromotion {

    @Test
    public void validatePromotionPiece() {
        Board testBoard = new Board();
        List<ChessMan> layout = new ArrayList<>();
        layout.add(new ChessMan(Color.BLACK.name(), Pieces.PAWN.name(), 4, 0, 6));
        testBoard.createChessBoard(layout);
        Assert.assertTrue(new PawnPromotion().validatePromotionPiece
                (new PieceFactory().getChessPiece(Pieces.PAWN.name(), Color.BLACK.name()),
                        new PieceFactory().getChessPiece(Pieces.QUEEN.name(), Color.BLACK.name())));
        Assert.assertFalse(new PawnPromotion().validatePromotionPiece
                (new PieceFactory().getChessPiece(Pieces.PAWN.name(), Color.BLACK.name()),
                        new PieceFactory().getChessPiece(Pieces.KING.name(), Color.BLACK.name())));
        Assert.assertFalse(new PawnPromotion().validatePromotionPiece
                (new PieceFactory().getChessPiece(Pieces.PAWN.name(), Color.BLACK.name()),
                        new PieceFactory().getChessPiece(Pieces.QUEEN.name(), Color.WHITE.name())));
    }

    @Test
    public void isMoveApplicable() {
        Board testBoard = new Board();
        List<ChessMan> layout = new ArrayList<>();
        layout.add(new ChessMan(Color.BLACK.name(), Pieces.PAWN.name(), 4, 0, 6));
        testBoard.createChessBoard(layout);
        Assert.assertTrue(new PawnPromotion().isMoveApplicable
                (new Position(4, 0, new PieceFactory().getChessPiece(Pieces.PAWN.name(), Color.BLACK.name()))));
        Assert.assertFalse(new PawnPromotion().isMoveApplicable
                (new Position(4, 0, new PieceFactory().getChessPiece(Pieces.ROOK.name(), Color.BLACK.name()))));
        Assert.assertFalse(new PawnPromotion().isMoveApplicable
                (new Position(2, 1, new PieceFactory().getChessPiece(Pieces.PAWN.name(), Color.BLACK.name()))));
    }
}
