package API.bank.services.impl;

import API.bank.DTO.Dto;
import API.bank.DTO.DtoReturnAnswer;
import API.bank.config.SessionFactoryConfig;
import API.bank.entity.UserBalance;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserBalanceServicesImplTest {

    @Autowired
    UserBalanceServicesImpl userBalanceServices;
    @Autowired
    SessionFactory sessionFactory;


    @Test
    void getUserIfNotId() {
        int id = 1234;
        DtoReturnAnswer returnAnswer = new DtoReturnAnswer(-1, "Неудалось найти пользователя по Id");
        DtoReturnAnswer returnAnswerTest = (DtoReturnAnswer) userBalanceServices.getUser(id);
        Assertions.assertEquals(returnAnswerTest, returnAnswer);
    }

    @Test
    void putMoney() {
        int id = 12;
        UserBalance userBalance = (UserBalance) userBalanceServices.getUser(id);
        double balance = userBalance.getUserBalance() + 10;
        userBalanceServices.putMoney(id, 10);
        UserBalance userBalanceTest = (UserBalance) userBalanceServices.getUser(id);
        Assertions.assertEquals(userBalanceTest.getUserBalance(), balance);

    }

    @Test
    void putMoneyIfNotId() {
        DtoReturnAnswer returnAnswer = new DtoReturnAnswer(-1, "Неудалось найти пользователя по Id");
        DtoReturnAnswer returnAnswerTest = (DtoReturnAnswer) userBalanceServices.putMoney(1234, 10);
        Assertions.assertEquals(returnAnswerTest, returnAnswer);
    }

    @Test
    void takeMoney() {
        int id = 12;
        UserBalance userBalance = (UserBalance) userBalanceServices.getUser(id);
        double balance = userBalance.getUserBalance() - 20;
        userBalanceServices.takeMoney(id, 20);
        UserBalance userBalanceTest = (UserBalance) userBalanceServices.getUser(id);
        Assertions.assertEquals(userBalanceTest.getUserBalance(), balance);
    }

    @Test
    void takeMoneyIfNotMoney() {
        DtoReturnAnswer answer = new DtoReturnAnswer(0, "Недостаточно средств");
        DtoReturnAnswer answerTest = (DtoReturnAnswer) userBalanceServices.takeMoney(234, 100);
        Assertions.assertEquals(answerTest, answer);
    }

}