package apartment.data;

import org.junit.jupiter.api.BeforeEach;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class GuestFileRepositoryTest implements GuestRepository {
    static final String SEED_PATH = "./data/hosts_seed.csv";
    static final String TEST_PATH = "./data/hosts_test.csv";

    GuestFileRepository repository= new GuestFileRepository(TEST_PATH);

    @BeforeEach
    void setup() throws IOException {
        Path seedPath = Paths.get(SEED_PATH);
        Path testPath = Paths.get(TEST_PATH);
        Files.copy(seedPath, testPath, StandardCopyOption.REPLACE_EXISTING);

    }


}
