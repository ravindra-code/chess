package com.ravindra.chess.memento;

import java.util.ArrayList;

// Is yet to be implemented. for Rollback functionality
public class Caretaker {

    private ArrayList<Memento> mementos = new ArrayList<>();

    public void addMemento(Memento m) {
        mementos.add(m);
    }

    public Memento getMemento() {
        return mementos.get(1);
    }
}
