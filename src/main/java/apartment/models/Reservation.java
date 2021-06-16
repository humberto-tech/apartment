package apartment.models;

import java.math.BigDecimal;
import java.time.LocalDate;


public class Reservation {
    String id;
    LocalDate startDate;
    LocalDate endDate;
    int guestId;
    BigDecimal total;

    public void setId(String id) {
        this.id = id;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public void setGuestId(int guestId) {
        this.guestId = guestId;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public int getGuestId() {
        return guestId;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public String getId() {
        return id;
    }

}
