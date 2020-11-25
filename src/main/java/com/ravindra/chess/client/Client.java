package com.ravindra.chess.client;

import com.ravindra.chess.game.Board;
import com.ravindra.chess.game.Move;
import com.ravindra.chess.game.Position;
import com.ravindra.chess.game.Tile;
import com.ravindra.chess.game.factory.PieceFactory;
import com.ravindra.chess.game.piece.Piece;

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Client {
    private static final String EMPTY_STRING = "-----";
    private PieceFactory pieceFactory;
    private static boolean isGameOver = false;
    private static String COLOR = "W";

    public Client() {
        pieceFactory = new PieceFactory();
    }

    public String createPieceObject(Piece piece) {
        StringBuilder builder = new StringBuilder();
        builder.append(pieceFactory.getChessPieceName(piece).substring(0, 2));
        builder.append("|" + piece.getColor().name().substring(0, 1));
        return builder.toString();

    }

    public void provideOptions() {
        System.out.println("## Please Select any of the below options.");
        System.out.println("1: Move a Piece.");
        System.out.println("2: Initiate Castling.");
        System.out.println("3: Initiate En Passant.");
        System.out.println("4: Initiate Pawn Promotion.");
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
        System.out.println("--------------------------------------------------------------------------------------");

    }


    public int getUserInput(){
        Scanner sc= new Scanner(System.in);
        return sc.nextInt();

    }

    public int generateRandomNumber() {
        Random random = new Random();
        return random.nextInt(8);
    }


    public boolean initiateMovement(Board board) {
        boolean moveStatus = false;
        System.out.println("Input the source tile. ");
        int sourceX = getUserInput();
        int sourceY = getUserInput();
        System.out.println("Source: "+ sourceX + " : "+ sourceY);
        System.out.println("Input the destination tile. ");
        int destX = getUserInput();
        int destY = getUserInput();
        System.out.println("Destination: "+ destX + " : "+destY);
        Position position = board.getPieceAtTileLocation(Tile.getTile(sourceX, sourceY));
        try {
            moveStatus = board.changePositions(new Move(Tile.getTile(sourceX, sourceY), Tile.getTile(destX, destY)), position.getPiece());
        } catch (Exception e) {
            System.out.println(e.getCause());
            System.out.println("Invalid move. Reinitialize the move..");
            initiateMovement(board);
        }
        return moveStatus;
    }

    public void initiateAction(Board board, int input) throws InterruptedException {
        switch (input) {
            case 1: {
                initiateMovement(board);
            }
            break;

        }
    }


        public void initiateSimulation() throws InterruptedException{
            Board layout = new Board();
            Client client = new Client();

            while (!isGameOver) {
                client.printChessBoard(layout);
                provideOptions();
                Thread.sleep(100);
                int ip = getUserInput();
                initiateAction(layout, ip);
            }


        }

}
