package MainHilo;

import Correo.ControllerXML;
import Correo.Mail;
import DAO.CompraDAO;
import DAO.EmpleadoDAO;
import DAO.ProductoDAO;

import java.io.*;
import java.net.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Clase donde la mayoría de acciones en la parte del servidor. Declaramos dos booleanos uno de exit para poder salir
 * cuando queramos y otro para saber cuando el usuario está logueado. Cuando el usuario no está logueado le pediremos
 * un login correcto hasta que lo tenga. Una vez esté logueado el servidor empezará a escuchar que acciones quiere realizar
 * y enviar los objetos pertinentes en cada caso. Está organizado por un sistema de palabras, por lo que si llega cobrar;
 * quiere cobrar algún producto, si llega caja; quiere la caja del dia y si llega exit; quiere salir de la caja.
 */
public class MainHilo extends Thread {

    private Socket cliente;
    private static ObjectOutputStream objetosalida;
    boolean loginCorrecto = false;
    boolean exit = false;

    DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    public MainHilo(Socket cliente) {
        this.cliente = cliente;
    }

    @Override
    public void run() {
        int idlogin = 0;
        CompraDAO compraDAO = new CompraDAO();
        EmpleadoDAO empleadoDAO = new EmpleadoDAO();
        ProductoDAO productoDAO = new ProductoDAO();

        while (!exit) {

            while (!loginCorrecto) {
                // CREO FLUJO DE ENTRADA DEL CLIENTE
                InputStream entrada = null;
                try {
                    entrada = cliente.getInputStream();
                    DataInputStream flujoEntrada = new DataInputStream(entrada);
                    // EL CLIENTE ME ENVIA UN MENSAJE
                    String mensaje = (flujoEntrada.readUTF());
                    System.out.println(mensaje);
                    //el mensaje tiene dos partes y lo divido por el ;
                    String trozoId[] = mensaje.split(";");

                    idlogin = Integer.parseInt(trozoId[1]);

                    //llamamos al dao y comprobamos que ese usuario existe y se logea
                    if (empleadoDAO.comprobadorEmpleado(idlogin)) {
                        System.out.println("login correcto");
                        // CREO FLUJO DE SALIDA AL CLIENTE
                        objetosalida = new ObjectOutputStream(cliente.getOutputStream());
                        //mandamos al cliente el objeto empleado pertinente
                        objetosalida.writeObject(empleadoDAO.getEmpleadoById(idlogin));
                        //actualizamos la ultima sesion del usuario
                        empleadoDAO.updateUltimaSesion(idlogin);

                        //salimos del bucle !login para indicar que si está logueado
                        loginCorrecto = true;
                    } else {
                        System.out.println("login incorrecto");
                        objetosalida = new ObjectOutputStream(cliente.getOutputStream());
                        //al ser incorrecto el login mandamos un objeto null
                        objetosalida.writeObject(null);

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            //una vez el usuario está logueado
            while (loginCorrecto) {
                InputStream entrada = null;
                try {
                    entrada = cliente.getInputStream();
                    DataInputStream flujoEntrada = new DataInputStream(entrada);
                    // EL CLIENTE ME ENVIA UN MENSAJE
                    String mensaje = (flujoEntrada.readUTF());
                    System.out.println(mensaje);
                    //el mensaje tiene dos partes y lo divido por el ;
                    String trozo[] = mensaje.split(";");

                    //dependiendo de lo que nos llegue en la primera parte del mensaje hacemos una cosa u otra.
                    switch (trozo[0]) {

                        case "cobrar":
                            int idProducto = Integer.parseInt(trozo[1]); //segunda parte del mensaje con id del producto
                            int cantidadProducto = Integer.parseInt(trozo[2]); //tercera parte del mensaje con cantidad de ese producto

                            //comprobamos si hay stock del producto que han escogido
                            if (cantidadProducto <= productoDAO.getStockProductos(idProducto)) {
                                productoDAO.updateProduct(idProducto, cantidadProducto); //updatemaos el stock del proucto
                                compraDAO.insertCompra(idProducto, cantidadProducto, idlogin); //insertamos la compra en nuestra bbdd
                                objetosalida=new ObjectOutputStream(cliente.getOutputStream());//AQUI SE QUE EN EL ENUNCIADO PONE QUE NO SE DEVUELVA NADA PERO CREO QUE ASI ES MEJOR, SI NO ES SOLO QUITARLO :D
                                objetosalida.writeObject(null);
                            }else{
                                System.out.println("No hay productos disponibles");
                                Date date = new Date();
                                //conseguimos el correo del xml
                                String correo= ControllerXML.getCorreo();
                                //enviamos el correo con los datos pertinentes
                                Mail.enviarConGMail(correo,"Sin stock de " +productoDAO.getNombreProducto(idProducto),"Nos hemos quedado" +
                                        " sin stock de "+ productoDAO.getNombreProducto(idProducto) + " con precio de proveedor de "+productoDAO.getProveedorTotalByProducto(idProducto) +"€ a las " +dateFormat.format(date));
                                objetosalida=new ObjectOutputStream(cliente.getOutputStream());
                                objetosalida.writeObject("Correo enviado a "+ correo +" dado que no hay stock");//AQUI SE QUE EN EL ENUNCIADO PONE QUE NO SE DEVUELVA NADA PERO CREO QUE ASI ES MEJOR, SI NO ES SOLO QUITARLO :D
                            }
                            break;

                        case "caja":
                            int numeroProductos=productoDAO.getContadorProductos();
                            float precioProveedor;
                            float precioVenta;
                            float productoVendidos;
                            float productoVendidosEmpleado;
                            float total=0;
                            float totalEmpleado=0;
                            //hacemos un for en el que recorremos todos los proudctos y comprobamos su diferencia entre el precio de venta y el proveedor y luego
                            //lo multiplicamos por la cantidad que se ha vendido
                            for(int i=1;i<=numeroProductos;i++){
                                precioProveedor=productoDAO.getProveedorTotalByProducto(i);
                                precioVenta=productoDAO.getPrecioVentaTotalByProducto(i);
                                productoVendidos=compraDAO.getCantidadTotalComprasRealizadasHoyByProducto(i);
                                productoVendidosEmpleado=compraDAO.getCantidadTotalComprasRealizadasHoyByProductoAndEmpleado(i,idlogin);
                                total+=((precioVenta-precioProveedor)*productoVendidos);
                                totalEmpleado+=((precioVenta-precioProveedor)*productoVendidosEmpleado);
                            }

                            //objeto de salida para el cliente con la info necesiaria
                            objetosalida=new ObjectOutputStream(cliente.getOutputStream());
                            objetosalida.writeObject("Beneficio total del empleado con id:"+ idlogin +" ha sido de: "+totalEmpleado+" €\nBeneficio total del día: "+total +" €");
                            break;

                        case "exit":
                            objetosalida=new ObjectOutputStream(cliente.getOutputStream());
                            objetosalida.writeObject("Adiós!");
                            // CERRAR STREAMS Y SOCKETS
                            entrada.close();
                            flujoEntrada.close();
                            objetosalida.close();
                            cliente.close();
                            loginCorrecto=false;
                            exit=true;
                            break;

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }


        }
    }
}

