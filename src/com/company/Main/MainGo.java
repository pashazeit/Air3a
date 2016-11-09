package com.company.Main;

import com.company.CreatAir.AviaPort;
import com.company.InterFace.Bank;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import java.io.*;
import java.util.ArrayList;

import static com.company.CreatAir.AviaPort.all;


public class MainGo extends Application implements Bank {
    public static ArrayList<AviaPort> newAAA;
    public static int Money = money;
    @FXML
    ComboBox planeid;


    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("AirPort");
        primaryStage.setScene(new Scene(root, 500, 600));
        primaryStage.show();
        primaryStage.setResizable(false);

        primaryStage.setOnCloseRequest(event -> {
         event.consume();
        });
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {

      /*  for (int i = 0; i < 10; i++) {
            {
                all.add(new AviaPort());
            }
        }

        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("Test.txt"));
        oos.writeObject(all);
        all.clear();*/

        ObjectInputStream ois = new ObjectInputStream(new FileInputStream("Test.txt"));
        newAAA = (ArrayList<AviaPort>) ois.readObject();

        launch(args);

        for (AviaPort p : newAAA)
            System.out.printf("Имя: %s \t номер: %d \t пассс: %d \n", p.name, p.number, p.passenger_seat);

    }

}



