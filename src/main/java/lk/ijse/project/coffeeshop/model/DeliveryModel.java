package lk.ijse.project.coffeeshop.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lk.ijse.project.coffeeshop.db.DbConnection;
import lk.ijse.project.coffeeshop.dto.DeliveryDto;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DeliveryModel {

    public boolean saveDelivery(DeliveryDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "INSERT INTO delivery VALUES (?,?,?,?,?,?)";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1,dto.getId());
        pstm.setString(2,dto.getOrder_id());
        pstm.setString(3,dto.getEmp_id());
        pstm.setString(4,dto.getStatus());
        pstm.setString(5,dto.getLocation());
        pstm.setString(6, String.valueOf(dto.getDate()));

        int i = pstm.executeUpdate();

        return i > 0;

    }

    public boolean deleteDelivery(String id) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "DELETE FROM delivery WHERE id=?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1,id);
        return pstm.executeUpdate()>0;

    }

    public boolean updateDelivery(DeliveryDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "UPDATE delivery SET order_id = ?,emp_id = ?,status = ?,location = ?,date = ? WHERE id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, dto.getOrder_id());
        pstm.setString(2, dto.getEmp_id());
        pstm.setString(3,dto.getStatus());
        pstm.setString(4,dto.getLocation());
        pstm.setString(5, String.valueOf(dto.getDate()));
        pstm.setString(6, dto.getId());

        return pstm.executeUpdate()>0;
    }

    public List<DeliveryDto> getAllDeliveries() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "SELECT * FROM delivery";
        PreparedStatement pstm = connection.prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();
        List<DeliveryDto> list =  new ArrayList<>();
        while (resultSet.next()){
            DeliveryDto dto = new DeliveryDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getString(6));
            list.add(dto);
        }
        return list;
    }
    public  String generateNextDeliverId() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT id FROM delivery ORDER BY id DESC LIMIT 1";
        ResultSet resultSet = connection.prepareStatement(sql).executeQuery();

        String currentOrderId = null;

        if (resultSet.next()) {
            currentOrderId = resultSet.getString(1);
            return splitDeliverId(currentOrderId);
        }
        return splitDeliverId(null);
    }

    private String splitDeliverId(String currentOrderId) {    //O008
        if (currentOrderId != null) {
            String[] split = currentOrderId.split("O");
            int id = Integer.parseInt(split[1]);    //008
            id++;  //9
            return "D00" + id;
        }
        return "D001";
    }

    public ObservableList<String> loaItemId() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM delivery";
        ObservableList<String> deliveryData = FXCollections.observableArrayList();
        try {
            PreparedStatement pstm = connection.prepareStatement(sql);
            ResultSet resultSet = pstm.executeQuery(sql);
            while (resultSet.next()) {
                deliveryData.add(
                        resultSet.getString(1)
                );
            }
            return deliveryData;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return null;
    }

}


