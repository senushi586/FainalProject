package lk.ijse.project.controller;

import com.jfoenix.controls.JFXButton;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import lk.ijse.project.model.*;
import lk.ijse.project.util.Navigation;


import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class DashboardFormController {

   // public AnchorPane mainpage;
    @FXML
    private BarChart<String, Integer> barChart;

    @FXML
    private AnchorPane rootNode;
    @FXML
    private JFXButton btnCustomer;

    @FXML
    private JFXButton btnBooking;

    @FXML
    private JFXButton btnDelivery;

    @FXML
    private JFXButton btnEmployee;

    @FXML
    private JFXButton btnItem;

    @FXML
    private JFXButton btnLogout;

    @FXML
    private JFXButton btnOrder;

    @FXML
    private JFXButton btnReport;

    @FXML
    private JFXButton btnSupplier;

    @FXML
    private Label lblOrders;

    @FXML
    private Label lblProfit;


    @FXML
    private PieChart pieChart;

    @FXML
    private Label lblCustomers;

    @FXML
    private Label lblEmpCount;

    @FXML
    private Label lblTime;

    @FXML
    private Label lblDate;
    DashboardModel dashboardModel = new DashboardModel();
    void setPieChart() {

        try {
            ObservableList<PieChart.Data> obList = FXCollections.observableArrayList();
            ArrayList<PieChart.Data> data =dashboardModel.getPieChartData();
            for (PieChart.Data datum : data) {
                obList.add(datum);
            }
            pieChart.getData().addAll(obList);
            pieChart.setTitle("Most Trending Products");
            pieChart.setStartAngle(180);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    void countOrders(){
        OrderModel orderModel = new OrderModel();

        try {
            int count=orderModel.countOrdersId();
            lblOrders.setText(String.valueOf("0"+count));

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    void countProfit(){
        OrderModel orderModel = new OrderModel();

        try {
            int count=orderModel.countProfit();
            lblProfit.setText(String.valueOf("Rs."+count));

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    void countCustomers(){
        CustomerModel customerModel = new CustomerModel();

        try {
            int count=customerModel.countCustomers();
            lblCustomers.setText(String.valueOf("0"+count));

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    void countEmployees(){
        EmployeeModel employeeModel = new EmployeeModel();

        try {
            int count=employeeModel.countEmployees();
            lblEmpCount.setText(String.valueOf("0"+count));

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


    @FXML
    void btnBookingOnAction(ActionEvent event) {
        btnBooking.getScene().getWindow().hide();
        Navigation.changeStage("/view/bookingForm.fxml","Booking Form");
    }

    @FXML
    void btnCustomerOnAction(ActionEvent event) throws IOException {
        /*Parent rootNode = FXMLLoader.load(this.getClass().getResource("/view/customerForm.fxml"));

        Scene scene = new Scene(rootNode);

        Stage stage = (Stage) this.rootNode.getScene().getWindow();

        stage.setTitle("Customer Form");
        stage.setScene(scene);
        stage.show();*/
        btnCustomer.getScene().getWindow().hide();
        Navigation.changeStage("/view/customerForm.fxml","Customer Form");
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
    void btnItemOnAction(ActionEvent event) {
        btnItem.getScene().getWindow().hide();
        Navigation.changeStage("/view/itemForm.fxml","Item Form");
    }

    @FXML
    void btnLogoutOnAction(ActionEvent event) {
        btnLogout.getScene().getWindow().hide();
        Navigation.changeStage("/view/loginPageForm.fxml","Login Form");
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
    void btnSupplierOnAction(ActionEvent event) {
        btnSupplier.getScene().getWindow().hide();
        Navigation.changeStage("/view/supplierForm.fxml","Supplier Form");
    }
    private void timenow(){
        Thread thread =new Thread(() ->{
            SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss a");
            SimpleDateFormat sdf1 = new SimpleDateFormat("MMMM,  dd, yyyy");
            while (true){
                try{
                    Thread.sleep(1000);

                }catch (Exception e){
                    e.printStackTrace();
                    System.out.println(e);
                }
                final String timenow = sdf.format(new Date());
                String timenow1 = sdf1.format(new Date());

                Platform.runLater(() ->{
                    lblTime.setText(timenow);
                    lblDate.setText(timenow1);
                });
            }
        });
        thread.start();
    }

    public void setDataToBarChart() throws SQLException {
        ObservableList<XYChart.Series<String, Integer>> barChartData = ItemModel.getDataToBarChart();

        barChart.setData(barChartData);
}

    public void initialize() throws SQLException {
        setPieChart();
        countOrders();
        countProfit();
        countCustomers();
        countEmployees();
        timenow();
        setDataToBarChart();
    }

}
