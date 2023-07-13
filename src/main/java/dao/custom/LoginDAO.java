package dao.custom;

import dao.SuperDAO;
import dto.UserDTO;
import entity.User;

import java.sql.SQLException;

public interface LoginDAO extends SuperDAO {
    User getUser(String username) throws SQLException;
    void updateUser(User user) throws SQLException;
}
