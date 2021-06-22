package apartment.data;

import apartment.models.Host;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class HostFileRepositoryTest {

    static final String SEED_PATH = "./data/hosts_seed.csv";
    static final String TEST_PATH = "./data/hosts_test.csv";

    HostFileRepository repository = new HostFileRepository(TEST_PATH);


    @BeforeEach
    void setup() throws IOException {
        Path seedPath = Paths.get(SEED_PATH);
        Path testPath = Paths.get(TEST_PATH);
        Files.copy(seedPath, testPath, StandardCopyOption.REPLACE_EXISTING);
    }

    @Test
    public void getAllHosts() throws DataException {
        List<Host> all = repository.findAll();
        assertEquals(3, all.size());
    }

    @Test
    public void findHostWithValidEmail() throws DataException {
        Host compareHost = new Host();
        compareHost.setId("b4f38829-c663-48fc-8bf3-7fca47a7ae70");
        compareHost.setLastName("Fader");
        compareHost.setEmail("mfader2@amazon.co.jp");
        compareHost.setPhone("(501) 2490895");
        compareHost.setAddress("99208 Morning Parkway");
        compareHost.setCity("North Little Rock");
        compareHost.setState("AR");
        compareHost.setPostalCode(Integer.parseInt("72118"));
        compareHost.setStandardRate(new BigDecimal("451"));
        compareHost.setWeekendRate(new BigDecimal("563.75"));

        assertEquals(compareHost, repository.findByEmail("mfader2@amazon.co.jp"));

    }

    @Test
    public void attemptToFindHostWithNonValidEmail() throws DataException {

        assertEquals(null, repository.findByEmail("madeThisEmail@up.co.jp"));
    }

}
