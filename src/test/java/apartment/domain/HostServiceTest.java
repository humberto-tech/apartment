package apartment.domain;

import apartment.data.DataException;
import apartment.data.HostRepository;
import apartment.data.HostRepositoryDouble;
import apartment.data.ReservationRepository;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class HostServiceTest {

    HostService hostService= new HostService(new HostRepositoryDouble());


    @Test
    public void getHostByTheirEmail() throws DataException{
        assertEquals(HostRepositoryDouble.HOST,hostService.findHostFromEmail("eyearnes0@sfgate.com"));
    }
}
