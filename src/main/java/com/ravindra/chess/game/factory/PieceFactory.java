package com.ravindra.chess.game.factory;

import com.ravindra.chess.game.Color;
import com.ravindra.chess.game.piece.*;

public class PieceFactory {

    public String getChessPieceName(Piece piece) {
        if (piece instanceof King) {
            return Pieces.KING.name();
        } else if (piece instanceof Knight) {
            return Pieces.KNIGHT.name();
        } else if (piece instanceof Queen) {
            return Pieces.QUEEN.name();
        } else if (piece instanceof Rook) {
            return Pieces.ROOK.name();
        } else if (piece instanceof Pawn) {
            return Pieces.PAWN.name();
        } else if (piece instanceof Bishop) {
            return Pieces.BISHOP.name();
        } else {
            throw new RuntimeException("Invalid Chess Piece!!!");
        }
    }

    public Piece getChessPiece(String piece, String color) {

        if (piece.equalsIgnoreCase(Pieces.KING.toString())) {
            return new King(Status.ALIVE, Color.valueOf(color));
        } else if (piece.equalsIgnoreCase(Pieces.KNIGHT.toString())) {
            return new Knight(Status.ALIVE, Color.valueOf(color));
        } else if (piece.equalsIgnoreCase(Pieces.BISHOP.toString())) {
            return new Bishop(Status.ALIVE, Color.valueOf(color));
        } else if (piece.equalsIgnoreCase(Pieces.ROOK.toString())) {
            return new Rook(Status.ALIVE, Color.valueOf(color));
        } else if (piece.equalsIgnoreCase(Pieces.PAWN.toString())) {
            return new Pawn(Status.ALIVE, Color.valueOf(color));
        } else if (piece.equalsIgnoreCase(Pieces.QUEEN.toString())) {
            return new Queen(Status.ALIVE, Color.valueOf(color));
        } else {
            throw new RuntimeException("Invalid Chess Piece!!!");
        }
    }
}
