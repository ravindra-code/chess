package com.ravindra.chess;

import com.ravindra.chess.game.Board;
import com.ravindra.chess.game.Color;
import com.ravindra.chess.game.Move;
import com.ravindra.chess.game.Tile;
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
    public void isValidMove(){
        Board testBoard = new Board();
        List<ChessMan> layout = new ArrayList<>();
        layout.add(new ChessMan(Color.BLACK.name(), Pieces.KING.name(), 4,4, 5));
        layout.add(new ChessMan(Color.WHITE.name(), Pieces.ROOK.name(), 5,2, 1));
        layout.add(new ChessMan(Color.WHITE.name(), Pieces.BISHOP.name(), 6,3, 3));
        testBoard.createChessBoard(layout);
        Assert.assertTrue(new King(Status.ALIVE, Color.BLACK).isValidMove(testBoard, new Move(Tile.getTile(4,4), Tile.getTile(4,3))));
        Assert.assertFalse(new King(Status.ALIVE, Color.BLACK).isValidMove(testBoard, new Move(Tile.getTile(4,4), Tile.getTile(5,4))));
        Assert.assertFalse(new King(Status.ALIVE, Color.BLACK).isValidMove(testBoard, new Move(Tile.getTile(4,4), Tile.getTile(5,5))));

    }

    @Test
    public void isKingUnderCheck(){
        Board testBoard = new Board();
        List<ChessMan> layout = new ArrayList<>();
        layout.add(new ChessMan(Color.BLACK.name(), Pieces.KING.name(), 1,2, 5));
        layout.add(new ChessMan(Color.WHITE.name(), Pieces.KNIGHT.name(), 2,4, 1));
        testBoard.createChessBoard(layout);
        Assert.assertTrue(new King(Status.ALIVE, Color.BLACK).isKingChecked(testBoard, 1,2));

    }

}
