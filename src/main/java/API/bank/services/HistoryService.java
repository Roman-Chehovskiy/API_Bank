package API.bank.services;

import API.bank.DTO.Dto;
import API.bank.entity.History;

import java.sql.Date;
import java.util.List;

public interface HistoryService {
    void putOperation(History history);
    List<Dto> getOperation(int id, String startDate, String endDate);
}
