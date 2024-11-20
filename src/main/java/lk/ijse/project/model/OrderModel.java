package lk.ijse.project.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lk.ijse.project.db.DbConnection;


import java.sql.*;
import java.time.LocalDate;

public class OrderModel {
    public static boolean saveOrder(String id, String cId, LocalDate date, Double payment) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "INSERT INTO orders VALUES(?, ?, ?,?)";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, id);
        pstm.setString(2, cId);
        pstm.setDate(3, Date.valueOf(date));
        pstm.setDouble(4, payment);


        return pstm.executeUpdate() > 0;
    }

    public String generateNextOrderId() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT id FROM orders ORDER BY id DESC LIMIT 1";
        ResultSet resultSet = connection.prepareStatement(sql).executeQuery();

        String currentOrderId = null;

        if (resultSet.next()) {
            currentOrderId = resultSet.getString(1);
            return splitOrderId(currentOrderId);
        }
        return splitOrderId(null);
    }

    private String splitOrderId(String currentOrderId) {    //O008
        if (currentOrderId != null) {
            String[] split = currentOrderId.split("O");
            int id = Integer.parseInt(split[1]);    //008
            id++;  //9
            return "O00" + id;
        }
        return "O001";
    }


    public ObservableList<String> loadOrderID() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM orders";
        ObservableList<String> empData = FXCollections.observableArrayList();
        try {
            PreparedStatement pstm = connection.prepareStatement(sql);
            ResultSet resultSet = pstm.executeQuery(sql);
            while (resultSet.next()) {
                empData.add(
                        resultSet.getString(1)
                );
            }
            return empData;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return null;
    }

    public int countOrdersId() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT COUNT(id) from orders";
        PreparedStatement pstm = connection.prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();
        if (resultSet.next()) {
            int idd = Integer.parseInt(resultSet.getString(1));
            return idd;
        }
        return Integer.parseInt(null);
    }

    public int countProfit() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT SUM(payment) AS total from orders";
        PreparedStatement pstm = connection.prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();
        if (resultSet.next()) {
            int idd = Integer.parseInt(String.valueOf(resultSet.getInt(1)));
            return idd;
        }
        return Integer.parseInt(null);

    }
}

