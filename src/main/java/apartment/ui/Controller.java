package apartment.ui;

import apartment.data.DataException;
import apartment.domain.HostService;
import apartment.domain.ReservationService;
import apartment.models.Host;
import apartment.models.Reservation;

import java.util.List;
import java.util.stream.Collectors;

public class Controller {

    View view;
    HostService hostService;
    ReservationService reservationService;




    public void run()throws DataException {
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
                    break;
                case EDIT_RESERVATION:
                    break;
                case CANCEL_RESERVATION:
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
        }
        //Most fancy stuff
        List<Reservation> hostReservations=reservationService.getReservationForParticularHost(host);
        view.printReservations(host,hostReservations);
    }

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
        reservations=reservations.stream().filter(reservation -> reservation.getGuest().getEmail().equals(guestEmail)).collect(Collectors.toList());
        view.printReservations(host,reservations);
        if(reservations.size()==0){
            return;
        }
        int deleteThisid=view.getUserIntInput("Reservation id to be removed: ",
                reservations.stream().mapToInt(Reservation::getId).min().getAsInt(),
                reservations.stream().mapToInt(Reservation::getId).max().getAsInt());
        view.displayReservationDeletionStatus(reservationService.removeReservationById(deleteThisid,host),host);

    }





}
