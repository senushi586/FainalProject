package lk.ijse.project.model;


import lk.ijse.project.db.DbConnection;
import lk.ijse.project.dto.UserDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserModel {
    public static boolean verifyCredentials(String username,String password){
        try {
            DbConnection instance = DbConnection.getInstance();
            Connection connection = instance.getConnection();

            String sql = "SELECT password FROM user WHERE username = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1,username);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()){
                if (password.equals(resultSet.getString(1))){
                    return true;
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    public UserDto getEmail(String username) throws SQLException {

            String sql = "SELECT * FROM user WHERE username=?";
            ResultSet resultSet = null;

            Connection connection = DbConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement(sql);

            pstm.setString(1,username);
            try {
                resultSet = pstm.executeQuery();
                if (resultSet.next()) {
                    return new UserDto(
                            resultSet.getString(1),
                            resultSet.getString(2),
                            resultSet.getString(3),
                            resultSet.getString(4)

                    );
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            return null;
        }


    public boolean updatePassword(String username, String text) throws SQLException {

        String sql = "UPDATE user SET password=? WHERE username=?";
        try (PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql)) {
            pstm.setString(1,text);
            pstm.setString(2,username);
            int rows = pstm.executeUpdate();
            if (rows > 0) {
                return true;
            }
        }
        return false;
    }
}
