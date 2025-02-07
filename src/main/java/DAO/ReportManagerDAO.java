package DAO;

import Model.PlayerReport;

import java.util.List;

public interface ReportManagerDAO {
    List<PlayerReport> getReportByLevel(String level_id);
    PlayerReport getTopPlayerSummary(String level_id);
    List<PlayerReport> searchPlayer(String keyword, String level_id);
    PlayerReport getSelectedPlayerSummary(String level_id, String comp_id);
}
