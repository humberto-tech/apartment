package apartment.ui;

import apartment.data.DataException;

public class Controller {

    View view;




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

    public void displayViewReservation(){
        view.displayHeader(MainMenuOption.VIEW_RESERVATION.getMessage());

        String hostEmail=view.getUserStringInput("Host Email: ");

        //Do some fancy stuff here

        //Most fancy stuff

        //display function
    }

    public void displayMakeReservation(){

        view.displayHeader(MainMenuOption.MAKE_RESERVATION.getMessage());

        String guestEmail=view.getUserStringInput("Guest Email: ");
        String hostEmail=view.getUserStringInput("Host Email: ");
    }





}
