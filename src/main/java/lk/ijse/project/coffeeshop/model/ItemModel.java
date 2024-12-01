package lk.ijse.project.coffeeshop.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;
import lk.ijse.project.coffeeshop.db.DbConnection;
import lk.ijse.project.coffeeshop.dto.ItemDto;
import lk.ijse.project.coffeeshop.dto.tm.CartTm;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemModel {

    public static ItemDto searchById(String value) throws SQLException {
        String sql = "SELECT * FROM item WHERE id=?";
        ResultSet resultSet = null;

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1,value);
        try {
            resultSet = pstm.executeQuery();
            if (resultSet.next()) {
                return new ItemDto(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getDouble(5),
                        resultSet.getInt(6)
                );
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public static boolean updateQty(List<CartTm> tmList) throws SQLException {

        for (CartTm cartTm : tmList) {
            if(!updateItem(cartTm)) {
                return false;
            }
        }
        return true;
    }

    public static boolean updateItem(CartTm cartTm) throws SQLException {

        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "UPDATE item SET qty = qty-? WHERE id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setInt(1, cartTm.getQty());
        pstm.setString(2, cartTm.getCode());

        return pstm.executeUpdate() > 0;
    }

    public static ObservableList<XYChart.Series<String, Integer>> getDataToBarChart() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql="SELECT name,qty FROM item ";

        ObservableList<XYChart.Series<String, Integer>> datalist =FXCollections.observableArrayList();

        PreparedStatement pstm= connection.prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();

        // Creating a new series object
        XYChart.Series<String, Integer> series = new XYChart.Series<>();

        while(resultSet.next()){
            String itemName = resultSet.getString("name");
            int itemQty = resultSet.getInt("qty");
            series.getData().add(new XYChart.Data<>(itemName, itemQty));
        }

        datalist.add(series);
        return datalist;
    }


    public boolean saveItem(ItemDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "INSERT INTO item VALUES (?,?,?,?,?,?)";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1,dto.getId());
        pstm.setString(2,dto.getName());
        pstm.setString(3,dto.getCategory());
        pstm.setString(4,dto.getBrand());
        pstm.setString(5, String.valueOf(dto.getUnitPrice()));
        pstm.setString(6, String.valueOf(dto.getQty()));

       int i = pstm.executeUpdate();

        return i > 0;

    }

    public boolean deleteItem(String id) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "DELETE FROM item WHERE id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1,id);

        return pstm.executeUpdate()>0;
    }

    public boolean updateItem(ItemDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "UPDATE item SET name = ?,category = ?,brand = ?,unit_price = ?,qty = ? WHERE id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, dto.getName());
        pstm.setString(2, dto.getCategory());
        pstm.setString(3, dto.getBrand());
        pstm.setString(4, String.valueOf(dto.getUnitPrice()));
        pstm.setString(5, String.valueOf(dto.getQty()));
        pstm.setString(6, dto.getId());

        return pstm.executeUpdate()>0;

    }

    public List<ItemDto> getAllItems() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "SELECT * FROM item";
        PreparedStatement pstm= connection.prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();
            List<ItemDto> list = new ArrayList<>();
        while (resultSet.next()){
            ItemDto dto = new ItemDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getDouble(5),
                    resultSet.getInt(6)
            );
            list.add(dto);
        }
        return list;
    }

    public ObservableList<String> loaItemId() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM item";
        ObservableList<String> itemData = FXCollections.observableArrayList();
        try {
            PreparedStatement pstm = connection.prepareStatement(sql);
            ResultSet resultSet = pstm.executeQuery(sql);
            while (resultSet.next()) {
                itemData.add(
                        resultSet.getString(1)
                );
            }
            return itemData;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return null;
    }

}
