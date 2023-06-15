package com.example.typo;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainPage implements Initializable {
    @FXML
    public Label newGame;
    public Label continueGame;
    public Label leaderBoard;
    public Label exit;
    public HBox newGameH;
    public HBox continueGameH;
    public HBox leaderboardH;
    public HBox exitH;
    public AnchorPane root;

    /**
     * This function makes the labels highlight with a distinct color
     * @param event is the mouse entered in the label
     */
    public void mouseEntered(Event event){
        Label source = (Label) event.getSource();
        source.setStyle("-fx-background-color: #FFC26F; -fx-font-family: 'Arial'; -fx-font-size: 24px; -fx-text-fill: #F9E0BB; -fx-background-radius: 10px;");

    }

    /**
     * This function makes the highlight of the labels go away
     * @param event is the mouse exited property
     */
    public void mouseExited(Event event){
        Label source = (Label) event.getSource();
        source.setStyle("-fx-background-color: none; -fx-font-family: 'Arial'; -fx-font-size: 24px; -fx-text-fill: #F9E0BB;");
    }

    public void goToClassicPlay(Event event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("classicPlay.fxml"));
        Parent rootForNextPage = loader.load();


        // Create a new scene using the root node from the FXML file
        Scene newScene = new Scene(rootForNextPage);
        ClassicPlay classicPlay = loader.getController();
        classicPlay.setScene(newScene);

        Node sourceNode = (Node) event.getSource();
        Stage stage = (Stage) sourceNode.getScene().getWindow();
        stage.setScene(newScene);
    }





    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
