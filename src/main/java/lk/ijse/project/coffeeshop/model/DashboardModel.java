package lk.ijse.project.coffeeshop.model;

import javafx.scene.chart.PieChart;
import lk.ijse.project.coffeeshop.db.DbConnection;
import lk.ijse.project.coffeeshop.dto.ItemDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DashboardModel {

    ItemModel itemModel = new ItemModel();

    public ArrayList<PieChart.Data> getPieChartData() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "select item_id,SUM(qty)as orderCount from order_detail group by item_id order by ordercount desc limit 5";

        PreparedStatement pstm = connection.prepareStatement(sql);


        ArrayList<PieChart.Data> data = new ArrayList<>();
        ResultSet resultSet = pstm.executeQuery();
        while (resultSet.next()) {
            ItemDto item = itemModel.searchById(resultSet.getString(1));
            data.add(
                    new PieChart.Data(item.getName(), resultSet.getInt(2))
            );
        }
        return data;
    }
}

