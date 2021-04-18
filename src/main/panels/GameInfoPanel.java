package main.panels;

import javafx.geometry.Insets;
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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * Class for the game info panel GUI.
 *
 * @author G38.dev
 */
public class GameInfoPanel extends VBox {

    private Game game;
    private TipLabel tipLabel;
    private Button next;
    private Button navNext;
    private PlayerTurnPanel ptp;
    ImageView downArrows = new ImageView();
    ImageView upArrows = new ImageView();
    MenuBars menuBar = new MenuBars(new Stage());
    private static final AudioClip NEXT_SFX = new AudioClip(new File("src/resources/SFX/next.mp3").toURI().toString());

    /**
     * Constructor sets the game info panel in the game window.
     *
     * @param game of type main.Game.
     */
    public GameInfoPanel(Game game) {
        this.game = game;
        this.setAlignment(Pos.TOP_CENTER);
        this.setPadding(new Insets(0, 60, 0, 0));
        this.setSpacing(100);
        this.setMaxWidth(200);
        try {
            downArrows = new ImageView(new Image(new FileInputStream("src/resources/Images/game_images/downArrows.gif"), 100, 100, true, false));
            upArrows = new ImageView(new Image(new FileInputStream("src/resources/Images/game_images/upArrows.gif"), 100, 100, true, false));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        initNodes();
    }

    /**
     * Set up the panel so the user can see it
     */
    private void initNodes() {

        ptp = new PlayerTurnPanel(game);
        this.getChildren().add(ptp);
        ptp.setVisible(true);
        tipLabel = new TipLabel(game);
        this.getChildren().add(tipLabel);
        tipLabel.setDrawTileText();
        next = new Button(Utils.translate("Next", MainMenu.getLang()));
        this.getChildren().add(downArrows);
        this.getChildren().add(next);
        next.setOnMouseClicked(event -> {
            NEXT_SFX.play(menuBar.getSFXVolume());
            Mataha.getGame().gameLoop(Mataha.getGame().nextPlayer());
            next.setVisible(false);
            downArrows.setVisible(false);
        });
        next.setVisible(false);
        downArrows.setVisible(false);

        navNext = new Button(Utils.translate("Next", MainMenu.getLang()));
        this.getChildren().add(navNext);
        this.getChildren().add(upArrows);
        navNext.setOnMouseClicked(event -> {
            NEXT_SFX.play(menuBar.getSFXVolume());
            Mataha.getGame().initNavigationUI();
            navNext.setVisible(false);
            upArrows.setVisible(false);
        });
        navNext.setVisible(false);
        upArrows.setVisible(false);
    }

    public TipLabel getTipLabel() {
        return tipLabel;
    }

    public void showNextButton() {
        next.setVisible(true);
        upArrows.setVisible(MainMenu.getMenuBar().getTutorial());
    }

    public void showNavNextButton() {
        navNext.setVisible(true);
        downArrows.setVisible(MainMenu.getMenuBar().getTutorial());
    }

    public void hideNavNextButton() {
        navNext.setVisible(false);
        upArrows.setVisible(false);
        downArrows.setVisible(false);
    }

    public void hideNextButton() {
        next.setVisible(false);
        upArrows.setVisible(false);
        downArrows.setVisible(false);
    }

    public PlayerTurnPanel getPtp() {
        return ptp;
    }
}