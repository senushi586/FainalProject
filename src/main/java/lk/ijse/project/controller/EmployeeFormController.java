package lk.ijse.project.controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import lk.ijse.project.dto.EmployeeDto;
import lk.ijse.project.dto.tm.CustomerTm;
import lk.ijse.project.dto.tm.EmployeeTm;
import lk.ijse.project.model.EmployeeModel;
import lk.ijse.project.util.DataValidateController;
import lk.ijse.project.util.Navigation;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.function.Predicate;

public class EmployeeFormController {

    @FXML
    private JFXButton btnBooking;

    @FXML
    private JFXButton btnCustomer;

    @FXML
    private JFXButton btnDashboard;

    @FXML
    private JFXButton btnDelivery;

    @FXML
    private JFXButton btnItem;

    @FXML
    private JFXButton btnOrder;

    @FXML
    private JFXButton btnSupplier;

    @FXML
    private JFXButton btnreport;



    @FXML
    private TableColumn<?, ?> colEmail;

    @FXML
    private TableColumn<?, ?> colID;

    @FXML
    private TableColumn<?, ?> colMobile;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colRole;

    @FXML
    private TableView<EmployeeTm> tblEmployee;

    @FXML
    private TextField txtEmpEmail;

    @FXML
    private TextField txtEmpId;

    @FXML
    private TextField txtEmpMobile;

    @FXML
    private TextField txtEmpName;

    @FXML
    private TextField txtEmpRole;

    @FXML
    private Label empIdValidate;

    @FXML
    private Label empMobileValidate;

    @FXML
    private Label empNameValidate;

    @FXML
    private Label empRoleValidate;

    @FXML
    private Label empEmailValidate;

    @FXML
    private TextField txtIdSearch;


    @FXML
    void btnClearOnAction(ActionEvent event) {
        clearFields();
    }

