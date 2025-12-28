/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.clientxogame.navigation;

import com.mycompany.clientxogame.ui.game.board.TwoPlayersBoardController;
import com.mycompany.clientxogame.ui.game.board.SinglePlayerBoardController;
import com.mycompany.clientxogame.ui.history.RecordController;
import com.mycompany.clientxogame.ui.game.multiplayer.OfferController;
import com.mycompany.clientxogame.ui.game.board.OnlineBoardController;
import java.io.IOException;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import static javafx.stage.Window.getWindows;

/**
 *
 * @author user
 */
public class NavigationManager {

    public static ActionEvent lastEvent;
    public static String invitedFrom;
    public static String mySymbol;
    public static boolean isMyTurn;
    public static String currentOpponent;

    private static void changeScene(ActionEvent event, String fxmlFile, String title) {
        Platform.runLater(() -> {
            try {
                Parent root = FXMLLoader.load(NavigationManager.class.getResource(fxmlFile));
                Stage stage = null;

                if (event != null && event.getSource() instanceof Node) {
                    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                }

                if (stage == null) {
                    for (javafx.stage.Window window : getWindows()) {
                        if (window instanceof Stage && window.isShowing()) {
                            stage = (Stage) window;
                            break;
                        }
                    }
                }

                if (stage != null) {
                    stage.setScene(new Scene(root));
                    stage.setTitle(title);
                    stage.show();
                } else {
                    System.err.println("CRITICAL: No Stage found even after deep search!");
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
    }

    private static void changeSceneWithDifficultyFOrWinOrLoss(String fxmlFile, String title, String difficulty) {
        try {
            FXMLLoader loader = new FXMLLoader(NavigationManager.class.getResource(fxmlFile));
            Parent root = loader.load();

            Object controller = loader.getController();

            try {
                if (controller != null) {
                    controller.getClass()
                            .getMethod("setDifficulty", String.class)
                            .invoke(controller, difficulty);
                }
            } catch (NoSuchMethodException e) {
            }

            Stage stage = (Stage) Stage.getWindows().filtered(window -> window.isShowing()).stream().findFirst().orElse(null);

            if (stage != null) {
                stage.setScene(new Scene(root));
                stage.setTitle(title);
                stage.show();
            } else {
                System.err.println("CRITICAL: No Stage found even from Window list!");
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static void changeSceneWithDifficulty(ActionEvent event, String fxmlFile, String title, String difficulty) {
        try {
            FXMLLoader loader = new FXMLLoader(NavigationManager.class.getResource(fxmlFile));
            Parent root = loader.load();

            SinglePlayerBoardController controller = loader.getController();
            controller.setDifficulty(difficulty);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle(title);
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private static void changeScenePlayRecords(ActionEvent event, String fxml, String file) {
        try {
            FXMLLoader loader = new FXMLLoader(NavigationManager.class.getResource(fxml));
            Parent root = loader.load();

            RecordController controller = loader.getController();
            controller.setFile(file);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void goToSingleMode(ActionEvent event) {
        changeScene(event, "/com/mycompany/clientxogame/level-selection.fxml", "Level Selection");
    }

    public static void backToModeSelection(ActionEvent event) {
        changeScene(event, "/com/mycompany/clientxogame/mode-selection.fxml", "Mode Selection");
    }

    public static void backToLevelSelection(ActionEvent event) {
        changeScene(event, "/com/mycompany/clientxogame/level-selection.fxml", "Level Selection");
    }

    public static void goToDoubleMode(ActionEvent event) {
        changeScene(event, "/UI/players/offline-players.fxml", "Offline Players");
        //E:\ITI\java\Team4\client\Client-XO-Game\src\main\resources
    }

    public static void backToOfflinePlayer(ActionEvent event) {
        changeScene(event, "/UI/players/offline-players.fxml", "Offline Players");
    }

    public static void goToRegister(ActionEvent event) {
        changeScene(event, "/UI/Register/signUp.fxml", "Sign Up");
    }

    public static void goToLogIn(ActionEvent event) {
        changeScene(event, "/UI/Register/login.fxml", "Login");

        //E:\ITI\java\Team4\client\Client-XO-Game\src\main\resources
    }

    public static void goToAvailablePlayer(ActionEvent event) {
        changeScene(event, "/UI/players/available-players.fxml", "Available Players");
        //F:\traning\iti\Java\Final Project\ClientXOGame\src\main\resources\
    }
//ui not exist

    public static void goToShowProfile(ActionEvent event) {

        changeScene(event, "/com/mycompany/clientxogame/profile.fxml", "Profile");

    }

    public static void goToShowHistory(ActionEvent event) {

        changeScene(event, "/records/game-history.fxml", "Profile");
    }

    public static void backToShowHistory(ActionEvent event) {

        changeScene(event, "/com/mycompany/clientxogame/profile.fxml", "Profile");
    }

    public static void logOut(ActionEvent event) {
        changeScene(event, "/UI/Register/signUp.fxml", "Sign Up");
    }

    public static void invite(ActionEvent event, String fromPlayer) {
        Platform.runLater(() -> {
            try {
                invitedFrom = fromPlayer;

                FXMLLoader loader = new FXMLLoader(NavigationManager.class.getResource("/com/mycompany/clientxogame/offer.fxml"));
                Parent root = loader.load();

                OfferController controller = loader.getController();
                controller.setFromPlayer(fromPlayer);

                Stage stage;
                if (event != null && event.getSource() instanceof Node) {
                    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                } else {

                    stage = (Stage) Stage.getWindows().stream()
                            .filter(window -> window.isShowing())
                            .findFirst()
                            .orElse(null);
                }

                if (stage != null) {
                    stage.setScene(new Scene(root));
                    stage.setTitle("New Invitation");
                    stage.show();
                } else {
                    System.out.println("Stage is null, cannot switch to Offer screen");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public static void acceptToPlay(ActionEvent event) {
        changeScene(event, "/game/online-board.fxml", "XO Game");
    }

    public static void rejectInvitation(ActionEvent event) {
        changeScene(event, "/UI/players/available-players.fxml", "Available Players");
    }

    public static void cancelInvitation(ActionEvent event) {
        changeScene(event, "/UI/players/available-players.fxml", "Available Players");
    }

    public static void backFromProfile(ActionEvent event) {
        changeScene(event, "/UI/players/available-players.fxml", "Available Players");
    }

    public static void goToShowRecords(ActionEvent event) {
        changeScene(event, "/records/game-records.fxml", "Game Records");
    }

    public static void drawGame() {
        changeScene(null, "/com/mycompany/clientxogame/draw.fxml", "draw");
    }

    public static void winGame() {
        changeScene(null, "/com/mycompany/clientxogame/win.fxml", "Win");
    }

    public static void winGameForLevel(String difficulty) {
        switch (difficulty) {
            case "Easy":
                changeSceneWithDifficultyFOrWinOrLoss("/fxml/WinForLevel.fxml", "You Win", "Easy");
                break;
            case "Medium":
                changeSceneWithDifficultyFOrWinOrLoss("/fxml/WinForLevel.fxml", "You Win", "Medium");
                break;
            case "Hard":
                changeSceneWithDifficultyFOrWinOrLoss("/fxml/WinForLevel.fxml", "You Win", "Hard");
                break;
        }
    }

    public static void loseGameForLevel(String difficulty) {
        switch (difficulty) {
            case "Easy":
                changeSceneWithDifficultyFOrWinOrLoss("/fxml/loseForLevel.fxml", "You Win", "Easy");
                break;
            case "Medium":
                changeSceneWithDifficultyFOrWinOrLoss("/fxml/loseForLevel.fxml", "You Win", "Medium");
                break;
            case "Hard":
                changeSceneWithDifficultyFOrWinOrLoss("/fxml/loseForLevel.fxml", "You Win", "Hard");
                break;
        }
    }

    public static void drawGameForLevel(String difficulty) {
        switch (difficulty) {
            case "Easy":
                changeSceneWithDifficultyFOrWinOrLoss("/fxml/drawForLevel.fxml", "You Win", "Easy");
                break;
            case "Medium":
                changeSceneWithDifficultyFOrWinOrLoss("/fxml/drawForLevel.fxml", "You Win", "Medium");
                break;
            case "Hard":
                changeSceneWithDifficultyFOrWinOrLoss("/fxml/drawForLevel.fxml", "You Win", "Hard");
                break;
        }
    }

    public static void drawGameForTwo() {
        changeScene(null, "/fxml/drawForTwo.fxml", "draw");
    }

    public static void loseGameForTwo() {
        changeScene(null, "/fxml/loseForTwo.fxml", "draw");
    }

    public static void winGameForTwo() {
        changeScene(null, "/fxml/winForTwo.fxml", "draw");
    }

    public static void loseGame() {
        changeScene(null, "/com/mycompany/clientxogame/lose.fxml", "Lose");
    }

    public static void goToPlayAgain(ActionEvent event) {
        changeScene(event, "/game/online-board.fxml", "XO Game");
    }

    public static void goToNewGame(ActionEvent event) {
        changeScene(event, "/game/online-board.fxml", "XO Game");
    }

    public static void goToEasyLevel(ActionEvent event) {
        changeSceneWithDifficulty(event, "/game/single-player-board.fxml", "XO Easy Level", "Easy");
    }

    public static void goToMeduimLevel(ActionEvent event) {
        changeSceneWithDifficulty(event, "/game/single-player-board.fxml", "XO Medium Level", "Medium");
    }

    public static void goToHardLevel(ActionEvent event) {
        changeSceneWithDifficulty(event, "/game/single-player-board.fxml", "XO Hard Level", "Hard");
    }

    public static void backToShowProfile(ActionEvent event) {
        changeScene(event, "/com/mycompany/clientxogame/profile.fxml", "Profile");
    }

    public static void goToPlayRecords(ActionEvent event, String file) {
        changeScenePlayRecords(event, "/records/record.fxml", file);
    }

    public static void backToGameRecords(ActionEvent event) {
        changeScene(event, "/records/game-records.fxml", "Game Record");
    }

    public static void goToWaitAccept(ActionEvent event) {
        lastEvent = event;
        changeScene(event, "/com/mycompany/clientxogame/accept.fxml", "Waiting for Response");
    }

    public static void goToPlay(ActionEvent event) {
        Platform.runLater(() -> {
            try {
                FXMLLoader loader = new FXMLLoader(NavigationManager.class.getResource("/game/online-board.fxml"));
                Parent root = loader.load();

                OnlineBoardController controller = loader.getController();
                controller.setOnlineMode(currentOpponent, mySymbol, isMyTurn);

                Stage stage = null;
                if (event != null && event.getSource() instanceof Node) {
                    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                } else {
                    stage = (Stage) Stage.getWindows().stream().filter(w -> w.isShowing()).findFirst().orElse(null);
                }

                if (stage != null) {
                    stage.setScene(new Scene(root));
                    stage.setTitle("Tic Tac Toe - Online vs " + currentOpponent);
                    stage.show();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
    }

    public static void goToTwoPlayersMode(ActionEvent event, String name1, String name2) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    NavigationManager.class.getResource("/game/two-players-board.fxml")
            );
            Parent root = loader.load();

            TwoPlayersBoardController controller = loader.getController();
            controller.setPlayersNames(name1, name2);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void gotoModeSelection(ActionEvent event) {
        changeScene(event, "/com/mycompany/clientxogame/mode-selection.fxml", "XO Game");
    }

}
