package main.panels;

import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.media.AudioClip;
import javafx.stage.Stage;
import main.Mataha;
import main.Player;
import main.extra_features.MenuBars;
import main.tile.EffectTile;
import main.tile.EffectTileContainer;

import java.io.File;

/**
 * Class for the player tiles panel
 *
 * @author G38.dev
 */
public class PlayerTilesPanel extends HBox {

    private BottomPanel context;
    MenuBars menuBar = new MenuBars(new Stage());
    private static final AudioClip FIRE_SFX = new AudioClip(new File("src/resources/SFX/fire.mp3").toURI().toString());
    private static final AudioClip ICE_SFX = new AudioClip(new File("src/resources/SFX/ice.mp3").toURI().toString());
    private static final AudioClip DOUBLEMOVE_SFX = new AudioClip(new File("src/resources/SFX/doublemove.mp3").toURI().toString());
    private static final AudioClip BACKTRACK_SFX = new AudioClip(new File("src/resources/SFX/backtrack.mp3").toURI().toString());

    public PlayerTilesPanel(BottomPanel context) {
        this.context = context;
        initSelf();
    }

    /**
     * actually initialises the panel
     */
    private void initSelf() {
        this.setAlignment(Pos.CENTER_LEFT);
        this.setStyle("-fx-background-color: linear-gradient(to bottom,#636363, #999b9e)");
        this.setMinHeight(120);
        this.prefWidthProperty().bind(Mataha.getStage().widthProperty());
        this.setMinWidth(980);
        this.setSpacing(20);
        this.setPadding(new Insets(10));
    }

    public void insertEffectTile(EffectTile tile) {
        EffectTileContainer container = new EffectTileContainer(tile);
        this.getChildren().add(container);
    }

    /**
     * Shows the current players tiles.
     */
    public void setPlayerTiles() {
        this.getChildren().clear();
        Player currentPlayer = context.getGame().getCurrentPlayer();
        System.out.println(currentPlayer);

        for (EffectTile tile : currentPlayer.getHeldTiles()) {

            EffectTileContainer container = new EffectTileContainer(tile);
            container.setOnMouseClicked(event -> {
                if (container.getTile().isFirstTurn()) return;
                Mataha.getGame().getCurrentPlayer().setCurrentlySelectedEffect(container);
                container.setClicked(true);
                unClickAllExcept(container);
                Mataha.getGame().getGameInfoPanel().hideNavNextButton();
                switch (tile.getTileEffect().getEffectName()) {
                    case "ice":
                        ICE_SFX.play(menuBar.getSFXVolume());
                        Mataha.getGame().setBoardState(1);
                        break;
                    case "fire":
                        FIRE_SFX.play(menuBar.getSFXVolume());
                        Mataha.getGame().setBoardState(2);
                        break;
                    case "backtrack":
                        BACKTRACK_SFX.play(menuBar.getSFXVolume());
                        Mataha.getGame().setBoardState(3);
                        break;
                    case "doublemove":
                        //main.Mataha.getGame().setBoardState(4);
                        DOUBLEMOVE_SFX.play(menuBar.getSFXVolume());
                        Mataha.getGame().getCurrentPlayer().setNumMoves(2);
                        Mataha.getGame().getBottomPanel().getPlayerTilesPanel().removeEffectTile();
                        Mataha.getGame().setBoardState(0);
                        Mataha.getGame().initNavigationUI();
                        break;
                }
            });
            container.setOnMouseEntered(event -> {
                if (container.getTile().isFirstTurn()) return;
                container.setMouseHover(true);
            });
            container.setOnMouseExited(event -> {
                if (container.getTile().isFirstTurn()) return;
                container.setMouseHover(false);
            });
            this.getChildren().add(container);

        }
    }

    public void setNewTile(EffectTile effectTile) {
        this.getChildren().clear();
        Player currentPlayer = context.getGame().getCurrentPlayer();
        System.out.println(currentPlayer);
        EffectTileContainer container = new EffectTileContainer(effectTile);
        container.setOnMouseClicked(event -> {
            Mataha.getGame().getCurrentPlayer().setCurrentlySelectedEffect(container);
            container.setClicked(true);
            // unClickAllExcept(container);
            Mataha.getGame().getGameInfoPanel().hideNextButton();
            /*
            switch (effectTile.getTileEffect().getEffectName()) {
                case "ice":
                    main.Mataha.getGame().setBoardState(1);
                    break;
                case "fire":
                    main.Mataha.getGame().setBoardState(2);
                    break;
                case "backtrack":
                    main.Mataha.getGame().setBoardState(3);
                    break;
                case "doublemove":
                    //main.Mataha.getGame().setBoardState(4);
                    main.Mataha.getGame().getCurrentPlayer().setNumMoves(2);
                    main.Mataha.getGame().getBottomPanel().getPlayerTilesPanel().removeEffectTile();
                    main.Mataha.getGame().setBoardState(0);
                    main.Mataha.getGame().initNavigationUI();
                    break;
            }

             */
        });
        /*
            container.setOnMouseEntered(event -> {
                container.setMouseHover(true);
            });
            container.setOnMouseExited(event -> {
                container.setMouseHover(false);
            });

         */
        this.getChildren().add(container);
    }

    /**
     * Remove the effect main.tile whe played.
     */
    public void removeEffectTile() {
        Player currentPlayer = Mataha.getGame().getCurrentPlayer();
        EffectTileContainer effectTileContainer = Mataha.getGame().getCurrentPlayer().getCurrentlySelectedEffect();
        currentPlayer.getHeldTiles().remove(effectTileContainer.getTile());
        currentPlayer.setCurrentlySelectedEffect(null);
        setPlayerTiles();
    }

    /**
     * Method unclicks all the tiles.
     *
     * @param container the object to NOT unclick.
     */
    private void unClickAllExcept(EffectTileContainer container) {
        for (Node n : this.getChildren()) {
            if (n instanceof EffectTileContainer) {
                EffectTileContainer e = (EffectTileContainer) n;
                if (e == container) continue;
                e.setClicked(false);
            }
        }
    }
}