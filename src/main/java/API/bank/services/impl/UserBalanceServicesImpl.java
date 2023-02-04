package API.bank.services.impl;

import API.bank.DTO.Dto;
import API.bank.DTO.DtoReturnAnswer;
import API.bank.entity.History;
import API.bank.entity.UserBalance;
import API.bank.services.UserBalanceServices;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

// сервис для работы с балансом
@Service
public class UserBalanceServicesImpl implements UserBalanceServices {

    private static final Logger log = LoggerFactory.getLogger(UserBalanceServicesImpl.class);


    private SessionFactory sessionFactory;
    private HistoryServiсesImpl historyServiсes;
    private SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");

    @Autowired
    public UserBalanceServicesImpl(SessionFactory sessionFactory, HistoryServiсesImpl historyServiсes) {
        this.sessionFactory = sessionFactory;
        this.historyServiсes = historyServiсes;
    }

    public UserBalanceServicesImpl() {

    }

    public HistoryServiсesImpl getHistoryServiсes() {
        return historyServiсes;
    }

    public void setHistoryServiсes(HistoryServiсesImpl historyServiсes) {
        this.historyServiсes = historyServiсes;
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    //получаем из базы данных баланс пользователя
    private Dto getUserBalance(int id) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        String sql = "select * from balance where id_user=" + id;
        Query query = session.createSQLQuery(sql).addEntity(UserBalance.class);
        List<UserBalance> userBalance = query.list();
        session.getTransaction().commit();
        session.close();
        if (userBalance.size() != 0) {
            log.info("Операция выполненна, баланс получен, Id = " + id);
            return userBalance.get(0);
        } else {
            log.warn("Операция не выполненна, такого пользователя не существует, ID = " + id);
            DtoReturnAnswer returnAnswer = new DtoReturnAnswer(-1, "Неудалось найти пользователя по Id");
            return returnAnswer;
        }
    }

    @Override
    public Dto getBalance(int id) {
        Dto result = getUserBalance(id);
        if (result instanceof UserBalance) {
            History history = new History(id, java.sql.Date.valueOf(formatDate.format(new Date())), "get balance", "done");
            historyServiсes.putOperation(history);
        }
        return result;

    }

    //увеличиваем баланс
    @Override
    public Dto putMoney(int id, double money) {
        Dto dto = getUserBalance(id);
        if (dto instanceof UserBalance) {
            UserBalance userBalance = (UserBalance) dto;
            userBalance.setUserBalance(userBalance.getUserBalance() + money);
            saveOrUpdate(userBalance);
            historyServiсes.putOperation(new History(id, java.sql.Date.valueOf(formatDate.format(new Date())), "putMoney", "done", money));
            Dto answer = new DtoReturnAnswer(1, "Ok");
            log.info("Баланс успешно пополнен, ID = " + id);
            return answer;
        } else {
            DtoReturnAnswer returnAnswer = new DtoReturnAnswer(-1, "Неудалось найти пользователя по Id");
            log.warn("Операция не выполненна, такого пользователя не существует, ID = " + id);
            return returnAnswer;
        }
    }

    //уменьшаем баланс
    @Override
    public Dto takeMoney(int id, double money) {
        Dto dto = getUserBalance(id);
        if (dto instanceof UserBalance) {
            UserBalance userBalance = (UserBalance) dto;
            if (userBalance.getUserBalance() < money) {
                historyServiсes.putOperation(new History(id, java.sql.Date.valueOf(formatDate.format(new Date())), "takeMoney", "not done", money));
                Dto answer = new DtoReturnAnswer(0, "Недостаточно средств");
                log.warn("Операция не выполненна, недостаточно средств, пользователь ID = " + id);
                return answer;
            } else {
                userBalance.setUserBalance(userBalance.getUserBalance() - money);
                saveOrUpdate(userBalance);
                log.info("Средства успешно списаны со счета пользователя ID = " + id);
                historyServiсes.putOperation(new History(id, java.sql.Date.valueOf(formatDate.format(new Date())), "takeMoney", "done", money));
                return new DtoReturnAnswer(1, "Операция выполненна");
            }
        } else {
            DtoReturnAnswer answer = (DtoReturnAnswer) dto;
            answer.setKeyAnswer(0);
            log.warn("Операция не выполненна, такого пользователя не существует, ID = " + id);
            return answer;
        }
    }

    //сохраняем или обновляем баланс
    private void saveOrUpdate(UserBalance userBalance) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.saveOrUpdate(userBalance);
        session.getTransaction().commit();
        session.close();
        log.info("Сохранение прошло успешно");
    }

    //переводим средства от одного пользователя другому
    @Override
    public Dto transferMoney(int senderId, int recipientId, double money) {
        DtoReturnAnswer answer = new DtoReturnAnswer();
        Dto senderDto = getUserBalance(senderId);
        Dto recipientDto = getUserBalance(recipientId);
        if ((senderDto instanceof UserBalance) && (recipientDto instanceof UserBalance)) {
            UserBalance senderUserBalance = (UserBalance) senderDto;
            UserBalance recipientUserBalance = (UserBalance) recipientDto;
            if (senderUserBalance.getUserBalance() >= money) {
                Session session = sessionFactory.openSession();
                session.beginTransaction();
                senderUserBalance.setUserBalance(senderUserBalance.getUserBalance() - money);
                recipientUserBalance.setUserBalance(recipientUserBalance.getUserBalance() + money);
                session.saveOrUpdate(senderUserBalance);
                session.saveOrUpdate(recipientUserBalance);
                session.getTransaction().commit();
                session.close();
                historyServiсes.putOperation(new History(senderId, java.sql.Date.valueOf(formatDate.format(new Date())), "transferMoney", "done", money));
                historyServiсes.putOperation(new History(recipientId, java.sql.Date.valueOf(formatDate.format(new Date())), "transferMoney", "done", money));
                answer.setKeyAnswer(1);
                answer.setDescriptionKey("Перевод успешно выполнен");
                log.info("Перевод успешно выполнен");
            } else {
                answer.setKeyAnswer(0);
                answer.setDescriptionKey("Недостаточно средств");
                historyServiсes.putOperation(new History(senderId, java.sql.Date.valueOf(formatDate.format(new Date())), "transferMoney", "not done", money));
                log.warn("Перевод не выполнен, Недостаточно средств");
            }
        } else {
            answer.setKeyAnswer(0);
            answer.setDescriptionKey("Операция не выполненна, проверьте параметры перевода");
            log.warn("Перевод не выполнен, неверные параметры перевода");
        }

        return answer;
    }
}
