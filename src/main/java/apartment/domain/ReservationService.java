package apartment.domain;

import apartment.data.*;
import apartment.models.Guest;
import apartment.models.Host;
import apartment.models.Reservation;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReservationService {
   private ReservationRepository reservationRepository;
   private GuestRepository guestRepository;
   private HostRepository hostRepository;

   public ReservationService(ReservationRepository reservationRepository, GuestRepository guestRepository, HostRepository hostRepository){
       this.reservationRepository=reservationRepository;
       this.guestRepository=guestRepository;
       this.hostRepository=hostRepository;
   }

   public List<Reservation> getReservationForParticularHost(Host host) throws DataException {
       List<Reservation> reservations=reservationRepository.findByHostId(host.getId());
       List<Guest> guests=guestRepository.findAll();
       if(reservations.size()!=0){
           reservations.stream()
                   .forEach(reservation -> reservation.setGuest(
                           guests.stream()
                           .filter(guest -> guest.getId()==reservation.getGuestId())
                           .findFirst()
                           .orElse(null)));
           reservations.stream().forEach(
                   reservation -> reservation.setHost(host));
       }

       reservations=  reservations.stream().sorted((r1,r2)-> r1.getStartDate().compareTo(r2.getStartDate())).collect(Collectors.toList());

       return reservations;
   }



   public boolean removeReservationById(int reservationId, Host host) throws  DataException{
       List<Reservation> reservations=reservationRepository.findByHostId(host.getId());
       Reservation reservation=reservations.stream().filter(r-> r.getId()==reservationId).findFirst().orElse(null);
       if(reservation==null){
           return false;
       }
       if(reservation.getStartDate().compareTo(LocalDate.now())<=0){
           return false;
       }


       return reservationRepository.removeById(reservationId,host);
   }

   public Result<Boolean> updateReservation(Reservation updatedReservation) throws DataException{
       Result<Reservation> validationResults=validate(updatedReservation,true);

       Result<Boolean> output=new Result<>();
       output.setMessage(validationResults.getErrorMessages());

       if(output.isSuccess()){
           output.setPayload(reservationRepository.update(updatedReservation));
           return  output;
       }
       output.setPayload(false);
       return  output;
   }


   public Result<Reservation> add(Reservation reservation,boolean updating) throws DataException{
       Result<Reservation> results=validate(reservation,updating);
       if( results.isSuccess()){

           results.setPayload(reservationRepository.add(reservation));
       }
       return results;
   }

   private Result<Reservation> validate(Reservation reservation,boolean updating) throws DataException{
       Result<Reservation> result=new Result<>();
       validateNullValues(reservation,result);
       if(!result.isSuccess()){
           return result;
       }
       validateValues(reservation,result, updating);


       return result;
   }

   private void validateValues(Reservation reservation,Result<Reservation> result,boolean updating ) throws DataException{


       if((reservation.getStartDate().compareTo(LocalDate.now())<0)){
           String message=String.format("Error: Start date needs to be current day: %s or later", LocalDate.now());
           result.addErrorMessage(message);
       }

       if(reservation.getStartDate().isAfter(reservation.getEndDate())){
           result.addErrorMessage("Error: Start date is after the end date. Start date should be before the end date.");
       }

       if(validateDateOverlap(reservation,updating)){
           result.addErrorMessage("Error: The reservation has an overlap with a current reservation.");
       }

   }

   private boolean validateDateOverlap(Reservation reservation, boolean updating){
      List<Reservation> currentHostReservations=reservationRepository.findByHostId(reservation.getHost().getId());


      for(Reservation currentReservation: currentHostReservations){
          if(currentReservation.getId()==reservation.getId() && updating==true){
                  continue;
          }

          boolean overLap=!(currentReservation.getStartDate().compareTo(reservation.getEndDate()) >=0|| currentReservation.getEndDate().compareTo(reservation.getStartDate())<=0);
          if(overLap){

              return true;
          }
      }
      return false;
   }



   private void validateNullValues(Reservation reservation, Result<Reservation> result) throws DataException {

       if(reservation==null){
           result.addErrorMessage("Error: Reservation is Null");
           return;
       }

      if(reservation.getGuest()==null){
          result.addErrorMessage("Error: Guest is null.");
      }
      if(reservation.getStartDate()==null){
          result.addErrorMessage("Error: start date is empty or null.");
      }

       if(reservation.getEndDate()==null){
           result.addErrorMessage("Error: end date is empty or null.");
       }

       if(reservation.getTotal()==null){
           result.addErrorMessage("Error: total is null. ");
       }
       if(reservation.getTotal()!=null&&reservation.getTotal().signum()==-1 ){
           result.addErrorMessage("Error: total is negative this is not possible.");
       }
       if(reservation.getHost()==null){
           result.addErrorMessage("Error: Host is  null.");
       }

       if(reservation.getHost()!=null&&hostRepository.findByEmail(reservation.getHost().getEmail())==null){
           result.addErrorMessage("Error: Host doesn't exist in the current host database.");
       }

       if(reservation.getGuest()!=null &&(guestRepository.findByEmail(reservation.getGuest().getEmail())==null)){
           result.addErrorMessage("Error: Guest doesn't exist in the current guest database.");
       }
   }










}
