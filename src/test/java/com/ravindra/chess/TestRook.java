package com.ravindra.chess;

import com.ravindra.chess.game.*;
import com.ravindra.chess.game.dto.ChessMan;
import com.ravindra.chess.game.piece.*;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestRook {

    @Test
    public void isValidMove() {
        Board testBoard = new Board();
        List<ChessMan> layout = new ArrayList<>();
        layout.add(new ChessMan(Color.BLACK.name(), Pieces.ROOK.name(), 4, 4, 5));
        layout.add(new ChessMan(Color.WHITE.name(), Pieces.PAWN.name(), 4, 2, 1));
        testBoard.createChessBoard(layout);
        Assert.assertTrue(new Rook(Status.ALIVE, Color.BLACK).isValidMove(testBoard, new Move(Tile.getTile(4, 4), Tile.getTile(0, 4))));
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
        Assert.assertFalse(new Rook(Status.ALIVE, Color.BLACK).isPathBlocked(testBoard, new Move(Tile.getTile(4, 4), Tile.getTile(0, 4))));
        Assert.assertFalse(new Rook(Status.ALIVE, Color.BLACK).isPathBlocked(testBoard, new Move(Tile.getTile(4, 4), Tile.getTile(4, 6))));
        Assert.assertFalse(new Rook(Status.ALIVE, Color.BLACK).isPathBlocked(testBoard, new Move(Tile.getTile(4, 4), Tile.getTile(2, 4))));
        Assert.assertTrue(new Rook(Status.ALIVE, Color.BLACK).isPathBlocked(testBoard, new Move(Tile.getTile(4, 4), Tile.getTile(4, 0))));
        Assert.assertFalse(new Rook(Status.ALIVE, Color.BLACK).isPathBlocked(testBoard, new Move(Tile.getTile(4, 4), Tile.getTile(4, 2))));

    }

    @Test
    public void testAllPossibleMoves(){
        Board testBoard = new Board();
        Position position = testBoard.getPieceAtTileLocation(Tile.getTile(7,0));
        List<Tile> moves = position.getPiece().getAllPossibleMoves(testBoard, position);
        Assert.assertEquals(0, moves.size());

        moves.clear();
        position = testBoard.getPieceAtTileLocation(Tile.getTile(0,0));
        moves = position.getPiece().getAllPossibleMoves(testBoard, position);
        Assert.assertEquals(0, moves.size());

        testBoard.changePositions(new Move(Tile.getTile(0,1), Tile.getTile(0,3)),
                testBoard.getPieceAtTileLocation(Tile.getTile(0,1)).getPiece());

        moves = position.getPiece().getAllPossibleMoves(testBoard, position);
        Assert.assertEquals(2, moves.size());

        testBoard.changePositions(new Move(Tile.getTile(1,0), Tile.getTile(2,2)),
                testBoard.getPieceAtTileLocation(Tile.getTile(1,0)).getPiece());
        moves = position.getPiece().getAllPossibleMoves(testBoard, position);
        Assert.assertEquals(3, moves.size());

    }
}
