package com.ravindra.chess.game;

import com.ravindra.chess.game.dto.ChessMan;
import com.ravindra.chess.game.exception.InvalidMoveException;
import com.ravindra.chess.game.factory.PieceFactory;
import com.ravindra.chess.game.piece.*;
import com.ravindra.chess.memento.Caretaker;
import com.ravindra.chess.memento.Originator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Board implements Cloneable {

    private Position[][] board = new Position[8][8];
    private ArrayList<Piece> deadBlackPieces = new ArrayList<>();
    private ArrayList<Piece> deadWhitePieces = new ArrayList<>();
    private Caretaker caretaker = new Caretaker();
    private Originator originator = new Originator();

    public Board() {
        initializeChessBoard();
    }

    public Board(Position[][] board) {
        this.board = board;
    }

    public Board(List<ChessMan> layout) {
        createChessBoard(layout);
    }

    Logger log = LoggerFactory.getLogger(this.getClass().getName());


    public void createChessBoard(List<ChessMan> layout) {
        emptyBoard();
        layout.forEach(e -> {
            Piece piece = new PieceFactory().getChessPiece(e.getName(), e.getColor());
            piece.setCounter(e.getCounter());
            Position position = new Position(e.getxCoordinate(), e.getyCoordinate(), piece);
            board[e.getxCoordinate()][e.getyCoordinate()] = position;

        });
    }

    private void emptyBoard() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                board[i][j] = null;
            }
        }
    }

    public void initializeChessBoard() {

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                board[i][j] = new Position(j, i, null);
            }
        }

        setPieces(Color.WHITE);
        setPieces(Color.BLACK);
    }

    public void initializeChessBoard(Position[][] board, ArrayList<Piece> blackDead,
                                     ArrayList<Piece> whiteDead) {
        blackDead.forEach(e -> deadBlackPieces.add(e));
        whiteDead.forEach(e -> deadWhitePieces.add(e));

    }

    private void setPieces(Color color) {
        /*int pawn = color.equals(Color.WHITE) ? 1 : 6;
        int piece = color.equals(Color.WHITE) ? 0 : 7;

        for (int i = 0; i < 8; i++) {
            board[pawn][i] = new Position(i, pawn, new Pawn(Status.ALIVE, color));
        }

        board[piece][0] = new Position(0, piece, new Rook(Status.ALIVE, color));
        board[piece][7] = new Position(7, piece, new Rook(Status.ALIVE, color));

        board[piece][1] = new Position(1, piece, new Knight(Status.ALIVE, color));
        board[piece][6] = new Position(6, piece, new Knight(Status.ALIVE, color));

        board[piece][2] = new Position(2, piece, new Bishop(Status.ALIVE, color));
        board[piece][5] = new Position(5, piece, new Bishop(Status.ALIVE, color));

        board[piece][3] = new Position(3, piece, new Queen(Status.ALIVE, color));
        board[piece][4] = new Position(4, piece, new King(Status.ALIVE, color));*/

        int pawn = color.equals(Color.WHITE) ? 1 : 6;
        int piece = color.equals(Color.WHITE) ? 0 : 7;

        for (int i = 0; i < 8; i++) {
            board[pawn][i] = new Position(i, pawn, new Pawn(Status.ALIVE, color));
        }

        board[piece][0] = new Position(0, piece, new Rook(Status.ALIVE, color));
        board[piece][7] = new Position(7, piece, new Rook(Status.ALIVE, color));

        board[piece][1] = new Position(1, piece, new Knight(Status.ALIVE, color));
        board[piece][6] = new Position(6, piece, new Knight(Status.ALIVE, color));

        board[piece][2] = new Position(2, piece, new Bishop(Status.ALIVE, color));
        board[piece][5] = new Position(5, piece, new Bishop(Status.ALIVE, color));

        board[piece][3] = new Position(3, piece, new Queen(Status.ALIVE, color));
        board[piece][4] = new Position(4, piece, new King(Status.ALIVE, color));
    }

    public synchronized void setPosition(Position position) {
        this.board[position.getY()][position.getX()] = new Position(position.getY(), position.getX(),
                position.getPiece());
    }

    public synchronized Position getPosition(Tile tile) {
        return board[tile.getY()][tile.getX()];
    }

    public void vacate(int x, int y) {
        board[y][x] = new Position(y, x, null);
    }

    public Position getPieceAtTileLocation(Tile tile) {
        return this.board[tile.getY()][tile.getX()];
    }

    public boolean isTileEmpty(Tile tile) {
        log.debug("##Board:: isTileEmpty:: Check if the tile is empty..");
        Position position = this.board[tile.getY()][tile.getX()];
        return (position == null || position.getPiece() == null) ? true : false;
    }

    public boolean changePositions(Move move, Piece piece) {

        log.debug("##Board:: changePosition:: Start changing positions..");
        if (piece.isValidMove(this, move) && move.getSource()!=move.getDestination()) {
            Tile destination = Tile.getTile(move.getDestination().getX(), move.getDestination().getY());

            if (!this.isTileEmpty(destination)) {
                Piece destinationPiece = this.getPosition(destination).getPiece();
                if (!destinationPiece.getColor().equals(piece.getColor())) {
                    kill(destinationPiece);
                }
            }
            setPosition(new Position(move.getDestination().getX(), move.getDestination().getY(), piece));
            vacate(move.getSource().getX(), move.getSource().getY());
            piece.increment();
            piece.setPreviousPosition(move.getSource());
            return true;
        } else {
            log.error("##Board:: changePositions:: Move not allowed..");
            throw new InvalidMoveException("Move not valid..");
        }

    }

    public void kill(Piece piece) {
        log.debug("##Board:: kill:: Initiate killing of piece.. ");
        if (null != piece) {
            piece.setStatus(Status.KILLED);
            if (piece.getColor().equals(Color.WHITE)) {
                deadWhitePieces.add(piece);
            } else {
                deadBlackPieces.add(piece);
            }
        }
    }

    public int[][] calculateThreatMatrix(Board layout, Color color) {

        log.debug("##Board::calculateThreatMatrix:: Calculate threat matrix..");
        int[][] threatMatrix = new int[8][8];

        Arrays.stream(Tile.values()).forEach(e -> {
            Position position = getPosition(e);
            if (null != position && null != position.getPiece() &&
                    !position.getPiece().getColor().equals(color)) {

                position.getPiece().getAllPossibleMoves(layout, position).stream().forEach(t -> {
                    threatMatrix[t.getX()][t.getY()] = 1;
                });

            }
        });
        return threatMatrix;

    }

    public void executeMove(List<ChessMan> layout, Move move, Piece piece) {
        // Save the Object for rollback.
        log.debug("##Board:: executeMove:: ");
        createChessBoard(layout);
        originator.setState(board);
        caretaker.addMemento(originator.save());
        changePositions(move, piece);

    }

    @Override
    public Object clone()  {
        // TODO: make sure that the cloning is proper.
        Board clone = new Board();
        clone.board = this.board;
        return clone;
    }
}
