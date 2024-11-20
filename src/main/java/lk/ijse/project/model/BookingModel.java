package lk.ijse.project.model;


import lk.ijse.project.db.DbConnection;
import lk.ijse.project.dto.BookingDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookingModel {

    public boolean saveBooking(BookingDto dto) throws SQLException {

        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "INSERT INTO booking VALUES(?,?,?,?,?)";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1,dto.getId());
        pstm.setString(2,dto.getC_id());
        pstm.setString(3,dto.getNic());
        pstm.setString(4, String.valueOf(dto.getDate()));
        pstm.setString(5, dto.getTable_name());

        int i = pstm.executeUpdate();

        return i > 0;
    }

    public boolean deleteBooking(String id) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "DELETE FROM booking WHERE id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1,id);
        return pstm.executeUpdate()>0;
    }

    public boolean updateBooking(BookingDto dto) throws SQLException {
        Connection connection =DbConnection.getInstance().getConnection();

        String sql = "UPDATE booking SET c_id = ?,nic = ?,date = ?,table_name = ? WHERE id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, dto.getC_id());
        pstm.setString(2, dto.getNic());
        pstm.setString(3, String.valueOf(dto.getDate()));
        pstm.setString(4,dto.getTable_name());
        pstm.setString(5,dto.getId());

        return pstm.executeUpdate()>0;
    }

    public List<BookingDto> getAllBookings() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "SELECT * FROM booking";
        PreparedStatement pstm = connection.prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();
        List<BookingDto> list = new ArrayList<>();
        while (resultSet.next()){
            BookingDto dto = new BookingDto(
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
}
