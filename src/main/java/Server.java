import MainHilo.MainHilo;
import com.sun.tools.javac.Main;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public static void main(String args[]) throws IOException {

        ServerSocket servidor;
        servidor = new ServerSocket(6000);
        System.out.println("Servidor iniciado...");
        while (true) {
            Socket cliente = new Socket();
            cliente=servidor.accept();//esperando cliente
            MainHilo hilo = new MainHilo(cliente);
            hilo.start(); //Se atiende al cliente

        }// Fin de while
    }// Fin de main
}// Fin de Servidor