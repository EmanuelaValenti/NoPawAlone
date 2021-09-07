import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.net.UnknownHostException;


/*Come prima cosa, per far partire il programma, vengono inseriti indirizzo e porta necessari per far partire un socket
che verrà assegnato al client, con il supporto del ClientManager.*/

public class NoPawAloneClient {

    Socket socket;
    private String address;
    private int port;

    public static void main (String args[]){

        if (args.length !=2){
            System.out.println("Usage: java NoPawAloneClient <address> <port>");
            return;
        }

        NoPawAloneClient client = new NoPawAloneClient(args[0], Integer.parseInt(args[1]));
        client.start();

    }/*main*/

    public NoPawAloneClient(String address, int port){
        this.address = address;
        this.port = port;
    }

    public void start() {
        System.out.println("Starting client connection to "+address+": "+port);
    /*Inizio la creazione del socket e parto creando gli scanner ed i printwriter
    * per la comunicazione tra client e client manager*/
        try {
            socket = new Socket(address, port);
            System.out.println("Started client connection to " + address + ":" + port);

            // Usato per inviare comunicazioni al server
            PrintWriter pw = new PrintWriter(socket.getOutputStream());

            // Usato per le comunicazioni del server
            Scanner server_scanner = new Scanner(socket.getInputStream());

            // Usato per leggere gli input dell'utente.
            Scanner user_scanner = new Scanner(System.in);

            /*Il PrintWriter e gli scanner saranno utilizzati per le comunicazioni tra il client ed il client manager e
             * e questo motivo vengono utilizzate anche le seguenti due stringhe:*/

            String msg_to_send;
            String msg_received;

            boolean go = true; /*Per la stampa del menù*/
            int choice;

            while (go) {
                System.out.println("*******************************************************************");
                System.out.println("************************* NO PAW ALONE ****************************");
                System.out.println("***************************  WELCOME  *****************************");
                System.out.println("");
                System.out.println("***** MENU' *****");
                System.out.println("1. Enter a new dog in the National Dog Register");
                System.out.println("2. Found a dog with microchip");
                System.out.println("3. Found a dog without microchip");
                System.out.println("4. Report the missing of dog");
                System.out.println("5. Adopt a stray dog");
                System.out.println("6. Register the death of a dog");
                System.out.println("7. Print the National Dog Register");
                System.out.println("8. Print the Lost Dog Register");
                System.out.println("9. Print the Stray dog register");
                System.out.println("0. Save and Quit");
                System.out.println("");
                System.out.println("*******************************************************************");
                System.out.print("Enter choice -> ");
                choice = user_scanner.nextInt();

                switch (choice) {
                    case 1:
                        /*Inserisci nuovo cane*/

                        System.out.print("Enter the owner's name:");
                        String nameowner = user_scanner.next();
                        System.out.print("Enter the owner's surname:");
                        String surnameowner = user_scanner.next();
                        System.out.print("Enter phone:");
                        String phone = user_scanner.next();
                        System.out.print("Enter the dog's name:");
                        String namedog = user_scanner.next();
                        System.out.print("Enter the breed of the dog:");
                        String race = user_scanner.next();
                        String microchip = MicrochipGenerator.getRandomString(15);
                        System.out.println("The microchip assigned to this dog is: " + microchip);
                        System.out.print("Enter the weight of the dog:");
                        int weight = user_scanner.nextInt();

                        msg_to_send = "ADD_NEW_DOG" + " " + nameowner + " " + surnameowner + " "
                                + phone + " " + namedog + " " + race + " " + microchip + " " + weight;
                        System.out.println("DEBUG: Sending " + msg_to_send);
                        pw.println(msg_to_send);
                        pw.flush();
                        msg_received = server_scanner.nextLine();

                        if (msg_received.equals("ADD_NEW_OK")) {
                            System.out.println("The dog was added successfully!");
                        } else if (msg_received.equals("ADD_NEW_ERROR")) {
                            System.out.println("Error adding dog!!!");
                        } else {
                            System.out.println("ERROR: unkown message-> " + msg_received);
                        }
                        break;

                    case 2:
                        /*Trovato cane con microchip*/
                        System.out.println("Enter the microchip of the dog found:");
                        String mic = user_scanner.next();
                        msg_to_send = "FOUND_MICROCHIP" + " " + mic;
                        System.out.println("DEBUG: Sending " + msg_to_send);
                        pw.println(msg_to_send);
                        pw.flush();

                        msg_received = server_scanner.nextLine();
                        if (msg_received.equals("FOUND_MICROCHIP_OK")) {
                            System.out.println("In the National Dog Register there is a dog with this microchip");

                            System.out.println("Was the dog with this microchip already in the Lost Dog Register?");
                            msg_to_send = "CANC_FOUND" + " " + mic;
                            System.out.println("DEBUG: Sending " + msg_to_send);
                            pw.println(msg_to_send);
                            pw.flush();
                            msg_received = server_scanner.nextLine();
                            if (msg_received.equals("REMOVE_L_OK")) {
                                System.out.println("The dog was already in the Lost Dog Register and was deleted");
                            }
                            if (msg_received.equals("REMOVE_ERROR")) {
                                System.out.println("The loss of the dog was not recorded");
                            }
                        } else if (msg_received.equals("FOUND_MICROCHIP_ERROR")) {
                            System.out.println("Error!");
                        } else {
                            System.out.println("Error: Unkown message ->" + msg_received);
                        }

                        break;

                    case 3:
                        /*Trovato cane senza microchip*/
                        System.out.println("Enter 'Unknown' in the appropriate fields");
                        System.out.print("Enter the owner's name:");
                        String nameOwner1 = user_scanner.next();
                        System.out.print("Enter the owner's surname:");
                        String surnameOwner1 = user_scanner.next();
                        System.out.print("Enter phone:");
                        String phone1 = user_scanner.next();
                        System.out.print("Enter the dog's name:");
                        String nameDog1 = user_scanner.next();
                        System.out.print("Enter the breed of the dog:");
                        String race1 = user_scanner.next();
                        String microchip1 = MicrochipGenerator.getRandomString(15);
                        System.out.println("The microchip assigned to this dog is: " + microchip1);
                        System.out.print("Enter weight of the dog:");
                        int weight1 = user_scanner.nextInt();

                        msg_to_send = "ADD_STRAY_DOG " + " " + nameOwner1 + " " + surnameOwner1 + " "
                                + phone1 + " " + nameDog1 + " " + race1 + " " + microchip1 + " " + weight1;
                        System.out.println("DEBUG: Sending " + msg_to_send);
                        pw.println(msg_to_send);
                        pw.flush();

                        msg_received = server_scanner.nextLine();
                        if (msg_received.equals("ADD_STRAY_OK")) {
                            System.out.println("Dog added correctly!");
                        } else if (msg_received.equals("ADD_ERROR")) {
                            System.out.println("Error adding dog!!!");
                        } else {
                            System.out.println("ERROR: unkown message->" + msg_received);
                        }


                        break;

                    case 4:
                        /*Denunciare lo smarrimento di un cane*/
                        System.out.println("Enter the microchip of the lost dog:");
                        String lostMicro = user_scanner.next();
                        msg_to_send = "LOST_DOG" + " " + lostMicro;

                        System.out.println("DEBUG: Sending " + msg_to_send);
                        pw.println(msg_to_send);
                        pw.flush();

                        msg_received = server_scanner.nextLine();
                        if (msg_received.equals("FOUND_MICROCHIP_OK")) {
                            System.out.println("Dog found correctly in the National Dog Register!");

                            System.out.println("Enter updated information of the lost dog");
                            System.out.print("Enter the owner's name:");
                            String nameOwner2 = user_scanner.next();
                            System.out.print("Enter the owner's surname:");
                            String surnameOwner2 = user_scanner.next();
                            System.out.print("Enter phone:");
                            String phone2 = user_scanner.next();
                            System.out.print("Enter the dog's name:");
                            String nameDog2 = user_scanner.next();
                            System.out.print("Enter the breed of the dog:");
                            String race2 = user_scanner.next();
                            System.out.println("The dog's microchip is: " + lostMicro);
                            System.out.print("Enter weight:");
                            int weight2 = user_scanner.nextInt();


                            msg_to_send = "FOUND_LOST" + " " + nameOwner2 + " " + surnameOwner2 + " "
                                    + phone2 + " " + nameDog2 + " " + race2 + " " + lostMicro + " " + weight2;

                            System.out.println("DEBUG: Sending " + msg_to_send);
                            pw.println(msg_to_send);
                            pw.flush();

                            msg_received = server_scanner.nextLine();

                            if (msg_received.equals("LOST_DOG_COPY")) {
                                System.out.println("Lost dog correctly entered in the Lost Dog Register!");
                            }

                        }
                        else if (msg_received.equals("FOUND_MICROCHIP_ERROR")) {
                            System.out.println("Error found dog!!!");
                        }
                        else {
                            System.out.println("ERROR: unkown message->"+msg_received);
                        }

                        break;


                    case 5:
                        /*Adottare un cane randagio*/
                        msg_to_send = "REG_STRAY_DOG";
                        pw.println(msg_to_send);
                        pw.flush();

                        msg_received = server_scanner.nextLine();
                        boolean reg = true;
                        if(msg_received.equals("BEGIN")){
                            System.out.println("Receiving StrayDogRegister...");
                            while(reg){
                                msg_received = server_scanner.nextLine();
                                if(msg_received.equals("END")){
                                    reg = false;
                                    System.out.println("StrayDogRegister ended!");
                                }else{
                                    System.out.println(msg_received);
                                }
                            }
                        }else{
                            System.out.println("Unknown response: " + msg_received);
                        }


                        System.out.println("Enter the microchip of the dog you would like to adopt:");
                        String microadopt = user_scanner.next();
                        msg_to_send = "ADOPT" + " " + microadopt;
                        System.out.println("DEBUG: Sending "+msg_to_send);
                        pw.println(msg_to_send);
                        pw.flush();

                        msg_received = server_scanner.nextLine();
                        if(msg_received.equals("REMOVE_S_OK")){
                            System.out.println("Dog correctly selected and deleted from the Stray Dog Register");
                        }else if(msg_received.equals("REMOVE_ERROR")){
                            System.out.println("Stray dog deletion error");
                        }

                        System.out.print("Enter the owner's name:");
                        String nameOwner2 = user_scanner.next();
                        System.out.print("Enter the owner's surname:");
                        String surnameOwner2 = user_scanner.next();
                        System.out.print("Enter phone:");
                        String phone2 = user_scanner.next();
                        System.out.print("Enter the dog's name:");
                        String nameDog2 = user_scanner.next();
                        System.out.print("Enter the breed of the dog:");
                        String race2 = user_scanner.next();
                        System.out.println("The dog's microchip is: " + microadopt);
                        System.out.print("Enter weight:");
                        int weight2 = user_scanner.nextInt();

                        msg_to_send = "ADD_NEW_DOG" + " " + nameOwner2 + " " + surnameOwner2 + " "
                                + phone2 + " " + nameDog2 + " " + race2 + " " + microadopt + " " + weight2;
                        System.out.println("DEBUG: Sending "+msg_to_send);
                        pw.println(msg_to_send);
                        pw.flush();

                        msg_received = server_scanner.nextLine();
                        if (msg_received.equals("ADD_NEW_OK")) {
                            System.out.println("A stray dog has been adopted!!!");
                            System.out.println("The dog has been registered correctly in the National Dog Register!");
                        }
                        else if (msg_received.equals("ADD_ERROR")) {
                            System.out.println("Error adding dog!!!");
                        }
                        else {
                            System.out.println("ERROR: unkown message-> "+msg_received);
                        }




                        break;

                    case 6:
                        /* Registrare la morte di un cane */
                        System.out.println("Recording the death of a dog entered in the register:");
                        System.out.println("1. National Dog Register");
                        System.out.println("2. Lost Dog Register");
                        System.out.println("3. Stray Dog Register");
                        System.out.println("Enter choice:");
                        int dd = user_scanner.nextInt();
                        System.out.println("Enter microchip:");
                        String microdd = user_scanner.next();
                        msg_to_send = "DEAD_DOG" + " " + dd + " " + microdd;
                        System.out.println("DEBUG: Sending " + msg_to_send);
                        pw.println(msg_to_send);
                        pw.flush();

                        msg_received = server_scanner.nextLine();
                        if (msg_received.equals("REMOVE_N_OK")) {
                            System.out.println("The death of a national dog has been registered!");
                        }
                        else if (msg_received.equals("REMOVE_L_OK")) {
                            System.out.println("The death of a lost dog has been registered!");
                        }
                        else if(msg_received.equals("REMOVE_S_OK")){
                            System.out.println("The death of a stray dog has been registered!");
                        }
                        else {
                            System.out.println("ERROR: unkown message-> "+msg_received);
                        }


                        break;

                    case 7:
                        /*Stampare registro nazionale cani*/
                        msg_to_send = "STAMP_NAT";
                        System.out.println("DEBUG: Sending " + msg_to_send);
                        pw.println(msg_to_send);
                        pw.flush();

                        msg_received = server_scanner.nextLine();
                        boolean sn = true;
                        if(msg_received.equals("BEGIN")){
                            System.out.println("Receiving NationalDogRegister...");
                            while(sn){
                                msg_received = server_scanner.nextLine();
                                if(msg_received.equals("END")){
                                    sn = false;
                                    System.out.println("NationalDogRegister ended!");
                                }else{
                                    System.out.println(msg_received);
                                }
                            }
                        }else{
                            System.out.println("Unknown response: " + msg_received);
                        }



                        break;

                    case 8:
                        /*Stampare il registro dei cani smarriti*/
                        msg_to_send = "STAMP_LOST";
                        System.out.println("DEBUG: Sending " + msg_to_send);
                        pw.println(msg_to_send);
                        pw.flush();

                        msg_received = server_scanner.nextLine();
                        boolean sl = true;
                        if(msg_received.equals("BEGIN")){
                            System.out.println("Receiving LostDogRegister...");
                            while(sl){
                                msg_received = server_scanner.nextLine();
                                if(msg_received.equals("END")){
                                    sl = false;
                                    System.out.println("LostDogRegister ended!");
                                }else{
                                    System.out.println(msg_received);
                                }
                            }
                        }else{
                            System.out.println("Unknown response: " + msg_received);
                        }

                        break;

                    case 9:
                        /*Stampare il registro dei cani randagi*/
                        msg_to_send = "STAMP_STRAY";
                        System.out.println("DEBUG: Sending " + msg_to_send);
                        pw.println(msg_to_send);
                        pw.flush();

                        msg_received = server_scanner.nextLine();
                        boolean ss = true;
                        if(msg_received.equals("BEGIN")){
                            System.out.println("Receiving StrayDogRegister...");
                            while(ss){
                                msg_received = server_scanner.nextLine();
                                if(msg_received.equals("END")){
                                    ss = false;
                                    System.out.println("StrayDogRegister ended!");
                                }else{
                                    System.out.println(msg_received);
                                }
                            }
                        }else{
                            System.out.println("Unknown response: " + msg_received);
                        }

                        break;

                    case 0:
                        /*Salvo tutto e chiudo*/
                        msg_to_send = "SAVE_N";
                        System.out.println("DEBUG: Sending " + msg_to_send);
                        pw.println(msg_to_send);
                        pw.flush();

                        msg_received = server_scanner.nextLine();
                        if (msg_received.equals("SAVE_N_OK")) {
                            System.out.println("NationalDogRegister save correctly");
                        }else if(msg_received.equals("SAVE_ERROR")){
                            System.out.println("Error saving file");
                        }else {
                                System.out.println("Unknown message: " + msg_received);
                        }

                        msg_to_send = "SAVE_L";
                        System.out.println("DEBUG: Sending " + msg_to_send);
                        pw.println(msg_to_send);
                        pw.flush();

                        msg_received = server_scanner.nextLine();
                        if (msg_received.equals("SAVE_L_OK")) {
                            System.out.println("LostDogRegister save correctly");
                        }else if(msg_received.equals("SAVE_ERROR")){
                            System.out.println("Error saving file");
                        }else {
                            System.out.println("Unknown message: " + msg_received);
                        }


                        msg_to_send = "SAVE_S";
                        System.out.println("DEBUG: Sending " + msg_to_send);
                        pw.println(msg_to_send);
                        pw.flush();

                        msg_received = server_scanner.nextLine();
                        if (msg_received.equals("SAVE_S_OK")) {
                            System.out.println("StrayDogRegister save correctly");
                        }else if(msg_received.equals("SAVE_ERROR")){
                            System.out.println("Error saving file");
                        }else {
                            System.out.println("Unknown message: " + msg_received);
                        }




                        go = false;
                        System.out.println("Quitting Client...");
                        msg_to_send = "QUIT";
                        pw.println(msg_to_send);
                        pw.flush();
                        break;

                    default:
                        System.out.println("Choise "+ choice + "not valid!");

                }/*switch*/
            }/*while*/
        }/*try*/
        catch (UnknownHostException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }/*catch*/
    }/*start*/
}/*NoPawAlone*/
