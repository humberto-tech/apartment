package apartment.domain;

import apartment.data.DataException;
import apartment.data.GuestRepositoryDouble;
import apartment.data.HostRepositoryDouble;
import apartment.data.ReservationRepositoryDouble;
import apartment.models.Guest;
import apartment.models.Host;
import apartment.models.Reservation;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ReservationServiceTest {

    ReservationService reservationService = new ReservationService(
            new ReservationRepositoryDouble(),
            new GuestRepositoryDouble(),
            new HostRepositoryDouble());


    @Test
    public void getReservationForSpecificHost() throws DataException {
        List<Reservation> reservations = reservationService.getReservationForParticularHost(HostRepositoryDouble.HOST);

        assertEquals(2, reservations.size());
    }

    @Test
    public void removeReservationWithDateBeforeTheCurrentDay() throws DataException {
        assertEquals(false,
                reservationService.removeReservationById(1, HostRepositoryDouble.HOST));
    }

    @Test
    public void removeReservationWithSpecificNonValidId() throws DataException {
        assertEquals(false, reservationService.removeReservationById(3, HostRepositoryDouble.HOST));
    }

    @Test
    public void removeReservationWillSpecificId() throws DataException {
        assertEquals(2, reservationService.getReservationForParticularHost(HostRepositoryDouble.HOST).size());

        assertTrue(reservationService.removeReservationById(2, HostRepositoryDouble.HOST));

        assertEquals(1, reservationService.getReservationForParticularHost(HostRepositoryDouble.HOST).size());
    }

    @Test
    public void addNullReservation() throws DataException {
        Result<Reservation> result = reservationService.add(null, false);
        assertEquals(1, result.getErrorMessages().size());

        assertEquals("Error: Reservation is Null", result.getErrorMessages().get(0));
    }

    @Test
    public void addReservationWillNoObjectSet() throws DataException {
        Reservation reservation = new Reservation();
        Result<Reservation> result = reservationService.add(reservation, false);
        assertEquals(5, result.getErrorMessages().size());
    }

    @Test
    public void addReservationWithNegativeTotal() throws DataException {
        Reservation reservation = new Reservation();
        reservation.setId(3);
        reservation.setStartDate(LocalDate.now());
        reservation.setEndDate(LocalDate.now().plusDays(1));
        reservation.setTotal(new BigDecimal("-2"));
        reservation.setGuest(GuestRepositoryDouble.GUEST3);
        reservation.setHost(HostRepositoryDouble.HOST);
        Result<Reservation> result = reservationService.add(reservation, false);
        assertEquals(1, result.getErrorMessages().size());

        assertEquals("Error: total is negative this is not possible.", result.getErrorMessages().get(0));
    }

    @Test
    public void addReservationWithHostAndGuestThatDoesNotExistInDatabase() throws DataException {
        Host host = new Host();
        host.setEmail("yahoo@kdj.com");
        host.setId("jkldajf-3kjldkafj-dkfjladkfj-kjld");
        host.setWeekendRate(new BigDecimal("400"));

        Guest basicGuest = new Guest();
        basicGuest.setPhone("612 09448094");
        basicGuest.setState("NY");
        basicGuest.setLastName("basicLast");
        basicGuest.setFirstName("basicFirst");
        basicGuest.setEmail("basic-guest@yahoo.com");

        Reservation reservation = new Reservation();
        reservation.setId(3);
        reservation.setStartDate(LocalDate.now());
        reservation.setEndDate(LocalDate.now().plusDays(1));
        reservation.setTotal(new BigDecimal(400));
        reservation.setGuest(basicGuest);
        reservation.setHost(host);

        Result<Reservation> result = reservationService.add(reservation, false);
        assertEquals(2, result.getErrorMessages().size());
    }

    @Test
    public void addReservationWithStartDateBeforeCurrentDay() throws DataException {
        Reservation reservation = new Reservation();
        reservation.setId(3);

        reservation.setStartDate(LocalDate.now().minusDays(1));
        reservation.setEndDate(LocalDate.now().plusDays(1));
        reservation.setTotal(new BigDecimal(400));
        reservation.setGuest(GuestRepositoryDouble.GUEST3);
        reservation.setHost(HostRepositoryDouble.HOST);
        reservation.setGuestId(3);

        Result<Reservation> result = reservationService.add(reservation, false);
        assertEquals(1, result.getErrorMessages().size());

    }

    @Test
    public void addReservationWithStartDateAfterEndDate() throws DataException {
        Reservation reservation = new Reservation();
        reservation.setId(3);

        reservation.setStartDate(LocalDate.now().plusDays(5));
        reservation.setEndDate(LocalDate.now().plusDays(1));
        reservation.setTotal(new BigDecimal(400));
        reservation.setGuest(GuestRepositoryDouble.GUEST3);
        reservation.setHost(HostRepositoryDouble.HOST);
        reservation.setGuestId(3);

        Result<Reservation> result = reservationService.add(reservation, false);
        assertEquals(1, result.getErrorMessages().size());
    }

    @Test
    public void addReservationsWithOverLap() throws DataException {
        Reservation reservation = new Reservation();
        reservation.setId(3);

        reservation.setStartDate(LocalDate.of(2035, 2, 12));
        reservation.setEndDate(LocalDate.of(2035, 2, 13));
        reservation.setTotal(new BigDecimal(400));
        reservation.setGuest(GuestRepositoryDouble.GUEST3);
        reservation.setHost(HostRepositoryDouble.HOST);
        reservation.setGuestId(3);

        Result<Reservation> result = reservationService.add(reservation, false);
        assertEquals(1, result.getErrorMessages().size());

        reservation.setStartDate(LocalDate.of(2035, 2, 9));
        reservation.setEndDate(LocalDate.of(2035, 2, 12));
        reservationService.add(reservation, false);
        assertEquals(1, result.getErrorMessages().size());


        reservation.setStartDate(LocalDate.of(2035, 2, 9));
        reservation.setEndDate(LocalDate.of(2035, 2, 13));
        reservationService.add(reservation, false);
        assertEquals(1, result.getErrorMessages().size());

        reservation.setStartDate(LocalDate.of(2035, 2, 1));
        reservation.setEndDate(LocalDate.of(2035, 2, 13));
        reservationService.add(reservation, false);
        assertEquals(1, result.getErrorMessages().size());
    }

    @Test
    public void addSuccessfulReservation() throws DataException {
        Reservation reservation = new Reservation();
        reservation.setId(3);

        reservation.setStartDate(LocalDate.of(2035, 3, 12));
        reservation.setEndDate(LocalDate.of(2035, 3, 13));
        reservation.setTotal(new BigDecimal(400));
        reservation.setGuest(GuestRepositoryDouble.GUEST3);
        reservation.setHost(HostRepositoryDouble.HOST);
        reservation.setGuestId(3);

        Result<Reservation> result = reservationService.add(reservation, false);
        assertEquals(0, result.getErrorMessages().size());
    }


    @Test
    public void updateReservationWithANullReservation() throws DataException {
        Result<Boolean> result = reservationService.updateReservation(null);
        assertEquals(1, result.getErrorMessages().size());

        assertEquals("Error: Reservation is Null", result.getErrorMessages().get(0));
    }

    @Test
    public void updateReservationWithReservationThatIsNotSet() throws DataException {
        Reservation reservation = new Reservation();
        Result<Boolean> result = reservationService.updateReservation(reservation);
        assertEquals(5, result.getErrorMessages().size());
    }

    @Test
    public void updateReservationWithATotalIsNegative() throws DataException {
        Reservation reservation = new Reservation();
        reservation.setId(3);
        reservation.setStartDate(LocalDate.now());
        reservation.setEndDate(LocalDate.now().plusDays(1));
        reservation.setTotal(new BigDecimal("-2"));
        reservation.setGuest(GuestRepositoryDouble.GUEST3);
        reservation.setHost(HostRepositoryDouble.HOST);
        Result<Boolean> result = reservationService.updateReservation(reservation);
        assertEquals(1, result.getErrorMessages().size());

        assertEquals("Error: total is negative this is not possible.", result.getErrorMessages().get(0));
    }

    @Test
    public void updateReservationWithHostAndGuestThatDoesNotExistInDatabase() throws DataException {
        Host host = new Host();
        host.setEmail("yahoo@kdj.com");
        host.setId("jkldajf-3kjldkafj-dkfjladkfj-kjld");
        host.setWeekendRate(new BigDecimal("400"));

        Guest basicGuest = new Guest();
        basicGuest.setPhone("612 09448094");
        basicGuest.setState("NY");
        basicGuest.setLastName("basicLast");
        basicGuest.setFirstName("basicFirst");
        basicGuest.setEmail("basic-guest@yahoo.com");

        Reservation reservation = new Reservation();
        reservation.setId(3);
        reservation.setStartDate(LocalDate.now());
        reservation.setEndDate(LocalDate.now().plusDays(1));
        reservation.setTotal(new BigDecimal(400));
        reservation.setGuest(basicGuest);
        reservation.setHost(host);

        Result<Boolean> result = reservationService.updateReservation(reservation);
        assertEquals(2, result.getErrorMessages().size());
    }

    @Test
    public void updateReservationWithStartDateBeforeCurrentDay() throws DataException {
        Reservation reservation = new Reservation();
        reservation.setId(3);

        reservation.setStartDate(LocalDate.now().minusDays(1));
        reservation.setEndDate(LocalDate.now().plusDays(1));
        reservation.setTotal(new BigDecimal(400));
        reservation.setGuest(GuestRepositoryDouble.GUEST3);
        reservation.setHost(HostRepositoryDouble.HOST);
        reservation.setGuestId(3);

        Result<Boolean> result = reservationService.updateReservation(reservation);
        assertEquals(1, result.getErrorMessages().size());

    }

    @Test
    public void updateReservationWithStartDateAfterEndDate() throws DataException {
        Reservation reservation = new Reservation();
        reservation.setId(3);

        reservation.setStartDate(LocalDate.now().plusDays(5));
        reservation.setEndDate(LocalDate.now().plusDays(1));
        reservation.setTotal(new BigDecimal(400));
        reservation.setGuest(GuestRepositoryDouble.GUEST3);
        reservation.setHost(HostRepositoryDouble.HOST);
        reservation.setGuestId(3);

        Result<Boolean> result = reservationService.updateReservation(reservation);
        assertEquals(1, result.getErrorMessages().size());
    }

    @Test
    public void updateReservationsWithOverLap() throws DataException {
        Reservation reservation = new Reservation();
        reservation.setId(1);

        reservation.setStartDate(LocalDate.of(2035, 2, 12));
        reservation.setEndDate(LocalDate.of(2035, 2, 13));
        reservation.setTotal(new BigDecimal(400));
        reservation.setGuest(GuestRepositoryDouble.GUEST2);
        reservation.setHost(HostRepositoryDouble.HOST);
        reservation.setGuestId(2);

        Result<Boolean> result = reservationService.updateReservation(reservation);
        assertEquals(1, result.getErrorMessages().size());

        reservation.setStartDate(LocalDate.of(2035, 2, 9));
        reservation.setEndDate(LocalDate.of(2035, 2, 12));
        result = reservationService.updateReservation(reservation);
        assertEquals(1, result.getErrorMessages().size());


        reservation.setStartDate(LocalDate.of(2035, 2, 9));
        reservation.setEndDate(LocalDate.of(2035, 2, 13));
        result = reservationService.updateReservation(reservation);
        assertEquals(1, result.getErrorMessages().size());

        reservation.setStartDate(LocalDate.of(2035, 2, 1));
        reservation.setEndDate(LocalDate.of(2035, 2, 13));
        result = reservationService.updateReservation(reservation);
        assertEquals(1, result.getErrorMessages().size());
    }

    @Test
    public void makeSuccessfulUpdate() throws DataException {
        Reservation reservation = new Reservation();
        reservation.setId(1);

        reservation.setStartDate(LocalDate.of(2035, 2, 13));
        reservation.setEndDate(LocalDate.of(2035, 3, 13));
        reservation.setTotal(new BigDecimal(400));
        reservation.setGuest(GuestRepositoryDouble.GUEST);
        reservation.setHost(HostRepositoryDouble.HOST);
        reservation.setGuestId(1);

        Result<Boolean> result = reservationService.updateReservation(reservation);
        assertEquals(0, result.getErrorMessages().size());
        assertTrue(result.getPayload());

        assertEquals(reservation, reservationService.getReservationForParticularHost(HostRepositoryDouble.HOST).get(1));


    }


}