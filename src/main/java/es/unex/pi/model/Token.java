package es.unex.pi.model;
import java.sql.Date;
public class Token {
    private String value;
    private Date expiryDate;
    
    public Token(String value, Date expiryDate2) {
        this.value = value;
        this.expiryDate = expiryDate2;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }
}
