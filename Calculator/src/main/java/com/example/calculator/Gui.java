package com.example.calculator;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class Gui extends Application {

    //maximum width of the text/label
    private final double MAX_TEXT_WIDTH = 210;
    //default (nonscaled) font size of the text/label
    private final double defaultFontSize = 46.0;
    private final Font defaultFont = Font.font(defaultFontSize);


    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(Gui.class.getResource("Calculator.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 229, 295);

        //makes the label font size responsive for its input
        Label lbl = (Label)fxmlLoader.getNamespace().get("lbl");
        lbl.setFont(defaultFont);
        lbl.textProperty().addListener((observable, oldValue, newValue) -> {
            //create temp Text object with the same text as the label
            //and measure its width using default label font size
            Text tmpText = new Text(newValue);
            tmpText.setFont(defaultFont);

            double textWidth = tmpText.getLayoutBounds().getWidth();

            //check if text width is smaller than maximum width allowed
            if (textWidth <= MAX_TEXT_WIDTH) {
                lbl.setFont(defaultFont);
            } else {
                //and if it isn't, calculate new font size,
                // so that label text width matches MAX_TEXT_WIDTH
                double newFontSize = defaultFontSize * MAX_TEXT_WIDTH / textWidth;
                lbl.setFont(Font.font(defaultFont.getFamily(), newFontSize));
            }

        });

        stage.setTitle("");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();

    }

    public static void main(String[] args) {


        launch();
    }
}