package lk.ijse.project.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lk.ijse.project.db.DbConnection;
import lk.ijse.project.dto.SupplierDto;
import lk.ijse.project.dto.tm.SupplierTm;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SupplierModel {

    public boolean saveSupplier(SupplierDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "INSERT INTO supplier Values (?,?,?,?,?)";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, dto.getId());
        pstm.setString(2, dto.getCompany_name());
        pstm.setString(3, dto.getLocation());
        pstm.setString(4, dto.getMobile());
        pstm.setString(5, dto.getEmail());

        int i = pstm.executeUpdate();

        return i > 0;
    }

    public boolean deleteSupplier(String id) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "DELETE FROM supplier WHERE id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1,id);
        return pstm.executeUpdate() > 0;
    }


    public boolean updateSupplier(SupplierDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "UPDATE supplier SET company_name = ?,location = ?,mobile = ?,email = ? WHERE id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1,dto.getCompany_name());
        pstm.setString(2,dto.getLocation());
        pstm.setString(3,dto.getMobile());
        pstm.setString(4,dto.getEmail());
        pstm.setString(5,dto.getId());

        return pstm.executeUpdate() > 0;
    }

    public List<SupplierDto> getAllSuppliers() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "SELECT * FROM supplier";
        PreparedStatement pstm = connection.prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();
        List<SupplierDto> list = new ArrayList<>();
        while (resultSet.next()){
            SupplierDto dto = new SupplierDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5)
                    );
            list.add(dto);
        }
        return list;
    }

    public ObservableList<SupplierTm> getAll() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "SELECT * FROM supplier";

        PreparedStatement pstm = connection.prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();

        //List<CustomerDto> list = new ArrayList<>();
        ObservableList<SupplierTm> supplierData = FXCollections.observableArrayList();
        while (resultSet.next()){
            supplierData.add(
                    new SupplierTm(resultSet.getString(1),
                            resultSet.getString(2),
                            resultSet.getString(3),
                            resultSet.getString(4),
                            resultSet.getString(5))

            );
        }
        return supplierData;
    }
}
