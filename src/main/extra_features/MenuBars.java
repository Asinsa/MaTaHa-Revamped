package main.extra_features;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import main.MainMenu;
import main.Mataha;

/**
 * MenuBars is the class for the menu bar.
 *
 * @author Asinsa Guniyangodage
 * @version 1
 */
public class MenuBars extends BorderPane {

    private static double musicVolume;
    private static double sfxVolume;
    private static int musicVolSliderValue, sfxVolSliderValue;
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

        // Music volume submenu
        Menu musicMenu = new Menu("Music Volume");
        Slider musicVolSlider = new Slider(0, 100, 0);
        musicVolSlider.setMajorTickUnit(10.0);
        musicVolSlider.adjustValue(musicVolSliderValue);
        musicVolSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                musicVolSliderValue = newValue.intValue();
                musicVolume = Math.round(newValue.intValue() * 10.0) / 1000.0;
                System.out.println(musicVolume);
                MainMenu.getMediaPlayer().setVolume(musicVolume);
            }
        });

        CustomMenuItem musicVolumeSlider = new CustomMenuItem();
        musicVolumeSlider.setContent(musicVolSlider);
        musicVolumeSlider.setHideOnClick(false);
        musicMenu.getItems().add(musicVolumeSlider);

        MenuItem musicMute = new MenuItem("Mute");
        ImageView imageView = new ImageView("src\\resources\\Images\\menu_images\\mute.png");
        imageView.setFitHeight(30);
        imageView.setFitWidth(30);
        musicMute.setGraphic(imageView);
        musicMute.setOnAction(e -> {
            MainMenu.getMediaPlayer().setVolume(0);
            musicVolSlider.setValue(0);
        });
        musicMenu.getItems().add(musicMute);

        // SFX volume submenu
        Menu sfxMenu = new Menu("SFX Volume");
        Slider sfxVolSlider = new Slider(0, 100, 0);
        sfxVolSlider.setMajorTickUnit(10.0);
        sfxVolSlider.adjustValue(sfxVolSliderValue);
        sfxVolSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                sfxVolSliderValue = newValue.intValue();
                sfxVolume = Math.round(newValue.intValue() * 10.0) / 1000.0;
                System.out.println(sfxVolume);
            }
        });

        CustomMenuItem sfxVolumeSlider = new CustomMenuItem();
        sfxVolumeSlider.setContent(sfxVolSlider);
        sfxVolumeSlider.setHideOnClick(false);
        sfxMenu.getItems().add(sfxVolumeSlider);

        MenuItem sfxmute = new MenuItem("Mute");
        sfxmute.setGraphic(imageView);
        sfxmute.setOnAction(e -> {
            sfxVolume = 0;
            sfxVolSlider.setValue(0);
        });
        sfxMenu.getItems().add(sfxmute);

        volumeMenu.getItems().add(musicMenu);
        volumeMenu.getItems().add(sfxMenu);


        // Quit Tab
        Menu quitMenu = new Menu("Quit");

        MenuItem quitToMenu = new MenuItem("Quit To Menu");
        quitToMenu.setOnAction((ActionEvent t) -> {
            MainMenu back = new MainMenu();
            try {
                Mataha.getStage().close();
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

    public double getMusicVolume() {
        return musicVolume;
    }

    public double getSFXVolume() {
        return sfxVolume;
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
