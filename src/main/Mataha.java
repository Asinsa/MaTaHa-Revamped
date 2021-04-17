package main;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.util.Optional;

/**
 * main.Mataha is the main game class that runs the game itself.
 *
 * @author G38.dev
 * @version 1
 */
public class Mataha {

    private static Game game;
    private static Scene mainGame;
    private static Stage gameStage = new Stage();
    public static String texturePack = "road";


    public Mataha() {
        showGameStage();
    }

    public static Scene getScene() {
        return mainGame;
    }


    /**
     * Method creates and shows the main game window where the game will be played.
     */
    public void showGameStage() {
        BorderPane mainLayout = new BorderPane();
        mainGame = new Scene(mainLayout, 900, 750);
        mainGame.getStylesheets().add("src/resources/StyleSheets/MaTaHa.css");
        game = new Game(mainLayout);
        gameStage.setScene(mainGame);

        gameStage.setMaximized(true);
        gameStage.setTitle(Utils.translate("play now!", MainMenu.getLang()));
        gameStage.setResizable(MainMenu.getResizable());
        gameStage.getIcons().add(new Image("src/resources/Images/menu_images/icon2.png"));
        gameStage.show();
    }

    /**
     * @return the current game instance
     */
    public static Game getGame() {
        return game;
    }

    public static Stage getStage() {
        return gameStage;
    }
}