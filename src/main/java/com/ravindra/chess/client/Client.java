package com.ravindra.chess.client;

import com.ravindra.chess.game.Board;
import com.ravindra.chess.game.Move;
import com.ravindra.chess.game.Position;
import com.ravindra.chess.game.Tile;
import com.ravindra.chess.game.exception.InvalidStrategyException;
import com.ravindra.chess.game.factory.PieceFactory;
import com.ravindra.chess.game.piece.King;
import com.ravindra.chess.game.piece.Pawn;
import com.ravindra.chess.game.piece.Piece;
import com.ravindra.chess.game.strategies.Castling;
import com.ravindra.chess.game.strategies.EnPassant;
import com.ravindra.chess.game.strategies.PawnPromotion;

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Client {
    private static final String EMPTY_STRING = "-----";
    private PieceFactory pieceFactory;
    private static boolean isGameOver = false;
    private static final String BLACK_TURN = "BLACK";
    private static final String WHITE_TURN = "WHITE";
    private static String COLOR = WHITE_TURN;

    public Client() {
        pieceFactory = new PieceFactory();
    }

    public String createPieceObject(Piece piece) {
        StringBuilder builder = new StringBuilder();
        builder.append(pieceFactory.getChessPieceName(piece).substring(0, 2));
        builder.append("|" + piece.getColor().name().substring(0, 1));
        return builder.toString();

    }

    public void provideOptions(Board layout) throws Exception{
        System.out.println("## Please Select any of the below options.");
        System.out.println("1: Move a Piece.");
        System.out.println("2: Initiate Castling.");
        System.out.println("3: Initiate En Passant.");

        Thread.sleep(100);

        boolean isKingUnderCheck = isKingUnderCheck(layout);
        if (isKingUnderCheck){
            System.out.println(" ALERT::" + COLOR+ " The King is under check. Please be careful..");
        }
        /*boolean isKingUnderCheckMate = isKingUnderCheckMate(layout);
        if (isKingUnderCheckMate){
            System.out.println(" ALERT:: The King is under checkmate. Better luck next time..");
        }*/
        int ip = getUserInput();
        initiateAction(layout, ip);
    }

    public void alternateTurns(){
        if (COLOR.equalsIgnoreCase(WHITE_TURN)){
            COLOR = BLACK_TURN;
        }else{
            COLOR = WHITE_TURN;
        }
    }

    public Tile findKingLocation(Board board, String color){
        Tile[] tile = new Tile[1];
        Arrays.stream(Tile.values()).filter(t->!board.isTileEmpty(t))
                .forEach(e->{
                    Piece piece = board.getPieceAtTileLocation(e).getPiece();
                    if (piece instanceof King &&
                            piece.getColor().name().equalsIgnoreCase(color)){
                        tile[0] = e;
                    }

        });
        return tile[0];
    }

    public void printChessBoard(Board board) {

        Arrays.stream(Tile.values()).forEach(e -> {
            String piece = EMPTY_STRING;
            if (!board.isTileEmpty(e)) {
                Position position = board.getPieceAtTileLocation(e);
                piece = createPieceObject(position.getPiece());
            }
            System.out.print(e.getX() + ":" + e.getY() + ":" + piece + "\t\t");
            if (e.getY() == 7) {
                System.out.println();
                System.out.println();
            }
        });
        System.out.println("-----------------------------------------------------------------------------------------------------------------");

    }


    public int getUserInput(){
        Scanner sc= new Scanner(System.in);
        return sc.nextInt();

    }

    public String getPieceFromUser(){
        Scanner sc = new Scanner(System.in);
        return sc.nextLine();
    }

    public int generateRandomNumber() {
        Random random = new Random();
        return random.nextInt(8);
    }


    public boolean isValidTurn(Board board, int x, int y){
        Position position = board.getPieceAtTileLocation(Tile.getTile(x, y));
        if (position.getPiece().getColor().name().equalsIgnoreCase(COLOR)){
            return true;
        }
        return false;
    }

    public boolean initiateMovement(Board board) {
        System.out.println(COLOR);
        boolean moveStatus = false;
        System.out.println("Input the source tile. ");
        int sourceX = getUserInput();
        int sourceY = getUserInput();
        System.out.println("Source: "+ sourceX + " : "+ sourceY);
        System.out.println("Input the destination tile. ");
        int destX = getUserInput();
        int destY = getUserInput();
        System.out.println("Destination: "+ destX + " : "+destY);
        if (board.isTileEmpty(Tile.getTile(sourceX,sourceY))){
            System.out.println("Invalid starting point. Start location empty. Please try again.");
            initiateMovement(board);
        }
        if (isValidTurn(board, sourceX, sourceY)) {
            Position position = board.getPieceAtTileLocation(Tile.getTile(sourceX, sourceY));
            try {
                moveStatus = board.changePositions(new Move(Tile.getTile(sourceX, sourceY), Tile.getTile(destX, destY)), position.getPiece());
                if (moveStatus && position.getPiece() instanceof Pawn) {
                    if (destY == 0 || destY == 7) {
                        initiatePawnPromotion(board);
                    }
                }
            } catch (Exception e) {
                System.out.println(e.getCause());
                System.out.println("Invalid move. Reinitialize the move..\n");
                initiateMovement(board);
            }
        }else{
            System.out.println("Invalid turn.");
            initiateMovement(board);
        }
        alternateTurns();
        return moveStatus;
    }

    public Board initiateCastling(Board board) throws Exception{
        try {
            System.out.println("Initiating Castling...");
            System.out.println("Enter the x coordinate for the king..");
            int kingX = getUserInput();
            System.out.println("Enter the y coordinate for the king..");
            int kingY = getUserInput();
            System.out.println("Enter the x coordinate for the Rook..");
            int rookX = getUserInput();
            System.out.println("Enter the y coordinate for the Rook..");
            int rookY = getUserInput();
            Castling castling = new Castling();

            board = castling.initiateStrategy(board, board.getPosition(Tile.getTile(rookX, rookY)),
                    board.getPosition(Tile.getTile(kingX, kingY)));
            printChessBoard(board);
            alternateTurns();

        }catch (InvalidStrategyException e){
            System.out.println("\nCasting not Possible. Please try again..\n");
            provideOptions(board);
        }
        return board;

    }

    public Board initiatePawnPromotion(Board board) throws Exception{
        try {
            System.out.println("Initiating Pawn Promotion Strategy..");
            System.out.println("Enter the x coordinate of the pawn to be promoted.");
            int pawnX = getUserInput();
            System.out.println("Enter the y coordinate of the pawn to be promoted. ");
            int pawnY = getUserInput();
            System.out.println("Enter the piece the pawn is to be promoted to. ");
            String piece = getPieceFromUser();
            String pieceColor = COLOR;
            Piece toPromote = PieceFactory.getChessPiece(piece, pieceColor);
            PawnPromotion pawnPromotion = new PawnPromotion();
            board = pawnPromotion.initiateStrategy(board, board.getPieceAtTileLocation(Tile.getTile(pawnX, pawnY)), toPromote);
            printChessBoard(board);

        }catch (InvalidStrategyException e){
            System.out.println("Pawn Promotion is not valid in this case.. ");
            provideOptions(board);
        }
        return board;

    }

    public Board initiateEnPassant(Board board) throws Exception{
        try {
            System.out.println("Initiate EnPassant strategy.. ");
            System.out.println("Enter the x coordinate of the attacker.");
            int sourceX = getUserInput();
            System.out.println("Enter the y coordinate of the attacker.");
            int sourceY = getUserInput();
            System.out.println("Enter the x coordinate of the attacked piece.");
            int attackedX = getUserInput();
            System.out.println("Enter the y coordinate of attacked piece.");
            int attackedY = getUserInput();
            System.out.println("Enter the x coordinate of destination piece.");
            int destX = getUserInput();
            System.out.println("Enter the y coordinate of destination piece.");
            int destY = getUserInput();

            EnPassant enPassant = new EnPassant();
            board= enPassant.initiateStrategy(board, new Move(Tile.getTile(sourceX, sourceY),
                            Tile.getTile(destX, destY)), board.getPieceAtTileLocation(Tile.getTile(sourceX, sourceY)).getPiece(),
                    board.getPieceAtTileLocation(Tile.getTile(attackedX, attackedY)).getPiece());


        }catch (Exception e){
            System.out.println("\nEn Passant is not valid in this case.. \n");
            provideOptions(board);
        }
        alternateTurns();
        return board;
    }

    public boolean initiateCheck(Board board){
        System.out.println("Initiating Check..");
        System.out.println("Enter the x coordinate of the King..");
        int kingX = getUserInput();
        System.out.println("Enter the y coordinate of the king..");
        int kingY = getUserInput();

        Position position = board.getPieceAtTileLocation(Tile.getTile(kingX, kingX));
        Piece piece = position.getPiece();
        if (piece instanceof King){
            return ((King) piece).isKingChecked(board, kingX, kingY);
        }
        return false;


    }

    public String getAlternateColor(){
        if (COLOR.equalsIgnoreCase(WHITE_TURN)){
            return BLACK_TURN;
        }else{
            return WHITE_TURN;
        }
    }

    public boolean isKingUnderCheck(Board board){
        Tile kingTile = findKingLocation(board, COLOR);
        Piece piece = board.getPieceAtTileLocation(kingTile).getPiece();
        if (piece instanceof King){
            return ((King) piece).isKingChecked(board, kingTile.getX(), kingTile.getY());
        }
        return false;

    }

    public boolean isKingUnderCheckMate(Board board) throws Exception{
        Tile kingTile = findKingLocation(board, COLOR);
        Piece piece = board.getPieceAtTileLocation(kingTile).getPiece();
        if (piece instanceof King){
            return ((King) piece).isCheckMate(board, kingTile.getX(), kingTile.getY());
        }
        return false;

    }

    public boolean initiateCheckmate(Board board) throws Exception{

        System.out.println("Initiating Check..");
        System.out.println("Enter the x coordinate of the King..");
        int kingX = getUserInput();
        System.out.println("Enter the y coordinate of the king..");
        int kingY = getUserInput();

        Position position = board.getPieceAtTileLocation(Tile.getTile(kingX, kingX));
        Piece piece = position.getPiece();
        if (piece instanceof King){
            return ((King) piece).isCheckMate(board, kingX, kingY);
        }
        return false;

    }

    public void initiateAction(Board board, int input) throws Exception {
        switch (input) {
            case 1: {
                initiateMovement(board);
                break;
            }
            case 2: {
                initiateCastling(board);
                break;
            }
            case 3: {
                initiateEnPassant(board);
                break;
            }
            default: {
                System.out.println("Invalid move. Please try again..");
                provideOptions(board);
            }

        }
    }


    public void initiateSimulation() throws Exception{
        Board layout = new Board();
        Client client = new Client();

        while (!isGameOver) {
            client.printChessBoard(layout);
            provideOptions(layout);

        }

    }

}
