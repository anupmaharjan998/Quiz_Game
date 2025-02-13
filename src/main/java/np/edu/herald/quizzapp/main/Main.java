package np.edu.herald.quizzapp.main;

import np.edu.herald.quizzapp.dao.*;
import np.edu.herald.quizzapp.dao.implementation.*;
import np.edu.herald.quizzapp.ui.LoginPage;

import static np.edu.herald.quizzapp.connection.DBConnection.initializeDatabase;

public class Main {
    static boolean isFirstRun = true;

    public static void main(String[] args) {
        if (isFirstRun) {
            initializeDatabase();
            isFirstRun = false;
        }

        AuthServiceDAO authService = new AuthServiceDAOImp();
        LevelDAO  levelDAO = new LevelDAOImp();
        QuestionDAO questionDAO = new QuestionDAOImp();
        ReportManagerDAO reportManagerDAO = new ReportManagerDAOImp();
        ScoreDAO scoreDAO = new ScoreDAOImp();
        new LoginPage(authService, levelDAO, questionDAO, reportManagerDAO, scoreDAO);
    }
}
