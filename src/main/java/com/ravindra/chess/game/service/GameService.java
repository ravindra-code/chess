package com.ravindra.chess.game.service;

import com.ravindra.chess.game.*;
import com.ravindra.chess.game.dto.ChessMan;
import com.ravindra.chess.game.factory.PieceFactory;
import com.ravindra.chess.game.piece.*;
import com.ravindra.chess.game.strategies.Castling;
import com.ravindra.chess.game.strategies.PawnPromotion;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/chess")
public class GameService {

    Logger log = LoggerFactory.getLogger(this.getClass().getName());

    @GetMapping("/game/begin")
    @ApiOperation(value = "Start a new game.")
    @ResponseStatus(HttpStatus.OK)
    public List<ChessMan> startNewGame() {
        log.debug("##GameService:: startNewGame:: Begin new game..");
        Board board = new Board();
        return getGameLayout(board);
    }

    @PostMapping(value = "/game/castle/{color}/{xKing}/{yKing}/{xRook}/{yRook}", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Initiate Castling strategy.")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public List<ChessMan> initiateCastling(@Valid @RequestBody List<ChessMan> layout, @PathVariable String color, @PathVariable int xKing,
                                           @PathVariable int yKing, @PathVariable int xRook, @PathVariable int yRook) {

        log.debug("##GameService:: initiateCastling:: Initiate request for Castling move..");
        Board board = new Board(layout);
        Board finalLayout = new Castling().initiateStrategy(board,
                new Position(xRook, yRook, new PieceFactory().getChessPiece(Pieces.ROOK.name(), color)),
                new Position(xKing, yKing, new PieceFactory().getChessPiece(Pieces.KING.name(), color)));
        return getGameLayout(finalLayout);
    }

    @PostMapping(value = "/game/enpassant/{color}/{xSource}/{ySource}/{xDestination}/{yDestination}", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Initiate En Passant strategy.")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public List<ChessMan> initiateEnPassant(@Valid @RequestBody List<ChessMan> layout, @PathVariable String color, @PathVariable int xSource,
                                            @PathVariable int ySource, @PathVariable int xDestination, @PathVariable int yDestination) {
        // Initiate enpassant strategy.
        log.debug("##GameService:: initiateEnPassant:: Initiate request for EnPassant strategy..");
        return null;
    }

    @PostMapping(value = "/game/promotion/{color}/{topromote}{xCoordinate}{yCoordinate}", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Initiate Pawn Promotion strategy.")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public List<ChessMan> initiatePawnPromotion(@Valid List<ChessMan> layout, @PathVariable String color, @PathVariable String topromote,
                                                @PathVariable int xCoordinate, @PathVariable int yCoordinate) {

        log.debug("##GameService:: initiatePawnPromotion:: Initiate Pawn Promotion strategy..");
        Board board = new Board(layout);
        Board finalLayout = new PawnPromotion().initiateStrategy(board,
                new Position(xCoordinate, yCoordinate, new Pawn(Status.ALIVE, Color.valueOf(color))),
                new PieceFactory().getChessPiece(topromote, color)
        );
        return getGameLayout(finalLayout);
    }

    @PostMapping(value = "/game/move/{sourceX}/{sourceY}/{destX}/{destY}/{piece}/{color}", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Initiate a move.")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Board makeMove(@Valid @RequestBody List<ChessMan> layout, @PathVariable(name = "sourceX") int sourceX, @PathVariable(name = "sourceY") int sourceY,
                          @PathVariable(name = "destX") int destX,
                          @PathVariable(name = "destY") int destY, @PathVariable(name = "piece") String piece, @PathVariable(name = "color") String color) {

        log.debug("##GameService:: makeMove:: Execute the move..");
        Board board = new Board(layout);
        board.executeMove(layout, new Move(Tile.getTile(sourceX, sourceY), Tile.getTile(destX, destY)),
                new PieceFactory().getChessPiece(piece, color));
        return board;
    }

    @PostMapping("/game/rollback")
    public void rollBackMove(List<ChessMan> layout) {
        //todo. Yet to implement.
    }

    @PostMapping(value = "/game/valid/{startX}/{startY}/{destX}/{destY}/{piece}/{color}", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Validate if a particular move is valid or not.")
    @ResponseStatus(HttpStatus.OK)
    public boolean isMoveValid(@Valid @RequestBody List<ChessMan> layout, @PathVariable(name = "startX") Integer startX,
                               @PathVariable(name = "startY") Integer startY, @PathVariable(name = "destX") Integer destX,
                               @PathVariable(name = "destY") Integer destY, @PathVariable(name = "piece") String piece,
                               @PathVariable(name = "color") String color) {

        log.debug("##GameService:: isMoveValid:: Validate if the move is valid or not..");
        Board board = new Board(layout);
        Piece chessPiece = new PieceFactory().getChessPiece(piece, color);
        Move move = new Move(Tile.getTile(startX, startY), Tile.getTile(destX, destY));
        return chessPiece.isValidMove(board, move);
    }

    @PostMapping(value = "/check/{xCoordinate}/{yCoordinate}", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Validate if the king is under check.")
    @ResponseStatus(HttpStatus.OK)
    public boolean isKingUnderCheck(@Valid @RequestBody List<ChessMan> layout,
                                    @PathVariable int xCoordinate, @PathVariable int yCoordinate) {

        log.debug("##GameService:: isKingUnderCheck:: Validate if the king is under check..");
        Board board = new Board(layout);
        Position position = board.getPieceAtTileLocation(Tile.getTile(xCoordinate, yCoordinate));
        if (position.getPiece() instanceof King) {
            return ((King) position.getPiece()).isKingChecked(board, xCoordinate, yCoordinate);
        }
        return false;
    }

    @PostMapping(value = "/checkmate/{xCoordinate}/{yCoordinate}", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Validate if the king is under checkmate.")
    @ResponseStatus(HttpStatus.OK)
    public boolean isKingUnderCheckMate(@Valid @RequestBody List<ChessMan> layout, @PathVariable int xCoordinate, @PathVariable int yCoordinate) {

        try {
            log.debug("##GameService:: isKingUnderCheckMate:: Validate if the king is under checkmate..");
            Board board = new Board(layout);
            Position position = board.getPieceAtTileLocation(Tile.getTile(xCoordinate, yCoordinate));
            if (position.getPiece() instanceof King) {
                return ((King) position.getPiece()).isCheckMate(board, xCoordinate, yCoordinate);
            }
        }catch (Exception e){
            log.error("Error while checking checkmate..");
        }
        return false;

    }

    public List<ChessMan> getGameLayout(Board board) {
        List<ChessMan> layout = new ArrayList<>();
        Arrays.stream(Tile.values()).forEach(e -> {

            if (!board.isTileEmpty(e)) {
                Piece piece = board.getPieceAtTileLocation(e).getPiece();
                layout.add(new ChessMan(piece.getColor().name(),
                        new PieceFactory().getChessPieceName(piece), e.getX(), e.getY(), piece.getCounter()
                ));
            }
        });
        return layout;
    }


}
