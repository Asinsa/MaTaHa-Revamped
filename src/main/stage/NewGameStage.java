package main.stage;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.AudioClip;
import javafx.stage.Stage;
import main.FileReader;
import main.MainMenu;
import main.Mataha;
import main.Utils;
import main.extra_features.MenuBars;
import main.extra_features.Tutorial;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Optional;

/**
 * NewGameStage allows the user to create a new game by choosing the level and players.
 *
 * @author G38.dev
 * @version 4
 */
public class NewGameStage {

    private Stage newGameStage = new Stage();
    private Scene newGame, characterSelection;
    public static int numOfPlayers;
    private static int ONE = 1;
    public static String fileUrl;
    public static ArrayList<String> players = new ArrayList<String>();
    public static ArrayList<String> chosenCharacters = new ArrayList<String>();
    private MenuBars menuBar;
    String colour = "";
    String url = "";
    ToggleButton chosenButton = null;
    private static final AudioClip CLICK_SFX = new AudioClip(new File("src/resources/SFX/click.mp3").toURI().toString());

    /**
     * Method creates and shows the new game window.
     */
    public void showNewGameStage() {
        menuBar = MainMenu.getMenuBar();

        BorderPane border = new BorderPane();
        border.setTop(menuBar.showMenuBar());

        Label label2 = new Label(Utils.translate("Level Picker", MainMenu.getLang()) + "\n");
        label2.setId("title");
        final ComboBox levelFileComboBox = new ComboBox();
        levelFileComboBox.setPromptText("Level Name");
        File directoryPath = new File("src/resources/LevelFiles");
        String contents[] = directoryPath.list();
        for (int i = 0; i < contents.length; i++) {
            String chosenSave = (contents[i].substring(0, contents[i].indexOf('.')));
            levelFileComboBox.getItems().add(chosenSave);
        }

        Label label3 = new Label(Utils.translate("Player Picker", MainMenu.getLang()));
        label3.setId("title");
        ChoiceBox p1 = new ChoiceBox();
        p1.setItems(FileReader.readProfileNames("playerProfiles"));
        ChoiceBox p2 = new ChoiceBox();
        p2.setItems(FileReader.readProfileNames("playerProfiles"));
        ChoiceBox p3 = new ChoiceBox();
        p3.setItems(FileReader.readProfileNames("playerProfiles"));
        ChoiceBox p4 = new ChoiceBox();
        p4.setItems(FileReader.readProfileNames("playerProfiles")); //Names of all the profiles

        Label warnings = new Label("");

        Button next = new Button(Utils.translate("Next", MainMenu.getLang()));
        next.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                CLICK_SFX.play(menuBar.getSFXVolume());
                fileUrl = "src/resources/LevelFiles/" + levelFileComboBox.getValue() + ".txt";
                if (levelFileComboBox.getValue() != null) {
                    if (p1.getValue() == null || p2.getValue() == null) {
                        warnings.setText(Utils.translate("Please select at least 2 players!", MainMenu.getLang()));
                    }
                    else if (p1.getValue().equals(p2.getValue())) {
                        warnings.setText(Utils.translate("Please select 2 different profiles", MainMenu.getLang()));
                    }
                    else {
                        //main.FileReader.readFile("leve1 file " + levelfile);
                        players.add((String) p1.getValue());

                        players.add((String) p2.getValue());
                        numOfPlayers = 2;
                        if (p3.getValue() != null) {
                            players.add((String) p3.getValue());
                            numOfPlayers++;
                        }
                        if (p4.getValue() != null) {
                            players.add((String) p4.getValue());
                            numOfPlayers++;
                        }
                        int counter = 0;

                        try {

                            characterSelection();

                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }

                    }
                }
                else {
                    warnings.setText(Utils.translate("Please select a level", MainMenu.getLang()));
                }
            }
        });

        VBox vBoxMainLayout = new VBox();
        vBoxMainLayout.setAlignment(Pos.CENTER);

        //HBox level selector
        HBox hBoxLevelSelector = new HBox();
        hBoxLevelSelector.getChildren().addAll(levelFileComboBox);
        hBoxLevelSelector.setAlignment(Pos.CENTER);
        hBoxLevelSelector.setSpacing(40);

        //HBox profile selector
        HBox hBoxProfileSelector = new HBox();
        hBoxProfileSelector.getChildren().addAll(p1, p2, p3, p4);
        hBoxProfileSelector.setAlignment(Pos.CENTER);
        hBoxProfileSelector.setSpacing(40);

        vBoxMainLayout.setSpacing(40);
        vBoxMainLayout.getChildren().addAll(label2, hBoxLevelSelector, label3, hBoxProfileSelector, warnings, next);
        border.setCenter(vBoxMainLayout);

        newGame = new Scene(border, 700, 500);
        newGame.getStylesheets().add("src/resources/StyleSheets/MaTaHa.css");
        newGameStage.setTitle(Utils.translate("New Game", MainMenu.getLang()));
        newGameStage.setScene(newGame);
        newGameStage.setResizable(MainMenu.getResizable());
        newGameStage.getIcons().add(new Image("src/resources/Images/menu_images/icon2.png"));
        newGameStage.show();
    }

    int count = ONE;

    /**
     * Method that lets each player choose the character they play as and removes each character as they are chosen so there's only 1 of each character in the game
     */
    public void characterSelection() throws FileNotFoundException {
        FlowPane layout = new FlowPane();

        //HBox for colours
        FlowPane hBoxCharacters = new FlowPane();
        //hBoxColours.setSpacing(20);
        hBoxCharacters.setAlignment(Pos.CENTER);
        hBoxCharacters.getChildren().addAll();
        hBoxCharacters.setHgap(40);
        hBoxCharacters.setVgap(40);
        layout.setAlignment(Pos.CENTER);

        characterSelection = new Scene(layout, 700, 500);
        characterSelection.getStylesheets().add("src/resources/StyleSheets/MaTaHa.css");
        newGameStage.setScene(characterSelection);

        Label title = new Label(Utils.translate("Pick a character", MainMenu.getLang()) + " " + players.get(0));
        title.setId("title");

        ToggleGroup characterToggle = new ToggleGroup();

        File directoryPath = new File("src//resources/Images//character_images");
        String contents[] = directoryPath.list();
        for (int i = 0; i < contents.length; i++) {
            String chosenImg = (contents[i].substring(0, contents[i].indexOf('.')));
            String chosenCharacterName = chosenImg.substring(0, 1).toUpperCase() + chosenImg.substring(1).toLowerCase();
            ToggleButton character = new ToggleButton(chosenCharacterName);
            character.setWrapText(true);
            character.setGraphic(new ImageView(new Image(new FileInputStream("src//resources/Images//character_images//" + contents[i]), 50, 50, true, false)));
            character.setToggleGroup(characterToggle);
            character.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    colour = chosenCharacterName;
                    url = "src//resources/Images//character_images//" + chosenCharacterName + ".gif";
                    chosenButton = character;
                }
            });
            hBoxCharacters.getChildren().add(character);
        }

        Label warnings = new Label("");

        Button next = new Button(Utils.translate("Next", MainMenu.getLang()));
        next.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                CLICK_SFX.play(menuBar.getSFXVolume());
                if (characterToggle.getSelectedToggle() == null) {
                    warnings.setText(Utils.translate("Please select a character", MainMenu.getLang()));
                }
                else {
                    chosenCharacters.add(url);
                    hBoxCharacters.getChildren().remove(chosenButton);
                    if (count < players.size()) {
                        characterToggle.getSelectedToggle().setSelected(false);
                        title.setText(Utils.translate("Pick a character", MainMenu.getLang()) + " " + players.get(count));
                        count++;
                    }
                    else {
                        newGameStage.close();
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setContentText("Do you want a tutorial before beginning to play?");
                        ButtonType yesButton = new ButtonType("Yes");
                        ButtonType noButton = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

                        alert.getButtonTypes().setAll(yesButton, noButton);

                        Optional<ButtonType> result = alert.showAndWait();
                        if (result.get() == yesButton){
                            CLICK_SFX.play(menuBar.getSFXVolume());
                            Tutorial tutorial = new Tutorial(true);
                        } else {
                            CLICK_SFX.play(menuBar.getSFXVolume());
                            Mataha mataha = new Mataha();
                            MainMenu.getMenuBar().setEnabled(false);
                        }
                    }
                }
            }
        });

        layout.setOrientation(Orientation.VERTICAL);
        layout.setColumnHalignment(HPos.CENTER);
        layout.setHgap(40);
        layout.setVgap(40);
        layout.getChildren().addAll(title, hBoxCharacters, warnings, next);
    }
}