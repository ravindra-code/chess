package com.ravindra.chess;

import com.ravindra.chess.game.Board;
import com.ravindra.chess.game.Color;
import com.ravindra.chess.game.Move;
import com.ravindra.chess.game.Tile;
import com.ravindra.chess.game.dto.ChessMan;
import com.ravindra.chess.game.piece.Knight;
import com.ravindra.chess.game.piece.Pieces;
import com.ravindra.chess.game.piece.Rook;
import com.ravindra.chess.game.piece.Status;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class TestRook {

    @Test
    public void isValidMove() {
        Board testBoard = new Board();
        List<ChessMan> layout = new ArrayList<>();
        layout.add(new ChessMan(Color.BLACK.name(), Pieces.ROOK.name(), 4, 4, 5));
        layout.add(new ChessMan(Color.WHITE.name(), Pieces.PAWN.name(), 4, 2, 1));
        testBoard.createChessBoard(layout);
        Assert.assertFalse(new Rook(Status.ALIVE, Color.BLACK).isValidMove(testBoard, new Move(Tile.getTile(4, 4), Tile.getTile(4, 0))));
        Assert.assertTrue(new Rook(Status.ALIVE, Color.BLACK).isValidMove(testBoard, new Move(Tile.getTile(4, 4), Tile.getTile(4, 6))));
        Assert.assertTrue(new Rook(Status.ALIVE, Color.BLACK).isValidMove(testBoard, new Move(Tile.getTile(4, 4), Tile.getTile(2, 4))));


    }

    @Test
    public void testTracePath() {
        Move move = new Move(Tile.getTile(4, 4), Tile.getTile(4, 0));
        List<Tile> path = new Rook(Status.ALIVE, Color.BLACK).tracePath(move);
        StringBuilder builder = new StringBuilder();
        path.forEach(e -> {
            builder.append(e.name());
        });
        Assert.assertEquals("E2E3E4", builder.toString());

    }

    @Test
    public void isPathBlocked() {
        Board testBoard = new Board();
        List<ChessMan> layout = new ArrayList<>();
        layout.add(new ChessMan(Color.BLACK.name(), Pieces.ROOK.name(), 4, 4, 5));
        layout.add(new ChessMan(Color.WHITE.name(), Pieces.PAWN.name(), 4, 2, 1));
        testBoard.createChessBoard(layout);
        Assert.assertTrue(new Rook(Status.ALIVE, Color.BLACK).isPathBlocked(testBoard, new Move(Tile.getTile(4, 4), Tile.getTile(4, 0))));
        Assert.assertFalse(new Rook(Status.ALIVE, Color.BLACK).isPathBlocked(testBoard, new Move(Tile.getTile(4, 4), Tile.getTile(4, 6))));
        Assert.assertFalse(new Rook(Status.ALIVE, Color.BLACK).isPathBlocked(testBoard, new Move(Tile.getTile(4, 4), Tile.getTile(2, 4))));

    }
}
