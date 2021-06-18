package apartment.data;

import apartment.domain.GuestService;
import apartment.models.Guest;
import apartment.models.Host;

import java.util.ArrayList;
import java.util.List;

public class GuestRepositoryDouble implements GuestRepository {
    private ArrayList<Guest> guests= new ArrayList<>();

    public final static Guest GUEST=makeGuest();

    public final static Guest GUEST2=makeGuest2();

    public final static Guest GUEST3=makeGuest3();

    public static Guest makeGuest2(){
        Guest guest= new Guest();
        guest.setId(2);
        guest.setFirstName("First2");
        guest.setLastName("Last2");
        guest.setEmail("empty2@yahoo.com");
        guest.setState("NY");
        guest.setPhone("(612) 9393583");
        return guest;
    }

    public static Guest makeGuest3(){
        Guest guest= new Guest();
        guest.setId(3);
        guest.setFirstName("First3");
        guest.setLastName("Last3");
        guest.setEmail("empty2@yahoo.com");
        guest.setState("NY");
        guest.setPhone("(612) 9393588");
        return guest;
    }



    public static Guest makeGuest(){
        Guest guest= new Guest();
        guest.setId(1);
        guest.setFirstName("First");
        guest.setLastName("Last");
        guest.setEmail("empty@yahoo.com");
        guest.setState("MN");
        guest.setPhone("(612) 9348583");
        return guest;
    }
    public GuestRepositoryDouble(){

        guests.add(GUEST);
        guests.add(GUEST3);
    }

    public List<Guest> findAll() throws DataException{
        return guests;
    }
    public Guest findByEmail(String email) throws DataException{
        return findAll().stream()
                .filter(guest -> guest.getEmail().equalsIgnoreCase(email))
                .findFirst()
                .orElse(null);
    }
    public Guest add(Guest guest) throws DataException{
        guests.add(guest);
        return guest;
    }

}
