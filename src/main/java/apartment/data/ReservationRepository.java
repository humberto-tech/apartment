package apartment.data;

import apartment.models.Host;
import apartment.models.Reservation;

import java.util.List;

public interface ReservationRepository {
    List<Reservation> findByHostId(String hostId);
    Reservation add(Reservation reservation) throws DataException;

    boolean removeById(int id, Host host) throws DataException;

     boolean update(Reservation updatedReservation) throws DataException;


}
