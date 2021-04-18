package main.panels;

import javafx.beans.binding.Bindings;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.AudioClip;
import javafx.stage.Stage;
import main.*;
import main.extra_features.MenuBars;
import main.tile.NavigationTile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/***
 * This class is providing the bottom panel for the navigation tiles.
 * @author G38.dev
 * @version 3
 */

public class NavigationButtonPanel extends VBox {

    private BottomPanel context;
    private Button up;
    private Button right;
    private Button down;
    private Button left;
    ImageView downArrows = new ImageView();
    MenuBars menuBar = new MenuBars(new Stage());
    private static final AudioClip MOVEMENT_SFX = new AudioClip(new File("src/resources/SFX/movement.mp3").toURI().toString());

    public NavigationButtonPanel(BottomPanel context) {
        this.context = context;
        try {
            downArrows = new ImageView(new Image(new FileInputStream("src/resources/Images/game_images/downArrows.gif"), 100, 100, true, false));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        initSelf();
    }

    /***
     * This method is getting the buttons which are used for navigation throughout the game.
     */
    private void initSelf() {
        this.setAlignment(Pos.CENTER);
        this.setSpacing(3);

        up = new Button(Utils.translate("Up", MainMenu.getLang()));
        up.setOnMouseClicked(event -> {
            MOVEMENT_SFX.play(menuBar.getSFXVolume());
            Player currentPlayer = Mataha.getGame().getCurrentPlayer();
            currentPlayer.setNumMoves(currentPlayer.getNumMoves() - 1);
            currentPlayer.moveCardinally(NavigationTile.DIRECTION.UP);
            updateUI();
//            System.out.println(currentPlayer.getNumMoves());
            Mataha.getGame().getGameBoard().updateAllButtonsForFire();
            if (currentPlayer.getNumMoves() <= 0) {
                Mataha.getGame().getGameBoard().updateAllButtonsForFire();
                Mataha.getGame().gameLoop(Mataha.getGame().nextPlayer());
            }
        });

        left = new Button(Utils.translate("Left", MainMenu.getLang()));
        left.setOnMouseClicked(event -> {
            MOVEMENT_SFX.play(menuBar.getSFXVolume());
            Player currentPlayer = Mataha.getGame().getCurrentPlayer();
            currentPlayer.setNumMoves(currentPlayer.getNumMoves() - 1);
            currentPlayer.moveCardinally(NavigationTile.DIRECTION.LEFT);
            updateUI();
            Mataha.getGame().getGameBoard().updateAllButtonsForFire();
            if (currentPlayer.getNumMoves() <= 0) {
                Mataha.getGame().getGameBoard().updateAllButtonsForFire();
                Mataha.getGame().gameLoop(Mataha.getGame().nextPlayer());
            }
        });

        right = new Button(Utils.translate("Right", MainMenu.getLang()));
        right.setOnMouseClicked(event -> {
            MOVEMENT_SFX.play(menuBar.getSFXVolume());
            Player currentPlayer = Mataha.getGame().getCurrentPlayer();
            currentPlayer.setNumMoves(currentPlayer.getNumMoves() - 1);
            currentPlayer.moveCardinally(NavigationTile.DIRECTION.RIGHT);
            updateUI();
            Mataha.getGame().getGameBoard().updateAllButtonsForFire();
            if (currentPlayer.getNumMoves() <= 0) {
                Mataha.getGame().getGameBoard().updateAllButtonsForFire();
                Mataha.getGame().gameLoop(Mataha.getGame().nextPlayer());
            }
        });

        down = new Button(Utils.translate("Down", MainMenu.getLang()));
        down.setOnMouseClicked(event -> {
            MOVEMENT_SFX.play(menuBar.getSFXVolume());
            Player currentPlayer = Mataha.getGame().getCurrentPlayer();
            currentPlayer.setNumMoves(currentPlayer.getNumMoves() - 1);
            currentPlayer.moveCardinally(NavigationTile.DIRECTION.DOWN);
            updateUI();
            Mataha.getGame().getGameBoard().updateAllButtonsForFire();
            if (currentPlayer.getNumMoves() <= 0) {
                Mataha.getGame().getGameBoard().updateAllButtonsForFire();
                Mataha.getGame().gameLoop(Mataha.getGame().nextPlayer());
            }
        });

        HBox top = new HBox();
        top.setAlignment(Pos.CENTER);
        HBox middle = new HBox();
        middle.setAlignment(Pos.CENTER);
        middle.setSpacing(3);
        HBox bottom = new HBox();
        bottom.setAlignment(Pos.CENTER);

        top.getChildren().add(up);
        middle.getChildren().add(left);
        middle.getChildren().add(right);
        bottom.getChildren().add(down);

        this.getChildren().add(downArrows);
        this.getChildren().add(top);
        this.getChildren().add(middle);
        this.getChildren().add(bottom);
        this.setStyle("-fx-background-color: linear-gradient(to bottom,#636363, #999b9e)");
        this.setMinHeight(120);
        this.setMinWidth(980);
        this.prefWidthProperty().bind(Mataha.getStage().widthProperty());
    }

    /***
     * This method checks if the player can move in any direction, if they cannot the corresponding button is disabled.
     */
    public void updateUI() {

        Player currentPlayer = Mataha.getGame().getCurrentPlayer();
        BoardSquare bs = currentPlayer.getSquare();

        downArrows.setVisible(MainMenu.getMenuBar().getTutorial());

        if (currentPlayer.canMoveUp()) {
            up.setDisable(false);
        } else {
            up.setDisable(true);
        }

        if (currentPlayer.canMoveRight()) {
            right.setDisable(false);
        } else {
            right.setDisable(true);
        }

        if (currentPlayer.canMoveDown()) {
            down.setDisable(false);
        } else {
            down.setDisable(true);
        }

        if (currentPlayer.canMoveLeft()) {
            left.setDisable(false);
        } else {
            left.setDisable(true);
        }
    }
}
