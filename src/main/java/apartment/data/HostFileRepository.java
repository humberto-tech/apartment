package apartment.data;

import apartment.models.Host;
import org.springframework.beans.factory.annotation.Value;

import java.math.BigDecimal;

public class HostFileRepository implements HostRepository{

    private static final String HEADER = "id,last_name,email,phone,address,city,state,postal_code,standard_rate,weekend_rate";
    private final String filePath;


    public HostFileRepository(@Value("${hostFilepath}")String filePath){
        this.filePath=filePath;
    }

    private String serialize(Host host) {
        return String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s,%s",
                host.getId(),
                host.getLastName(),
                host.getEmail(),
                host.getPhone(),
                host.getAddress(),
                host.getCity(),
                host.getState(),
                host.getPostalCode(),
                host.getStandardRate(),
                host.getWeekendRage());

    }

    private Host deserialize(String[] fields) {
        Host host = new Host();
        host.setId(fields[0]);
        host.setLastName(fields[1]);
        host.setEmail(fields[2]);
        host.setPhone(fields[3]);
        host.setAddress(fields[4]);
        host.setCity(fields[5]);
        host.setState(fields[6]);
        host.setPostalCode(Integer.parseInt(fields[7]));
        host.setStandardRate(new BigDecimal(fields[8]));
        host.setWeekendRage(new BigDecimal(fields[9]));
        return host;
    }




}
