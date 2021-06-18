package apartment.data;

import apartment.models.Guest;
import apartment.models.Host;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GuestFileRepositoryTest  {
    static final String SEED_PATH = "./data/guests_seed.csv";
    static final String TEST_PATH = "./data/guests_test.csv";

    GuestFileRepository repository= new GuestFileRepository(TEST_PATH);

    @BeforeEach
    void setup() throws IOException {
        Path seedPath = Paths.get(SEED_PATH);
        Path testPath = Paths.get(TEST_PATH);
        Files.copy(seedPath, testPath, StandardCopyOption.REPLACE_EXISTING);
    }

    @Test
    public void getAllGuests() throws DataException{
        List<Guest> all=repository.findAll();
        assertEquals(6,all.size());
    }
    //6,Kenn,Curson,kcurson5@youku.com,(941) 9618942,FL
    @Test
    public void findGuestByEmail() throws DataException{
        Guest findThisGuest= new Guest();
        findThisGuest.setEmail("kcurson5@youku.com");
        findThisGuest.setState("FL");
        findThisGuest.setPhone("(941) 9618942");
        findThisGuest.setId(6);
        findThisGuest.setFirstName("Kenn");
        findThisGuest.setLastName("Curson");

        assertEquals(findThisGuest,repository.findByEmail("kcurson5@youku.com"));

    }
    @Test
    public void findGuestByNonValidEmail() throws DataException{
        assertEquals(null, repository.findByEmail("nonvalid@email.com") );
    }






}
