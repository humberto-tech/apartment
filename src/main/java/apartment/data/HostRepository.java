package apartment.data;

import apartment.models.Host;

import java.util.List;

public interface HostRepository {
     List<Host> findAll() throws DataException;
    Host add(Host host) throws DataException;

     Host findByEmail(String email) throws DataException;
}
