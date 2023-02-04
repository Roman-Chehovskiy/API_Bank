package API.bank.controllers;

import API.bank.DTO.Dto;
import API.bank.services.impl.HistoryServiсesImpl;
import API.bank.services.impl.UserBalanceServicesImpl;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/bank")
public class BalanceController {

    @Autowired
    private UserBalanceServicesImpl userBalanceServices;
    @Autowired
    private HistoryServiсesImpl historyServiсes;



    //Точка входа для получения для получения
    @GetMapping("/balances/{id}")
    public Dto getBalance(@PathVariable int id) {
        Dto answer = userBalanceServices.getBalance(id);
        return answer;
    }

    //точка входа для уменьшения баланса
    @PutMapping("/balances/take/{id}&&{money}")
    public Dto takeMoney(@PathVariable int id, @PathVariable double money) {
        return (userBalanceServices.takeMoney(id, money));
    }

    //точка входа для увеличения баланса
    @PutMapping("/balances/put/{id}&&{money}")
    public Dto putMoney(@PathVariable int id, @PathVariable double money) {
        return (userBalanceServices.putMoney(id, money));
    }

    //точка входа для получения списка операций по заданному id
    @GetMapping("/history/{id}&&{startDateStr}&&{endDateStr}")
    public List<Dto> getOperation(@PathVariable int id, @PathVariable String startDateStr, @PathVariable String endDateStr) {
        return (historyServiсes.getOperation(id, startDateStr, endDateStr));
    }

    //точка входа для перевода
    @PutMapping("/balances/{senderId}&&{recipientId}&&{money}")
    public Dto transferMoney(@PathVariable int senderId, @PathVariable int recipientId, @PathVariable double money) {
        return (userBalanceServices.transferMoney(senderId, recipientId, money));
    }

}
