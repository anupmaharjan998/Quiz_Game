package DAO;

import Model.LoginModel;
import Model.RegisterModel;

public interface AuthServiceDAO {
//    LoginModel login(LoginModel model);
    boolean register(RegisterModel model);

    LoginModel login(String username, String password);
}
