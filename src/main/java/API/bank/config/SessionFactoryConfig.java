package API.bank.config;

import API.bank.entity.History;
import API.bank.entity.UserBalance;
import API.bank.services.impl.UserBalanceServicesImpl;
import org.flywaydb.core.Flyway;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.postgresql.ds.PGSimpleDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.DriverManager;
@Configuration

public class SessionFactoryConfig {
    private static final Logger log = LoggerFactory.getLogger(SessionFactoryConfig.class);
    public SessionFactory sessionFactory;


    // Создаем SessionFactory для работы с базой данных
    @Bean
    public SessionFactory createSessionFactory() {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder().
                configure().build();
        try {
            sessionFactory = new MetadataSources(registry).addAnnotatedClass(UserBalance.class).addAnnotatedClass(History.class)
                    .buildMetadata().buildSessionFactory();

        } catch (Exception e) {
            log.error(e.getMessage());
        }
        Flyway flyway = Flyway.configure().dataSource("jdbc:postgresql://localhost:5432/apibanc", "postgres", "09451")
                .baselineOnMigrate(true)
                .baselineVersion("2")
                .load();
        flyway.migrate();
        return sessionFactory;
    }
}

