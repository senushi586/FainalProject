package lk.ijse.project.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;

import lk.ijse.project.db.DbConnection;
import lk.ijse.project.dto.CustomerDto;
import lk.ijse.project.dto.ItemDto;
import lk.ijse.project.dto.OrdersDto;
import lk.ijse.project.dto.tm.CartTm;
import lk.ijse.project.model.CustomerModel;
import lk.ijse.project.model.ItemModel;
import lk.ijse.project.model.OrderModel;
import lk.ijse.project.model.PlaceOrderModel;
import lk.ijse.project.util.Navigation;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;

import java.io.InputStream;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

public class PlaceOrderFormController  implements Initializable{
    private final ObservableList<CartTm> obList = FXCollections.observableArrayList();

    @FXML
    private TableColumn<?, ?> colAction;

    @FXML
    private JFXButton btnPlaceOrder;

    @FXML
    private Label txtmoremoney;

    @FXML
    private TextField txtCash;


    @FXML
    private Label lblmoreneeded;

    @FXML
    private Label balancelbl;

    @FXML
    private TableView<CartTm> tblOrderCart;

    @FXML
    private TextField txtDescription;

    @FXML
    private JFXButton btnAddToCart;

    @FXML
    private JFXComboBox<String> cmbCustomerId;

    @FXML
    private JFXComboBox<String> cmbItemCode;

    @FXML
    private TableColumn<?, ?> colCode;

    @FXML
    private TableColumn<?, ?> colDescription;

    @FXML
    private TableColumn<?, ?> colQty;

    @FXML
    private TableColumn<?, ?> colTotal;

    @FXML
    private TableColumn<?, ?> colUnitPrice;





    @FXML
    private TextField txtCustomerName;

    @FXML
    private TextField txtOrderDate;

    @FXML
    private TextField txtOrderId;

    @FXML
    private TextField txtQty;

    @FXML
    private TextField txtQtyOnHand;

    @FXML
    private TextField txtTotal;

    @FXML
    private TextField txtUnitPrice;

    @FXML
    private JFXButton btnBooking;

    @FXML
    private JFXButton btnCustomer;

    @FXML
    private JFXButton btnDashboard;

    @FXML
    private JFXButton btnDelivery;

    @FXML
    private JFXButton btnEmployee;

    @FXML
    private JFXButton btnItem;

    @FXML
    private JFXButton btnLogOut;

    @FXML
    private JFXButton btnOrder;

    @FXML
    private JFXButton btnSupplier;

    @FXML
    private JFXButton btnreport;




    OrderModel orderModel = new OrderModel();
    ItemDto item;
    CustomerDto customer;
    public void customerIdClick(){

        try {
            customer = CustomerModel.searchById(cmbCustomerId.getValue());
            txtCustomerName.setText(customer.getName());
        } catch (Exception throwables) {

        }
    }
    public void loadCustomerId() throws SQLException {
        CustomerModel customerModel = new CustomerModel();

        ObservableList<String> custData = customerModel.loadCustId();
        cmbCustomerId.setItems(custData);
    }

    public void loadItemId() throws SQLException {
        ItemModel itemModel = new ItemModel();

        ObservableList<String> custData = itemModel.loaItemId();
        cmbItemCode.setItems(custData);
    }


