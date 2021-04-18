package main.extra_features;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import main.MainMenu;

/**
 * MenuBars is the class for the menu bar.
 *
 * @author Asinsa Guniyangodage
 * @version 1
 */
public class MenuBars extends BorderPane {

    private double volume;
    private int sliderValue;
    private Stage stage;
    private boolean isGame = false;
    private boolean isEnabled = false;
    private boolean tutorial;
   // private Scene scene;

    /**
     * This initialises the menu bar
     *
     * @param stage This is the instance of the stage
     */
    public MenuBars(Stage stage) {
        this.stage = stage;
        showMenuBar();
    }

    /***
     * This displays the menu bar
     *
     * @return the MenuBar
     */
    public MenuBar showMenuBar() {

        // Volume Tab
        Menu volumeMenu = new Menu("Volume");

        Slider volSlider = new Slider(0, 100, 0);
        volSlider.setMajorTickUnit(10.0);
        volSlider.adjustValue(sliderValue);
        volSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                sliderValue = newValue.intValue();
                volume = Math.round(newValue.intValue() * 10.0) / 1000.0;
                System.out.println(volume);
                MainMenu.getMediaPlayer().setVolume(volume);
            }
        });

        CustomMenuItem volumeSlider = new CustomMenuItem();
        volumeSlider.setContent(volSlider);
        volumeSlider.setHideOnClick(false);
        volumeMenu.getItems().add(volumeSlider);

        MenuItem mute = new MenuItem("Mute");
        ImageView imageView = new ImageView("src\\resources\\Images\\menu_images\\mute.png");
        imageView.setFitHeight(30);
        imageView.setFitWidth(30);
        mute.setGraphic(imageView);
        mute.setOnAction(e -> {
            MainMenu.getMediaPlayer().setVolume(0);
            volSlider.setValue(0);
        });
        volumeMenu.getItems().add(mute);

        // Quit Tab
        Menu quitMenu = new Menu("Quit");

        MenuItem quitToMenu = new MenuItem("Quit To Menu");
        quitToMenu.setOnAction((ActionEvent t) -> {
            MainMenu back = new MainMenu();
            try {
                back.start(new Stage());
            } catch (Exception e) {
                e.printStackTrace();
            }
            stage.close();
        });
        quitMenu.getItems().add(quitToMenu);

        MenuItem exitGame = new MenuItem("Exit Game");
        exitGame.setOnAction((ActionEvent t) -> {
            System.exit(0);
        });
        quitMenu.getItems().add(exitGame);

        // Tutorial Tab
        Menu tutorialMenu = new Menu("Tutorial");

        RadioMenuItem enabled = new RadioMenuItem("Enabled");
        enabled.setOnAction((ActionEvent t) -> {
            tutorial = true;
        });
        RadioMenuItem disabled = new RadioMenuItem("Disabled");
        disabled.setOnAction((ActionEvent t) -> {
            tutorial = false;
        });

        ToggleGroup toggleGroup = new ToggleGroup();
        toggleGroup.getToggles().add(enabled);
        toggleGroup.getToggles().add(disabled);
        enabled.setSelected(isEnabled);
        disabled.setSelected(!isEnabled);

        tutorialMenu.getItems().add(enabled);
        tutorialMenu.getItems().add(disabled);

        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().add(volumeMenu);
        menuBar.getMenus().add(quitMenu);
        if (isGame) {
            menuBar.getMenus().add(tutorialMenu);
        }

        return menuBar;
    }

    public double getVolume() {
        return volume;
    }

    public void setGame(boolean state) {
        isGame = state;
    }

    public void setEnabled(boolean state) {
        isEnabled = state;
    }

    public boolean getTutorial() {
        return tutorial;
    }
}
