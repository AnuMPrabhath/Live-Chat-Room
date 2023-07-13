package dao.custom.impl;

import dao.SQLUtil;
import dao.custom.LoginDAO;
import dto.UserDTO;
import entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginDAOImpl implements LoginDAO {
    @Override
    public User getUser(String username) throws SQLException {
        ResultSet resultSet = SQLUtil.execute("SELECT * FROM user WHERE userName = ?", username);
        if (resultSet.next()){
            return new User(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getInt(3)
            );
        }
        return null;
    }

    @Override
    public void updateUser(User user) throws SQLException {
        SQLUtil.execute("UPDATE user SET password = ?, status = ? WHERE userName = ?", user.getPassword(), user.getStatus(), user.getUsername());
    }
}
