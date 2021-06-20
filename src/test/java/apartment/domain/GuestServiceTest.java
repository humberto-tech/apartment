package apartment.domain;

import apartment.data.DataException;
import apartment.data.GuestRepositoryDouble;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GuestServiceTest {
    GuestService guestService = new GuestService(new GuestRepositoryDouble());

    @Test
    public void getAllGuests() throws DataException {
        assertEquals(2, guestService.getAllGuests().size());
    }

    @Test
    public void getAGuestByItsEmail() throws DataException {
        assertEquals(GuestRepositoryDouble.GUEST3, guestService.findGuestByEmail("empty2@yahoo.com"));
    }

}
