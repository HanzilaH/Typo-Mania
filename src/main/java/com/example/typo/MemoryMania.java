package com.example.typo;

import javafx.animation.Animation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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

import java.io.IOException;
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
    public Text guidanceText;
    public HBox lives;
    private int livesLeft = 5;
    public Text scoreValue;
    private int score = 0;



    /**
     * This function is used to set scene from the last page and initialize some necessary details such as creating a keyboard
     * @param sc is the scene created by the last page
     */
    public void setScene(Scene sc){
        this.scene = sc;
        handleHeartImages();
        guidanceText.setText("This is a memory test \nType the words you see on the keyboard\nPress SPACE to start");
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
                String inputletters = textField.getText();
                if(checkInput(inputletters)){
                    guidanceText.setText("Your answer \""+inputletters+"" +
                            "\" is True \nPress SPACE to continue");
                    score+=5;
                    scoreValue.setText(String.valueOf(score));
                }else{
                    guidanceText.setText("Your answer is False \nPress SPACE to continue");
                    livesLeft --;
                    handleHeartImages();
                }

                letters.clear();
                textField.setDisable(true);
                scene.setOnKeyPressed(ev -> {
                    if (ev.getCode()== KeyCode.SPACE){
                        guidanceText.setText("");
                        System.out.println("Space pressed");
                        startRound();
                    }
                });


            }
        });

        scene.setOnKeyPressed(event -> {
            if (event.getCode()== KeyCode.SPACE){
                System.out.println("Space pressed");
                startRound();
            }
        });





    }


    private void handleHeartImages(){
        lives.getChildren().clear();
        for (int i = 0; i< livesLeft; i++){
            try{
                //the red heart image exists in the resources folder
                Image image = new Image(getClass().getResource("heart.png").openStream());
                ImageView imageView = new ImageView(image);
                imageView.setFitHeight(25);
                imageView.setFitWidth(30);
                lives.getChildren().add(imageView);
            }catch (IOException e){}
        }
        for (int j = 0; j< 5 - livesLeft; j++){
            try{
                //the white heart images also exists in the resources folder
                Image image = new Image(getClass().getResource("whiteheart.png").openStream());
                ImageView imageView = new ImageView(image);
                imageView.setFitHeight(25);
                imageView.setFitWidth(30);
                lives.getChildren().add(imageView);
            }catch (IOException e){}
        }


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



            System.out.println(str);
//            keyHashMap.get(str).setFill(Color.LIGHTPINK);




            Rectangle rectangle = keyHashMap.get(str);


            Timeline timeline = new Timeline(
                    new KeyFrame(Duration.ZERO, event -> rectangle.setFill(Color.LIGHTGREEN)),
                    new KeyFrame(COLOR_CHANGE_DURATION)
            );
            timeline.setCycleCount(1);
            timeline.setOnFinished(event -> rectangle.setFill(Color.LIGHTBLUE));

            timeline.play();


        }

        textField.setDisable(false);

    }


    public boolean checkInput(String str){



        //Make a new map for the input letters and then compares both the maps
        ArrayList<String> inputLetters = new ArrayList<>();


        for (int i = 0; i < str.length(); i++) {
            inputLetters.add(   String.valueOf(str.charAt(i) ).toUpperCase()   )  ;
        }

        if (inputLetters.size()!= letters.size()) return false;


        for(String ltr: inputLetters){
            if (!letters.contains(ltr) ){
                return false;
            }
        }


        System.out.println(letters);




        return true;







    }
}
