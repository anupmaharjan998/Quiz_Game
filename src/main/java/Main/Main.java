package Main;

import DAO.AuthServiceDAO;
import DAO.Implementation.AuthServiceDAOImp;
import DAO.Implementation.QuestionDAOImp;
import DAO.QuestionDAO;
import Model.LoginModel;
import Model.Question;
import Model.RegisterModel;
import UI.LoginPage;

import static Connection.DBConnection.initializeDatabase;

public class Main {
    public static void main(String[] args) {

        initializeDatabase();
        AuthServiceDAO authService = new AuthServiceDAOImp();
        new LoginPage(authService);
    }
}
