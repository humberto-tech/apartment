package apartment.domain;

import apartment.data.DataException;
import apartment.data.GuestRepository;
import apartment.models.Guest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GuestService {
    public GuestRepository guestRepository;

    public GuestService(GuestRepository guestRepository) {
        this.guestRepository = guestRepository;
    }

    public List<Guest> getAllGuests() throws DataException {
        return guestRepository.findAll();
    }

    public Guest findGuestByEmail(String email) throws DataException {
        return guestRepository.findByEmail(email);
    }

}
