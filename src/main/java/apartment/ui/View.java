package apartment.ui;

import apartment.models.Host;
import apartment.models.Reservation;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class View {

    private final ConsoleIO io;

    public View(ConsoleIO io) {
        this.io = io;
    }




    public MainMenuOption selectMainMenuOption() {
        displayHeader("Main Menu");
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        for (MainMenuOption option : MainMenuOption.values()) {

            io.printf("%s. %s%n", option.getValue(), option.getMessage());
            min = Math.min(min, option.getValue());
            max = Math.max(max, option.getValue());
        }

        String message = String.format("Select [%s-%s]: ", min, max - 1);
        return MainMenuOption.fromValue(io.readInt(message, min, max));
    }

    public void displayHeader(String message) {
        io.println("");
        io.println(message);
        io.println("=".repeat(message.length()));
    }

    public void displayException(Exception ex) {
        displayHeader("A critical error occurred:");
        io.println(ex.getMessage());
    }

    public String getUserStringInput(String prompt){
        return io.readString(prompt);
    }


    public void printReservations(Host host, List<Reservation> reservations){
        io.printf("%s: %s, %s", host.getLastName(),host.getCity(),host.getState());

        if(reservations.size()==0){
            io.println("No reservations!!!");
        }
        reservations.stream().forEach(
               reservation ->  io.printf("ID: %d, %s - %s, Guest: %s, %s, Email: %s",
                       reservation.getId(),
                       reservation.getStartDate(),
                       reservation.getEndDate(),
                       reservation.getGuest().getLastName(),
                       reservation.getGuest().getFirstName(),
                       reservation.getGuest().getEmail()));
    }

    public void hostNotFound(String email){
        io.println("No host found with the following: "+email);
    }

    public int getUserIntInput(String prompt, int min, int max){
        return io.readInt(prompt,min,max);
    }

    public void displayReservationDeletionStatus(boolean deleted, Host host){
        if(deleted){

        }else{

        }
    }










}
