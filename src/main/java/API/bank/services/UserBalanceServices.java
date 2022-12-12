package API.bank.services;

import API.bank.DTO.Dto;
import API.bank.entity.UserBalance;

public interface UserBalanceServices {

    void saveOrUpdate(UserBalance userBalance);

    Dto getBalance(int id);

    Dto putMoney(int id, double money);

    Dto takeMoney(int id, double money);
}
