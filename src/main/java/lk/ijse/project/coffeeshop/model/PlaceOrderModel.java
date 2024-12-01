package lk.ijse.project.coffeeshop.model;

import lk.ijse.project.coffeeshop.db.DbConnection;
import lk.ijse.project.coffeeshop.dto.OrdersDto;

import java.sql.Connection;
import java.sql.SQLException;

public class PlaceOrderModel {


        public static boolean placeOrder(OrdersDto placeOrderDto) throws SQLException {
            System.out.println(placeOrderDto);
            boolean result = false;
            Connection connection = null;
            try {
                connection = DbConnection.getInstance().getConnection();
                connection.setAutoCommit(false);

                boolean isOrderSaved = OrderModel.saveOrder(placeOrderDto.getId(), placeOrderDto.getC_id(), placeOrderDto.getDate(),placeOrderDto.getPayment());
                if (isOrderSaved) {
                    boolean isUpdated = ItemModel.updateQty(placeOrderDto.getTmList());
                    if(isUpdated) {
                        boolean isOrderDetailSaved = OrderDetailModel.saveOrderDetail(placeOrderDto.getId(), placeOrderDto.getTmList());
                        System.out.println(isOrderDetailSaved);
                        if(isOrderDetailSaved) {
                            connection.commit();
                            result = true;
                        }
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
                connection.rollback();
            } finally {
                connection.setAutoCommit(true);
            }
            return result;}

}
