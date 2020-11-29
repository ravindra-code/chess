package com.ravindra.chess;

import com.ravindra.chess.game.*;
import com.ravindra.chess.game.dto.ChessMan;
import com.ravindra.chess.game.piece.King;
import com.ravindra.chess.game.piece.Pieces;
import com.ravindra.chess.game.piece.Queen;
import com.ravindra.chess.game.piece.Status;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class TestQueen {

    @Test
    public void testIsValidMove() {
        Board testBoard = new Board();
        List<ChessMan> layout = new ArrayList<>();
        layout.add(new ChessMan(Color.BLACK.name(), Pieces.QUEEN.name(), 4, 4, 5));
        layout.add(new ChessMan(Color.BLACK.name(), Pieces.ROOK.name(), 5, 2, 1));
        layout.add(new ChessMan(Color.WHITE.name(), Pieces.BISHOP.name(), 6, 4, 3));
        testBoard.createChessBoard(layout);
        Assert.assertTrue(new Queen(Status.ALIVE, Color.BLACK).isValidMove(testBoard, new Move(Tile.getTile(4, 4), Tile.getTile(4, 0))));
        Assert.assertTrue(new Queen(Status.ALIVE, Color.BLACK).isValidMove(testBoard, new Move(Tile.getTile(4, 4), Tile.getTile(6, 4))));
        Assert.assertTrue(new Queen(Status.ALIVE, Color.BLACK).isValidMove(testBoard, new Move(Tile.getTile(4, 4), Tile.getTile(5, 5))));

        layout.clear();
        layout.add(new ChessMan(Color.BLACK.name(), Pieces.QUEEN.name(), 0, 0, 5));
        testBoard.createChessBoard(layout);
        Assert.assertTrue(new Queen(Status.ALIVE, Color.BLACK).isValidMove(testBoard, new Move(Tile.getTile(0, 0), Tile.getTile(7, 7))));
        Assert.assertTrue(new Queen(Status.ALIVE, Color.BLACK).isValidMove(testBoard, new Move(Tile.getTile(0, 0), Tile.getTile(6, 6))));
        Assert.assertTrue(new Queen(Status.ALIVE, Color.BLACK).isValidMove(testBoard, new Move(Tile.getTile(0, 0), Tile.getTile(0, 7))));

    }

    @Test
    public void testAllPossibleMoves(){
        Board testBoard = new Board();
        List<ChessMan> layout = new ArrayList<>();
        layout.add(new ChessMan(Color.WHITE.name(), Pieces.QUEEN.name(), 3, 2, 2));
        testBoard.createChessBoard(layout);
        Position position = testBoard.getPieceAtTileLocation(Tile.getTile(3,2));
        List<Tile> moves = position.getPiece().getAllPossibleMoves(testBoard, position);
        Assert.assertEquals(moves.size(), 25);

        layout.clear();
        layout.add(new ChessMan(Color.WHITE.name(), Pieces.QUEEN.name(), 3, 2, 2));
        layout.add(new ChessMan(Color.BLACK.name(), Pieces.PAWN.name(), 7, 6, 2));
        testBoard.createChessBoard(layout);
        position = testBoard.getPieceAtTileLocation(Tile.getTile(3,2));
        moves = position.getPiece().getAllPossibleMoves(testBoard, position);
        Assert.assertEquals(moves.size(), 25);

        layout.clear();
        layout.add(new ChessMan(Color.WHITE.name(), Pieces.QUEEN.name(), 3, 2, 2));
        layout.add(new ChessMan(Color.WHITE.name(), Pieces.PAWN.name(), 7, 6, 2));
        testBoard.createChessBoard(layout);
        position = testBoard.getPieceAtTileLocation(Tile.getTile(3,2));
        moves = position.getPiece().getAllPossibleMoves(testBoard, position);
        Assert.assertEquals(moves.size(), 24);

        layout.clear();
        layout.add(new ChessMan(Color.WHITE.name(), Pieces.QUEEN.name(), 0, 0, 2));
        testBoard.createChessBoard(layout);
        position = testBoard.getPieceAtTileLocation(Tile.getTile(0,0));
        moves = position.getPiece().getAllPossibleMoves(testBoard, position);
        Assert.assertEquals(moves.size(), 21);

        layout.clear();
        layout.add(new ChessMan(Color.WHITE.name(), Pieces.QUEEN.name(), 0, 0, 2));
        layout.add(new ChessMan(Color.BLACK.name(), Pieces.ROOK.name(), 7, 7, 2));
        layout.add(new ChessMan(Color.WHITE.name(), Pieces.ROOK.name(), 0, 7, 2));
        testBoard.createChessBoard(layout);
        position = testBoard.getPieceAtTileLocation(Tile.getTile(0,0));
        moves = position.getPiece().getAllPossibleMoves(testBoard, position);
        Assert.assertEquals(moves.size(), 20);

        layout.clear();
        layout.add(new ChessMan(Color.WHITE.name(), Pieces.QUEEN.name(), 3, 2, 2));
        layout.add(new ChessMan(Color.WHITE.name(), Pieces.PAWN.name(), 7, 6, 2));
        layout.add(new ChessMan(Color.WHITE.name(), Pieces.PAWN.name(), 4, 2, 2));
        testBoard.createChessBoard(layout);
        position = testBoard.getPieceAtTileLocation(Tile.getTile(3,2));
        moves = position.getPiece().getAllPossibleMoves(testBoard, position);
        Assert.assertEquals(moves.size(), 20);
    }

    @Test
    public void isPathBlocked() {

    }
}
