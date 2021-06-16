package apartment.data;

import apartment.models.Host;

import java.util.List;

public interface HostRepository {
    public List<Host> findAll() throws DataException;
    Host add(Host host) throws DataException;

    public Host findByEmail(String email) throws DataException;
}
