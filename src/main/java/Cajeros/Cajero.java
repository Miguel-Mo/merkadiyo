package Cajeros;

import Util.Util;
import modelos.Empleado;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Cajero {
    private static String Host = "localhost";
    private  static int Puerto = 6000;//puerto remoto
    private static boolean logueado = false;
    private static boolean exit = false;

    private static Socket cliente;

    public Cajero() throws IOException {
    }

    public static void main() throws Exception {


        cliente= new Socket(Host, Puerto);
        System.out.println("PROGRAMA CAJERO INICIADO....");

        //booleano para entrar en bucle
        while (!exit) {

            //booleano para comprobar si esta logueado
            while (!logueado) {
               login();
            }

            while (logueado) {
                menu();
            }

        }

    }

    public static void login() throws IOException {
        //LOGIN
        Scanner scanner = new Scanner(System.in);
        System.out.println("Por favor indique su id de empleado");
        // CREO FLUJO DE SALIDA AL SERVIDOR
        DataOutputStream flujoSalida = new DataOutputStream(cliente.getOutputStream());

        //SIGUIENTE LINEA
        String mensaje = scanner.nextLine();

        if (Util.isNumeric(mensaje)) {
            int id = Integer.parseInt(mensaje);
            // Envia la ID al servidor
            flujoSalida.writeUTF("login;" + id);
            try {
                ObjectInputStream objetoRecibo;
                objetoRecibo = new ObjectInputStream(cliente.getInputStream());
                Empleado login = (Empleado) objetoRecibo.readObject();
                if (login == null) {
                    System.out.println("Error, ese usuario no existe");

                } else {
                    System.out.println("Eres el usuario " + login.toString());
                    System.out.println("SESIÓN INICIADA");
                    logueado = true;
                }
            } catch (ClassNotFoundException | IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Intoduce un número por favor");
        }
    }

    public static void menu() throws IOException {
        DataOutputStream salida;
        String opcionInicial;
        String productoInicial;
        String cantidadInicial;
        int opcion=0;
        int producto=0;
        int cantidad=0;
        System.out.println("¿Qué quieres hacer?: (Pulsa los números)");
        System.out.println("1.Cobrar Compra \n2.Obtener caja del día \n3.Exit");
        Scanner scanner = new Scanner(System.in);
        opcionInicial = scanner.nextLine();
        if(Util.isNumeric(opcionInicial)){
            opcion = Integer.parseInt(opcionInicial);
        }else{
            System.out.println("No has introducido un número");
            menu();
        }

        switch (opcion) {

            case 1:
                System.out.println("¿Qué producto quiere comprar? \n 1.Galleta-1€ \n 2.Donete-1€ \n 3.Baguette-0,5€ \n 4.Napolitana-3€ \n 5.Bizcocho-5€");
                productoInicial = scanner.nextLine();
                if(Util.isNumeric(productoInicial)){
                    System.out.println("¿Qué cantidad quiere comprar?");
                    cantidadInicial = scanner.nextLine();
                    if(Util.isNumeric(cantidadInicial)){
                        producto= Integer.parseInt(productoInicial);
                        cantidad= Integer.parseInt(cantidadInicial);
                    }

                }else {
                    System.out.println("No has introducido un número");
                    menu();
                }

                if(producto!=0 && cantidad!=0){
                    salida = new DataOutputStream(cliente.getOutputStream());
                    salida.writeUTF("cobrar;" + producto + ";" + cantidad);
                }

                ObjectInputStream nostock;
                nostock = new ObjectInputStream(cliente.getInputStream());
                Object confirmacion = null;
                try {
                    confirmacion = nostock.readObject();
                    if (confirmacion == null) {
                        System.out.println("Venta realizada");

                    } else {
                        System.out.println(confirmacion);
                    }
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                break;

            case 2:
                salida = new DataOutputStream(cliente.getOutputStream());
                salida.writeUTF("caja;");
                ObjectInputStream objetoRecibo;
                objetoRecibo = new ObjectInputStream(cliente.getInputStream());
                Object total = null;
                try {
                    total = objetoRecibo.readObject();
                    if (total == null) {
                        System.out.println("Hoy no se ha vendido nada");

                    } else {
                        System.out.println(total);
                    }
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                break;

            case 3:
                salida = new DataOutputStream(cliente.getOutputStream());
                salida.writeUTF("exit;");
                ObjectInputStream mensajeRecibo;
                mensajeRecibo = new ObjectInputStream(cliente.getInputStream());
                Object adios = null;
                try {
                    adios = mensajeRecibo.readObject();
                    if (adios == null) {
                        System.out.println("Fallo en el cerrado de sesión, consulta al servicio técnico");

                    } else {
                        System.out.println(adios);
                        logueado=false;
                        exit=true;
                    }
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                break;
        }
    }
}

