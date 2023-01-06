package API.bank.services.impl;

import API.bank.DTO.Dto;
import API.bank.DTO.DtoReturnAnswer;
import API.bank.entity.History;
import API.bank.services.HistoryService;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//сервис для работы с историей орераций
@Service
public class HistoryServiсesImpl implements HistoryService {

    SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
    private SessionFactory sessionFactory;
    private static final Logger log = LoggerFactory.getLogger(HistoryServiсesImpl.class);

    @Autowired
    public HistoryServiсesImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public HistoryServiсesImpl() {
    }

    //добавляем операцию в базу данных
    @Override
    public void putOperation(History history) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.saveOrUpdate(history);
        session.getTransaction().commit();
        session.close();
        log.info("операция добавлена в базу данных");
    }

    //получаем список операций
    @Override
    public List<Dto> getOperation(int id, String startDate, String endDate) {
        List<Dto> historyList = new ArrayList<>();
        Date dateStart = new Date();
        Date dateEnd = new Date();
        try {
            String sql = "";

            // проверяем, что заданны обе даты
            if (!startDate.equals("0") && !endDate.equals("0")) {

                // даты к необходимому формату
                dateStart = formatDate.parse(startDate);
                dateEnd = formatDate.parse(endDate);
                dateStart.setDate(dateStart.getDate() - 1);
                dateEnd.setDate(dateEnd.getDate() + 1);
                startDate = formatDate.format(dateStart);
                endDate = formatDate.format(dateEnd);

                //пишем sql запрос
                sql = "select * from history where id_user = " + id + " and date > '" + startDate + "' and date < '" + endDate + "'";
            }

            // если не указана начальная дата
            else if (startDate.equals("0") && !endDate.equals("0")) {
                dateEnd = formatDate.parse(endDate);
                dateEnd.setDate(dateEnd.getDate() + 1);
                endDate = formatDate.format(dateEnd);
                sql = "select * from history where id_user = " + id + " and date < '" + endDate + "'";
            }

            //если не указана конечная дата
            else if (endDate.equals("0") && !startDate.equals("0")) {
                dateStart = formatDate.parse(startDate);
                dateStart.setDate(dateStart.getDate() - 1);
                startDate = formatDate.format(dateStart);
                sql = "select * from history where id_user = " + id + " and date > '" + startDate + "'";
            }

            //если не указанны обе даты
            else {
                sql = "select * from history where id_user = " + id;
            }
            Session session = sessionFactory.openSession();
            session.beginTransaction();
            Query query = session.createSQLQuery(sql).addEntity(History.class);
            session.getTransaction().commit();
            historyList = query.list();
            session.close();
            if (historyList.size() == 0) {
                log.info("История не найдена");
                historyList.add(new DtoReturnAnswer(-1, "История не найдена"));
            } else {
                log.info("Получен список операций");
                putOperation(new History(id, new java.sql.Date(new Date().getTime()), "getOperation", "done"));
            }

        } catch (ParseException e) {
            log.warn("Неверный формат даты");
            historyList.add(new DtoReturnAnswer(-1, "Неверный формат даты"));
        }

        return historyList;
    }
}
