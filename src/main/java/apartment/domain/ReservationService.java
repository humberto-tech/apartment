package apartment.domain;

import apartment.data.*;
import apartment.models.Guest;
import apartment.models.Host;
import apartment.models.Reservation;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;


public class ReservationService {
   private ReservationRepository reservationRepository;
   private GuestRepository guestRepository;
   private HostRepository hostRepository;

   public ReservationService(ReservationRepository reservationRepository, GuestRepository guestRepository, HostRepository hostRepository){
       this.reservationRepository=reservationRepository;
       this.guestRepository=guestRepository;
       this.hostRepository=hostRepository;
   }

   //TODO: View REservaations
    //TOD0: CHANGE THIS PARAMETER TO HOST
   public List<Reservation> getReservationForParticularHost(Host host) throws DataException {
       List<Reservation> reservations=reservationRepository.findByHostId(host.getId());
       List<Guest> guests=guestRepository.findAll();
       if(reservations.size()!=0){
           reservations.stream()
                   .forEach(reservation -> reservation.setGuest(
                           guests.stream()
                           .filter(guest -> guest.getId()==reservation.getId())
                           .findFirst()
                           .orElse(null)));
           reservations.stream().forEach(
                   reservation -> reservation.setHost(host));
       }
       //sorting it by first date.
       reservations=  reservations.stream().sorted((r1,r2)-> r1.getStartDate().compareTo(r2.getStartDate())).collect(Collectors.toList());

       return reservations;
   }


   //TODO THIS COULD USE SOME IMPROVEMENTS.
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

   public boolean updateReservation(int reservationId, Host host, Reservation updatedReservation){
       return false;
   }


   public Result<Reservation> add(Reservation reservation) throws DataException{
       Result<Reservation> results=validateAdd(reservation);
       if( results.isSuccess()){
           //total math over here.
           results.setPayload(reservationRepository.add(reservation));
       }
       return results;
   }

   private Result<Reservation> validateAdd(Reservation reservation) throws DataException{
       Result<Reservation> result=new Result<>();
       validateNullValues(reservation,result);
       if(!result.isSuccess()){
           return result;
       }
       validateValues(reservation,result);
       if(!result.isSuccess()){
           return result;
       }

       return result;
   }

   private void validateValues(Reservation reservation,Result<Reservation> result ) throws DataException{


       if(!hostRepository.findByEmail(reservation.getHost().getEmail()).equals(reservation.getHost())){
           result.addErrorMessage("Host doesn't exist in the current host file");
       }

       if(!guestRepository.findByEmail(reservation.getGuest().getEmail()).equals(reservation.getGuest())){
           result.addErrorMessage("Guest doesn't exist in the current guest file. ");
       }

       if(!reservation.getStartDate().isBefore(LocalDate.now())){
           String message=String.format("Start date needs to be after today's date %s", LocalDate.now());
           result.addErrorMessage(message);
       }

       if(reservation.getStartDate().isAfter(reservation.getEndDate())){
           result.addErrorMessage("Start date is after the end date. ");
       }

       if(validateDateOverlap(reservation)){
           result.addErrorMessage("The reservation has an overlap with current reservations.");
       }

   }

   private boolean validateDateOverlap(Reservation reservation){
      List<Reservation> currentHostReservations=reservationRepository.findByHostId(reservation.getHost().getId());

      for(Reservation currentReservation: currentHostReservations){
          boolean overLap=!(currentReservation.getStartDate().compareTo(reservation.getEndDate()) >=0|| currentReservation.getEndDate().compareTo(reservation.getStartDate())<=0);
          if(overLap){

              return true;
          }
      }
      return false;

   }



   private void validateNullValues(Reservation reservation, Result<Reservation> result){

       if(reservation==null){
           result.addErrorMessage("Reservation is Null");
           return;
       }

      if(reservation.getGuest()==null){
          result.addErrorMessage("Guest is empty or null.");
      }
      if(reservation.getStartDate()==null){
          result.addErrorMessage("start date is empty or null.");
      }

       if(reservation.getEndDate()==null){
           result.addErrorMessage("end date is empty or null.");
       }

       if(reservation.getTotal()==null){
           result.addErrorMessage("total is null. ");
       }
       if(reservation.getTotal().signum()==-1){
           result.addErrorMessage("total is negative this is not possible. ");
       }
       if(reservation.getHost()==null){
           result.addErrorMessage("Guest is empty or null.");
       }
   }










}