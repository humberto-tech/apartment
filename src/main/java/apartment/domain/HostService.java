package apartment.domain;

import apartment.data.DataException;
import apartment.data.HostRepository;
import apartment.models.Host;
import org.springframework.stereotype.Service;

@Service
public class HostService {
    private final HostRepository hostRepository;

    public HostService(HostRepository repository) {
        this.hostRepository = repository;
    }

    public Host findHostFromEmail(String email) throws DataException {
        return hostRepository.findByEmail(email);
    }
}
