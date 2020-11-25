package com.ravindra.chess;

import com.ravindra.chess.game.*;
import com.ravindra.chess.game.dto.ChessMan;
import com.ravindra.chess.game.piece.Knight;
import com.ravindra.chess.game.piece.Piece;
import com.ravindra.chess.game.piece.Pieces;
import com.ravindra.chess.game.piece.Status;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class TestKnight {

    @Test
    public void isValidMoveForKnight() {
        Board testBoard = new Board();
        List<ChessMan> layout = new ArrayList<>();
        layout.add(new ChessMan(Color.BLACK.name(), Pieces.KNIGHT.name(), 4, 4, 5));
        layout.add(new ChessMan(Color.BLACK.name(), Pieces.ROOK.name(), 5, 2, 1));
        layout.add(new ChessMan(Color.WHITE.name(), Pieces.BISHOP.name(), 6, 3, 3));
        testBoard.createChessBoard(layout);
        Assert.assertTrue(new Knight(Status.ALIVE, Color.BLACK).isValidMove(testBoard, new Move(Tile.getTile(4, 4), Tile.getTile(6, 3))));
        Assert.assertFalse(new Knight(Status.ALIVE, Color.BLACK).isValidMove(testBoard, new Move(Tile.getTile(4, 4), Tile.getTile(2, 5))));
        Assert.assertFalse(new Knight(Status.ALIVE, Color.BLACK).isValidMove(testBoard, new Move(Tile.getTile(4, 4), Tile.getTile(6, 1))));
        Assert.assertTrue(new Knight(Status.ALIVE, Color.BLACK).isValidMove(testBoard, new Move(Tile.getTile(4, 4), Tile.getTile(3, 2))));

    }

    @Test
    public void testAllPossibleMovesForKnight() {
        Board testBoard = new Board();
        List<ChessMan> layout = new ArrayList<>();
        layout.add(new ChessMan(Color.BLACK.name(), Pieces.KNIGHT.name(), 4, 4, 5));
        layout.add(new ChessMan(Color.BLACK.name(), Pieces.ROOK.name(), 5, 2, 1));
        layout.add(new ChessMan(Color.WHITE.name(), Pieces.BISHOP.name(), 6, 3, 3));
        testBoard.createChessBoard(layout);
        Piece piece = new Knight(Status.ALIVE, Color.BLACK);
        StringBuilder builder = new StringBuilder();
        piece.getAllPossibleMoves(testBoard, new Position(4, 4, piece)).forEach(e -> {
            builder.append(e.name());
        });
        Assert.assertEquals("C4C6D3D7F7G4G6", builder.toString());

    }
}
