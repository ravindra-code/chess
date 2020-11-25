package com.ravindra.chess.game.piece;

import ch.qos.logback.core.status.StatusUtil;
import com.ravindra.chess.game.*;
import com.ravindra.chess.game.factory.PieceFactory;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class King extends Piece {

    Logger log = LoggerFactory.getLogger(this.getClass().getName());

    public King(Status status, Color color) {
        this.status = status;
        this.color = color;
    }

    public King(Status status, Color color, int counter) {
        this.status = status;
        this.color = color;
        this.counter = counter;
    }

    @Override
    public boolean isValidMove(Board board, Move move) {
        log.debug("##King:: isValidMove:: Validate move for the King..");
        if (move.getDestination().isValid(move.getDestination().getX(), move.getDestination().getY())) {

            if (isKingChecked(board, move.getDestination().getX(), move.getDestination().getY())) {
                return false;
            }
            int diffX = Math.abs(move.getDestination().getX() - move.getSource().getX());
            int diffY = Math.abs(move.getDestination().getY() - move.getSource().getY());
            if (diffX <= 1 &&
                    diffY <= 1) {

                if (board.isTileEmpty
                        (Tile.getTile(move.getDestination().getX(),
                        move.getDestination().getY()))) {
                    return true;
                } else {
                    Piece piece = board.getPieceAtTileLocation(Tile.getTile(move.getDestination().getX(),
                            move.getDestination().getY())).getPiece();
                    if (!this.getColor().equals(piece.getColor())) {
                        return true;
                    }
                }


            } else if (diffX == 2 && diffY == 0) {
                return move.isCastleMove() ? true : false;
            }
        }
        log.debug("##King:: isMoveValid:: Move not valid..");
        return false;
    }


    @Override
    public boolean isPathBlocked(Board board, Move move) {
        return false;
    }

    public boolean isCheckMate(Board board, int x, int y) throws Exception{
        log.debug("##King:: isCheckMate:: Find if the king is under checkmate..");
        try {
            boolean[] result = new boolean[1];
            result[0]=true;
            // Check if it is possible for king to get out of check by moving.
            int[][] threatMatrix = board.calculateThreatMatrix(board, this.getColor());
            List<Tile> possibleMoves = getAllPossibleMoves(board,
                    new Position(x, y, new PieceFactory().getChessPiece(Pieces.KING.name(), this.getColor().name())));
            List<Tile> relevantMoves = possibleMoves.stream().filter(e -> threatMatrix[e.getX()][e.getY()] == 0).collect(Collectors.toList());
            if (relevantMoves.size() > 0)return false;

            List<Position> threateningPositions = findAllAttackingPieces(board, new King(Status.ALIVE, this.color), Tile.getTile(x, y));

            threateningPositions.forEach(e -> {
                // See if the threatening positions can be killed.
                Piece piece = e.getPiece();
                if (piece.isPositionUnderThreat(board, piece, Tile.getTile(e.getX(), e.getY()))) {
                    // check if once the threatening position is killed, will the king still stay under check.
                    // if the king is still under check then it's checkmate.
                    List<Position> tPositions = findAllAttackingPieces(board, piece, Tile.getTile(e.getX(), e.getY()));
                    tPositions.forEach(t->{

                        Board clone = (Board) board.clone();
                        clone.changePositions(new Move(Tile.getTile(t.getX(), t.getY()),
                                Tile.getTile(e.getX(), e.getY())), t.getPiece());
                        if (!isKingChecked(clone, x, y)){
                            result[0]=false;
                        }
                    });
                }
            });
            if (!result[0]) return result[0];

            // Check if some other piece can block the path of blocker and hence removing check.
            Arrays.stream(Tile.values())
                    .filter(e-> !board.isTileEmpty(e) &&
                    board.getPieceAtTileLocation(e).getPiece().getColor().name().equals(color.name()))
                    .forEach(t->{
                        Position position = board.getPieceAtTileLocation(t);
                        position.getPiece().getAllPossibleMoves(board, position).stream().forEach(p->{
                            Board clone = (Board) board.clone();
                            if (result[0] && null!=board.getPieceAtTileLocation(t).getPiece()) {
                                clone.changePositions(new Move(Tile.getTile(t.getX(), t.getY()), Tile.getTile(p.getX(), p.getY())),
                                        board.getPieceAtTileLocation(t).getPiece());
                                if (!isKingChecked(clone, x, y)) {
                                    result[0] = false;
                                }
                            }
                        });
                    });


            return result[0];
        }catch (Exception e){
            log.error("Error while checking the checkmate condition..");
            throw new Exception(e);
        }

    }

    public boolean isKingChecked(Board board, int x, int y) {
        log.debug("##King:: isKingChecked:: Find if the king is under check..");
        int[][] threatMatrix = board.calculateThreatMatrix(board, this.getColor());
        return threatMatrix[x][y] == 1 ? true : false;

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
