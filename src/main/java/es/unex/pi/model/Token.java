package es.unex.pi.model;
import java.sql.Date;
public class Token {
	private long id;
    private String value;
    private long idU;
    private Date expiryDate;
    
    public Token(String value, Date expiryDate2, long idU) {
        this.value = value;
        this.expiryDate = expiryDate2;
        this.idU= idU;
    }

    public String getValue() {
        return value;
    }
    
    public long getId() {
        return id;
    }

    public void setValue(String value) {
        this.value = value;
    }
    
    public long getIdU() {
        return idU;
    }

    public void setIdU(long id) {
        this.idU = id;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }
}
