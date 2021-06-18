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

    public void setId(int id) {
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

    public int getId() {
        return id;
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

    public void calculateTotal(){
        for(LocalDate date=this.startDate; date.isBefore(startDate); date=date.plusDays(1)){
            if(date.getDayOfWeek()== DayOfWeek.FRIDAY || date.getDayOfWeek()==DayOfWeek.SATURDAY){
                total=total.add(host.weekendRate);
            }else{
                total=total.add(host.standardRate);
            }
        }
    }
}
