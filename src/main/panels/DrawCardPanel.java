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
import main.MainMenu;
import main.Mataha;
import main.Utils;
import main.extra_features.MenuBars;
import main.tile.NavigationTile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * This class is for the panel which holds the draw card, function on the game board.
 *
 * @author G38.dev
 * @version 1.5
 */
public class DrawCardPanel extends VBox {

    public static final String TILE_BAG_IMAGE = "src\\resources\\Images\\game_images\\tilebag.gif";
    public ImageView imageView;
    public NavigationTile navigationTile;
    public Button drawTile;
    private HBox hBox;
    private Button rotateLeft;
    private Button rotateRight;
    ImageView downArrows = new ImageView();
    ImageView upArrows = new ImageView();
    MenuBars menuBar = new MenuBars(new Stage());
    private static final AudioClip NEXT_SFX = new AudioClip(new File("src/resources/SFX/next.mp3").toURI().toString());

    /**
     * This creates the space for the draw card panel upon the game, this sets the width, the alignment ready for the content.
     */
    public DrawCardPanel() {
        this.setAlignment(Pos.CENTER);
        this.setPadding(new Insets(20));
        this.setSpacing(20);
        this.setPrefWidth(200);
        try {
            downArrows = new ImageView(new Image(new FileInputStream("src/resources/Images/game_images/downArrows.gif"), 100, 100, true, false));
            upArrows = new ImageView(new Image(new FileInputStream("src/resources/Images/game_images/upArrows.gif"), 100, 100, true, false));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        initNodes();
    }

    /**
     * This loads the data into the draw card pane.
     * This consists of a button, which once clicked will provide a main.tile/ effect main.tile to the player to play.
     */
    public void initNodes() {
        drawTile = new Button(Utils.translate("Draw card", MainMenu.getLang()));
        drawTile.setOnMouseClicked(event -> {
            NEXT_SFX.play(menuBar.getSFXVolume());
            Mataha.getGame().getCurrentPlayer().drawCard();
            drawTile.setDisable(true);
            downArrows.setVisible(false);
        });
        this.getChildren().add(downArrows);
        this.getChildren().add(drawTile);
        imageView = new ImageView();
        imageView.setFitHeight(50);
        imageView.setFitWidth(50);
        setImageAsTileBag();
    }

    /**
     * This allows new images to be added to the draw card panel.
     * This method also removes old images and adds new elements in.
     */

    public void setImageAsTileBag() {
        if (this.getChildren().contains(imageView)) return;
        this.getChildren().remove(navigationTile);
        this.getChildren().remove(hBox);
        imageView.setImage(new Image(TILE_BAG_IMAGE));
        this.getChildren().add(imageView);
    }

    /**
     * This method allows the setting of the main.tile if it is a navigation main.tile,
     * this means that the user can rotate the main.tile by 90 degrees dependant on which button was pressed.
     */
    public void setImageAsTile() {
        if (this.getChildren().contains(navigationTile)) return;
        this.getChildren().remove(imageView);
        navigationTile = Mataha.getGame().getCurrentPlayer().getHeldNavigationTile();
        this.getChildren().add(navigationTile);

        rotateLeft = new Button("\t↺");
        rotateLeft.setOnMouseClicked(event -> {
            NEXT_SFX.play(menuBar.getSFXVolume());
            navigationTile.rotateClockwise(3);
        });
        rotateRight = new Button("\t↻");
        rotateRight.setOnMouseClicked(event -> {
            NEXT_SFX.play(menuBar.getSFXVolume());
            navigationTile.rotateClockwise();
        });

        hBox = new HBox();
        hBox.setSpacing(20);
        hBox.getChildren().add(rotateLeft);
        hBox.getChildren().add(rotateRight);

        this.getChildren().add(hBox);
        this.getChildren().add(upArrows);
        showRotateButtons();

    }

    /**
     * This shows the main.tile which has been drawn from the main.tile bag.
     */
    public void showDrawTileUI() {
        imageView.setVisible(true);
        setImageAsTileBag();
        drawTile.setVisible(true);
        drawTile.setDisable(false);
        downArrows.setVisible(check());
    }

    /**
     * This method shows the rotate buttons for the Navigation Tiles.
     */
    public void showRotateButtons() {
        rotateRight.setVisible(true);
        rotateLeft.setVisible(true);
        upArrows.setVisible(check());
    }

    /**
     * This method hides the buttons for all the other tiles except navigation tiles.
     */
    public void hideRotationButtons() {
        rotateRight.setVisible(false);
        rotateLeft.setVisible(false);
        upArrows.setVisible(false);
    }

    private boolean check() {
        return MainMenu.getMenuBar().getTutorial();
    }
}