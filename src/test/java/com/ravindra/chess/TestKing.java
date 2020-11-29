package com.ravindra.chess;

import com.ravindra.chess.game.*;
import com.ravindra.chess.game.dto.ChessMan;
import com.ravindra.chess.game.piece.King;
import com.ravindra.chess.game.piece.Knight;
import com.ravindra.chess.game.piece.Pieces;
import com.ravindra.chess.game.piece.Status;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class TestKing {

    @Test
    public void isValidMove() {
        Board testBoard = new Board();
        List<ChessMan> layout = new ArrayList<>();
        layout.add(new ChessMan(Color.BLACK.name(), Pieces.KING.name(), 4, 4, 5));
        layout.add(new ChessMan(Color.WHITE.name(), Pieces.ROOK.name(), 5, 2, 1));
        layout.add(new ChessMan(Color.WHITE.name(), Pieces.BISHOP.name(), 6, 3, 3));
        testBoard.createChessBoard(layout);
        Assert.assertTrue(new King(Status.ALIVE, Color.BLACK).isValidMove(testBoard, new Move(Tile.getTile(4, 4), Tile.getTile(3, 4))));
        Assert.assertTrue(new King(Status.ALIVE, Color.BLACK).isValidMove(testBoard, new Move(Tile.getTile(4, 4), Tile.getTile(3, 5))));
        Assert.assertTrue(new King(Status.ALIVE, Color.BLACK).isValidMove(testBoard, new Move(Tile.getTile(4, 4), Tile.getTile(5, 4))));
        Assert.assertFalse(new King(Status.ALIVE, Color.BLACK).isValidMove(testBoard, new Move(Tile.getTile(4, 4), Tile.getTile(6, 4))));

    }

    @Test
    public void isKingUnderCheck() {
        Board testBoard = new Board();
        List<ChessMan> layout = new ArrayList<>();
        layout.add(new ChessMan(Color.BLACK.name(), Pieces.KING.name(), 1, 2, 5));
        layout.add(new ChessMan(Color.WHITE.name(), Pieces.KNIGHT.name(), 2, 4, 1));
        testBoard.createChessBoard(layout);
        Assert.assertTrue(new King(Status.ALIVE, Color.BLACK).isKingChecked(testBoard, 1, 2));

    }

    @Test
    public void testAllPossibleMoves(){

        Board testBoard = new Board();
        List<ChessMan> layout = new ArrayList<>();
        layout.add(new ChessMan(Color.WHITE.name(), Pieces.KING.name(), 3, 2, 2));
        testBoard.createChessBoard(layout);
        Position position = testBoard.getPieceAtTileLocation(Tile.getTile(3,2));
        List<Tile> moves = position.getPiece().getAllPossibleMoves(testBoard, position);
        Assert.assertEquals(moves.size(), 8);

        layout.clear();
        layout.add(new ChessMan(Color.WHITE.name(), Pieces.KING.name(), 3, 2, 2));
        layout.add(new ChessMan(Color.WHITE.name(), Pieces.ROOK.name(), 3, 3, 2));
        testBoard.createChessBoard(layout);
        position = testBoard.getPieceAtTileLocation(Tile.getTile(3,2));
        moves = position.getPiece().getAllPossibleMoves(testBoard, position);
        Assert.assertEquals(moves.size(), 7);

        layout.clear();
        layout.add(new ChessMan(Color.WHITE.name(), Pieces.KING.name(), 3, 2, 2));
        layout.add(new ChessMan(Color.WHITE.name(), Pieces.ROOK.name(), 3, 3, 2));
        layout.add(new ChessMan(Color.WHITE.name(), Pieces.ROOK.name(), 4, 3, 2));
        layout.add(new ChessMan(Color.WHITE.name(), Pieces.BISHOP.name(), 2, 3, 2));
        layout.add(new ChessMan(Color.WHITE.name(), Pieces.PAWN.name(), 3, 1, 2));
        testBoard.createChessBoard(layout);
        position = testBoard.getPieceAtTileLocation(Tile.getTile(3,2));
        moves = position.getPiece().getAllPossibleMoves(testBoard, position);
        Assert.assertEquals(moves.size(), 4);

        layout.clear();
        layout.add(new ChessMan(Color.WHITE.name(), Pieces.KING.name(), 3, 2, 2));
        layout.add(new ChessMan(Color.WHITE.name(), Pieces.ROOK.name(), 3, 3, 2));
        layout.add(new ChessMan(Color.WHITE.name(), Pieces.ROOK.name(), 4, 3, 2));
        layout.add(new ChessMan(Color.WHITE.name(), Pieces.BISHOP.name(), 2, 3, 2));
        layout.add(new ChessMan(Color.WHITE.name(), Pieces.PAWN.name(), 3, 1, 2));
        layout.add(new ChessMan(Color.BLACK.name(), Pieces.PAWN.name(), 4, 1, 2));
        testBoard.createChessBoard(layout);
        position = testBoard.getPieceAtTileLocation(Tile.getTile(3,2));
        moves = position.getPiece().getAllPossibleMoves(testBoard, position);
        Assert.assertEquals(moves.size(), 4);
    }

    @Test
    public void isKingUnderCheckMate() throws Exception{

        Board testBoard = new Board();
        List<ChessMan> layout = new ArrayList<>();
        layout.add(new ChessMan(Color.BLACK.name(), Pieces.KING.name(), 1, 2, 5));
        layout.add(new ChessMan(Color.WHITE.name(), Pieces.KNIGHT.name(), 2, 4, 1));
        testBoard.createChessBoard(layout);
        Assert.assertFalse(new King(Status.ALIVE, Color.BLACK).isCheckMate(testBoard, 1, 2));

        layout.clear();
        layout.add(new ChessMan(Color.BLACK.name(), Pieces.KING.name(), 1, 2, 5));
        layout.add(new ChessMan(Color.WHITE.name(), Pieces.KNIGHT.name(), 2, 4, 1));
        layout.add(new ChessMan(Color.BLACK.name(), Pieces.ROOK.name(), 4, 4, 3));
        testBoard.createChessBoard(layout);
        Assert.assertFalse(new King(Status.ALIVE, Color.BLACK).isCheckMate(testBoard, 1, 2));

        layout.clear();
        layout.add(new ChessMan(Color.BLACK.name(), Pieces.KING.name(), 1, 2, 5));
        layout.add(new ChessMan(Color.WHITE.name(), Pieces.KNIGHT.name(), 2, 4, 1));
        layout.add(new ChessMan(Color.BLACK.name(), Pieces.ROOK.name(), 4, 5, 3));
        testBoard.createChessBoard(layout);
        Assert.assertFalse(new King(Status.ALIVE, Color.BLACK).isCheckMate(testBoard, 1, 2));

        layout.clear();
        layout.add(new ChessMan(Color.BLACK.name(), Pieces.KING.name(), 0, 0, 5));
        layout.add(new ChessMan(Color.WHITE.name(), Pieces.ROOK.name(), 0, 3, 1));
        layout.add(new ChessMan(Color.WHITE.name(), Pieces.ROOK.name(), 1, 3, 3));
        testBoard.createChessBoard(layout);
        Assert.assertTrue(new King(Status.ALIVE, Color.BLACK).isCheckMate(testBoard, 0, 0));

        layout.clear();
        layout.add(new ChessMan(Color.BLACK.name(), Pieces.KING.name(), 0, 0, 5));
        layout.add(new ChessMan(Color.WHITE.name(), Pieces.ROOK.name(), 0, 3, 1));
        layout.add(new ChessMan(Color.WHITE.name(), Pieces.ROOK.name(), 1, 3, 3));
        layout.add(new ChessMan(Color.BLACK.name(), Pieces.ROOK.name(), 0, 7, 3));
        testBoard.createChessBoard(layout);
        Assert.assertFalse(new King(Status.ALIVE, Color.BLACK).isCheckMate(testBoard, 0, 0));

        layout.clear();
        layout.add(new ChessMan(Color.BLACK.name(), Pieces.KING.name(), 0, 0, 5));
        layout.add(new ChessMan(Color.WHITE.name(), Pieces.ROOK.name(), 0, 3, 1));
        layout.add(new ChessMan(Color.WHITE.name(), Pieces.ROOK.name(), 1, 3, 3));
        layout.add(new ChessMan(Color.BLACK.name(), Pieces.ROOK.name(), 5, 2, 3));
        testBoard.createChessBoard(layout);
        Assert.assertFalse(new King(Status.ALIVE, Color.BLACK).isCheckMate(testBoard, 0, 0));

        layout.clear();
        layout.add(new ChessMan(Color.BLACK.name(), Pieces.KING.name(), 0, 0, 5));
        layout.add(new ChessMan(Color.WHITE.name(), Pieces.ROOK.name(), 0, 3, 1));
        layout.add(new ChessMan(Color.WHITE.name(), Pieces.ROOK.name(), 1, 3, 3));
        layout.add(new ChessMan(Color.BLACK.name(), Pieces.ROOK.name(), 7, 7, 3));
        testBoard.createChessBoard(layout);
        Assert.assertTrue(new King(Status.ALIVE, Color.BLACK).isCheckMate(testBoard, 0, 0));

    }

}
