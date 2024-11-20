package lk.ijse.project.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lk.ijse.project.db.DbConnection;
import lk.ijse.project.dto.EmployeeDto;
import lk.ijse.project.dto.tm.EmployeeTm;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeModel {

    public boolean saveEmployee(EmployeeDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "INSERT INTO employee VALUES (?,?,?,?,?)";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, dto.getId());
        pstm.setString(2, dto.getName());
        pstm.setString(3, dto.getRole());
        pstm.setString(4, dto.getMobile());
        pstm.setString(5, dto.getEmail());

        int i = pstm.executeUpdate();

        return i > 0;


    }

    public boolean deleteEmployee(String id) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "DELETE FROM employee WHERE id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1,id);
        return pstm.executeUpdate()>0;
    }

    public boolean updateEmployee(EmployeeDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "UPDATE employee SET name = ?,role = ?, mobile = ?, email = ? WHERE id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, dto.getName());
        pstm.setString(2, dto.getRole());
        pstm.setString(3, dto.getMobile());
        pstm.setString(4, dto.getEmail());
        pstm.setString(5, dto.getId());

        return pstm.executeUpdate()>0;


    }

    public List<EmployeeDto> getAllEmployees() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "SELECT * FROM employee";

        PreparedStatement  pstm = connection.prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();

        List<EmployeeDto> list = new ArrayList<>();
        while (resultSet.next()){
            EmployeeDto dto = new EmployeeDto(
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

    public ObservableList<String> loadEmployeeID() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM employee";
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

    public int countEmployees() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT COUNT(id) from employee";
        PreparedStatement pstm = connection.prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();
        if (resultSet.next()) {
            int idd = Integer.parseInt(resultSet.getString(1));
            return idd;
        }
        return Integer.parseInt(null);
    }
    public ObservableList<EmployeeTm> getAll() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "SELECT * FROM employee";

        PreparedStatement pstm = connection.prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();

        //List<CustomerDto> list = new ArrayList<>();
        ObservableList<EmployeeTm> employeeData = FXCollections.observableArrayList();
        while (resultSet.next()){
            employeeData.add(
                    new EmployeeTm(resultSet.getString(1),
                            resultSet.getString(2),
                            resultSet.getString(3),
                            resultSet.getString(4),
                            resultSet.getString(5))
            );
        }
        return employeeData;
    }
}

