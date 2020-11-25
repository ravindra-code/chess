package com.ravindra.chess;

import com.ravindra.chess.game.Tile;
import org.junit.Assert;
import org.junit.Test;

public class TestTile {

    @Test
    public void isTileValid() {
        Assert.assertTrue(Tile.isValid(3, 4));
        Assert.assertFalse(Tile.isValid(9, 10));
    }

    @Test
    public void getTile() {
        Tile tile = Tile.getTile(2, 4);
        Assert.assertEquals(tile.name(), "C5");
        Assert.assertEquals(tile.getX(), 2);
        Assert.assertEquals(tile.getY(), 4);

    }
}
