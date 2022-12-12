package API.bank.services.impl;

import API.bank.DTO.Dto;
import API.bank.DTO.DtoReturnAnswer;
import API.bank.entity.UserBalance;
import API.bank.services.UserBalanceServices;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserBalanceServicesImpl implements UserBalanceServices {

    private static final Logger log = LoggerFactory.getLogger(UserBalanceServicesImpl.class);


    private SessionFactory sessionFactory;

    @Autowired
    public UserBalanceServicesImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public UserBalanceServicesImpl() {

    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Dto getUser(int id) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        String sql = "select * from balance where id_user=" + id;
        Query query = session.createSQLQuery(sql).addEntity(UserBalance.class);
        List<UserBalance> userBalance = query.list();
        if (userBalance.size() != 0) {
            session.getTransaction().commit();
            session.close();
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
        return getUser(id);

    }

    @Override
    public Dto putMoney(int id, double money) {
        Dto dto = getUser(id);
        if (dto instanceof UserBalance) {
            UserBalance userBalance = (UserBalance) dto;
            userBalance.setUserBalance(userBalance.getUserBalance() + money);
            saveOrUpdate(userBalance);
            Dto answer = new DtoReturnAnswer(1, "Ok");
            log.info("Баланс успешно пополнен, ID = " + id);
            return answer;
        } else {
            DtoReturnAnswer returnAnswer = new DtoReturnAnswer(-1, "Неудалось найти пользователя по Id");
            log.warn("Операция не выполненна, такого пользователя не существует, ID = " + id);
            return returnAnswer;
        }
    }

    @Override
    public Dto takeMoney(int id, double money) {
        Dto dto = getUser(id);
        if (dto instanceof UserBalance) {
            UserBalance userBalance = (UserBalance) dto;
            if (userBalance.getUserBalance() < money) {
                Dto answer = new DtoReturnAnswer(0, "Недостаточно средств");
                log.warn("Операция не выполненна, недостаточно средств, пользователь ID = " + id);
                return answer;
            } else {
                userBalance.setUserBalance(userBalance.getUserBalance() - money);
                saveOrUpdate(userBalance);
                log.info("Средства успешно списаны со счета пользователя ID = " + id);
                return new DtoReturnAnswer(1, "Операция выполненна");
            }
        } else {
            DtoReturnAnswer answer = (DtoReturnAnswer) dto;
            answer.setKeyAnswer(0);
            log.warn("Операция не выполненна, такого пользователя не существует, ID = " + id);
            return answer;
        }
    }

    @Override
    public void saveOrUpdate(UserBalance userBalance) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.saveOrUpdate(userBalance);
        session.getTransaction().commit();
        session.close();
        log.info("Сохранение прошло успешно");
    }
}
