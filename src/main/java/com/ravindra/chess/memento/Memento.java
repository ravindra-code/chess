package com.ravindra.chess.memento;

import com.ravindra.chess.game.Position;

// Is yet to be implemented. for Rollback functionality
public class Memento {

    private Position[][] state;

    public Memento(Position[][] state) {
        this.state = state;
    }

    public Position[][] getState() {
        return state;
    }
}