    @FXML
    void btnAddToCartOnAction(ActionEvent event) throws SQLException {
        String code = cmbItemCode.getValue();
        String description = txtDescription.getText();
        int qty = Integer.parseInt(txtQty.getText());
        double unitPrice = Double.parseDouble(txtUnitPrice.getText());
        double total = qty * unitPrice;
        JFXButton btnremove = new JFXButton("Remove");
        btnremove.setCursor(Cursor.HAND);
        // Setting style using CSS
        btnremove.setStyle("-fx-background-color: #f68fad; -fx-text-fill: white;");

        if(qty>Integer.parseInt(txtQtyOnHand.getText())){
            new Alert(Alert.AlertType.CONFIRMATION,"out of stock or not enough stock").show();

        }else {
            setRemoveBtnOnAction(btnremove);
            for (int i = 0; i < tblOrderCart.getItems().size(); i++) {
                if (code.equals(colCode.getCellData(i))) {
                    qty += (int) colQty.getCellData(i);
                    total = qty * unitPrice;

                    obList.get(i).setQty(qty);
                    obList.get(i).setTot(total);

                    tblOrderCart.refresh();
                    calculateNetTotal();
                    return;
                }
            }


            obList.add(new CartTm(
                    code,
                    description,
                    qty,
                    unitPrice,
                    total,
                    btnremove
            ));
            tblOrderCart.setItems(obList);
            calculateNetTotal();
            txtQty.clear();
        }

    }
    private void setRemoveBtnOnAction(Button btn) {
        btn.setOnAction((e) -> {
            ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

            Optional<ButtonType> result = new Alert(Alert.AlertType.INFORMATION, "Are you sure to remove?", yes, no).showAndWait();

            if (result.orElse(no) == yes) {
                int index = tblOrderCart.getSelectionModel().getSelectedIndex();

                TablePosition pos = tblOrderCart.getSelectionModel().getSelectedCells().get(0);
                int row = pos.getRow();
                ObservableList<TableColumn<CartTm, ?>> columns = tblOrderCart.getColumns();


                obList.remove(index);
                tblOrderCart.refresh();
                calculateNetTotal();
            }

 });
}



    @FXML
    void btnBookingOnAction(ActionEvent event) {
        btnBooking.getScene().getWindow().hide();
        Navigation.changeStage("/view/bookingForm.fxml","Booking Form");
    }

    @FXML
    void btnCustomerOnAction(ActionEvent event) {
        btnCustomer.getScene().getWindow().hide();
        Navigation.changeStage("/view/customerForm.fxml","Customer Form");
    }

    @FXML
    void btnDashBoardOnAction(ActionEvent event) {
        btnDashboard.getScene().getWindow().hide();
        Navigation.changeStage("/view/dashboardForm.fxml","Dashboard Form");
    }

    @FXML
    void btnEmployeeAction(ActionEvent event) {
        btnEmployee.getScene().getWindow().hide();
        Navigation.changeStage("/view/employeeForm.fxml","Employee Form");
    }

    @FXML
    void btnItemOnAction(ActionEvent event) {
        btnItem.getScene().getWindow().hide();
        Navigation.changeStage("/view/itemForm.fxml","Item Form");
    }

