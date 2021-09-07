import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;


public class ClientManager implements Runnable{

    private Socket client_socket;
    private Register NationalDogRegister;
    private Register LostDogRegister;
    private Register StrayDogRegister;


    public ClientManager(Socket client_socket, Register NationalDogRegister, Register LostDogRegister, Register StrayDogRegister){
        this.client_socket = client_socket;
        this.NationalDogRegister = NationalDogRegister;
        this.LostDogRegister = LostDogRegister;
        this.StrayDogRegister = StrayDogRegister;
    }


    @Override
    public void run() {

        String tid = Thread.currentThread().getName();
        System.out.println(tid+"-> Accepted connection from " + client_socket.getRemoteSocketAddress());

        Scanner client_scanner = null;
        PrintWriter pw = null;

        try {
            client_scanner = new Scanner(client_socket.getInputStream());
            pw = new PrintWriter(client_socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        ObjectInputStream nd;
        try{
            nd = new ObjectInputStream(new FileInputStream("NationalDogRegister.ser"));
            NationalDogRegister = (Register) nd.readObject();
        }catch (IOException e){
            e.printStackTrace();
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }

        ObjectInputStream ld;
        try{
            ld = new ObjectInputStream(new FileInputStream("LostDogRegister.ser"));
            LostDogRegister = (Register) ld.readObject();
        }catch (IOException e){
            e.printStackTrace();
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }

        ObjectInputStream sd;
        try{
            sd = new ObjectInputStream(new FileInputStream("StrayDogRegister.ser"));
            StrayDogRegister = (Register) sd.readObject();
        }catch (IOException e){
            e.printStackTrace();
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }










        boolean go = true;

        while(go) {
            String message = client_scanner.nextLine();
            System.out.println("Server: Received "+message);
            Scanner msg_scanner = new Scanner(message);

            String cmd = msg_scanner.next();
            System.out.println("Received Command: "+cmd);

            if (cmd.equals("ADD_NEW_DOG")){
                String nameOwner = msg_scanner.next();
                String surnameOwner = msg_scanner.next();
                String phone = msg_scanner.next();
                String nameDog = msg_scanner.next();
                String race = msg_scanner.next();
                String microchip = msg_scanner.next();
                int weight = msg_scanner.nextInt();
                Dog d = new Dog (nameOwner, surnameOwner, phone, nameDog, race, microchip, weight);
                NationalDogRegister.addNewDog(d);
                System.out.println("SERVER LOG: Added "+ d);
                pw.println("ADD_NEW_OK");
                pw.flush();
            }

            else if (cmd.equals("FOUND_MICROCHIP")) {
                String mic = msg_scanner.next();
                String s = NationalDogRegister.foundMicrochip(mic);
                pw.println(s);
                pw.flush();
            }

            else if(cmd.equals("CANC_FOUND")){
                String mic = msg_scanner.next();
                int i=2;
                String canc = LostDogRegister.remove(mic, i);
                pw.println(canc);
                pw.flush();
            }

            else if (cmd.equals("ADD_STRAY_DOG")){
                String nameOwner = msg_scanner.next();
                String surnameOwner = msg_scanner.next();
                String phone = msg_scanner.next();
                String nameDog = msg_scanner.next();
                String race = msg_scanner.next();
                String microchip = msg_scanner.next();
                int weight = msg_scanner.nextInt();
                Dog d = new Dog (nameOwner, surnameOwner, phone, nameDog, race, microchip, weight);
                StrayDogRegister.addStrayDog(d);
                System.out.println("SERVER LOG: Added "+ d);
                pw.println("ADD_STRAY_OK");
                pw.flush();
            }

            else if (cmd.equals("LOST_DOG")){
                String lostMicro = msg_scanner.next();
                String s = NationalDogRegister.foundMicrochip(lostMicro);
                pw.println(s);
                pw.flush();
            }

            else if (cmd.equals("FOUND_LOST")){
                String nameOwner = msg_scanner.next();
                String surnameOwner = msg_scanner.next();
                String phone = msg_scanner.next();
                String nameDog = msg_scanner.next();
                String race = msg_scanner.next();
                String lostMicro = msg_scanner.next();
                int weight = msg_scanner.nextInt();
                Dog d = new Dog (nameOwner, surnameOwner, phone, nameDog, race, lostMicro, weight);
                LostDogRegister.addLostDog(d);
                System.out.println("SERVER LOG: Added "+ d);
                pw.println("LOST_DOG_COPY");
                pw.flush();
            }

            else if (cmd.equals("REG_STRAY_DOG")) {
                pw.println("BEGIN");
                pw.flush();

                ArrayList<Dog> strayDog;
                strayDog = StrayDogRegister.getStrayCopy();
                for (Dog d: strayDog){
                    pw.println(d);
                    pw.flush();
                }
                pw.println("END");
                pw.flush();
            }

            else if(cmd.equals("ADOPT")){
                String micAdopt = msg_scanner.next();
                int i=3;
                String adopt = StrayDogRegister.remove(micAdopt, i);
                pw.println(adopt);
                pw.flush();
            }

            else if(cmd.equals("DEAD_DOG")){
                int dd = msg_scanner.nextInt();
                String ddm = msg_scanner.next();
                if(dd==1){
                    int i = 1;
                    String ddn = NationalDogRegister.remove(ddm,i);
                    pw.println(ddn);
                    pw.flush();
                }
                if(dd==2){
                    int i = 2;
                    String ddl = LostDogRegister.remove(ddm,i);
                    pw.println(ddl);
                    pw.flush();

                }
                if(dd==3){
                    int i = 3;
                    String dds = StrayDogRegister.remove(ddm,i);
                    pw.println(dds);
                    pw.flush();

                }
            }

            else if(cmd.equals("STAMP_NAT")){
                pw.println("BEGIN");
                pw.flush();

                ArrayList<Dog> natDog;
                natDog = NationalDogRegister.getNatCopy();
                for (Dog d: natDog){
                    pw.println(d);
                    pw.flush();
                }
                pw.println("END");
                pw.flush();
            }

            else if(cmd.equals("STAMP_LOST")){
                pw.println("BEGIN");
                pw.flush();

                ArrayList<Dog> lostDog;
                lostDog = LostDogRegister.getLostCopy();
                for (Dog d: lostDog){
                    pw.println(d);
                    pw.flush();
                }
                pw.println("END");
                pw.flush();
            }

            else if(cmd.equals("STAMP_STRAY")){
                pw.println("BEGIN");
                pw.flush();

                ArrayList<Dog> strayDog;
                strayDog = StrayDogRegister.getStrayCopy();
                for (Dog d: strayDog){
                    pw.println(d);
                    pw.flush();
                }
                pw.println("END");
                pw.flush();
            }

            else if (cmd.equals("QUIT")){
                /*Dovrebbe essere OK*/
                System.out.println("Server: Closing connection to "+client_socket.getRemoteSocketAddress());
                try {
                    client_socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                go = false;
            }

            else if (cmd.equals("SAVE_N")){
                try{
                    var nos = new ObjectOutputStream(new FileOutputStream("NationalDogRegister.ser"));
                    nos.writeObject(NationalDogRegister);
                    nos.close();
                    pw.println("SAVE_N_OK");
                    pw.flush();;
                    System.out.println("SERVER LOG: list saved correctly");
                }
                catch (IOException e){
                    pw.println("SAVE_ERROR");
                    pw.flush();
                    e.printStackTrace();
                }}


            else if (cmd.equals("SAVE_L")){
                try{
                    var los = new ObjectOutputStream(new FileOutputStream("LostDogRegister.ser"));
                    los.writeObject(LostDogRegister);
                    los.close();
                    pw.println("SAVE_L_OK");
                    pw.flush();
                    System.out.println("SERVER LOG: list saved correctly");
                }
                catch (IOException e){
                    pw.println("SAVE_ERROR");
                    pw.flush();
                    e.printStackTrace();
                }
            }

             else if (cmd.equals("SAVE_S")){
                try{
                    var sos = new ObjectOutputStream(new FileOutputStream("StrayDogRegister.ser"));
                    sos.writeObject(StrayDogRegister);
                    sos.close();
                    pw.println("SAVE_S_OK");
                    pw.flush();;
                    System.out.println("SERVER LOG: list saved correctly");
                }
                catch (IOException e){
                    pw.println("SAVE_ERROR");
                    pw.flush();
                    e.printStackTrace();
                }

            }


            else {
                System.out.println("Unknown command" + message);
                pw.println("ERROR_CMD");
                pw.flush();
            }

        }/*while*/

    }/*run */

}/*ClientManager*/
