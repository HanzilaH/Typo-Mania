package com.example.typo;

import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.input.KeyCode;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Path;
import java.util.*;


public class ClassicPlay implements Initializable {
    public Text scoreText;
    private int scoreInt;
    public AnchorPane root;
    private int INTERVAL = 2000;
    private double coordX = 1000 - 40;
    private double coordY = 800 - 40;

    /**
     * random is used to generate random coordinates on the screen and then random letters on the screen
     */
    private Random random = new Random();

    /**
     * circleMap is used to map the String objects to the Circle objects on the screen
     */
    private Map<String, Circle> circleMap = new HashMap<>();
    /**
     * textCircleMap is used to keep track of all the Text objects on the screen
     */
    private Map< String, Text> textCircleMap = new HashMap<>();

    public Scene scene;
    private static final Duration REMOVAL_DURATION = Duration.seconds(2);
    private int speedRelatedVariable = 60;
    public HBox lives;
    private int livesLeft = 5;
    AnimationTimer timer;
    public Label gameOver;
    public Button continueButton;


    /**
     * This function is used by the previous controller to pass the scene
     * It also sets the ability of the scene to handle key presses
     * If the circleMap contains the keypress then that entry from the HashMaps (circleMap and textCircleMap) is removed
     * Furthermore the corresponding Circle and Text objects are also removed from the scene
     * @param sc is the name of the scene
     */
    public void setScene(Scene sc){
        this.scene = sc;
        scene.setOnKeyPressed(event -> {
            String keyPressed = event.getText().toLowerCase();
            System.out.println(keyPressed);

            if (circleMap.containsKey(keyPressed)) {
                System.out.println("This circle is there");


                Circle circle = circleMap.get(keyPressed);
                circle.setFill(Color.LIGHTPINK);
                Text letterText = textCircleMap.get(keyPressed);

                //the Circle and Text entries from hashmap are removed as soon as the button is clicked
                //This is done is fix a bug (which continuously increased the score if the player kept pressing the button)
                //However the Circle and Text from the Scene are removed after a short delay
                circleMap.remove(keyPressed);
                textCircleMap.remove(keyPressed);

                //These two lines are responsible for getting the current score, increasing the score by ten and then
                //resetting the score text
                scoreInt += 10;
                scoreText.setText(String.valueOf(scoreInt));

                //If the scoreInt is a multiple of speedRelatedVariable then the frequency for the circle generation increases by 5 percent
                //I have also set a limit of 400ms to the interval meaning speed is only increase if the interval is bigger than 400ms
                if (INTERVAL >= 600 && scoreInt / speedRelatedVariable == Math.round(scoreInt / speedRelatedVariable)) {
                    INTERVAL = INTERVAL - ((int) Math.round(0.05 * INTERVAL));
                }


                // timeline with a single keyframe to remove the circle and text after a delay
                KeyFrame keyFrame = new KeyFrame(REMOVAL_DURATION, eventSecond -> {

                    root.getChildren().removeAll(circle, letterText);


                });
                Timeline timeline = new Timeline(keyFrame);
                timeline.play();
            }else if( event.getCode() == KeyCode.SPACE){
                //pauseGame();
            }else {
                //this if condition is used so that the variable liveLeft doesnt keep on decrementing
                if (livesLeft> 1){
                    livesLeft -= 1;
                    handleHeartImages();
                }else{
                    livesLeft -= 1;
                    handleHeartImages();
                    timer.stop();
                    scene.setOnKeyPressed(null);
                    //the text and button need to be set to visible and they need to be brought to front
                    gameOver.setVisible(true);
                    continueButton.setVisible(true);
                    gameOver.toFront();
                    continueButton.toFront();
                }

            }
        });


        handleHeartImages();
    }

    /**
     * This function is used to handle the number of white and red heart images at the top right of the scene in the HBox
     * This uses the variable livesLeft to create the number of white/red hearts
     */
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

    /**
     * This function loads the fxml to go back to the main menu
     * @param event is the ActionEvent from clicking the button
     * @throws IOException
     */
    public void continueToMenu(ActionEvent event)throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("mainPage.fxml"));
        Parent rootForNextPage = loader.load();
        Scene newScene = new Scene(rootForNextPage);
        Node sourceNode = (Node) event.getSource();
        Stage stage = (Stage) sourceNode.getScene().getWindow();
        stage.setScene(newScene);

    }

    /**
     * This is the main function which generates the Circle and the Text and places them on the AnchorPane
     * The coordinates of the Text is such a way that its placed at the centre of the circle
     * This function also adds both the Circle and the Text to circleMap and textCircleMap respectively
     */
    public void generateKeyCircle(){




        //generating a random letter and a string from it
        char randomLetter = (char) (random.nextInt(26) + 'a');
        String str = String.valueOf(randomLetter);








        //I used the opposite condition previously which firstly removed the Circle and the Text from the scene and then added a new one someplace else
        //But that caused a bug where the circle was not being removed sometimes
        //This condition completely ignores the function if the letter is already on the screen
        if (!circleMap.containsKey(str)){


            //generating a circle
            Circle circle = new Circle( random.nextDouble(coordX)+ 20, random.nextDouble(coordY)+20, 20);
            circle.setFill(Color.LIGHTBLUE);


            //generating a text object and placing it at the centre of the circle
            Text text = new Text(str);
            text.setFont(Font.font(16));
            text.setX(circle.getCenterX() - text.getLayoutBounds().getWidth() / 2);
            text.setY(circle.getCenterY() + text.getLayoutBounds().getHeight() / 4);


            //Adding the objects created
            circleMap.put(str, circle);
            textCircleMap.put(str, text);
            root.getChildren().addAll(circle, text);



        }




    }

    /**
     * THis function is used to set the timer to continuously call the function to generate key cirle
     * It uses an AnimationTimer object
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        timer = new AnimationTimer() {
            private long lastUpdate = 0;

            @Override
            public void handle(long now) {
                if (now - lastUpdate >= INTERVAL * 1_000_000) {
                    generateKeyCircle();
                    lastUpdate = now;
                }
            }
        };
        timer.start();

    }
}
