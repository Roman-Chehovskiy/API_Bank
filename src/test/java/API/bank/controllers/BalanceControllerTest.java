package API.bank.controllers;

import API.bank.DTO.Dto;
import API.bank.DTO.DtoReturnAnswer;
import API.bank.entity.History;
import API.bank.entity.UserBalance;
import API.bank.services.impl.HistoryServiсesImpl;
import API.bank.services.impl.UserBalanceServicesImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(BalanceController.class)
@ExtendWith(MockitoExtension.class)
class BalanceControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private BalanceController balanceController;

    @MockBean
    private UserBalanceServicesImpl userBalanceServices;

    @MockBean
    private HistoryServiсesImpl historyServiсes;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(balanceController).build();
    }

    @Test
    void getBalance() {
        int userId = 1212;
        UserBalance balance = new UserBalance();
        balance.setIdUser(userId);
        balance.setUserBalance(454);
        when(userBalanceServices.getBalance(userId)).thenReturn(balance);
        try {
            mockMvc.perform(MockMvcRequestBuilders
                    .get("/api/bank/balances/{id}", userId))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").value(balance.getId()))
                    .andExpect(jsonPath("$.idUser").value(balance.getIdUser()))
                    .andExpect(jsonPath("$.userBalance").value(balance.getUserBalance()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void takeMoney() {
        int userId = 1212;
        when(userBalanceServices.takeMoney(userId, 50))
                .thenReturn(new DtoReturnAnswer(1, "Операция выполненна"));
        try {
            mockMvc.perform(put("/api/bank/balances/take/{id}&&{money}", userId, 50))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.keyAnswer").value(1))
                    .andExpect(jsonPath("$.descriptionKey").value("Операция выполненна"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void putMoney() {
        int userId = 234;
        when(userBalanceServices.putMoney(userId, 200))
                .thenReturn(new DtoReturnAnswer(1, "Ok"));
        try {
            mockMvc.perform(put("/api/bank//balances/put/{id}&&{money}", userId, 200))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.keyAnswer").value(1))
                    .andExpect(jsonPath("$.descriptionKey").value("Ok"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void getOperation() {
        int userId = 12;
        String startDate = "2022-12-01";
        String endDate = "2022-12-02";
        List<Dto> historyList = new ArrayList<>();
        historyList.add(new History(12, Date.valueOf("2022-12-01"), "getBalance", "done", 0));
        historyList.add(new History(12, Date.valueOf("2022-12-02"), "putMoney", "done", 50));
        historyList.add(new History(12, Date.valueOf("2022-12-02"), "takeMoney", "not done", 100));
        when(historyServiсes.getOperation(12, "2022-12-01", "2022-12-02")).thenReturn(historyList);
        try {
            mockMvc.perform(get("/api/bank/history/{id}&&{startDateStr}&&{endDateStr}", userId, startDate, endDate))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.length()").value(historyList.size()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void transferMoney() {
        int senderId = 12;
        int recipientId = 234;
        double money = 30;
        when(userBalanceServices.transferMoney(senderId, recipientId, money)).thenReturn(new DtoReturnAnswer(1, "Перевод успешно выполнен"));
        try {
            mockMvc.perform(put("/api/bank/balances/{senderId}&&{recipientId}&&{money}", senderId, recipientId, money))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.keyAnswer").value(1))
                    .andExpect(jsonPath("$.descriptionKey").value("Перевод успешно выполнен"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}