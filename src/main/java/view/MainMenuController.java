package view;

import java.util.Observable;
import java.util.Observer;

import model.newServer;
import javafx.fxml.FXML;
import model.Model;
import viewmodel.ViewModel;

public class MainMenuController {

    private SceneController sceneController;
    private newServer server;
    private ViewModel vm;
    private Model model;

    public void setSceneController(SceneController sceneController) {
        this.sceneController = sceneController;
    }

    @FXML
    public void startGame() {

        sceneController.showGame();
    }
    @FXML
    public void showHostWaitingRoom() {

        server = newServer.get();
        this.model.connectToServer();
        vm.getNumPlayers();
        vm.getId();
        sceneController.showHostWaitingRoom();
    }
    @FXML
    public void showGuestWaitingRoom() {
        this.model.connectToServer();
        vm.getNumPlayers();
        vm.getId();
        sceneController.showGuestWaitingRoom();
    }

    @FXML
    public void showHelp() {
        sceneController.showHelpMenu();
    }

    @FXML
    public void showStatistics() {
        // load statistics scene
    }

    public void setViewModel(ViewModel vm) {
        this.vm = vm;
    }


    public void setModel(Model m) {
        this.model = m;
    }
}
