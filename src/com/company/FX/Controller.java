package com.company.FX;

import com.company.CreatAir.AviaPort;
import com.company.Main.MainGo;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import static com.company.Main.MainGo.newAAA;

public class Controller {
    private int num, funct, bort_index, babki;
    private String funct_name;


    @FXML
    Label cash;
    @FXML
    Label fcost;
    @FXML
    Button button;
    @FXML
    TextArea info;
    @FXML
    ComboBox planeid;
    @FXML
    ComboBox func;
    @FXML
    Label pewpew;
    @FXML
    Button flyaway;

    @FXML
    Button out;

    public void getInfo(int bort_index) {
        info.setText("Bort name: " + newAAA.get(bort_index).name + "\n" +
                "Bort weight: " + newAAA.get(bort_index).weight + "\n" +
                "Bort speed: " + newAAA.get(bort_index).speed + "\n" +
                "Bort fuel: " + newAAA.get(bort_index).fuel + "\n" +
                "Bort tank capacity: " + newAAA.get(bort_index).tank + "\n" +
                "Bort passengers : " + newAAA.get(bort_index).passenger + "\n" +
                "Bort passenger seats : " + newAAA.get(bort_index).passenger_seat + "\n" +
                "Bort range: " + newAAA.get(bort_index).range + "\n");

    }

    public void initialize() throws IOException {

        babki = MainGo.Money;

        cash.setText(babki + "");

        func.getItems().addAll("Заправить нах", "Слить нах", "Посадить пассажиров", "Снять пассажиров");

        //добавляем самолеты в ComboBox
        int i = 0;
        for (AviaPort a : newAAA) {
            i++;
            planeid.getItems().addAll(i + " " + a.getNumber());
        }

        planeid.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if ((int) newValue >= 0) {
                    num = newAAA.get((int) newValue).number;
                    bort_index = (int) newValue;
                    pewpew.setText("Выберите что будем делать с #" + num);
                    fcost.setText("Выручим с полета: " + newAAA.get(bort_index).flyCost);
                    getInfo(bort_index);
                }

            }
        });

        func.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {

                funct_name = newValue + "";

            }
        });

        func.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {

                funct = (int) newValue;
                pewpew.setText("Для функции " + funct_name + " \"жмякните Жмяк\"");

            }
        });

        button.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                String txt = "";
                if ((num > 0) && (funct >= 0)) {

                    switch (funct) {
                        case 0:
                            newAAA.get(bort_index).inFuel();
                            getInfo(bort_index);
                            cash.setText(MainGo.Money + "");
                            txt = "Bort fuel: " + newAAA.get(bort_index).fuel + "";
                            break;
                        case 1:
                            newAAA.get(bort_index).outFuel();
                            getInfo(bort_index);
                            cash.setText(MainGo.Money + "");
                            txt = "Bort fuel: " + newAAA.get(bort_index).fuel + "";
                            break;
                        case 2:
                            newAAA.get(bort_index).inPassenger();
                            getInfo(bort_index);
                            fcost.setText("Выручим с полета: " + newAAA.get(bort_index).flyCost);
                            txt = "Bort passengers: " + newAAA.get(bort_index).passenger + "";
                            break;
                        case 3:
                            newAAA.get(bort_index).outPassenger();
                            getInfo(bort_index);
                            fcost.setText("Выручим с полета: " + newAAA.get(bort_index).flyCost);
                            txt = "Bort passengers: " + newAAA.get(bort_index).passenger + "";
                            break;

                    }
                    pewpew.setText("well done, my son!\n" + txt + "\n");
                    button.setText("Thanks!");
                }
            }

        });

        flyaway.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                String txt = "";
                if ((num > 0) && (funct >= 0)) {

                    planeid.getItems().removeAll();

                    planeid.getItems().clear();
                    newAAA.remove(bort_index);

                    for (AviaPort a : newAAA) {
                        planeid.getItems().addAll(a.getNumber());
                    }


                    pewpew.setText("well done, my son!\n" + txt + "\n");
                    flyaway.setText("Thanks!");
                }
            }

        });
        out.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {

            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("Test.txt"))) {
                oos.writeObject(newAAA);

            } catch (IOException e) {
                e.printStackTrace();
            }
            Stage stage = (Stage) out.getScene().getWindow();
            stage.close();
        });
    }
}