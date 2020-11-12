package com.ravindra.chess.game;

import com.ravindra.chess.game.dto.ChessMan;
import com.ravindra.chess.game.factory.PieceFactory;
import com.ravindra.chess.game.piece.*;
import com.ravindra.chess.memento.Caretaker;
import com.ravindra.chess.memento.Originator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Board implements Cloneable{

    private Position[][] board= new Position[8][8];
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

    public Board(List<ChessMan> layout){
        createChessBoard(layout);
    }


    public void createChessBoard(List<ChessMan> layout){
        emptyBoard();
        layout.forEach(e->{
            Piece piece = new PieceFactory().getChessPiece(e.getName(),e.getColor());
            piece.setCounter(e.getCounter());
            Position position =  new Position(e.getxCoordinate(), e.getyCoordinate(), piece);
            board[e.getxCoordinate()][e.getyCoordinate()] = position;

        });
    }

    private void emptyBoard(){
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                board[i][j] = null;
            }
        }
    }

    public void initializeChessBoard(){

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                board[i][j] = new Position(j, i, null);
            }
        }

        setPieces(Color.WHITE);
        setPieces(Color.BLACK);
    }

    public void initializeChessBoard(Position[][] board, ArrayList<Piece> blackDead,
                                     ArrayList<Piece> whiteDead){
        blackDead.forEach(e->deadBlackPieces.add(e));
        whiteDead.forEach(e->deadWhitePieces.add(e));

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
            board[i][pawn] = new Position(i, pawn, new Pawn(Status.ALIVE, color));
        }

        board[0][piece] = new Position(0, piece, new Rook(Status.ALIVE, color));
        board[7][piece] = new Position(7, piece, new Rook(Status.ALIVE, color));

        board[1][piece] = new Position(1, piece, new Knight(Status.ALIVE, color));
        board[6][piece] = new Position(6, piece, new Knight(Status.ALIVE, color));

        board[2][piece] = new Position(2, piece, new Bishop(Status.ALIVE, color));
        board[5][piece] = new Position(5, piece, new Bishop(Status.ALIVE, color));

        board[3][piece] = new Position(3, piece, new Queen(Status.ALIVE, color));
        board[4][piece] = new Position(4, piece, new King(Status.ALIVE, color));
    }

    public synchronized void setPosition( Position position){
        this.board[position.getX()][position.getY()] = new Position(position.getX(), position.getY(),
                position.getPiece());
    }

    public synchronized Position getPosition(Tile tile){
        return board[tile.getX()][tile.getY()];
    }

    private void vacate(int x, int y){
        board[x][y] = new Position(x, y, null);
    }

    public Position getPieceAtTileLocation(Tile tile){
        return this.board[tile.getX()][tile.getY()];
    }

    public boolean isTileEmpty( Tile tile){
        Position position = this.board[tile.getX()][tile.getY()];
        return (position==null||position.getPiece()==null)?true:false;
    }

    public boolean changePositions(Move move, Piece piece){

        if (piece.isValidMove(this, move)){
            Tile destination = Tile.getTile(move.getDestination().getX(),move.getDestination().getY());

            if (!this.isTileEmpty( destination)){
                Piece destinationPiece = this.getPosition(destination).getPiece();
                if (!destinationPiece.getColor().equals(piece.getColor())) {
                    kill(destinationPiece);
                }
            }
            setPosition( new Position(move.getDestination().getX(),move.getDestination().getY(), piece));
            vacate(move.getSource().getX(), move.getSource().getY());
            piece.increment();
            return true;
        }else{
            System.out.println("Move not allowed!!!");
            return false;
        }

    }

    public void kill(Piece piece){
        if (null!=piece) {
            piece.setStatus(Status.KILLED);
            if (piece.getColor().equals(Color.WHITE)) {
                deadWhitePieces.add(piece);
            } else {
                deadBlackPieces.add(piece);
            }
        }
    }

    public int[][] calculateThreatMatrix(Board layout, Color color){
        int[][] threatMatrix = new int[8][8];

        Arrays.stream(Tile.values()).forEach(e->{
            Position position = getPosition(e);
            if (null!=position && null!=position.getPiece() &&
                    !position.getPiece().getColor().equals(color)){

                position.getPiece().getAllPossibleMoves(layout, position).stream().forEach(t->{
                    threatMatrix[t.getX()][t.getY()] = 1;
                });

            }
        });
        return threatMatrix;

    }

    public void executeMove(List<ChessMan> layout, Move move, Piece piece){
        // Save the Object for rollback.
        createChessBoard(layout);
        originator.setState(board);
        caretaker.addMemento(originator.save());
        changePositions( move, piece);


    }
}
