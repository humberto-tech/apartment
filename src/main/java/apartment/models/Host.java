package apartment.models;

import java.math.BigDecimal;
import java.util.Objects;

public class Host {
    String id;
    String lastName;
    String email;
    String phone;
    String address;
    String city;
    String state;
    int postalCode;
    BigDecimal standardRate;
    BigDecimal weekendRate;


    public void setId(String id) {
        this.id = id;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setPostalCode(int postalCode) {
        this.postalCode = postalCode;
    }

    public void setStandardRate(BigDecimal standardRate) {
        this.standardRate = standardRate;
    }

    public void setWeekendRate(BigDecimal weekendRate) {
        this.weekendRate = weekendRate;
    }

    public String getId() {
        return id;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public int getPostalCode() {
        return postalCode;
    }

    public BigDecimal getStandardRate() {
        return standardRate;
    }

    public BigDecimal getWeekendRate() {
        return weekendRate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Host host = (Host) o;
        return postalCode == host.postalCode && Objects.equals(id, host.id) && Objects.equals(lastName, host.lastName) && Objects.equals(email, host.email) && Objects.equals(phone, host.phone) && Objects.equals(address, host.address) && Objects.equals(city, host.city) && Objects.equals(state, host.state) && Objects.equals(standardRate, host.standardRate) && Objects.equals(weekendRate, host.weekendRate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, lastName, email, phone, address, city, state, postalCode, standardRate, weekendRate);
    }
}
