package API.bank.entity;

import API.bank.DTO.Dto;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Objects;

//сущность для работы с балансом пользователя
@Table(name = "BALANCE")
@Entity
public class UserBalance implements Dto {


    private int id;
    @Expose
    @SerializedName("identifier_user")
    private int idUser;
    @Expose
    @SerializedName("balance_user")
    private double userBalance;



    public UserBalance() {
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
    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int user_id) {
        this.idUser = user_id;
    }

    @Column(name = "user_balance")
     public double getUserBalance() {
       return userBalance;
    }

    public void setUserBalance(double user_balance) {
         this.userBalance = user_balance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserBalance that = (UserBalance) o;
        return id == that.id &&
                idUser == that.idUser &&
                Double.compare(that.userBalance, userBalance) == 0;
    }


}
