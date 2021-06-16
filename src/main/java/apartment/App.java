package apartment;

import apartment.data.ReservationFileRepository;

public class App {
    public static void main(String[] args) {
        ReservationFileRepository repo=new ReservationFileRepository("./data/reservations");


    }
}
