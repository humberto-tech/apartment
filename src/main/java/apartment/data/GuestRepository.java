package apartment.data;

import apartment.models.Guest;

import java.util.List;

public interface GuestRepository {
    List<Guest> findAll() throws DataException;

    Guest findByEmail(String email) throws DataException;

    Guest add(Guest guest) throws DataException;


}
