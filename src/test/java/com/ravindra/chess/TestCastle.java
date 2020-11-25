package com.ravindra.chess;

import com.ravindra.chess.game.*;
import com.ravindra.chess.game.dto.ChessMan;
import com.ravindra.chess.game.piece.*;
import com.ravindra.chess.game.strategies.Castling;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class TestCastle {

    @Test
    public void isPathClear() {
        Board testBoard = new Board();
        List<ChessMan> layout = new ArrayList<>();
        layout.add(new ChessMan(Color.BLACK.name(), Pieces.KING.name(), 4, 7, 0));
        layout.add(new ChessMan(Color.BLACK.name(), Pieces.ROOK.name(), 7, 7, 0));
        testBoard.createChessBoard(layout);
        King king = new King(Status.ALIVE, Color.BLACK);
        Rook rook = new Rook(Status.ALIVE, Color.BLACK);
        Assert.assertTrue(new Castling().isPathClear(testBoard, new Position(4, 7, king),
                new Position(7, 7, rook)));
        layout.add(new ChessMan(Color.BLACK.name(), Pieces.KNIGHT.name(), 6, 7, 0));
        testBoard.createChessBoard(layout);
        Assert.assertFalse(new Castling().isPathClear(testBoard, new Position(4, 7, king),
                new Position(7, 7, rook)));
    }

    @Test
    public void isMoveApplicable() {

        Board testBoard = new Board();
        List<ChessMan> layout = new ArrayList<>();
        layout.add(new ChessMan(Color.BLACK.name(), Pieces.KING.name(), 4, 7, 0));
        layout.add(new ChessMan(Color.BLACK.name(), Pieces.ROOK.name(), 7, 7, 0));
        layout.add(new ChessMan(Color.BLACK.name(), Pieces.ROOK.name(), 0, 7, 0));
        testBoard.createChessBoard(layout);
        King king = new King(Status.ALIVE, Color.BLACK);
        Rook rook = new Rook(Status.ALIVE, Color.BLACK);
        Assert.assertTrue(new Castling().isMoveApplicable(testBoard, new Position(4, 7, king),
                new Position(7, 7, rook)));
        Rook rook1 = new Rook(Status.ALIVE, Color.BLACK, 1);
        testBoard.createChessBoard(layout);
        Assert.assertFalse(new Castling().isMoveApplicable(testBoard, new Position(4, 7, king),
                new Position(0, 7, rook1)));


    }

    @Test
    public void testStrategy() {
        Board testBoard = new Board();
        List<ChessMan> layout = new ArrayList<>();
        layout.add(new ChessMan(Color.BLACK.name(), Pieces.KING.name(), 4, 7, 0));
        layout.add(new ChessMan(Color.BLACK.name(), Pieces.ROOK.name(), 7, 7, 0));
        layout.add(new ChessMan(Color.BLACK.name(), Pieces.ROOK.name(), 0, 7, 0));
        testBoard.createChessBoard(layout);
        King king = new King(Status.ALIVE, Color.BLACK);
        Rook rook = new Rook(Status.ALIVE, Color.BLACK);
        Board board = new Castling().initiateStrategy(testBoard, new Position(7, 7, rook),
                new Position(4, 7, king));
        Assert.assertTrue(board.getPosition(Tile.getTile(6, 7)).getPiece() instanceof King);
        Assert.assertTrue(board.getPosition(Tile.getTile(5, 7)).getPiece() instanceof Rook);


    }
}
