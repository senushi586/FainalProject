package lk.ijse.project.controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import lk.ijse.project.dto.ItemDto;
import lk.ijse.project.dto.tm.EmployeeTm;
import lk.ijse.project.dto.tm.ItemTm;
import lk.ijse.project.model.ItemModel;
import lk.ijse.project.util.Navigation;

import java.sql.SQLException;
import java.util.List;

public class ItemFormController {

    @FXML
    private TableColumn<?, ?> colBrand;

    @FXML
    private TableColumn<?, ?> colItemCategory;

    @FXML
    private TableColumn<?, ?> colItemId;

    @FXML
    private TableColumn<?, ?> colItemName;

    @FXML
    private TableColumn<?, ?> colQty;

    @FXML
    private TableColumn<?, ?> colUnitPrice;

    @FXML
    private TableView<ItemTm> tblItem;

    @FXML
    private TextField txtBrand;

    @FXML
    private TextField txtCategory;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtQty;

    @FXML
    private TextField txtUnitPrice;

    @FXML
    private JFXButton btnBooking;

    @FXML
    private JFXButton btnCustomer;

    @FXML
    private JFXButton btnDashbaord;

    @FXML
    private JFXButton btnDelivery;

    @FXML
    private JFXButton btnEmployee;



    @FXML
    private JFXButton btnLogout;

    @FXML
    private JFXButton btnOrder;

    @FXML
    private JFXButton btnReport;

    @FXML
    private JFXButton btnSupplier;



    @FXML
    void btnBackOnAction(ActionEvent event) {

    }

    @FXML
    void btnBookingOnAction(ActionEvent event) {
        btnBooking.getScene().getWindow().hide();
        Navigation.changeStage("/view/bookingForm.fxml","Booking Form");
    }

    @FXML
    void btnClearOnAction(ActionEvent event) {
        clearFields();
    }

    private void clearFields() {
        txtBrand.clear();
        txtCategory.clear();
        txtId.clear();
        txtName.clear();
        txtQty.clear();
        txtUnitPrice.clear();
    }

    @FXML
    void btnCustomerOnAction(ActionEvent event) {
        btnCustomer.getScene().getWindow().hide();
        Navigation.changeStage("/view/customerForm.fxml","Customer Form");
    }

    @FXML
    void btnDashboardOnAction(ActionEvent event) {
        btnDashbaord.getScene().getWindow().hide();
        Navigation.changeStage("/view/dashboardForm.fxml","Dashboard Form");
    }

    @FXML
    void btnDeliveryOnAction(ActionEvent event) {
        btnDelivery.getScene().getWindow().hide();
        Navigation.changeStage("/view/deliveryForm.fxml","Delivery Form");
    }

    @FXML
    void btnEmployeeOnAction(ActionEvent event) {
        btnEmployee.getScene().getWindow().hide();
        Navigation.changeStage("/view/employeeForm.fxml","Employee Form");
    }

    @FXML
    void btnLogoutOnAction(ActionEvent event) {
        btnLogout.getScene().getWindow().hide();
        Navigation.changeStage("/view/loginPageForm.fxml","Login Form");
    }

    @FXML
    void btnNewCustomerOnAction(ActionEvent event) {

    }

    @FXML
    void btnOrderOnAction(ActionEvent event) {
        btnOrder.getScene().getWindow().hide();
        Navigation.changeStage("/view/placeOrderForm.fxml","Place Order Form");
    }

    @FXML
    void btnReportOnAction(ActionEvent event) {
        btnReport.getScene().getWindow().hide();
        Navigation.changeStage("/view/reportForm.fxml","Report Form");
    }

    @FXML
    void btnItemOnAction(ActionEvent event) {

    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        String id = txtId.getText();
        String name = txtName.getText();
        String category = txtBrand.getText();
        String brand = txtBrand.getText();
        double unitPrice = Double.parseDouble(txtUnitPrice.getText());
        int qty = Integer.parseInt(txtQty.getText());
        ItemDto dto = new ItemDto(id,name,category,brand,unitPrice,qty);

        ItemModel itemModel = new ItemModel();

        try {
            boolean isSaved = itemModel.saveItem(dto);
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "Item Saved Successfully").show();
                loadAllItems();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    @FXML
    void btnSupplierOnAction(ActionEvent event) {
        btnSupplier.getScene().getWindow().hide();
        Navigation.changeStage("/view/supplierForm.fxml","Supplier Form");
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String id = txtId.getText();
        String name = txtName.getText();
        String category = txtCategory.getText();
        String brand = txtBrand.getText();
        double unitprice = Double.parseDouble(txtUnitPrice.getText());
        int qty = Integer.parseInt(txtUnitPrice.getText());
        ItemDto dto = new ItemDto(id,name,category,brand,unitprice,qty);

        ItemModel itemModel = new ItemModel();
        try {
            boolean isUpdated = itemModel.updateItem(dto);
            if(isUpdated){
                loadAllItems();
                new Alert(Alert.AlertType.CONFIRMATION,"Item Update Successfully").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String id = txtId.getText();
        ItemModel itemModel = new ItemModel();
        try {
            boolean isDeleted = itemModel.deleteItem(id);
            if(isDeleted){
                new Alert(Alert.AlertType.CONFIRMATION,"Item Deleted Successfully").show();
                loadAllItems();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public void initialize() {
        setCellValueFactory();
        loadAllItems();
    }

    private void setCellValueFactory() {
        colItemId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colItemName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colItemCategory.setCellValueFactory(new PropertyValueFactory<>("category"));
        colBrand.setCellValueFactory(new PropertyValueFactory<>("brand"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));



    }

    private void loadAllItems() {
        ItemModel itemModel = new ItemModel();
        ObservableList<ItemTm> obList = FXCollections.observableArrayList();
        try {
            List<ItemDto> list = itemModel.getAllItems();
            for (ItemDto dto:list){
                ItemTm itemTm = new ItemTm(
                        dto.getId(),
                        dto.getName(),
                        dto.getCategory(),
                        dto.getBrand(),
                        dto.getUnitPrice(),
                        dto.getQty()
                );
                obList.add(itemTm);
            }
            tblItem.setItems(obList);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public void onclick(MouseEvent mouseEvent) {


        TablePosition pos = tblItem.getSelectionModel().getSelectedCells().get(0);
        int row = pos.getRow();
        // Get the data from the selected row
        ObservableList<TableColumn<ItemTm, ?>> columns = tblItem.getColumns();

        txtId.setText(columns.get(0).getCellData(row).toString());
        txtName.setText(columns.get(1).getCellData(row).toString());
        txtCategory.setText(columns.get(2).getCellData(row).toString());
        txtBrand.setText(columns.get(3).getCellData(row).toString());
        txtUnitPrice.setText(columns.get(4).getCellData(row).toString());
        txtQty.setText(columns.get(5).getCellData(row).toString());
    }
}
