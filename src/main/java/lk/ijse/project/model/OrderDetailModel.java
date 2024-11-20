package lk.ijse.project.model;


import lk.ijse.project.db.DbConnection;
import lk.ijse.project.dto.tm.CartTm;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class OrderDetailModel {

    public static boolean saveOrderDetail(String id, List<CartTm> tmList) throws SQLException {
        for (CartTm cartTm : tmList) {
            if(!saveOrderDetails(id, cartTm)) {
                return false;
            }
        }
        return true;
    }

    private static boolean saveOrderDetails(String orderId, CartTm cartTm) throws SQLException {
        System.out.println(cartTm);
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "INSERT INTO order_detail VALUES (?,?,?,?,?,?)";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, orderId);
        pstm.setString(2, cartTm.getCode());
        pstm.setString(3, cartTm.getDescription());
        pstm.setInt(4, cartTm.getQty());
        pstm.setDouble(5, cartTm.getUnitPrice());
        pstm.setDouble(6,cartTm.getTot());

         boolean isSaved = pstm.executeUpdate() > 0;
        System.out.println(isSaved);
        return isSaved;
    }
}
