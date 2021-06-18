package apartment.domain;

import apartment.data.DataException;
import apartment.data.GuestRepository;
import apartment.models.Guest;

import java.util.List;

public class GuestService {
    public GuestRepository guestRepository;

    public List<Guest> getAllGuests() throws DataException {
        return guestRepository.findAll();
    }

    public Guest findGuestByEmail(String email) throws DataException{
        return guestRepository.findByEmail(email);
    }


}
