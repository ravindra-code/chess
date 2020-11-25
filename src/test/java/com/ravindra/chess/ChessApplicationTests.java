package com.ravindra.chess;


import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({TestBishop.class, TestCastle.class, TestKing.class, TestKnight.class,
        TestBoard.class, TestPawn.class, TestPawnPromotion.class, TestTile.class,
        TestRook.class, TestQueen.class, TestPiece.class})
public class ChessApplicationTests {


    void contextLoads() {
    }

}
