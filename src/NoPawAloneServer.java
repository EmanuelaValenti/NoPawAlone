import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class NoPawAloneServer {
/*Definiamo il Server socket, il client socket, la porta usata per la connessione ed il client_id.*/
    ServerSocket socket;
    Socket client_socket;
    private int port;
    int client_id = 0;

    Register NewDogRegister = new Register();
    Register LostDogRegister = new Register();
    Register StrayDogRegister = new Register();


    public static void main(String args[]) {

        if (args.length != 1) {
            System.out.println("Usage: java NoPawAloneServer <port>");
            return;
        }

        NoPawAloneServer server = new NoPawAloneServer(Integer.parseInt(args[0]));
        server.start();
    }

    public NoPawAloneServer(int port) {
        System.out.println("Initializing server with port " + port);
        this.port = port;
    }


    public void start() {
        try {
            System.out.println("Starting server on port " + port);
            socket = new ServerSocket(port);
            System.out.println("Started server on port " + port);
            while (true) {
                System.out.println("Listening on port " + port);
                client_socket = socket.accept();
                System.out.println("Accepted connection from " + client_socket.getRemoteSocketAddress());

                ClientManager cm = new ClientManager(client_socket, NewDogRegister, LostDogRegister,StrayDogRegister);
                Thread t = new Thread(cm, "client_" + client_id);
                client_id++;
                t.start();


            }

        } catch (IOException e) {
            System.out.println("Could not start server on port " + port);
            e.printStackTrace();
        }
    }

}


