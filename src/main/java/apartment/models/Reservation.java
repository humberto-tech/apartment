package apartment.models;

import java.math.BigDecimal;
import java.time.LocalDate;


public class Reservation {
    String id;
    LocalDate startDate;
    LocalDate endDate;
    int guestId;
    BigDecimal total;
}
