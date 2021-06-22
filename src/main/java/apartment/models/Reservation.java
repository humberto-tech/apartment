package apartment.models;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;


public class Reservation {
    int id;
    LocalDate startDate;
    LocalDate endDate;
    int guestId;
    BigDecimal total;
    Guest guest;
    Host host;

    public Guest getGuest() {
        return guest;
    }

    public void setGuest(Guest guest) {
        this.guest = guest;
    }

    public Host getHost() {
        return host;
    }

    public void setHost(Host host) {
        this.host = host;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public int getGuestId() {
        return guestId;
    }

    public void setGuestId(int guestId) {
        this.guestId = guestId;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + id +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", guestId=" + guestId +
                ", total=" + total +
                ", guest=" + guest +
                ", host=" + host +
                '}';
    }

    public void calculateTotal() {
        BigDecimal newTotal = new BigDecimal(0);
        for (LocalDate date = this.startDate; date.isBefore(endDate); date = date.plusDays(1)) {
            if (date.getDayOfWeek() == DayOfWeek.FRIDAY || date.getDayOfWeek() == DayOfWeek.SATURDAY) {
                newTotal = newTotal.add(host.getWeekendRate());
            } else {
                newTotal = newTotal.add(host.getStandardRate());
            }
        }
        this.total = newTotal;
    }
}
