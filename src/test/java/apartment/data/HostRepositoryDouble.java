package apartment.data;

import apartment.models.Host;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class HostRepositoryDouble implements HostRepository {
    public final static Host HOST = makeHost();
    private final ArrayList<Host> hosts = new ArrayList<>();

    public HostRepositoryDouble() {
        hosts.add(HOST);
    }

    public static Host makeHost() {
        Host host = new Host();
        host.setId("3edda6bc-ab95-49a8-8962-d50b53f84b15");
        host.setLastName("Yearnes");
        host.setEmail("eyearnes0@sfgate.com");
        host.setPhone("(806) 1783815");
        host.setAddress("3 Nova Trail");
        host.setCity("Amarillo");
        host.setState("TX");
        host.setPostalCode(Integer.parseInt("79182"));
        host.setStandardRate(new BigDecimal("340"));
        host.setWeekendRate(new BigDecimal("425"));
        return host;
    }


    public List<Host> findAll() throws DataException {
        return hosts;
    }

    public Host add(Host host) throws DataException {
        host.setId(java.util.UUID.randomUUID().toString());
        hosts.add(host);
        return host;
    }

    public Host findByEmail(String email) throws DataException {
        return hosts.stream()
                .filter(host -> host.getEmail().equalsIgnoreCase(email))
                .findFirst()
                .orElse(null);
    }


}
