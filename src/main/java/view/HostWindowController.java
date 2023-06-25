package view;
import javafx.application.Platform;
import model.newServer;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import viewmodel.ViewModel;
import model.protocols;
import java.util.Observable;
import java.util.Observer;

public class HostWindowController extends Observable implements Observer {
    private SceneController sceneController;
    private Stage stage;

    @FXML
    private Button startGameButton;

    @FXML
    private Label numberOfPlayersLabel1;
    @FXML
    private Label PlayerNumber;

    public static newServer server;
    private ViewModel viewModel;

    public void setSceneController(SceneController sceneController) {
        this.sceneController = sceneController;
    }
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    public void startGame() {
        server = newServer.get();
        System.out.println("Starting game");
        sceneController.showGame();
        server.sendMessagesToAllClients(protocols.START_GAME);
        //
    }

    public void setViewModel(ViewModel viewModel) {
        this.viewModel = viewModel;
        numberOfPlayersLabel1.textProperty().bind(this.viewModel.numPlayers);
        PlayerNumber.textProperty().bind(this.viewModel.id);

        viewModel.getNumPlayers();

//        viewModel.gameStartedProperty().addListener((observable, oldValue, newValue) -> {
//            Platform.runLater(() -> {
//                //System.out.println("game started");
//
//            });
//
//        });
    }

    @Override
    public void update(Observable o, Object arg) {

    }
}
