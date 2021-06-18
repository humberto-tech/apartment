package apartment.domain;

import apartment.data.DataException;
import apartment.data.HostRepository;
import apartment.models.Host;

public class HostService {
    private HostRepository hostRepository;

    public Host findHostFromEmail(String email) throws DataException {
        return hostRepository.findByEmail(email);
    }
}
