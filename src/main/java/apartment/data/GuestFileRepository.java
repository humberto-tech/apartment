package apartment.data;

import apartment.models.Guest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class GuestFileRepository implements GuestRepository {
    private static final String HEADER = "guest_id,first_name,last_name,email,phone,state";
    private final String filePath;

    public GuestFileRepository(@Value("${guestFilepath}") String filePath) {
        this.filePath = filePath;
    }


    public List<Guest> findAll() throws DataException {
        ArrayList<Guest> result = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {

            reader.readLine(); // read header

            for (String line = reader.readLine(); line != null; line = reader.readLine()) {

                String[] fields = line.split(",", -1);
                if (fields.length == 6) {
                    result.add(deserialize(fields));
                }
            }
        } catch (IOException ex) {
            // don't throw on read
        }
        return result;
    }

    private void writeAll(List<Guest> guests) throws DataException {
        try (PrintWriter writer = new PrintWriter(filePath)) {

            writer.println(HEADER);

            if (guests == null) {
                return;
            }

            for (Guest guest : guests) {
                writer.println(serialize(guest));
            }

        } catch (FileNotFoundException ex) {
            throw new DataException(ex);
        }
    }

    public Guest findByEmail(String email) throws DataException {
        return findAll().stream()
                .filter(guest -> guest.getEmail().equalsIgnoreCase(email))
                .findFirst()
                .orElse(null);
    }


    public Guest add(Guest guest) throws DataException {

        if (guest == null) {
            return null;
        }

        List<Guest> all = findAll();

        int nextId = all.stream()
                .mapToInt(Guest::getId)
                .max()
                .orElse(0) + 1;

        guest.setId(nextId);

        all.add(guest);
        writeAll(all);

        return guest;
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
