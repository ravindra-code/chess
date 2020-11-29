package com.ravindra.chess;

import com.ravindra.chess.game.Board;
import com.ravindra.chess.game.Color;
import com.ravindra.chess.game.Move;
import com.ravindra.chess.game.Tile;
import com.ravindra.chess.game.dto.ChessMan;
import com.ravindra.chess.game.factory.PieceFactory;
import com.ravindra.chess.game.piece.*;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class TestBoard {


    @Test
    public void testThreatMatrix() {
        Board testBoard = new Board();
        List<ChessMan> layout = new ArrayList<>();
        layout.add(new ChessMan(Color.BLACK.name(), Pieces.KNIGHT.name(), 5, 2, 1));
        testBoard.createChessBoard(layout);
        int[][] threatMatrix = testBoard.calculateThreatMatrix(testBoard, Color.WHITE);
        Assert.assertEquals(threatMatrix[3][1], 1);
        Assert.assertEquals(threatMatrix[3][3], 1);
        Assert.assertEquals(threatMatrix[4][0], 1);
        Assert.assertEquals(threatMatrix[4][4], 1);
        Assert.assertEquals(threatMatrix[2][2], 0);

        layout.clear();
        layout.add(new ChessMan(Color.BLACK.name(), Pieces.KNIGHT.name(), 5, 2, 1));
        layout.add(new ChessMan(Color.BLACK.name(), Pieces.ROOK.name(), 0, 7, 0));
        testBoard.createChessBoard(layout);
        threatMatrix = testBoard.calculateThreatMatrix(testBoard, Color.WHITE);
        Assert.assertEquals(threatMatrix[3][1], 1);
        Assert.assertEquals(threatMatrix[3][3], 1);
        Assert.assertEquals(threatMatrix[4][0], 1);
        Assert.assertEquals(threatMatrix[4][4], 1);
        Assert.assertEquals(threatMatrix[2][2], 0);

        Assert.assertEquals(threatMatrix[0][4], 1);
        Assert.assertEquals(threatMatrix[0][3], 1);
        Assert.assertEquals(threatMatrix[0][1], 1);
        Assert.assertEquals(threatMatrix[5][7], 1);
        Assert.assertEquals(threatMatrix[7][6], 0);

        layout.clear();
        layout.add(new ChessMan(Color.BLACK.name(), Pieces.KNIGHT.name(), 5, 2, 1));
        layout.add(new ChessMan(Color.BLACK.name(), Pieces.ROOK.name(), 0, 7, 0));
        layout.add(new ChessMan(Color.BLACK.name(), Pieces.BISHOP.name(), 6, 5, 5));
        layout.add(new ChessMan(Color.BLACK.name(), Pieces.PAWN.name(), 6, 4, 2));
        testBoard.createChessBoard(layout);
        threatMatrix = testBoard.calculateThreatMatrix(testBoard, Color.WHITE);
        Assert.assertEquals(threatMatrix[3][3], 1);
        Assert.assertEquals(threatMatrix[4][0], 1);
        Assert.assertEquals(threatMatrix[4][4], 1);
        Assert.assertEquals(threatMatrix[2][2], 0);

        Assert.assertEquals(threatMatrix[0][3], 1);
        Assert.assertEquals(threatMatrix[0][1], 1);
        Assert.assertEquals(threatMatrix[5][7], 1);
        Assert.assertEquals(threatMatrix[7][6], 1);

        // Pawn
        //Assert.assertEquals(threatMatrix[7][3], 1);
        //Assert.assertEquals(threatMatrix[5][3], 1);

        Assert.assertEquals(threatMatrix[7][4], 1);
        Assert.assertEquals(threatMatrix[5][6], 1);
        Assert.assertEquals(threatMatrix[4][7], 1);
        Assert.assertEquals(threatMatrix[6][6], 0);
        Assert.assertEquals(threatMatrix[5][4], 1);
        Assert.assertEquals(threatMatrix[4][3], 1);
        Assert.assertEquals(threatMatrix[2][1], 1);
        Assert.assertEquals(threatMatrix[3][2], 1);

        layout.clear();
        layout.add(new ChessMan(Color.BLACK.name(), Pieces.QUEEN.name(), 6, 4, 2));
        testBoard.createChessBoard(layout);
        threatMatrix = testBoard.calculateThreatMatrix(testBoard, Color.WHITE);

        Assert.assertEquals(threatMatrix[6][5], 1);
        Assert.assertEquals(threatMatrix[6][6], 1);
        Assert.assertEquals(threatMatrix[6][7], 1);
        Assert.assertEquals(threatMatrix[6][3], 1);
        Assert.assertEquals(threatMatrix[6][2], 1);
        Assert.assertEquals(threatMatrix[6][1], 1);
        Assert.assertEquals(threatMatrix[6][0], 1);
        Assert.assertEquals(threatMatrix[7][4], 1);
        Assert.assertEquals(threatMatrix[5][4], 1);
        Assert.assertEquals(threatMatrix[4][4], 1);
        Assert.assertEquals(threatMatrix[3][4], 1);
        Assert.assertEquals(threatMatrix[2][4], 1);
        Assert.assertEquals(threatMatrix[1][4], 1);
        Assert.assertEquals(threatMatrix[0][4], 1);
        Assert.assertEquals(threatMatrix[7][3], 1);
        Assert.assertEquals(threatMatrix[5][5], 1);
        Assert.assertEquals(threatMatrix[4][6], 1);
        Assert.assertEquals(threatMatrix[3][7], 1);
        Assert.assertEquals(threatMatrix[7][5], 1);
        Assert.assertEquals(threatMatrix[5][3], 1);
        Assert.assertEquals(threatMatrix[4][2], 1);
        Assert.assertEquals(threatMatrix[3][1], 1);
        Assert.assertEquals(threatMatrix[2][0], 1);
        Assert.assertEquals(threatMatrix[4][5], 0);






    }

    @Test
    public void testKill() {
        Board testBoard = new Board();
        List<ChessMan> layout = new ArrayList<>();
        layout.add(new ChessMan(Color.BLACK.name(), Pieces.PAWN.name(), 5, 2, 1));
        testBoard.createChessBoard(layout);
        Assert.assertEquals(testBoard.getPosition(Tile.getTile(5, 2)).getPiece().getStatus(), Status.ALIVE);
        testBoard.kill(testBoard.getPosition(Tile.getTile(5, 2)).getPiece());
        Assert.assertEquals(testBoard.getPosition(Tile.getTile(5, 2)).getPiece().getStatus(), Status.KILLED);

    }

    @Test
    public void testChangePositions() {
        Board testBoard = new Board();
        List<ChessMan> layout = new ArrayList<>();
        layout.add(new ChessMan(Color.BLACK.name(), Pieces.PAWN.name(), 4, 4, 1));
        testBoard.createChessBoard(layout);
        testBoard.changePositions(new Move(Tile.getTile(4, 4), Tile.getTile(4, 3)),
                new PieceFactory().getChessPiece(Pieces.PAWN.name(), Color.BLACK.name()));
        Assert.assertTrue(testBoard.getPosition(Tile.getTile(4, 3)).getPiece() instanceof Pawn);
    }

    @Test
    public void validateSetup() {
        Board layout = new Board();
        Assert.assertTrue(layout.getPosition(Tile.getTile(0, 0)).getPiece() instanceof Rook);
        Assert.assertTrue(layout.getPosition(Tile.getTile(1, 0)).getPiece() instanceof Knight);
        Assert.assertTrue(layout.getPosition(Tile.getTile(2, 0)).getPiece() instanceof Bishop);
        Assert.assertTrue(layout.getPosition(Tile.getTile(2, 1)).getPiece() instanceof Pawn);


    }

    @Test
    public void isTileEmpty(){
        Board testBoard = new Board();
        List<ChessMan> layout = new ArrayList<>();
        layout.add(new ChessMan(Color.WHITE.name(), Pieces.BISHOP.name(), 3, 4, 2));
        testBoard.createChessBoard(layout);
        Assert.assertTrue(testBoard.isTileEmpty(Tile.getTile(4,3)));
        Assert.assertFalse(testBoard.isTileEmpty(Tile.getTile(3,4)));


    }
}
