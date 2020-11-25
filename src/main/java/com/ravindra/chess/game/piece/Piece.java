package com.ravindra.chess.game.piece;

import com.ravindra.chess.game.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class Piece implements Cloneable {

    public int counter;
    protected Status status;
    protected Color color;
    public Tile previousPosition;
    Logger log = LoggerFactory.getLogger(this.getClass().getName());

    public Piece() {
        this.status = Status.ALIVE;
        this.counter = 0;
    }

    public Status getStatus() {
        return status;
    }

    public abstract boolean isValidMove(Board board, Move move);

    public abstract boolean isPathBlocked(Board board, Move move);

    public List<Tile> getAllPossibleMoves(Board board, Position position) {

        log.debug("##Piece:: getAllPossibleMoves:: get all possible moves..");
        ArrayList<Tile> possibleMoves = new ArrayList<>();

        Arrays.stream(Tile.values()).
                forEach(e -> {
            Move move = new Move(Tile.getTile(position.getX(), position.getY()),
                    Tile.getTile(e.getX(), e.getY()));
            if (isValidMove(board, move)) {
                possibleMoves.add(e);
            }
        });
        return possibleMoves;
    }


    public boolean isPositionUnderThreat(Board board, Piece piece, Tile tile){
        log.debug("##Piece:: isPositionUnderThreat:: Check if the position is under threat..");
        List<Position> attackers= findAllAttackingPieces(board, piece, tile);
        if (attackers==null || attackers.size()==0){
            return false;
        }
        return true;
    }

    public List<Position> findAllAttackingPieces(Board board, Piece piece, Tile tile){
        log.debug("##Piece:: findAllAttackingPieces:: find all attacking pieces for position:  "
                +tile.getX()+":"+tile.getY());
        Color color = piece.getColor();
        List<Position> attackingPieces = new ArrayList<>();
        List<Tile> pieces = Arrays.stream(Tile.values()).
                filter(e->
                        !board.isTileEmpty(e) && !board.getPieceAtTileLocation(e).getPiece().getColor().name().equals(color.name())
                                ).collect(Collectors.toList());

        pieces.forEach(e->{
            Position piece1 = board.getPieceAtTileLocation(e);
            boolean isMoveValid = piece1.getPiece().isValidMove(board, new Move(Tile.getTile(e.getX(), e.getY()), tile));
            if (isMoveValid) {
                attackingPieces.add(piece1);
            }
        });
        return attackingPieces;


    }

    public Tile getPreviousPosition() {
        return previousPosition;
    }

    public void setPreviousPosition(Tile previousPosition) {
        this.previousPosition = previousPosition;
    }

    public abstract List<Tile> tracePath(Move move);

    public synchronized void setCounter(int counter) {
        this.counter = counter;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public synchronized void increment() {
        counter++;
    }

    public synchronized int getCounter() {
        return counter;
    }

    @Override
    public abstract Object clone() throws CloneNotSupportedException;
}
