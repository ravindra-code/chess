package com.ravindra.chess;

import com.ravindra.chess.game.Board;
import com.ravindra.chess.game.Color;
import com.ravindra.chess.game.Move;
import com.ravindra.chess.game.Tile;
import com.ravindra.chess.game.dto.ChessMan;
import com.ravindra.chess.game.piece.Pawn;
import com.ravindra.chess.game.piece.Pieces;
import com.ravindra.chess.game.piece.Status;
import com.ravindra.chess.game.strategies.EnPassant;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class TestEnPassant {

    @Test
    public void isMoveApplicable() {

        Board testBoard = new Board();
        List<ChessMan> layout = new ArrayList<>();
        layout.add(new ChessMan(Color.BLACK.name(), Pieces.PAWN.name(), 3, 3, 1));
        layout.add(new ChessMan(Color.WHITE.name(), Pieces.PAWN.name(), 4, 3, 2));
        testBoard.createChessBoard(layout);
        Pawn attacker = new Pawn(Status.ALIVE, Color.BLACK);
        attacker.setPreviousPosition(Tile.getTile(3, 4));
        Pawn attacked = new Pawn(Status.ALIVE, Color.WHITE);
        attacked.setPreviousPosition(Tile.getTile(4, 1));
        Assert.assertTrue(new EnPassant().isMoveApplicable(testBoard, new Move(Tile.getTile(3, 3), Tile.getTile(4, 2)),
                attacker, attacked));
        layout.add(new ChessMan(Color.BLACK.name(), Pieces.ROOK.name(), 4, 2, 2));
        testBoard.createChessBoard(layout);
        Assert.assertFalse(new EnPassant().isMoveApplicable(testBoard, new Move(Tile.getTile(3, 3), Tile.getTile(4, 2)),
                attacker, attacked));

    }

    @Test
    public void testInitiateStrategy() {
        Board testBoard = new Board();
        List<ChessMan> layout = new ArrayList<>();
        layout.add(new ChessMan(Color.BLACK.name(), Pieces.PAWN.name(), 3, 3, 1));
        layout.add(new ChessMan(Color.WHITE.name(), Pieces.PAWN.name(), 4, 3, 2));
        testBoard.createChessBoard(layout);
        Pawn attacker = new Pawn(Status.ALIVE, Color.BLACK);
        attacker.setPreviousPosition(Tile.getTile(3, 4));
        Pawn attacked = new Pawn(Status.ALIVE, Color.WHITE);
        attacked.setPreviousPosition(Tile.getTile(4, 1));
        Board finalBoard = new EnPassant().initiateStrategy(testBoard, new Move(Tile.getTile(3, 3), Tile.getTile(4, 2)), attacker, attacked);
        Assert.assertTrue(finalBoard.getPieceAtTileLocation(Tile.getTile(4, 2)).getPiece() instanceof Pawn);
        Assert.assertNull(finalBoard.getPieceAtTileLocation(Tile.getTile(3, 3)).getPiece());
        Assert.assertNull(finalBoard.getPieceAtTileLocation(Tile.getTile(4, 3)).getPiece());

    }

}
