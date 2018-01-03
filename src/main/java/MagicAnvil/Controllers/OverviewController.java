package MagicAnvil.Controllers;

import MagicAnvil.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;


public class OverviewController {

    private Main mainApp;
    private PropertyReader reader;

    @FXML
    private TextArea result;

    @FXML
    private Button doThis;

    @FXML
    private Label t;

    @FXML
    private Label mm;

    @FXML
    private Label am;

    @FXML
    private Label d;

    @FXML
    private ComboBox<Equipment.EquipTypes> thingType;

    @FXML
    private ComboBox<Material> mainMaterial;

    @FXML
    private ComboBox<Material> additionalMaterial;

    @FXML
    private ComboBox<Decoration> decore;

    @FXML
    private void initialize(){
        reader = PropertyReader.getInstance();

        ObservableList<Equipment.EquipTypes> types = FXCollections.observableArrayList();
        types.addAll(Equipment.EquipTypes.values());
        thingType.setItems(types);

        ObservableList<Material> mainmat = FXCollections.observableArrayList();

        mainmat.addAll(reader.getMaterials().values());
        mainMaterial.setItems(mainmat);
        additionalMaterial.setItems(mainmat);


        ObservableList<Decoration> dec = FXCollections.observableArrayList();
        dec.addAll(reader.getDecorations().values());
        decore.setItems(dec);
    }

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }

    @FXML
    private void doThisThing(){
        if (thingType.getSelectionModel().getSelectedItem()!= null&&
            mainMaterial.getSelectionModel().getSelectedItem()!= null&&
            additionalMaterial.getSelectionModel().getSelectedItem()!=null&&
            decore.getSelectionModel().getSelectedItem()!= null){

            ItemCrafter crafter = ItemCrafter.getInstance();
            //Вызываем изготовитель предметов, передавая ему выбранные пользователем данные
            //он возвращает нам изготовленный предмет.
            AbstractItem item = crafter.equipCraft(thingType.getSelectionModel().getSelectedItem(),
            mainMaterial.getSelectionModel().getSelectedItem(),
                    additionalMaterial.getSelectionModel().getSelectedItem(),
                    decore.getSelectionModel().getSelectedItem());
            //Показываем описание созданного предмета
            result.setText(item.getDescription());
        }
        else result.setText("Сначала нужно выбрать материалы!");
    }
}
