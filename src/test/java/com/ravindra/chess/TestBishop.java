package com.ravindra.chess;

import com.ravindra.chess.game.*;
import com.ravindra.chess.game.dto.ChessMan;
import com.ravindra.chess.game.piece.*;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class TestBishop {

    @Test
    public void validateValidMoveForBishop() {

        Board testBoard = new Board();
        List<ChessMan> layout = new ArrayList<>();
        layout.add(new ChessMan(Color.WHITE.name(), Pieces.BISHOP.name(), 3, 5, 2));
        layout.add(new ChessMan(Color.BLACK.name(), Pieces.PAWN.name(), 5, 2, 1));
        layout.add(new ChessMan(Color.WHITE.name(), Pieces.PAWN.name(), 2, 3, 2));
        testBoard.createChessBoard(layout);
        Assert.assertFalse(new Bishop().isValidMove(testBoard, new Move(Tile.getTile(3, 5),
                Tile.getTile(3, 2))));
        Assert.assertFalse(new Bishop().isValidMove(testBoard, new Move(Tile.getTile(3, 5),
                Tile.getTile(5, 2))));
        Assert.assertFalse(new Bishop().isValidMove(testBoard, new Move(Tile.getTile(3, 5),
                Tile.getTile(1, 2))));
        Assert.assertTrue(new Bishop().isValidMove(testBoard, new Move(Tile.getTile(3, 5),
                Tile.getTile(2, 6))));


    }

    @Test
    public void checkIfPathIsBlocked() {

        Board testBoard = new Board();
        List<ChessMan> layout = new ArrayList<>();
        layout.add(new ChessMan(Color.WHITE.name(), Pieces.BISHOP.name(), 3, 4, 2));
        layout.add(new ChessMan(Color.BLACK.name(), Pieces.PAWN.name(), 5, 2, 1));
        layout.add(new ChessMan(Color.WHITE.name(), Pieces.PAWN.name(), 2, 3, 2));
        testBoard.createChessBoard(layout);
        Assert.assertFalse(new Bishop().isPathBlocked(testBoard, new Move(Tile.getTile(3, 5),
                Tile.getTile(2, 6))));


    }

    @Test
    public void testTracePath() {
        Move move = new Move(Tile.getTile(4, 4), Tile.getTile(1, 1));
        List<Tile> path = new Bishop(Status.ALIVE, Color.BLACK).tracePath(move);
        StringBuilder builder = new StringBuilder();
        path.forEach(e -> {
            builder.append(e.name());
        });
        Assert.assertEquals("E5D4C3", builder.toString());
    }

    @Test
    public void testAllPossibleMoves(){
        Board testBoard = new Board();
        List<ChessMan> layout = new ArrayList<>();
        layout.add(new ChessMan(Color.WHITE.name(), Pieces.BISHOP.name(), 3, 2, 2));
        testBoard.createChessBoard(layout);
        Position position = testBoard.getPieceAtTileLocation(Tile.getTile(3,2));
        List<Tile> moves = position.getPiece().getAllPossibleMoves(testBoard, position);
        Assert.assertEquals(moves.size(), 11);

        layout.clear();
        layout.add(new ChessMan(Color.WHITE.name(), Pieces.BISHOP.name(), 3, 2, 2));
        layout.add(new ChessMan(Color.BLACK.name(), Pieces.ROOK.name(), 4, 3, 2));
        testBoard.createChessBoard(layout);
        position = testBoard.getPieceAtTileLocation(Tile.getTile(3,2));
        moves = position.getPiece().getAllPossibleMoves(testBoard, position);
        Assert.assertEquals(moves.size(), 8);

        layout.clear();
        layout.add(new ChessMan(Color.WHITE.name(), Pieces.BISHOP.name(), 3, 2, 2));
        layout.add(new ChessMan(Color.BLACK.name(), Pieces.ROOK.name(), 5, 4, 2));
        testBoard.createChessBoard(layout);
        position = testBoard.getPieceAtTileLocation(Tile.getTile(3,2));
        moves = position.getPiece().getAllPossibleMoves(testBoard, position);
        Assert.assertEquals(moves.size(), 9);

        layout.clear();
        layout.add(new ChessMan(Color.WHITE.name(), Pieces.BISHOP.name(), 3, 2, 2));
        layout.add(new ChessMan(Color.BLACK.name(), Pieces.ROOK.name(), 5, 4, 2));
        layout.add(new ChessMan(Color.BLACK.name(), Pieces.ROOK.name(), 2, 1, 2));
        testBoard.createChessBoard(layout);
        position = testBoard.getPieceAtTileLocation(Tile.getTile(3,2));
        moves = position.getPiece().getAllPossibleMoves(testBoard, position);
        Assert.assertEquals(moves.size(), 8);

        layout.clear();
        layout.add(new ChessMan(Color.WHITE.name(), Pieces.BISHOP.name(), 3, 2, 2));
        layout.add(new ChessMan(Color.BLACK.name(), Pieces.ROOK.name(), 5, 4, 2));
        layout.add(new ChessMan(Color.BLACK.name(), Pieces.ROOK.name(), 2, 1, 2));
        layout.add(new ChessMan(Color.BLACK.name(), Pieces.BISHOP.name(), 4, 1, 2));
        layout.add(new ChessMan(Color.BLACK.name(), Pieces.BISHOP.name(), 2, 3, 2));
        testBoard.createChessBoard(layout);
        position = testBoard.getPieceAtTileLocation(Tile.getTile(3,2));
        moves = position.getPiece().getAllPossibleMoves(testBoard, position);
        Assert.assertEquals(moves.size(), 5);

        layout.clear();
        layout.add(new ChessMan(Color.WHITE.name(), Pieces.BISHOP.name(), 3, 2, 2));
        layout.add(new ChessMan(Color.WHITE.name(), Pieces.ROOK.name(), 5, 4, 2));
        layout.add(new ChessMan(Color.WHITE.name(), Pieces.ROOK.name(), 2, 1, 2));
        layout.add(new ChessMan(Color.WHITE.name(), Pieces.BISHOP.name(), 4, 1, 2));
        layout.add(new ChessMan(Color.WHITE.name(), Pieces.PAWN.name(), 2, 3, 2));
        testBoard.createChessBoard(layout);
        position = testBoard.getPieceAtTileLocation(Tile.getTile(3,2));
        moves = position.getPiece().getAllPossibleMoves(testBoard, position);
        Assert.assertEquals(moves.size(), 1);

    }

}
