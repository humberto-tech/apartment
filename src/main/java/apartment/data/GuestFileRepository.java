package apartment.data;

import apartment.models.Guest;
import org.springframework.beans.factory.annotation.Value;

import java.math.BigDecimal;

public class GuestFileRepository implements GuestRepository {
    private static final String HEADER = "guest_id,first_name,last_name,email,phone,state";
    private final String filePath;

    public GuestFileRepository( @Value("${guestFilepath}") String filePath){
        this.filePath=filePath;
    }




    private String serialize(Guest guest) {
        return String.format("%d,%s,%s,%s,%s,%s",
                guest.getId(),
                guest.getFirstName(),
                guest.getLastName(),
                guest.getEmail(),
                guest.getPhone(),
                guest.getState());
    }

    private Guest deserialize(String[] fields) {
        Guest guest = new Guest();
        guest.setId(Integer.parseInt(fields[0]));
        guest.setFirstName(fields[1]);
        guest.setLastName(fields[2]);
        guest.setEmail(fields[3]);
        guest.setPhone(fields[4]);
        guest.setState(fields[5]);
        return guest;
    }






}
