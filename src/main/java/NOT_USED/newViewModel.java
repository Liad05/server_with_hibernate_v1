package NOT_USED;

import model.protocols;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

import java.util.Observable;
import java.util.Observer;

public class newViewModel implements Observer {
    private newModelClient model;
    private BooleanProperty gameStartedProperty;
    public newViewModel(newModelClient model){
        this.model=model;
        gameStartedProperty = new SimpleBooleanProperty(false);
    }
    public BooleanProperty gameStartedProperty() {
        return gameStartedProperty;
    }

    public void setGameStarted(boolean gameStarted) {
        gameStartedProperty.set(gameStarted);
        model.setGameStarted();
    }

    @Override
    public void update(Observable o, Object arg) {
        String updateFromModel = (String) arg;
        System.out.println("ViewModel: " + updateFromModel);
        switch (updateFromModel) {
            case protocols.START_GAME:
                gameStartedProperty.set(true);
                break;

        }
    }
}
