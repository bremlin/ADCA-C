package sample;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.css.PseudoClass;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTreeTableCell;
import javafx.stage.FileChooser;
import javafx.util.Callback;
import sample.model.DataHelper;
import sample.model.ItemType;
import sample.model.TreeTableElement;
import service.xml.XMLHelper;

import java.io.File;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    public ComboBox groupOne;
    @FXML
    public ComboBox groupTwo;
    @FXML
    public ComboBox groupThree;

    @FXML
    public Label currentDate;

    @FXML
    TreeTableView<TreeTableElement> treeTableView;
    @FXML
    TreeTableColumn<TreeTableElement, String> idColumn;
    @FXML
    public TreeTableColumn<TreeTableElement, String> nameColumn;
    @FXML
    public TreeTableColumn<TreeTableElement, String> startDateColumn;
    @FXML
    public TreeTableColumn<TreeTableElement, String> finishDateColumn;
    @FXML
    public TreeTableColumn<TreeTableElement, String> pvTypeColumn;
    @FXML
    public TreeTableColumn<TreeTableElement, String> pvVolume;
    @FXML
    public TreeTableColumn<TreeTableElement, String> weight;
    @FXML
    public TreeTableColumn<TreeTableElement, String> percentComplete;

    private XMLHelper xmlHelper;
    private DataHelper dataHelper = new DataHelper();
    private DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

    public void loadProject() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Загрузить XML-файл проекта");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("XML", "*.xml"));
        File xmlFile = fileChooser.showOpenDialog(null);

        if (xmlFile != null) {
            xmlHelper = new XMLHelper(xmlFile);
            dataHelper.setData(xmlHelper);
            treeTableView.setRoot(dataHelper.getRoot());
            treeTableView.setEditable(true);
            columnSettings();

            setGroupData();
        }
    }

    private void fillGroupData(String groupOneName, String groupNameTwo, String groupNameThree) {
        System.out.println("fillGroupData: " + groupOneName);
        treeTableView.setRoot(dataHelper.getGroupDataRoot(groupOneName, groupNameTwo, groupNameThree));
    }

    private void setGroupData() {
        if (groupOne.getItems().size() > 0) groupOne.getItems().clear();
        groupOne.getItems().addAll(dataHelper.getGroupList());
    }

    private void columnSettings() {
        idColumn.setCellValueFactory(param -> param.getValue().getValue().getId());
        nameColumn.setCellValueFactory(param -> param.getValue().getValue().getName());
        startDateColumn.setCellValueFactory(param -> param.getValue().getValue().getStartDate());
        finishDateColumn.setCellValueFactory(param -> param.getValue().getValue().getFinishDate());
        pvTypeColumn.setCellValueFactory(param -> param.getValue().getValue().getPVType());
        pvVolume.setCellValueFactory(param -> param.getValue().getValue().getPvVolume());
        weight.setCellValueFactory(param -> param.getValue().getValue().getWeight());
        percentComplete.setCellValueFactory(param -> param.getValue().getValue().getPercent());

        percentComplete.setCellFactory(TextFieldTreeTableCell.forTreeTableColumn());
        percentComplete.setOnEditCommit(event -> {
            TreeItem<TreeTableElement> currentEditStep = treeTableView.getTreeItem(event.getTreeTablePosition().getRow());
            currentEditStep.getValue().setPercent(event.getNewValue());
        });
        final PseudoClass projectClass = PseudoClass.getPseudoClass("project");
        final PseudoClass codeClass = PseudoClass.getPseudoClass("code");
        final PseudoClass activityClass = PseudoClass.getPseudoClass("activity");
        final PseudoClass stepClass = PseudoClass.getPseudoClass("step");

        treeTableView.setRowFactory(tv -> new TreeTableRow<TreeTableElement>() {
            @Override
            public void updateItem(TreeTableElement item, boolean empty) {
                super.updateItem(item, empty) ;
                if (item == null) {
                    setStyle("");
                } else {
                    switch (item.getElementType()) {
                        case Project:
                            getStyleClass().clear();
                            getStyleClass().add("projectStyle");
                            break;
                        case Code:
                            getStyleClass().clear();
                            getStyleClass().add("codeOneStyle");
                            break;
                        case Activity:
                            getStyleClass().clear();
                            getStyleClass().add("activityStyle");
                            break;
                        case Step:
                            getStyleClass().clear();
                            getStyleClass().add("stepStyle");
                            break;
                    }
                }


//                else if (item.getElementType().equals(ItemType.Code)) {
//                    getStyleClass().clear();
//                    getStyleClass().add("codeOneStyle");
////                    setStyle("-fx-background-color: #F6DEA3");
//                } else {
//                    setStyle("");
//                }
            }
        });
//        treeTableView.setRowFactory(param -> {
//            TreeTableRow<TreeTableElement> row = new TreeTableRow<>();
//            row.treeItemProperty().addListener((observable, oldValue, newValue) -> {
//                if (oldValue!= null) {
//                    switch (oldValue.getValue().getElementType()) {
//                        case Project:
//                            row.pseudoClassStateChanged(projectClass, param.getRoot().getChildren().contains(oldValue));
//                            break;
//                        case Code:
//                            row.pseudoClassStateChanged(codeClass, param.getRoot().getChildren().contains(oldValue));
//                            break;
//                        case Activity:
//                            row.pseudoClassStateChanged(activityClass, param.getRoot().getChildren().contains(oldValue));
//                            break;
//                        case Step:
//                            row.pseudoClassStateChanged(stepClass, param.getRoot().getChildren().contains(oldValue));
//                            break;
//                    }
//                }
//
//            });
//            return row;
//        });

    }

    public void save(ActionEvent actionEvent) {

    }

    public void groupOneAction() {
        if (groupTwo.getItems().size() > 0) groupTwo.getItems().clear();
        groupTwo.getItems().addAll(dataHelper.getGroupListTwo(String.valueOf(groupOne.getSelectionModel().getSelectedItem())));
    }

    public void groupTwoAction() {
        if (groupThree.getItems().size() > 0) groupThree.getItems().clear();
        groupThree.getItems().addAll(dataHelper.getGroupListThree(
                String.valueOf(groupOne.getSelectionModel().getSelectedItem()),
                String.valueOf(groupTwo.getSelectionModel().getSelectedItem())));
    }

    public void filter(ActionEvent actionEvent) {

    }

    public void apply() {
        System.out.println("apply");
        if (checkGroupAndFilter()) {
            System.out.println("checkGroupAndFilter: " + checkGroupAndFilter());
            String firstFilter = groupOne.getSelectionModel().getSelectedItem().toString();
            String secondFilter = null;
            String thirdFilter = null;
            if (groupTwo.getSelectionModel().getSelectedItem() != null) {
                secondFilter = groupTwo.getSelectionModel().getSelectedItem().toString();
            }
            if (groupThree.getSelectionModel().getSelectedItem() != null) {
                thirdFilter = groupThree.getSelectionModel().getSelectedItem().toString();
            }
            fillGroupData(firstFilter, secondFilter, thirdFilter);
        }
    }

    public void cancel() {
        groupOne.getSelectionModel().clearSelection();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        currentDate.setText("Текущая дата: " + df.format(Calendar.getInstance().getTime()));
    }

    private boolean checkGroupAndFilter() {
        return groupOne.getSelectionModel().getSelectedItem() != null;
    }
}
