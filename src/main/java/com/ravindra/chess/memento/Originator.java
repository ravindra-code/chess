package com.ravindra.chess.memento;

import com.ravindra.chess.game.Position;

// Is yet to be implemented. for Rollback functionality
public class Originator {

    private Position[][] state;

    public void setState(Position[][] state) {
        this.state = state.clone();
    }

    public Memento save() {
        return new Memento(state);
    }

    public void restore(Memento m) {
        state = m.getState();
    }
}
