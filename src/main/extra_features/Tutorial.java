package main.extra_features;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import main.MainMenu;
import main.Mataha;
import main.Utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Tutorial {

    private Stage tutorialStage = new Stage();
    private Scene tutorial;
    private BorderPane main, turns, effectTiles;
    private MenuBars menuBar = MainMenu.getMenuBar();

    public Tutorial() {
        try {
            showTutorialStage();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method creates and shows the main game window where the game will be played.
     */
    public void showTutorialStage() throws FileNotFoundException {
        menuBar = MainMenu.getMenuBar();

        main = new BorderPane();
        main.setTop(menuBar.showMenuBar());

        Label title = new Label("Aim & Summary" + "\n");
        title.setId("title");

        Label summary1 = new Label("Each player is a car and is competing for the single parking space.");

        ImageView image1 = new ImageView(new Image(new FileInputStream("src/resources/Images/character_images/Blue.gif"), 50, 50, true, false));
        ImageView image2 = new ImageView(new Image(new FileInputStream("src/resources/Images/game_images/texture_packs/road/goal.gif"), 50, 50, true, false));
        ImageView image3 = new ImageView(new Image(new FileInputStream("src/resources/Images/character_images/Red.gif"), 50, 50, true, false));
        HBox hbox1 = new HBox(image1, image2, image3);
        hbox1.setAlignment(Pos.CENTER);

        Label summary2 = new Label("This is a turn based game, for up to four players (minimum 2 players), in which each player can " +
                "\nmove where they are and change the roads by placing navigation tiles along the edge of the map");

        ImageView image4 = new ImageView(new Image(new FileInputStream("src/resources/Images/game_images/aasd.gif"), 200, 200, true, false));

        Label summary3 = new Label("There are some obstacles to be aware of such as fixed tiles and effect tiles (which will be explained later)");

        Button next = new Button(Utils.translate("Next", MainMenu.getLang()));
        next.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    showTurns();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });

        Button back = new Button(Utils.translate("Back", MainMenu.getLang()));
        back.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                tutorialStage.close();
                Mataha mataha = new Mataha();
            }
        });
        main.setBottom(back);

        VBox vBoxMainLayout = new VBox();
        vBoxMainLayout.setAlignment(Pos.CENTER);

        vBoxMainLayout.setSpacing(40);
        vBoxMainLayout.getChildren().addAll(title, summary1, hbox1, summary2, image4, summary3, next);
        main.setCenter(vBoxMainLayout);

        tutorial = new Scene(main, 700, 500);
        tutorial.getStylesheets().add("src/resources/StyleSheets/MaTaHa.css");
        tutorialStage.setTitle("Tutorial");
        tutorialStage.setScene(tutorial);
        tutorialStage.setResizable(MainMenu.getResizable());
        tutorialStage.setMaximized(true);
        tutorialStage.getIcons().add(new Image("src/resources/Images/menu_images/icon2.png"));
        tutorialStage.show();
    }

    public void showTurns() throws FileNotFoundException {
        turns = new BorderPane();
        turns.setTop(menuBar.showMenuBar());

        Label title = new Label("Turns" + "\n");
        title.setId("title");

        Label summary1 = new Label("On your turn you:");
        summary1.setId("subtitle");

        Label summary2 = new Label("1. Draw a random tile from the tilebag");
        summary2.setId("subtitle");
        ImageView image1 = new ImageView(new Image(new FileInputStream("src/resources/Images/game_images/tilebag.gif"), 50, 50, true, false));

        Label summary3 = new Label("If the tile is:");

        Label effect = new Label("a. An effect tile - ");
        ImageView effectimage1 = new ImageView(new Image(new FileInputStream("src/resources/Images/game_images/Effects/backtrack.gif"), 60, 60, true, false));
        ImageView effectimage2 = new ImageView(new Image(new FileInputStream("src/resources/Images/game_images/Effects/doublemove.gif"), 60, 60, true, false));
        ImageView effectimage3 = new ImageView(new Image(new FileInputStream("src/resources/Images/game_images/Effects/fire.gif"), 60, 60, true, false));
        ImageView effectimage4 = new ImageView(new Image(new FileInputStream("src/resources/Images/game_images/Effects/ice.gif"), 60, 60, true, false));
        Label effectDesc = new Label("Keep hold of it. You will be able to use it on your next turn.");
        effectDesc.setPrefWidth(500);
        effectDesc.setWrapText(true);
        effectDesc.setAlignment(Pos.CENTER);

        Label navigation = new Label("b. A navigation tile - ");
        ImageView navigationimage1 = new ImageView(new Image(new FileInputStream("src/resources/Images/game_images/texture_packs/road/corner.gif"), 60, 60, true, false));
        ImageView navigationimage2 = new ImageView(new Image(new FileInputStream("src/resources/Images/game_images/texture_packs/road/line.gif"), 60, 60, true, false));
        ImageView navigationimage3 = new ImageView(new Image(new FileInputStream("src/resources/Images/game_images/texture_packs/road/tshape.gif"), 60, 60, true, false));
        Label navigationDesc = new Label("You must slide it into the board from an available edge space on any row or column without fixed tiles. You can rotate the tiles before doing so.");
        navigationDesc.setPrefWidth(500);
        navigationDesc.setWrapText(true);
        navigationDesc.setAlignment(Pos.CENTER);

        HBox effectImgs = new HBox(effectimage1, effectimage2, effectimage3, effectimage4);
        effectImgs.setSpacing(10);
        effectImgs.setAlignment(Pos.CENTER);
        VBox effectTile = new VBox(effect, effectImgs, effectDesc);
        effectTile.setAlignment(Pos.CENTER);
        HBox navigationImgs = new HBox(navigationimage1, navigationimage2, navigationimage3);
        navigationImgs.setSpacing(10);
        navigationImgs.setAlignment(Pos.CENTER);
        VBox navigationTile = new VBox(navigation, navigationImgs, navigationDesc);
        navigationTile.setAlignment(Pos.CENTER);

        HBox hbox = new HBox(effectTile, navigationTile);
        hbox.setAlignment(Pos.CENTER);
        hbox.setSpacing(100);

        Label summary4 = new Label("2. Playing an action tile");
        summary4.setId("subtitle");
        Label summary5 = new Label("Play an action tile if you have any that you didn't pick up from this turn and want to");

        Label summary6 = new Label("3. Movement");
        summary6.setId("subtitle");
        Label summary7 = new Label("You must move your player piece in any 1 of the 4 cardinal directions if you are able to do so.");

        Button next = new Button(Utils.translate("Next", MainMenu.getLang()));
        next.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    showEffectTiles();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });

        Button back = new Button(Utils.translate("Back", MainMenu.getLang()));
        back.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                tutorialStage.getScene().setRoot(main);
            }
        });
        turns.setBottom(back);

        VBox vBoxMainLayout = new VBox();
        vBoxMainLayout.setAlignment(Pos.CENTER);

        Separator separator1 = new Separator();
        separator1.setOrientation(Orientation.HORIZONTAL);
        Separator separator2 = new Separator();
        separator2.setOrientation(Orientation.HORIZONTAL);
        Separator separator3 = new Separator();
        separator3.setOrientation(Orientation.HORIZONTAL);

        vBoxMainLayout.setSpacing(40);
        vBoxMainLayout.getChildren().addAll(title, summary1, separator1, summary2, image1, summary3, hbox, separator2, summary4, summary5, separator3, summary6, summary7, next);
        turns.setCenter(vBoxMainLayout);

        tutorialStage.getScene().setRoot(turns);
    }

    public void showEffectTiles() throws FileNotFoundException {
        effectTiles = new BorderPane();
        effectTiles.setTop(menuBar.showMenuBar());

        Label title = new Label("Effect Tiles" + "\n");
        title.setId("title");

        Label summary1 = new Label("The 4 types of effect tiles you may encounter are:");
        summary1.setId("subtitle");

        Label fireTile = new Label("1. Fire Tile");
        fireTile.setId("subtitle");
        ImageView fireTileImg = new ImageView(new Image(new FileInputStream("src/resources/Images/game_images/Effects/fire.gif"), 150, 150, true, false));
        Label fireTileDesc = new Label("- Choose a square on the board.\n" +
                "- Freeze a 3 by 3 area around tile with the chosen tile in the centre.\n" +
                "- Tiles in this area now act as fixed tiles until the ice melts and the effect wears off.\n" +
                "- That is, preventing the rows and columns from moving.\n" +
                "- The player pieces can still travel onto and off tiles that are frozen.\n" +
                "- The ice on these tiles melts away at the start of your next turn. The tiles then return to normal.");
        fireTileDesc.setPrefWidth(300);
        fireTileDesc.setWrapText(true);
        VBox fireBox = new VBox(fireTile, fireTileImg, fireTileDesc);

        Label iceTile = new Label("2. Ice Tile");
        iceTile.setId("subtitle");
        ImageView iceTileImg = new ImageView(new Image(new FileInputStream("src/resources/Images/game_images/Effects/ice.gif"), 150, 150, true, false));
        Label iceTileDesc = new Label("- Choose a square on the board.\n" +
                "- A 3 by 3 area around tile with the chosen tile in the centre now gets engulfed with fire.\n" +
                "- You cannot choose a tile such that a player piece would end up being on a tile that is on fire.\n" +
                "- Players can now not move onto any tile that is on fire.\n" +
                "- The fire on these tiles diminishes and is extinguished, not at the start of your next turn, but at the start of your turn after that.\n" +
                "- The tiles then return to normal.");
        iceTileDesc.setPrefWidth(300);
        iceTileDesc.setWrapText(true);
        VBox iceBox = new VBox(iceTile, iceTileImg, iceTileDesc);

        Label doubleMove = new Label("3. Double Move");
        doubleMove.setId("subtitle");
        ImageView doubleMoveImg = new ImageView(new Image(new FileInputStream("src/resources/Images/game_images/Effects/doublemove.gif"), 150, 150, true, false));
        Label doubleMoveDesc = new Label("- You may move twice during Step 3.");
        doubleMoveDesc.setPrefWidth(300);
        doubleMoveDesc.setWrapText(true);
        VBox doubleMoveBox = new VBox(doubleMove, doubleMoveImg, doubleMoveDesc);

        Label backtrack = new Label("4. Backtrack");
        backtrack.setId("subtitle");
        ImageView backtrackImg = new ImageView(new Image(new FileInputStream("src/resources/Images/game_images/Effects/backtrack.gif"), 150, 150, true, false));
        Label backtrackDesc = new Label("- Choose an opponent.\n" +
                "- Their player piece moves back to where they were 2 turns ago provided the tiles are currently not on fire.\n" +
                "- More specifically, if the previous position they were occupying is not currently on fire then they move back to that position.\n" +
                "- Furthermore, if the previous position to that one is not currently on fire then they move further back to that position.\n" +
                "- Note that this only moves the player piece.\n" +
                "- The current tiles remain unchanged.\n" +
                "- This Action Tile can only be used against each player once per game.");
        backtrackDesc.setPrefWidth(300);
        backtrackDesc.setWrapText(true);
        VBox backtrackBox = new VBox(backtrack, backtrackImg, backtrackDesc);

        Separator separator1 = new Separator();
        separator1.setOrientation(Orientation.VERTICAL);
        Separator separator2 = new Separator();
        separator2.setOrientation(Orientation.VERTICAL);
        Separator separator3 = new Separator();
        separator3.setOrientation(Orientation.VERTICAL);

        HBox allEffects = new HBox(30);
        allEffects.setAlignment(Pos.CENTER);
        allEffects.getChildren().addAll(fireBox, separator1, iceBox, separator2, doubleMoveBox, separator3, backtrackBox);


        Button next = new Button(Utils.translate("Next", MainMenu.getLang()));
        next.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                tutorialStage.close();
                Mataha mataha = new Mataha();
            }
        });

        Button back = new Button(Utils.translate("Back", MainMenu.getLang()));
        back.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                tutorialStage.getScene().setRoot(turns);
            }
        });
        effectTiles.setBottom(back);

        VBox vBoxMainLayout = new VBox();
        vBoxMainLayout.setAlignment(Pos.CENTER);

        vBoxMainLayout.setSpacing(40);
        vBoxMainLayout.getChildren().addAll(title, summary1, allEffects, next);
        effectTiles.setCenter(vBoxMainLayout);

        tutorialStage.getScene().setRoot(effectTiles);
    }
}
