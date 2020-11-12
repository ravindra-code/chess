package com.ravindra.chess;

import com.ravindra.chess.game.Board;
import com.ravindra.chess.game.Color;
import com.ravindra.chess.game.Position;
import com.ravindra.chess.game.Tile;
import com.ravindra.chess.game.dto.ChessMan;
import com.ravindra.chess.game.piece.*;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class TestPiece {

    @Test
    public void testGetAllPossibleMoves(){

        Board testBoard = new Board();
        List<ChessMan> layout = new ArrayList<>();
        testBoard.createChessBoard(layout);
        List<Tile> moves = new Knight(Status.ALIVE, Color.BLACK).getAllPossibleMoves(testBoard,
                new Position(2,4, new Knight(Status.ALIVE, Color.BLACK)));
        System.out.println(moves);
        StringBuilder builder = new StringBuilder();
        moves.forEach(e->{
            builder.append(e.name());
        });
        Assert.assertEquals("A4A6B3B7D3D7E4E6", builder.toString());

    }
}
