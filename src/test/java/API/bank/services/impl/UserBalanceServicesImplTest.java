package API.bank.services.impl;

import API.bank.DTO.DtoReturnAnswer;
import API.bank.config.TestConfig;
import API.bank.entity.UserBalance;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;


@SpringBootTest
@ContextConfiguration(
        classes = TestConfig.class)
class UserBalanceServicesImplTest {

    @Autowired
    UserBalanceServicesImpl userBalanceServices;
    @Autowired
    SessionFactory sessionFactory;


    @Test
    void getUserIfNotId() {
        int id = 1234;
        DtoReturnAnswer returnAnswer = new DtoReturnAnswer(-1, "Неудалось найти пользователя по Id");
        DtoReturnAnswer returnAnswerTest = (DtoReturnAnswer) userBalanceServices.getBalance(id);
        Assertions.assertEquals(returnAnswerTest, returnAnswer);
    }


    @Test
    void putMoney() {
        int id = 234;
        userBalanceServices.putMoney(id, 10);
        UserBalance userBalanceTest = (UserBalance) userBalanceServices.getBalance(id);
        Assertions.assertEquals(165, userBalanceTest.getUserBalance());

    }

    @Test
    void putMoneyIfNotId() {
        DtoReturnAnswer returnAnswer = new DtoReturnAnswer(-1, "Неудалось найти пользователя по Id");
        DtoReturnAnswer returnAnswerTest = (DtoReturnAnswer) userBalanceServices.putMoney(1234, 10);
        Assertions.assertEquals(returnAnswerTest, returnAnswer);
    }

    @Test
    void takeMoney() {
        int id = 454;
        userBalanceServices.takeMoney(id, 20);
        UserBalance userBalanceTest = (UserBalance) userBalanceServices.getBalance(id);
        Assertions.assertEquals(180, userBalanceTest.getUserBalance());
    }

    @Test
    void takeMoneyIfNotMoney() {
        DtoReturnAnswer answer = new DtoReturnAnswer(0, "Недостаточно средств");
        DtoReturnAnswer answerTest = (DtoReturnAnswer) userBalanceServices.takeMoney(454, 2000);
        Assertions.assertEquals(answerTest, answer);
    }

    @Test
    void transferMoney() {
        userBalanceServices.transferMoney(5,15, 30);
        UserBalance userBalance = (UserBalance)userBalanceServices.getBalance(5);
        Assertions.assertEquals(70,userBalance.getUserBalance());
        userBalance = (UserBalance)userBalanceServices.getBalance(15);
        Assertions.assertEquals(180,userBalance.getUserBalance());
    }

    @Test
    void transferMoneyIfNotMoney() {
        DtoReturnAnswer expectedAnswer = new DtoReturnAnswer(0, "Недостаточно средств");
        DtoReturnAnswer returnAnswer = (DtoReturnAnswer)userBalanceServices.transferMoney(5, 15, 1000);

        Assertions.assertEquals(expectedAnswer, returnAnswer);

    }

    @Test
    void transferMoneyIfIncorrectId() {
        DtoReturnAnswer expectedAnswer = new DtoReturnAnswer(0, "Операция не выполненна, проверьте параметры перевода");
        DtoReturnAnswer returnAnswer = (DtoReturnAnswer)userBalanceServices.transferMoney(1, 1212, 50);

        Assertions.assertEquals(expectedAnswer, returnAnswer);
    }

}