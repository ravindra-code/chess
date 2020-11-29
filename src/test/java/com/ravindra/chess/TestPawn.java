package com.ravindra.chess;

import com.ravindra.chess.game.*;
import com.ravindra.chess.game.dto.ChessMan;
import com.ravindra.chess.game.piece.*;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestPawn {

    @Test
    public void validateValidMoveForPawn() {

        Board testBoard = new Board();
        List<ChessMan> layout = new ArrayList<>();
        layout.add(new ChessMan(Color.WHITE.name(), Pieces.PAWN.name(), 3, 3, 2));
        layout.add(new ChessMan(Color.BLACK.name(), Pieces.PAWN.name(), 4, 4, 2));
        testBoard.createChessBoard(layout);
        Assert.assertTrue(new Pawn(Status.ALIVE, Color.WHITE).isValidMove(testBoard, new Move(Tile.getTile(3, 3),
                Tile.getTile(3, 4))));
        Assert.assertTrue(new Pawn(Status.ALIVE, Color.WHITE).isValidMove(testBoard, new Move(Tile.getTile(3, 3),
                Tile.getTile(4, 4))));
        Assert.assertFalse(new Pawn(Status.ALIVE, Color.WHITE).isValidMove(testBoard, new Move(Tile.getTile(3, 3),
                Tile.getTile(2, 4))));
        Assert.assertFalse(new Pawn(Status.ALIVE, Color.WHITE).isValidMove(testBoard, new Move(Tile.getTile(3, 3),
                Tile.getTile(3, 5))));


    }

    @Test
    public void checkIfPathIsBlocked() {

        Board testBoard = new Board();
        List<ChessMan> layout = new ArrayList<>();
        layout.add(new ChessMan(Color.WHITE.name(), Pieces.PAWN.name(), 3, 3, 2));
        layout.add(new ChessMan(Color.WHITE.name(), Pieces.PAWN.name(), 4, 4, 2));
        testBoard.createChessBoard(layout);
        Assert.assertFalse(new Pawn(Status.ALIVE, Color.WHITE).isPathBlocked(testBoard, new Move(Tile.getTile(3, 3),
                Tile.getTile(3, 4))));
        Assert.assertTrue(new Pawn(Status.ALIVE, Color.WHITE).isPathBlocked(testBoard, new Move(Tile.getTile(3, 3),
                Tile.getTile(4, 4))));

    }

    @Test
    public void testIfAttackMove() {
        Board testBoard = new Board();
        List<ChessMan> layout = new ArrayList<>();
        layout.add(new ChessMan(Color.WHITE.name(), Pieces.PAWN.name(), 3, 3, 2));
        layout.add(new ChessMan(Color.BLACK.name(), Pieces.ROOK.name(), 3, 4, 1));
        layout.add(new ChessMan(Color.BLACK.name(), Pieces.PAWN.name(), 4, 4, 2));
        testBoard.createChessBoard(layout);
        Assert.assertTrue(new Pawn(Status.ALIVE, Color.WHITE).isAttackMove(testBoard, new Move(Tile.getTile(3, 3),
                Tile.getTile(4, 4))));
        Assert.assertFalse(new Pawn(Status.ALIVE, Color.BLACK).isAttackMove(testBoard, new Move(Tile.getTile(3, 3),
                Tile.getTile(2, 4))));
        Assert.assertFalse(new Pawn(Status.ALIVE, Color.BLACK).isAttackMove(testBoard, new Move(Tile.getTile(3, 3),
                Tile.getTile(3, 4))));

    }

    @Test
    public void testAllPossibleMoves() {
        Board testBoard = new Board();
        List<ChessMan> layout = new ArrayList<>();
        layout.add(new ChessMan(Color.WHITE.name(), Pieces.PAWN.name(), 3, 3, 2));
        testBoard.createChessBoard(layout);
        Position position = testBoard.getPieceAtTileLocation(Tile.getTile(3,3));
        List<Tile> moves = position.getPiece().getAllPossibleMoves(testBoard, position);
        Assert.assertEquals(moves.size(), 1);

        layout.clear();
        layout.add(new ChessMan(Color.WHITE.name(), Pieces.PAWN.name(), 3, 3, 2));
        layout.add(new ChessMan(Color.BLACK.name(), Pieces.ROOK.name(), 4, 4, 2));
        testBoard.createChessBoard(layout);
        position = testBoard.getPieceAtTileLocation(Tile.getTile(3,3));
        moves = position.getPiece().getAllPossibleMoves(testBoard, position);
        Assert.assertEquals(moves.size(), 2);

        layout.clear();
        layout.add(new ChessMan(Color.WHITE.name(), Pieces.PAWN.name(), 3, 3, 2));
        layout.add(new ChessMan(Color.WHITE.name(), Pieces.ROOK.name(), 4, 4, 2));
        testBoard.createChessBoard(layout);
        position = testBoard.getPieceAtTileLocation(Tile.getTile(3,3));
        moves = position.getPiece().getAllPossibleMoves(testBoard, position);
        Assert.assertEquals(moves.size(), 1);

        layout.clear();
        layout.add(new ChessMan(Color.WHITE.name(), Pieces.PAWN.name(), 3, 3, 2));
        layout.add(new ChessMan(Color.WHITE.name(), Pieces.ROOK.name(), 4, 4, 2));
        layout.add(new ChessMan(Color.BLACK.name(), Pieces.BISHOP.name(), 3, 4, 2));
        testBoard.createChessBoard(layout);
        position = testBoard.getPieceAtTileLocation(Tile.getTile(3,3));
        moves = position.getPiece().getAllPossibleMoves(testBoard, position);
        Assert.assertEquals(moves.size(), 0);
    }
}
