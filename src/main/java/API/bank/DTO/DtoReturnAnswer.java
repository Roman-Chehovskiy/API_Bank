package API.bank.DTO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Objects;

public class DtoReturnAnswer implements Dto {
    @Expose
    @SerializedName("Key_Answer")
    private int keyAnswer;
    @Expose
    @SerializedName("Description")
    private String descriptionKey;

    public DtoReturnAnswer(int keyAnswer, String descriptionKey) {
        this.keyAnswer = keyAnswer;
        this.descriptionKey = descriptionKey;
    }

    public int getKeyAnswer() {
        return keyAnswer;
    }

    public void setKeyAnswer(int keyAnswer) {
        this.keyAnswer = keyAnswer;
    }

    public String getDescriptionKey() {
        return descriptionKey;
    }

    public void setDescriptionKey(String descriptionKey) {
        this.descriptionKey = descriptionKey;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DtoReturnAnswer that = (DtoReturnAnswer) o;
        return keyAnswer == that.keyAnswer &&
                Objects.equals(descriptionKey, that.descriptionKey);
    }

}
