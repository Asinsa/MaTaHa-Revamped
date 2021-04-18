package main.panels;

import javafx.beans.binding.Bindings;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.media.AudioClip;
import main.Game;
import main.Mataha;
import main.tile.EffectTile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/***
 * This class is fro the bottom panel for the buttons which are used during the gameplay
 * @author G38.dev
 * @version 1
 */
public class BottomPanel extends Pane {

    private NavigationButtonPanel navigationButtonPanel;
    private PlayerTilesPanel playerTilesPanel;
    private Game game;
    ImageView downArrows = new ImageView();

    /***
     * This is the constructor for the bottom panel, this gets the current game, and sets the navigation panel
     * and player panel.
     * @param game This is the instance of the game.
     */
    public BottomPanel(Game game) {
        this.game = game;
        navigationButtonPanel = new NavigationButtonPanel(this);
        playerTilesPanel = new PlayerTilesPanel(this);
        try {
            downArrows = new ImageView(new Image(new FileInputStream("src/resources/Images/game_images/downArrows.gif"), 100, 100, true, false));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /***
     * This sets the game, as the current game.
     * @param game The current instance of the game
     */
    public void setGame(Game game) {
        this.game = game;
    }

    /***
     * This method once called returns the current instance of the game.
     * @return The current Instance of the game.
     */
    public Game getGame() {
        return game;
    }


    /***
     * This method once called displays the navigation tiles( left,right,Up and down) on the game panel.
     * It clears the original bottom panel children's and adds the new instance of the navigation children.
     */
    public void displayNavigationButtons() {
        this.getChildren().clear();
        navigationButtonPanel.updateUI();
        this.getChildren().add(downArrows);
        this.getChildren().add(navigationButtonPanel);
    }

    /***
     * This method once called displays the player tiles on the game panel.
     * It clears the original bottom panel children's and adds the new instance of the player tiles.
     */
    public void displayPlayerTiles() {
        this.getChildren().clear();
        playerTilesPanel.setPlayerTiles();
        this.getChildren().add(playerTilesPanel);
    }

    /***
     * This method once called displays the player tiles on the game panel.
     * It clears the original bottom panel children's and adds the new instance of the player tiles.
     */
    public void displayNewTile(EffectTile tile) {
        this.getChildren().clear();
        playerTilesPanel.setNewTile(tile);
        this.getChildren().add(downArrows);
        this.getChildren().add(playerTilesPanel);
    }

    /***
     * This method gets all the player Tiles on the panel
     * @return The instance of the playerTiles Panel.
     */
    public PlayerTilesPanel getPlayerTilesPanel() {
        return playerTilesPanel;
    }
}