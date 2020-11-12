package com.ravindra.chess.game.piece;

import ch.qos.logback.core.status.StatusUtil;
import com.ravindra.chess.game.*;
import com.ravindra.chess.game.factory.PieceFactory;

import java.util.List;
import java.util.stream.Collectors;

public class King extends Piece{

    public King(Status status, Color color) {
        this.status = status;
        this.color = color;
    }

    public King(Status status, Color color, int counter){
        this.status = status;
        this.color = color;
        this.counter = counter;
    }

    @Override
    public boolean isValidMove(Board board, Move move) {
        if (move.getDestination().isValid(move.getDestination().getX(), move.getDestination().getY())) {

            if (isKingChecked(board, move.getDestination().getX(), move.getDestination().getY())){
                return false;
            }
            int diffX = Math.abs(move.getDestination().getX() - move.getSource().getX());
            int diffY = Math.abs(move.getDestination().getY() - move.getSource().getY());
            if (diffX <= 1 &&
                    diffY <= 1) {

                if (board.isTileEmpty(Tile.getTile(move.getDestination().getX(),
                        move.getDestination().getY()))) {
                    return true;
                } else {
                    Piece piece = board.getPieceAtTileLocation(Tile.getTile(move.getDestination().getX(),
                            move.getDestination().getY())).getPiece();
                    if (!this.getColor().equals(piece.getColor())) {
                        return true;
                    }
                }


            }else if (diffX==2 && diffY==0){
                return move.isCastleMove()?true:false;
            }
        }
        return false;
    }


    @Override
    public boolean isPathBlocked(Board board, Move move) {
        return false;
    }

    //Todo: Few cases remaining.
    //Todo: comment
    public boolean isCheckMate(Board board, int x, int y){
        int[][] threatMatrix = board.calculateThreatMatrix(board, this.getColor());
        List<Tile> possibleMoves = getAllPossibleMoves(board,
                new Position(x, y, new PieceFactory().getChessPiece(Pieces.KING.name(), this.getColor().name())));
        List<Tile> relevantMoves = possibleMoves.stream().filter(e->threatMatrix[e.getX()][e.getY()]==0).collect(Collectors.toList());
        return relevantMoves.size()>0?false:true;

    }

    public boolean isKingChecked(Board board, int x, int y){
        int[][] threatMatrix = board.calculateThreatMatrix(board, this.getColor());
        return threatMatrix[x][y]==1?true:false;

    }

    @Override
    public List<Tile> tracePath(Move move) {
        return null;
    }


    @Override
    public Object clone() throws CloneNotSupportedException {
        return new King(this.getStatus(), this.getColor());
    }

}
