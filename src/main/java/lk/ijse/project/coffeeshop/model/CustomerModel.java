package lk.ijse.project.coffeeshop.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lk.ijse.project.coffeeshop.db.DbConnection;
import lk.ijse.project.coffeeshop.dto.CustomerDto;
import lk.ijse.project.coffeeshop.dto.tm.CustomerTm;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerModel {

    public static CustomerDto searchById(String value) throws SQLException {
        String sql = "SELECT * FROM customer WHERE id=?";
        ResultSet resultSet = null;

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1,value);
        try {
            resultSet = pstm.executeQuery();
            if (resultSet.next()) {
                return new CustomerDto(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getString(5),
                        resultSet.getString(6)
                );
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public boolean saveCustomer(CustomerDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "INSERT INTO customer VALUES(?,?,?,?,?,?)";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1,dto.getId());
        pstm.setString(2,dto.getName());
        pstm.setString(3,dto.getAddress());
        pstm.setString(4,dto.getMobile());
        pstm.setString(5,dto.getEmail());
        pstm.setString(6,dto.getDob());

        int i = pstm.executeUpdate();

        return i > 0;

    }


    public boolean deleteCustomer(String id) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "DELETE FROM customer WHERE id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1,id);
        return pstm.executeUpdate()>0;
    }

    public boolean updateCustomer(CustomerDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "UPDATE customer SET name = ?,address = ?,mobile = ?,email = ?,dob = ? WHERE id = ?";

        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, dto.getName());
        pstm.setString(2, dto.getAddress());
        pstm.setString(3, dto.getMobile());
        pstm.setString(4, dto.getEmail());
        pstm.setString(5, dto.getDob());
        pstm.setString(6, dto.getId());

        return pstm.executeUpdate()>0;
    }

    public List<CustomerDto> getAllCustomers() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "SELECT * FROM customer";

        PreparedStatement pstm = connection.prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();

        List<CustomerDto> list = new ArrayList<>();
        while (resultSet.next()){
            CustomerDto dto = new CustomerDto(
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

    public ObservableList<String> loadCustId() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM customer";
        ObservableList<String> custData = FXCollections.observableArrayList();
        try {
            PreparedStatement pstm = connection.prepareStatement(sql);
            ResultSet resultSet = pstm.executeQuery(sql);
            while (resultSet.next()) {
                custData.add(
                        resultSet.getString(1)
                );
            }
            return custData;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return null;
    }

    public int countCustomers() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT COUNT(id) from customer";
        PreparedStatement pstm = connection.prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();
        if (resultSet.next()) {
            int idd = Integer.parseInt(String.valueOf(resultSet.getInt(1)));
            return idd;
        }
        return Integer.parseInt(null);

    }

    public ObservableList<CustomerTm> getAll() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "SELECT * FROM customer";

        PreparedStatement pstm = connection.prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();

        //List<CustomerDto> list = new ArrayList<>();
        ObservableList<CustomerTm> customerData = FXCollections.observableArrayList();
        while (resultSet.next()){
            customerData.add(
                   new CustomerTm(resultSet.getString(1),
                           resultSet.getString(2),
                           resultSet.getString(3),
                           resultSet.getString(4),
                           resultSet.getString(5),
                           resultSet.getString(6))
            );
        }
        return customerData;
    }
}