    @FXML
    void btnLogOutOnAction(ActionEvent event) {
        btnLogOut.getScene().getWindow().hide();
        Navigation.changeStage("/view/loginPageForm.fxml","Login Form");
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

    @FXML
    void btndeliveryOnAction(ActionEvent event) {
        btnDelivery.getScene().getWindow().hide();
        Navigation.changeStage("/view/deliveryForm.fxml","Delivery Form");
    }



    @FXML
    void btnPlaceOrderOnAction(ActionEvent event) {

        String orderId = txtOrderId.getText();
        String cusId = cmbCustomerId.getValue();
        Double total = Double.valueOf(txtTotal.getText());


        List<CartTm> tmList = new ArrayList<>();

        for (CartTm cartTm : obList) {
            tmList.add(cartTm);
        }

        var placeOrderDto = new OrdersDto(
                orderId,
                cusId,
                LocalDate.now(),
                total,
                tmList
        );

        boolean isSuccess = false;
        try {
            isSuccess = PlaceOrderModel.placeOrder(placeOrderDto);
            txtOrderId.setText(orderModel.generateNextOrderId());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if(isSuccess) {
            String printcash = txtCash.getText();
            String balance = balancelbl.getText();
            //new Alert(Alert.AlertType.CONFIRMATION, "order completed!").show();
            ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

            Optional<ButtonType> result = new Alert(Alert.AlertType.INFORMATION, "Order completed!..Do you want to generate the Bill?", yes, no).showAndWait();



            if (result.orElse(no) == yes) {
                Map<String, Object> parameters = new HashMap<>();
                parameters.put("param1", printcash);
                parameters.put("param2", balance);

                InputStream resource = this.getClass().getResourceAsStream("/reports/PlaceOrder.jrxml");
                try {
                    JasperReport jasperReport = JasperCompileManager.compileReport(resource);
                    JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, DbConnection.getInstance().getConnection());
                    JasperViewer.viewReport(jasperPrint, false);
                } catch (Exception e) {
                    e.printStackTrace();
                }
     }
        }

    }

    @FXML
    void cmbCustomerOnAction(ActionEvent event) {
        customerIdClick();
    }

    @FXML
    void cmbItemOnAction(ActionEvent event) {
        try {
            item = ItemModel.searchById(cmbItemCode.getValue());
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
        txtDescription.setText(item.getName());
        txtQtyOnHand.setText(String.valueOf(item.getQty()));
        txtUnitPrice.setText(String.valueOf(item.getUnitPrice()));

        if (item.getQty() > 0) {
            txtQtyOnHand.setText(String.valueOf(item.getQty()));
        } else {
            txtQtyOnHand.setText("Out Of Stock");
            new Alert(Alert.AlertType.CONFIRMATION,"Out Of Stock").show();
        }
    }


/*    @FXML
    void keyCash(KeyEvent event) {
        if (!txtCash.getText().isEmpty()) {
            double balance = Double.parseDouble(txtCash.getText()) - Double.parseDouble(txtTotal.getText());
            if (balance >= 0) {
                txtCash.setStyle("-fx-text-fill: black");
                balancelbl.setText(String.valueOf(balance));
                lblmoreneeded.setVisible(false);
                txtmoremoney.setText("");
                btnPlaceOrder.setDisable(false);
                balancelbl.setVisible(true);
            } else if (balance < 0) {
                txtCash.setStyle("-fx-text-fill: black");
                btnPlaceOrder.setDisable(true);
                double positbalance = Math.abs(balance);
                lblmoreneeded.setVisible(true);
                txtmoremoney.setText(positbalance + "/=");
                balancelbl.setVisible(false);
            }
        }else {
            btnPlaceOrder.setDisable(true);
            txtCash.setStyle("-fx-text-fill: red");
            balancelbl.setVisible(false);
            lblmoreneeded.setVisible(false);
            txtmoremoney.setText("");
}
    }*/

    private void setCellValueFactory() {
        colCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("tot"));
        colAction.setCellValueFactory(new PropertyValueFactory<>("removebtn"));


    }

    private void calculateNetTotal() {
        double total = 0;
        for (int i = 0; i < tblOrderCart.getItems().size(); i++) {
            total += (double) colTotal.getCellData(i);
        }

        txtTotal.setText(String.valueOf(total));
    }
//    private void generateNextOrderId() {
//        try {
//            String orderId = orderModel.generateNextOrderId();
//            System.out.println(orderId);
//            txtOrderId.setText(orderId);
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            loadCustomerId();
            loadItemId();
            setCellValueFactory();
            txtOrderId.setText(orderModel.generateNextOrderId());

            txtOrderDate.setText(String.valueOf(LocalDate.now()));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

      lblmoreneeded.setVisible(false);
        btnPlaceOrder.setDisable(true);
    }

    public void keyCash(KeyEvent keyEvent) {
        if (!txtCash.getText().isEmpty()) {
            double balance = Double.parseDouble(txtCash.getText()) - Double.parseDouble(txtTotal.getText());
            if (balance >= 0) {
                txtCash.setStyle("-fx-text-fill: black");
                balancelbl.setText(String.valueOf(balance));
                lblmoreneeded.setVisible(false);
                txtmoremoney.setText("");
                btnPlaceOrder.setDisable(false);
                balancelbl.setVisible(true);
            } else if (balance < 0) {
                txtCash.setStyle("-fx-text-fill: black");
                btnPlaceOrder.setDisable(true);
                double positbalance = Math.abs(balance);
                lblmoreneeded.setVisible(true);
                txtmoremoney.setText(positbalance + "/=");
                balancelbl.setVisible(false);
            }
        } else {
            btnPlaceOrder.setDisable(true);
            txtCash.setStyle("-fx-text-fill: red");
            balancelbl.setVisible(false);
            lblmoreneeded.setVisible(false);
            txtmoremoney.setText("");
        }
    }
}
