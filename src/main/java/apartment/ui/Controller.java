package apartment.ui;

import apartment.data.DataException;
import apartment.domain.GuestService;
import apartment.domain.HostService;
import apartment.domain.ReservationService;
import apartment.domain.Result;
import apartment.models.Guest;
import apartment.models.Host;
import apartment.models.Reservation;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class Controller {

    View view;
    HostService hostService;
    ReservationService reservationService;
    GuestService guestService;

    public Controller(HostService hostService, ReservationService reservationService, GuestService guestService, View view){
        this.hostService=hostService;
        this.reservationService=reservationService;
        this.guestService=guestService;
        this.view=view;
    }


    public void run() {
        view.displayHeader("Welcome to Sustainable Foraging");
        try {
            appLoop();
        } catch (DataException ex) {
            view.displayException(ex);
        }
        view.displayHeader("Goodbye.");
    }

    public void appLoop() throws DataException{
        MainMenuOption option;
        do {
            option = view.selectMainMenuOption();
            switch (option) {
                case VIEW_RESERVATION:
                    displayViewReservation();
                    break;
                case MAKE_RESERVATION:
                    displayMakeReservation();
                    break;
                case EDIT_RESERVATION:
                    displayEditReservation();
                    break;
                case CANCEL_RESERVATION:
                    displayCancelReservation();
                    break;
            }
        } while (option != MainMenuOption.EXIT);
    }

    public void displayViewReservation() throws  DataException{
        //TODO: ALLOW THE USER TO SEARCH BY EMAIL OR SELECT HOST FROM THE LIST OF HOSTS.
        view.displayHeader(MainMenuOption.VIEW_RESERVATION.getMessage());

        String hostEmail=view.getUserStringInput("Host Email: ");

        //Do some fancy stuff here
        Host host= hostService.findHostFromEmail(hostEmail);

        if(host==null){
            view.hostNotFound(hostEmail);
            return;
        }
        //Most fancy stuff
        List<Reservation> hostReservations=reservationService.getReservationForParticularHost(host);
        view.printReservations(host,hostReservations);
    }
/*
    public void displayMakeReservation() throws DataException{

        view.displayHeader(MainMenuOption.MAKE_RESERVATION.getMessage());

        String guestEmail=view.getUserStringInput("Guest Email: ");
        String hostEmail=view.getUserStringInput("Host Email: ");

        Host host= hostService.findHostFromEmail(hostEmail);
        if(host==null){
            view.hostNotFound(hostEmail);
            return;
        }
        List<Reservation> reservations=reservationService.getReservationForParticularHost(host);
        //Understand this code
        reservations=reservations.stream().filter(reservation -> reservation.getGuest().getEmail().equals(guestEmail)).collect(Collectors.toList());
        view.printReservations(host,reservations);
        if(reservations.size()==0){
            return;
        }
        int deleteThisid=view.getUserIntInput("Reservation id to be removed: ",
                reservations.stream().mapToInt(Reservation::getId).min().getAsInt(),
                reservations.stream().mapToInt(Reservation::getId).max().getAsInt());
        view.displayReservationDeletionStatus(reservationService.removeReservationById(deleteThisid,host),host);

    }*/
    public void displayCancelReservation() throws DataException{
        view.displayHeader(MainMenuOption.CANCEL_RESERVATION.getMessage());
        String guestEmail=view.getUserStringInput("Guest Email: ");
        String hostEmail=view.getUserStringInput("Host Email: ");

        Host host= hostService.findHostFromEmail(hostEmail);
        if(host==null){
            view.hostNotFound(hostEmail);
            return;
        }

        Guest guest= guestService.findGuestByEmail(guestEmail);
        if(guest==null){
            view.guestNotFound(guestEmail);
            return;
        }

        List<Reservation> reservations=reservationService.getReservationForParticularHost(host);
        reservations=reservations.stream().filter(reservation -> reservation.getGuest().getEmail().equals(guestEmail)).collect(Collectors.toList());
        view.printReservations(host,reservations);
        if(reservations.size()==0){

            return;
        }
        int deleteThisid=view.getUserIntInput("Reservation id to be removed: ",
                reservations.stream().mapToInt(Reservation::getId).min().getAsInt(),
                reservations.stream().mapToInt(Reservation::getId).max().getAsInt());
        view.displayReservationDeletionStatus(reservationService.removeReservationById(deleteThisid,host),deleteThisid);

    }
    public void displayMakeReservation() throws  DataException{
        view.displayHeader(MainMenuOption.MAKE_RESERVATION.getMessage());

        String guestEmail=view.getUserStringInput("Guest Email: ");
        String hostEmail=view.getUserStringInput("Host Email: ");

        Host host= hostService.findHostFromEmail(hostEmail);
        if(host==null){
            view.hostNotFound(hostEmail);
            return;
        }
        Guest guest= guestService.findGuestByEmail(guestEmail);
        if(guest==null){
            view.guestNotFound(guestEmail);
            return;
        }

        List<Reservation> reservations=reservationService.getReservationForParticularHost(host);
        // Filter to get future reservations.
        reservations=reservations.stream().filter(reservation -> reservation.getEndDate().compareTo(LocalDate.now())>=0).collect(Collectors.toList());
        view.printReservations(host,reservations);
        if(reservations.size()==0){
            return;
        }
        LocalDate startDate=view.getDate("Start: ");
        LocalDate endDate=view.getDate("End:");

        Reservation newReservation= new Reservation();
        newReservation.setStartDate(startDate);
        newReservation.setEndDate(endDate);
        //Need to get guess id & guess.
        newReservation.setGuest(guest);
        newReservation.setGuestId(guest.getId());
        newReservation.setHost(host);
        newReservation.calculateTotal();

        //prompt user for validation.
    }
    public void displayEditReservation() throws DataException{
        view.displayHeader(MainMenuOption.EDIT_RESERVATION.getMessage());

        String guestEmail=view.getUserStringInput("Guest Email: ");
        String hostEmail=view.getUserStringInput("Host Email: ");

        Host host= hostService.findHostFromEmail(hostEmail);
        if(host==null){
            view.hostNotFound(hostEmail);
            return;
        }
        Guest guest= guestService.findGuestByEmail(guestEmail);
        if(guest==null){
            view.guestNotFound(guestEmail);
            return;
        }
        List<Reservation> reservations=reservationService.getReservationForParticularHost(host);
        // Filter to get future reservations.
        reservations=reservations.stream().filter(reservation -> reservation.getEndDate().compareTo(LocalDate.now())>=0 && reservation.getGuest().getEmail().equals(guestEmail) ).collect(Collectors.toList());
        view.printReservations(host,reservations);
        if(reservations.size()==0){
            return;
        }
        int deleteThisid=view.getUserIntInput("Reservation id to edit ",
                reservations.stream().mapToInt(Reservation::getId).min().getAsInt(),
                reservations.stream().mapToInt(Reservation::getId).max().getAsInt());

        Reservation newReservation=reservations.get(0);
        newReservation.setHost(host);
        newReservation.setGuest(guest);
        newReservation.setGuestId(deleteThisid);
        newReservation.setStartDate(view.getDate(String.format("Start (%s)", newReservation.getStartDate())));
        newReservation.setStartDate(view.getDate(String.format("End (%s)", newReservation.getStartDate())));

        if(view.displaySummaryOfNewReservations(newReservation).equals("y")){
            Result<Boolean> result= reservationService.updateReservation(newReservation,deleteThisid);
            view.displayEditReservationResult(result,newReservation);
        }




    }





}
