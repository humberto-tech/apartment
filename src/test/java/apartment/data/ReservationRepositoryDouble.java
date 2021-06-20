package apartment.data;

import apartment.models.Host;
import apartment.models.Reservation;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ReservationRepositoryDouble implements ReservationRepository {

    private ArrayList<Reservation> reservations= new ArrayList<>();

    final LocalDate startDate=LocalDate.of(2020,2,11);
    final LocalDate endDate=LocalDate.of(2020,2,13);

    final LocalDate futureStartDate=LocalDate.of(2035,2,11);
    final LocalDate futureEndDate=LocalDate.of(2035,2,13);

    public ReservationRepositoryDouble(){
        Reservation reservation= new Reservation();
        reservation.setId(1);
        reservation.setStartDate(startDate);
        reservation.setEndDate(endDate);
        reservation.setTotal(new BigDecimal("2").multiply(HostRepositoryDouble.HOST.getStandardRate()));
        reservation.setGuest(GuestRepositoryDouble.GUEST);
        reservation.setHost(HostRepositoryDouble.HOST);
        reservations.add(reservation);

        Reservation reservation2=new Reservation();
        reservation2.setId(2);
        reservation2.setStartDate(futureStartDate);
        reservation2.setEndDate(futureEndDate);
        reservation2.setTotal(new BigDecimal("2").multiply(HostRepositoryDouble.HOST.getWeekendRate()));
        reservation2.setGuest(GuestRepositoryDouble.GUEST3);
        reservation2.setHost(HostRepositoryDouble.HOST);
        reservations.add(reservation2);




    }
    public List<Reservation> findByHostId (String hostId){
        if(hostId.equals(HostRepositoryDouble.HOST.getId())){
            return reservations;
        }
        return null;
    }
    public Reservation add(Reservation reservation)throws DataException{
        List<Reservation> all = findByHostId(reservation.getHost().getId());
        int nextId=all.stream()
                .mapToInt(Reservation::getId)
                .max()
                .orElse(0) + 1;
        reservation.setId(nextId);
        reservations.add(reservation);

        return reservation;
    }

    public boolean removeById(int id,Host host)throws DataException{
        List<Reservation> reservations=findByHostId(host.getId());

        Reservation removeThisReservation=reservations.stream().filter(reservation -> reservation.getId()==id)
                .findFirst()
                .orElse(null);
        if(removeThisReservation==null){
            return false;
        }
        reservations.remove(removeThisReservation);
        return true;
    }








    public boolean update(Reservation updatedReservation) throws DataException {
        List<Reservation> reservations=findByHostId(updatedReservation.getHost().getId());

        for (int i = 0; i < reservations.size(); i++) {
            if (reservations.get(i).getId()==updatedReservation.getId()) {
                reservations.set(i, updatedReservation);
                return true;
            }
        }
        return  false;
    }


}
