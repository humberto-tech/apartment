package apartment.data;

import apartment.models.Host;
import apartment.models.Reservation;
import org.springframework.beans.factory.annotation.Value;

import java.io.*;
import java.math.BigDecimal;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ReservationFileRepository implements ReservationRepository{

    private static final String HEADER = "id,start_date,end_date,guest_id,total";
    private final String directory;

    public ReservationFileRepository(@Value("${reservationDirectory}") String directory){this.directory=directory;}

    public List<Reservation> findByHostId(String hostId) {
        ArrayList<Reservation> result = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(getFilePath(hostId)))) {

            reader.readLine(); // read header

            for (String line = reader.readLine(); line != null; line = reader.readLine()) {

                String[] fields = line.split(",", -1);
                if (fields.length == 5) {
                    result.add(deserialize(fields));
                }
            }
        } catch (IOException ex) {
            // don't throw on read
        }




        return result;
    }



    public Reservation add(Reservation reservation) throws DataException {
        List<Reservation> all = findByHostId(reservation.getHost().getId());
        //reservation.setId(java.util.UUID.randomUUID().toString());
        // Need to figure out how to add a new id

        int nextId = all.stream()
                .mapToInt(Reservation::getId)
                .max()
                .orElse(0) + 1;
        reservation.setId(nextId);

        all.add(reservation);
        writeAll(all, reservation.getHost().getId());
        return reservation;
    }

    @Override
    public boolean removeById(int id,Host host) throws DataException {


        List<Reservation> reservations=findByHostId(host.getId());

        Reservation removeThisReservation=reservations.stream().filter(reservation -> reservation.getId()==id)
                .findFirst()
                .orElse(null);

        if(removeThisReservation==null){
            return false;
        }
        reservations.remove(removeThisReservation);
        writeAll(reservations,host.getId());

        return true;
    }
    //EDIT feature:
    @Override
    public boolean update(int reservationId, Host host,Reservation updatedReservation)  {
        List<Reservation> reservations=findByHostId(host.getId());

//        Reservation currentReservation=reservations.stream().filter(reservation -> reservation.getId()==reservationId).findFirst().orElse(null);
//
//        if(currentReservation==null){
//            return false;
//        }
        for (int i = 0; i < reservations.size(); i++) {
            if (reservations.get(i).getId()==reservationId) {
                reservations.set(i, updatedReservation);
                return true;
            }
        }
        return  false;

    }


    private void writeAll(List<Reservation> reservations, String hostId) throws DataException {
        try (PrintWriter writer = new PrintWriter(getFilePath(hostId))) {

            writer.println(HEADER);

            for (Reservation reservation : reservations) {
                writer.println(serialize(reservation));
            }
        } catch (FileNotFoundException ex) {
            throw new DataException(ex);
        }
    }

    private String getFilePath(String hostId) {
        return Paths.get(directory, hostId + ".csv").toString();
    }


    private String serialize(Reservation reservation) {
        return String.format("%s,%s,%s,%s,%s",
                reservation.getId(),
                reservation.getStartDate(),
                reservation.getEndDate(),
                reservation.getGuestId(),
                reservation.getTotal());
    }

    private Reservation deserialize(String[] fields) {
        Reservation reservation = new Reservation();
        reservation.setId(Integer.parseInt( fields[0]));
        reservation.setStartDate(LocalDate.parse(fields[1]));
        reservation.setEndDate(LocalDate.parse(fields[2]));
        reservation.setGuestId(Integer.parseInt(fields[3]));
        reservation.setTotal(new BigDecimal(fields[4]));
        return reservation;
    }






}
