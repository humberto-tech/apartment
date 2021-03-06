package apartment.data;


import apartment.models.Host;
import apartment.models.Reservation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ReservationFileRepositoryTest {

    static final String SEED_FILE_PATH = "./data/seed-2e72f86c-b8fe-4265-b4f1-304dea8762db.csv";
    static final String TEST_FILE_PATH = "./data/reservations_test/2e72f86c-b8fe-4265-b4f1-304dea8762db.csv";
    static final String TEST_DIR_PATH = "./data/reservations_test";

    static final int NUM_OF_RESERVATIONS = 12;

    ReservationFileRepository repository = new ReservationFileRepository(TEST_DIR_PATH);

    @BeforeEach
    void setup() throws IOException {
        Path seedPath = Paths.get(SEED_FILE_PATH);
        Path testPath = Paths.get(TEST_FILE_PATH);
        Files.copy(seedPath, testPath, StandardCopyOption.REPLACE_EXISTING);
    }

    @Test
    void findAllReservationsInFile() {
        List<Reservation> reservations = repository.findByHostId("2e72f86c-b8fe-4265-b4f1-304dea8762db");
        assertEquals(NUM_OF_RESERVATIONS, reservations.size());
    }

    @Test
    void findingAHostIdThatDoesNotExist() {
        List<Reservation> reservations = repository.findByHostId("id-doesn'-exist-9fafk-jfklaj");
        assertEquals(0, reservations.size());
    }

    @Test
    void addReservation() throws DataException {
        Reservation reservation = new Reservation();
        Host host = new Host();
        host.setId("2e72f86c-b8fe-4265-b4f1-304dea8762db");

        reservation.setHost(host);
        reservation.setStartDate(LocalDate.of(1998, 12, 1));
        reservation.setEndDate(LocalDate.of(1998, 12, 29));
        reservation.setTotal(new BigDecimal("1000.20"));

        reservation = repository.add(reservation);
        assertEquals(13, reservation.getId());
    }

    @Test
    void deleteMultipleIds() throws DataException {
        Host host = new Host();
        host.setId("2e72f86c-b8fe-4265-b4f1-304dea8762db");


        repository.removeById(1, host);
        assertEquals(NUM_OF_RESERVATIONS - 1, repository.findByHostId(host.getId()).size());
        repository.removeById(12, host);
        assertEquals(NUM_OF_RESERVATIONS - 2, repository.findByHostId(host.getId()).size());

        repository.removeById(6, host);
        assertEquals(NUM_OF_RESERVATIONS - 3, repository.findByHostId(host.getId()).size());

    }

    @Test
    void deleteNonExistingId() throws DataException {
        Host host = new Host();
        host.setId("2e72f86c-b8fe-4265-b4f1-304dea8762db");


        repository.removeById(49, host);
        assertEquals(NUM_OF_RESERVATIONS, repository.findByHostId(host.getId()).size());

        repository.removeById(13, host);
        assertEquals(NUM_OF_RESERVATIONS, repository.findByHostId(host.getId()).size());
    }


    @Test
    void updatingAValidReservation() throws DataException {

        Host host = new Host();
        host.setId("2e72f86c-b8fe-4265-b4f1-304dea8762db");


        Reservation newReservation = new Reservation();
        newReservation.setId(2);
        newReservation.setStartDate(LocalDate.of(2022, 9, 10));
        newReservation.setEndDate(LocalDate.of(2022, 12, 10));
        newReservation.setGuestId(136);
        newReservation.setHost(host);

        newReservation.setTotal(new BigDecimal("8930"));
        assertEquals(true, repository.update(newReservation));
    }

    @Test
    void updatingAWithNonValidReservation() throws DataException {
        Host host = new Host();
        host.setId("2e72f86c-b8fe-4265-b4f1-304dea8762db");


        Reservation newReservation = new Reservation();
        newReservation.setId(27);
        newReservation.setStartDate(LocalDate.of(2022, 9, 10));
        newReservation.setEndDate(LocalDate.of(2022, 12, 10));
        newReservation.setGuestId(136);
        newReservation.setHost(host);

        newReservation.setTotal(new BigDecimal("8930"));
        assertEquals(false, repository.update(newReservation));
    }


}
