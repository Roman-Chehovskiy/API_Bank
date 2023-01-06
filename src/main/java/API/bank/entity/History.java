package API.bank.entity;

import API.bank.DTO.Dto;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;

// сущность для работы с историей операций
@Table(name = "history")
@Entity
public class History implements Dto {

    private int id;
    @Expose
    @SerializedName("Id_user")
    private int userId;
    @Expose
    @SerializedName("date_operation")
    private Date dateOperation;
    @Expose
    @SerializedName("operation_name")
    private String operation;
    @Expose
    @SerializedName("status_operation")
    private String status;
    @Expose
    @SerializedName("sum_operation")
    private double sumOperation;


    public History() {
    }

    public History(int userId, Date dateOperation, String operation, String status) {
        this.userId = userId;
        this.dateOperation = dateOperation;
        this.operation = operation;
        this.status = status;
    }

    public History(int userId, Date dateOperation, String operation, String status, double sumOperation) {
        this.userId = userId;
        this.dateOperation = dateOperation;
        this.operation = operation;
        this.status = status;
        this.sumOperation = sumOperation;
    }


    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "id_user")
    public int getUserId() {
        return userId;
    }

    public void setUserId(int user_id) {
        this.userId = user_id;
    }

    @Column(name = "date")
    public Date getDateOperation() {
        return dateOperation;
    }

    public void setDateOperation(Date date_operation) {
        this.dateOperation = date_operation;
    }

    @Column(name = "operation")
    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    @Column(name = "status")
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Column(name = "summa_operation")
    public double getSumOperation() {
        return sumOperation;
    }

    public void setSumOperation(double sumOperation) {
        this.sumOperation = sumOperation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        History history = (History) o;
        return userId == history.userId &&
                Double.compare(history.sumOperation, sumOperation) == 0 &&
                Objects.equals(dateOperation, history.dateOperation) &&
                Objects.equals(operation, history.operation) &&
                Objects.equals(status, history.status);
    }

}
