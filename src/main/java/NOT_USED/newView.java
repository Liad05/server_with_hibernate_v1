package NOT_USED;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.PlayerHandler;
import model.newServer;
import model.protocols;

public class newView extends Application {
    private newModelClient model;
    private newViewModel viewModel;
    private static newServer server;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        this.model = new newModelClient("1", 1);
        this.viewModel = new newViewModel(model);
        model.addObserver(viewModel);
        primaryStage.setTitle("Game Window");

        // Create grid pane for layout
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10));
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        // Add labels and text fields for IP and port
        Label ipLabel = new Label("IP:");
        TextField ipTextField = new TextField("127.0.0.1");
        Label portLabel = new Label("Port:");
        TextField portTextField = new TextField("9999");

        gridPane.add(ipLabel, 0, 0);
        gridPane.add(ipTextField, 1, 0);
        gridPane.add(portLabel, 0, 1);
        gridPane.add(portTextField, 1, 1);

        // Add "Start Game as Host" button
        Button startHostButton = new Button("Start Game as Host");
        // start game
        startHostButton.setOnAction(event -> {
            String ip = ipTextField.getText();
            int port = Integer.parseInt(portTextField.getText());
            server = new newServer(port, new PlayerHandler());
            server.start();
            new Thread(() -> {
                try {
                    this.model.connectToServer();
                    System.out.println("Starting game as host with IP: " + ip + " and port: " + port);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();

            showStartGameWindow();
            primaryStage.close();
        });

        // Add "Join Game as Guest" button
        Button joinGuestButton = new Button("Join Game as Guest");
        // join game
        joinGuestButton.setOnAction(event -> {
            String ip = ipTextField.getText();
            int port = Integer.parseInt(portTextField.getText());
            new Thread(() -> {
                try {
                    this.model.connectToServer();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
            System.out.println("Joining game as guest with IP: " + ip + " and port: " + port);
            showPleaseWaitWindow();
            primaryStage.close();

        });

        gridPane.add(startHostButton, 0, 2);
        gridPane.add(joinGuestButton, 1, 2);

        Scene scene = new Scene(gridPane, 300, 150);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void showStartGameWindow() {
        Stage startGameStage = new Stage();
        startGameStage.setTitle("Start Game");

        Button startGameButton = new Button("Start Game");
        startGameButton.setOnAction(event -> {
            // Move connected clients to the game board
            server.sendMessagesToAllClients(protocols.START_GAME);
            // send start game to all clients
            showGameBoardWindow();
            startGameStage.close();
            viewModel.setGameStarted(true);
            //startGameStage.hide(); // Hide the start game window

        });

        VBox vbox = new VBox(startGameButton);
        vbox.setPadding(new Insets(10));

        Scene scene = new Scene(vbox, 200, 100);
        startGameStage.setScene(scene);
        startGameStage.show();
    }

    private void showGameBoardWindow() {
        Stage gameBoardStage = new Stage();
        gameBoardStage.setTitle("Game Board");

        GridPane gameBoardGrid = new GridPane();
        gameBoardGrid.setPadding(new Insets(10));
        gameBoardGrid.setHgap(1);
        gameBoardGrid.setVgap(1);

        // Create a 3x3 game board
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                Button cellButton = new Button();
                cellButton.setPrefSize(100, 100);
                gameBoardGrid.add(cellButton, col, row);
            }
        }

        // Add label to display player's letters
        Label lettersLabel = new Label("Player's Letters: ABCDEFG");
        gameBoardGrid.add(lettersLabel, 0, 3, 3, 1); // Span across 3 columns

        // Add text field for player to write a letter
        TextField letterTextField = new TextField();
        letterTextField.setPromptText("Enter a letter");
        gameBoardGrid.add(letterTextField, 0, 4);

        // Add text field for player to write a number representing a place on the board
        TextField numberTextField = new TextField();
        numberTextField.setPromptText("Enter a number");
        gameBoardGrid.add(numberTextField, 1, 4);

        Scene scene = new Scene(gameBoardGrid, 200, 300); // Increased height to accommodate the text fields
        gameBoardStage.setScene(scene);
        gameBoardStage.show();
    }

    private void showPleaseWaitWindow() {
        Stage pleaseWaitStage = new Stage();

        pleaseWaitStage.setTitle("Please Wait");

        Label numOfPlayersLabel = new Label("Number of Players: " + 1);
        Label waitLabel = new Label("Please wait...");

        VBox vbox = new VBox(numOfPlayersLabel, waitLabel);
        vbox.setPadding(new Insets(10));

        Scene scene = new Scene(vbox, 200, 100);
        pleaseWaitStage.setScene(scene);
        pleaseWaitStage.show();
        //System.out.println(viewModel.gameStartedProperty().get());
        viewModel.gameStartedProperty().addListener((observable, oldValue, newValue) -> {
            Platform.runLater(() -> {
                System.out.println("game started");
                if (newValue) {
                    pleaseWaitStage.close();
                    showGameBoardWindow();
                }
            });

        });
    }
}


