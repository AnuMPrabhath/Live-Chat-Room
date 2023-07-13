package bo.custom.impl;

import bo.custom.LoginBO;
import dao.DAOFactory;
import dao.custom.LoginDAO;
import dto.UserDTO;
import entity.User;

import java.sql.SQLException;

public class LoginBOImpl implements LoginBO {
    LoginDAO loginDAO = (LoginDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.LOGIN);

    @Override
    public UserDTO getUser(String username) throws SQLException,NullPointerException {
        User user = loginDAO.getUser(username);
        return new UserDTO(user.getUsername(), user.getPassword(), user.getStatus());
    }

    @Override
    public void updateUser(UserDTO userDTO) throws SQLException {
        loginDAO.updateUser(new User(userDTO.getUsername(), userDTO.getPassword(), userDTO.getStatus()));
    }
}
