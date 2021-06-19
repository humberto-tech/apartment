package apartment.ui;

import apartment.domain.Result;
import apartment.models.Host;
import apartment.models.Reservation;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class View {

    private final ConsoleIO io;

    public View(ConsoleIO io) {
        this.io = io;
    }

    public void reservationSummary(Reservation reservation){
        io.println("Reservation Summary:");
        io.printf("Start date: %s",reservation.getStartDate());
        io.printf("End date: %s",reservation.getEndDate());
        io.printf("Total: %s",reservation.getTotal());
    }

    public String displayReservationSummary(Reservation reservation){
        reservationSummary(reservation);
        String userInput="";
        while(!(userInput.equals("y") || userInput.equals("n"))){
            userInput=io.readString("Is this okay? Select y or n.");
        }
        return userInput;
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

        //TODO: index box
        String message = String.format("Select [%s-%s]: ", min, max);
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
        io.printf("%s: %s, %s%n", host.getLastName(),host.getCity(),host.getState());

        if(reservations.size()==0){
            io.println("No reservations for this host!!!");
        }
        reservations.stream().forEach(
               reservation ->  io.printf("ID: %d, %s - %s, Guest: %s, %s, Email: %s%n",
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
    public void guestNotFound(String email){
        io.println("No guest in the database has the following email: "+email);
    }

    public int getUserIntInput(String prompt, int min, int max){
        return io.readInt(prompt,min,max);
    }

    public void displayReservationDeletionStatus(boolean deleted, int deletedId){
        if(deleted){
            io.println("Success");
            io.printf("Reservation %d has been removed%n", deletedId);
        }else{
            io.printf("No deletion occurred. This only occurs if you try to delete past reservation.");
        }
    }

    public LocalDate getDate(String prompt){
        return io.readLocalDate(prompt);
    }

    public String displaySummaryOfNewReservations(Reservation reservation){
        io.printf("Start: %s%n",reservation.getStartDate());

        io.printf("End %s%n",reservation.getEndDate());

        io.printf("Total: %s%n",reservation.getTotal());

        return io.readString("Is this okay [y/n]: ");
    }

    public void displayEditReservationResult(Result<Boolean> result, Reservation reservation){
        if(!result.isSuccess()){
            result.getErrorMessages().stream().forEach(a-> io.printf("%s%n",a));
        }

        if(result.getPayload()==true){
            io.printf("Congrantatlions edit occured%n");
        }else{
            io.println("Edit did not occur.%n");
        }
    }

    public void displayMakeReservationResults(Result<Reservation> result){
        if(!result.isSuccess()){
            result.getErrorMessages().stream().forEach(a-> io.printf("%s%n",a));
            return;
        }
        io.println("Success!!!");
        io.printf("Reservation %d was created%n",result.getPayload().getId());

    }








}
