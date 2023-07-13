package bo.custom;

import bo.SuperBO;
import dto.UserDTO;

import java.sql.SQLException;

public interface LoginBO extends SuperBO {
    UserDTO getUser(String username) throws SQLException;
    void updateUser(UserDTO userDTO) throws SQLException;
}