    private void clearFields() {
        txtEmpEmail.clear();
        txtEmpId.clear();
        txtEmpMobile.clear();
        txtEmpName.clear();
        txtEmpRole.clear();
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String id  = txtEmpId.getText();

        EmployeeModel model = new EmployeeModel();
        try {
            boolean isDeleted = model.deleteEmployee(id);
            if (isDeleted){
                new Alert(Alert.AlertType.CONFIRMATION,"Employee Deleted Successfully ").show();
                loadAllEmployees();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }

    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        String id = txtEmpId.getText();
        String name = txtEmpName.getText();
        String role = txtEmpRole.getText();
        String mobile = txtEmpMobile.getText();
        String email = txtEmpEmail.getText();
        EmployeeDto dto = new EmployeeDto(id,name,role,mobile,email);

        EmployeeModel employeeModel = new EmployeeModel();
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////
        if(DataValidateController.empIdValidate(txtEmpId.getText())) {
            empIdValidate.setText("");

            if (DataValidateController.empNameValidate(txtEmpName.getText())) {
                empNameValidate.setText("");

                if (DataValidateController.empRoleValidate(txtEmpRole.getText())) {
                    empRoleValidate.setText("");

                    if (DataValidateController.empMobileValidate(txtEmpMobile.getText())) {
                        empMobileValidate.setText("");

                        if (DataValidateController.emailCheck(txtEmpEmail.getText())) {
                            empEmailValidate.setText("");

                            try {
                                boolean isSaved = employeeModel.saveEmployee(dto);
                                if (isSaved) {
                                    new Alert(Alert.AlertType.CONFIRMATION, "Employee Saved Successfully ").show();
                                    loadAllEmployees();
                                }
                            } catch (SQLException e) {
                                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
                            }
                        } else {
                            empEmailValidate.setText("Invalid Email");
                        }
                    } else {
                        empMobileValidate.setText("Invalid Mobile");
                    }
                } else {
                    empRoleValidate.setText("Invalid Role");
                }
            } else {
                empNameValidate.setText("Invalid Name");
            }
        }else{
            empIdValidate.setText("Invalid ID");
        }
        }
        @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String id = txtEmpId.getText();
        String name = txtEmpName.getText();
        String role = txtEmpRole.getText();
        String mobile = txtEmpMobile.getText();
        String email = txtEmpEmail.getText();
        EmployeeDto dto = new EmployeeDto(id,name,role,mobile,email);

        EmployeeModel model = new EmployeeModel();
        try {
            boolean isUpdated = model.updateEmployee(dto);
            if(isUpdated){
                new Alert(Alert.AlertType.CONFIRMATION,"Employee Updated Successfully").show();
                loadAllEmployees();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }
    public void initialize(){
        setCellValueFactory();
        loadAllEmployees();
    }

    private void setCellValueFactory() {
        colID.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colRole.setCellValueFactory(new PropertyValueFactory<>("role"));
        colMobile.setCellValueFactory(new PropertyValueFactory<>("mobile"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));

    }

    private void loadAllEmployees() {
        EmployeeModel model = new EmployeeModel();
        ObservableList<EmployeeTm> oblist = FXCollections.observableArrayList();
        try {
            List<EmployeeDto> list = model.getAllEmployees();
            for (EmployeeDto dto : list){
                EmployeeTm employeeTm = new EmployeeTm(
                        dto.getId(),
                        dto.getName(),
                        dto.getRole(),
                        dto.getMobile(),
                        dto.getEmail()
                    );
                oblist.add(employeeTm);
            }
            tblEmployee.setItems(oblist);

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    @FXML
    void btnBookingOnAction(ActionEvent event) {
        btnBooking.getScene().getWindow().hide();
        Navigation.changeStage("/view/bookingForm.fxml","Booking Form");
    }

    @FXML
    void btnCustomerOnAction(ActionEvent event) {
        btnCustomer.getScene().getWindow().hide();
        Navigation.changeStage("/view/customerForm.fxml","Customer Form");    }

    @FXML
    void btnDashboardOnAction(ActionEvent event) {
        btnDashboard.getScene().getWindow().hide();
        Navigation.changeStage("/view/dashboardForm.fxml","Dashboard Form");
    }

    @FXML
    void btnDeliveryOnAction(ActionEvent event) {
        btnDelivery.getScene().getWindow().hide();
        Navigation.changeStage("/view/deliveryForm.fxml","Delivery Form");
    }

    @FXML
    void btnItemOnAction(ActionEvent event) {
        btnItem.getScene().getWindow().hide();
        Navigation.changeStage("/view/itemForm.fxml","Item Form");
    }

    @FXML
    void btnOrderOnAction(ActionEvent event) {
        btnOrder.getScene().getWindow().hide();
        Navigation.changeStage("/view/placeOrderForm.fxml","Place Order Form");
    }

    @FXML
    void btnReportOnAction(ActionEvent event) {
        btnreport.getScene().getWindow().hide();
        Navigation.changeStage("/view/reportForm.fxml","Report Form");
    }

    @FXML
    void btnSupplierOnAction(ActionEvent event) {
        btnSupplier.getScene().getWindow().hide();
        Navigation.changeStage("/view/supplierForm.fxml","Supplier Form");
    }

    public void txtSearch(KeyEvent keyEvent) throws SQLException {
        EmployeeModel employeeModel = new EmployeeModel();
        String searchValue = txtIdSearch.getText().trim();
        ObservableList<EmployeeTm> obList = employeeModel.getAll();

        if (!searchValue.isEmpty()) {
            ObservableList<EmployeeTm> filteredData = obList.filtered(new Predicate<EmployeeTm>() {
                @Override
                public boolean test(EmployeeTm employeeTm) {
                    return String.valueOf(employeeTm.getId()).toLowerCase().contains(searchValue.toLowerCase());
                }
            });
            tblEmployee.setItems(filteredData);
        } else {
            tblEmployee.setItems(obList);
        }
    }

    public void tableClickedOnAction(MouseEvent mouseEvent) {
        TablePosition pos = tblEmployee.getSelectionModel().getSelectedCells().get(0);
        int row = pos.getRow();
        // Get the data from the selected row
        ObservableList<TableColumn<EmployeeTm, ?>> columns = tblEmployee.getColumns();

        txtEmpId.setText(columns.get(0).getCellData(row).toString());
        txtEmpName.setText(columns.get(1).getCellData(row).toString());
        txtEmpRole.setText(columns.get(2).getCellData(row).toString());
        txtEmpMobile.setText(columns.get(3).getCellData(row).toString());
        txtEmpEmail.setText(columns.get(4).getCellData(row).toString());
    }
}
