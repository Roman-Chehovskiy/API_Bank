package API.bank.services.impl;

import API.bank.DTO.Dto;
import API.bank.config.TestConfig;
import API.bank.entity.History;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;


@SpringBootTest
@ContextConfiguration(
        classes = TestConfig.class)
class HistoryServiсesImplTest {

    @Autowired
    HistoryServiсesImpl historyServiсes;
    @Autowired
    SessionFactory sessionFactory;

    @Test
    void getOperation() {
        Date date = Date.valueOf("2023-01-06");
        List<Dto> dtoListExpected = new ArrayList<>();
        dtoListExpected.add(new History(12, date, "get balance", "done", 0));
        dtoListExpected.add(new History(12, date, "takeMoney", "not done", 50));
        List<Dto> dtoListReturn = historyServiсes.getOperation(12, "2023-01-06", "2023-01-06");
        for (int i = 0; i < dtoListExpected.size(); i++) {
            History historyReturn = (History) dtoListExpected.get(i);
            History historyExpected = (History) dtoListReturn.get(i);
            Assertions.assertEquals(historyExpected, historyReturn);
        }
    }

    @Test
    void putOperation() {
        History history = new History(234, new Date(new java.util.Date().getTime()),
                "addHistory", "done", 0);
        historyServiсes.putOperation(history);
        System.out.println("ok");
    }


}