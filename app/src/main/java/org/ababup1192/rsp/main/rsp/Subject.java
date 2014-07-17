package org.ababup1192.rsp.main.rsp;

import org.ababup1192.rsp.main.Observer;

import java.util.ArrayList;
import java.util.List;

public abstract class Subject {

    List<Observer> observerList = new ArrayList<Observer>();

    public void addObserver(Observer observer) {
        observerList.add(observer);
    }

    public void notifyObservers() {
        for (Observer observer : observerList) {
            observer.update();
        }
    }

}
