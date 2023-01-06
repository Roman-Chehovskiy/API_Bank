package API.bank.controllers;

import API.bank.DTO.Dto;
import API.bank.services.impl.HistoryServiсesImpl;
import API.bank.services.impl.UserBalanceServicesImpl;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/bank")
public class BalanceController {

    private Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
    @Autowired
    private UserBalanceServicesImpl userBalanceServices;
    @Autowired
    private HistoryServiсesImpl historyServiсes;


    //Точка входа для получения для получения
    @RequestMapping(value = "/getBalance/id={id}")
    public String getBalance(@PathVariable int id) {
        Dto answer = userBalanceServices.getBalance(id);
        return gson.toJson(answer);
    }

    //точка входа для уменьшения баланса
    @RequestMapping(value = "/takeMoney/id={id}&&money={money}")
    public String takeMoney(@PathVariable int id, @PathVariable double money) {
        return gson.toJson(userBalanceServices.takeMoney(id, money));
    }

    //точка входа для увеличения баланса
    @RequestMapping(value = "/putMoney/id={id}&&money={money}")
    public String putMoney(@PathVariable int id, @PathVariable double money) {
        return gson.toJson(userBalanceServices.putMoney(id, money));
    }

    //точка входа для получения списка операций по заданному id
    @RequestMapping(value = "/getOperation/id={id}&&startDate={startDateStr}&&endDate={endDateStr}")
    public String getOperation(@PathVariable int id, @PathVariable String startDateStr, @PathVariable String endDateStr) {
        return gson.toJson(historyServiсes.getOperation(id, startDateStr, endDateStr));
    }

    //точка входа для перевода
    @RequestMapping(value = "/transferMoney/senderId={senderId}&&recipientId={recipientId}&&money={money}")
    public String transferMoney(@PathVariable int senderId, @PathVariable int recipientId, @PathVariable double money) {
        return gson.toJson(userBalanceServices.transferMoney(senderId, recipientId, money));
    }

}
