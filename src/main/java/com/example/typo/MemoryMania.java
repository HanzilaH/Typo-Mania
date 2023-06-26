package com.example.typo;

import javafx.animation.Animation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxListCell;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;
import javafx.animation.FillTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;

import java.util.*;


public class MemoryMania {
    public VBox keyboard;
    public AnchorPane root;
    private Scene scene;
    private Map<String, Rectangle> keyHashMap = new HashMap<>();
    Random rand = new Random();
    private static final Duration COLOR_CHANGE_DURATION = Duration.seconds(2);
    private Set<String> letters = new HashSet<>();
    public TextField textField;
    private boolean correctAnswer = true;



    /**
     * This function is used to set scene from the last page and initialize some necessary details such as creating a keyboard
     * @param sc is the scene created by the last page
     */
    public void setScene(Scene sc){
        this.scene = sc;
        String[][] keyboardLayout = {
                {"1", "2", "3", "4", "5", "6", "7", "8", "9", "0"},
                {"Q", "W", "E", "R", "T", "Y", "U", "I", "O", "P"},
                {"A", "S", "D", "F", "G", "H", "J", "K", "L"},
                {"Z", "X", "C", "V", "B", "N", "M"}
        };

        //for each of the rows an HBox is created
        //for each of the label a stackpane is created that has the rectangle shape for the key and a Text for the label
        //a stackpane gives the illusion that the label is placed in front of the Text
        for(String[]rowArray: keyboardLayout){
            HBox hBox = new HBox();
            hBox.setAlignment(Pos.CENTER);
            hBox.setSpacing(20);

            for(String str: rowArray){
                Rectangle rectangle = new Rectangle(40, 40, Color.LIGHTBLUE);


                Text letterText = new Text(str);
                letterText.setFont(Font.font(20));


                StackPane stackPane = new StackPane(rectangle, letterText);
                hBox.getChildren().addAll(stackPane);

                //This hashmap can be used to trace each rectangle on the keyboard
                keyHashMap.put(str, rectangle);
            }
            keyboard.getChildren().addAll(hBox);

        }

        textField.setDisable(true);
        textField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
//                checkInput(textField.getText());

            }
        });

        scene.setOnKeyPressed(event -> {
            if (event.getCode()== KeyCode.SPACE){
                System.out.println("Space pressed");
                startRound();
            }
        });





    }

    public void startRound(){
        for (int i = 0; i< 5; i ++){

            char randomLetter = (char) (rand.nextInt(26) + 'A');
            String str = String.valueOf(randomLetter);
            while (letters.contains(str)){
                randomLetter = (char) (rand.nextInt(26) + 'A');
                str = String.valueOf(randomLetter);
                System.out.println("here");
            }

            letters.add(str);
//            keyHashMap.get(str).setFill(Color.LIGHTPINK);


            FillTransition fillTransition = new FillTransition(COLOR_CHANGE_DURATION, keyHashMap.get(str));
            fillTransition.setFromValue(Color.LIGHTBLUE);
            fillTransition.setToValue(Color.LIGHTGREEN);

            // Create the timeline animation
            Timeline timeline = new Timeline(
                    new KeyFrame(Duration.ZERO, event -> fillTransition.play()),
                    new KeyFrame(COLOR_CHANGE_DURATION)
            );
            timeline.setCycleCount(Timeline.INDEFINITE);
            timeline.setAutoReverse(true);
            timeline.play();


        }

        textField.setDisable(false);

    }

}
