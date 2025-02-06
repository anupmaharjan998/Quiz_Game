//package java11.main;
//
//import dao.CompetitorDAO;
//import dao.imp.CompetitorDaoImp;
//import ui.CompetitorUI;
//
//import javax.swing.*;
//
//public class Main {
//
//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(() -> {
//            CompetitorDAO competitorDao = new CompetitorDaoImp();
//            CompetitorUI ui = new CompetitorUI(competitorDao);
//            ui.setTitle("Competitor Management System");
//            ui.setLocationRelativeTo(null);
//            System.out.println("CompetitorUI is not visible!");
//            ui.setVisible(true);
//            System.out.println("CompetitorUI is now visible!");
//        });
//    }
//}
