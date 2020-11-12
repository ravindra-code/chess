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
    public void testThreatMatrix(){
        Board testBoard = new Board();
        List<ChessMan> layout = new ArrayList<>();
        layout.add(new ChessMan(Color.BLACK.name(), Pieces.KNIGHT.name(), 5,2, 1));
        testBoard.createChessBoard(layout);
        int[][] threatMatrix = testBoard.calculateThreatMatrix(testBoard, Color.WHITE);
        Assert.assertEquals(threatMatrix[3][1],1);
        Assert.assertEquals(threatMatrix[3][3],1);
        Assert.assertEquals(threatMatrix[4][0],1);
        Assert.assertEquals(threatMatrix[4][4],1);
        Assert.assertEquals(threatMatrix[2][2],0);
    }

    @Test
    public void testKill(){
        Board testBoard = new Board();
        List<ChessMan> layout = new ArrayList<>();
        layout.add(new ChessMan(Color.BLACK.name(), Pieces.PAWN.name(), 5,2, 1));
        testBoard.createChessBoard(layout);
        Assert.assertEquals(testBoard.getPosition(Tile.getTile(5,2)).getPiece().getStatus(), Status.ALIVE);
        testBoard.kill(testBoard.getPosition(Tile.getTile(5,2)).getPiece());
        Assert.assertEquals(testBoard.getPosition(Tile.getTile(5,2)).getPiece().getStatus(), Status.KILLED);

    }

    @Test
    public void testChangePositions(){
        Board testBoard = new Board();
        List<ChessMan> layout = new ArrayList<>();
        layout.add(new ChessMan(Color.BLACK.name(), Pieces.PAWN.name(), 4,4, 1));
        testBoard.createChessBoard(layout);
        testBoard.changePositions(new Move(Tile.getTile(4,4), Tile.getTile(4,3)),
                new PieceFactory().getChessPiece(Pieces.PAWN.name(), Color.BLACK.name()));
        Assert.assertTrue(testBoard.getPosition(Tile.getTile(4,3)).getPiece() instanceof Pawn);
    }

    @Test
    public void validateSetup(){
        Board layout = new Board();
        Assert.assertTrue(layout.getPosition(Tile.getTile(0,0)).getPiece() instanceof Rook);
        Assert.assertTrue(layout.getPosition(Tile.getTile(1,0)).getPiece() instanceof Knight);
        Assert.assertTrue(layout.getPosition(Tile.getTile(2,0)).getPiece() instanceof Bishop);
        Assert.assertTrue(layout.getPosition(Tile.getTile(2,1)).getPiece() instanceof Pawn);


    }
}
