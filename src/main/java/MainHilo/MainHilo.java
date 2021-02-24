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
                    String trozoId[] = mensaje.split(";");

                    idlogin = Integer.parseInt(trozoId[1]);

                    if (empleadoDAO.comprobadorEmpleado(idlogin)) {
                        System.out.println("login correcto");
                        // CREO FLUJO DE SALIDA AL CLIENTE
                        objetosalida = new ObjectOutputStream(cliente.getOutputStream());
                        objetosalida.writeObject(empleadoDAO.getEmpleadoById(idlogin));
                        empleadoDAO.updateUltimaSesion(idlogin);

                        loginCorrecto = true;
                    } else {
                        System.out.println("login incorrecto");
                        objetosalida = new ObjectOutputStream(cliente.getOutputStream());
                        objetosalida.writeObject(null);

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            while (loginCorrecto) {
                InputStream entrada = null;
                try {
                    entrada = cliente.getInputStream();
                    DataInputStream flujoEntrada = new DataInputStream(entrada);
                    // EL CLIENTE ME ENVIA UN MENSAJE
                    String mensaje = (flujoEntrada.readUTF());
                    System.out.println(mensaje);

                    String trozo[] = mensaje.split(";");

                    switch (trozo[0]) {

                        case "cobrar":
                            int idProducto = Integer.parseInt(trozo[1]);
                            int cantidadProducto = Integer.parseInt(trozo[2]);

                            //AQUI SE QUE EN EL ENUNCIADO PONE QUE NO SE DEVUELVA NADA PERO CREO QUE ASI ES MEJOR, SI NO ES SOLO QUITARLO :D
                            if (cantidadProducto <= productoDAO.getStockProductos(idProducto)) {
                                productoDAO.updateProduct(idProducto, cantidadProducto);
                                compraDAO.insertCompra(idProducto, cantidadProducto, idlogin);
                                objetosalida=new ObjectOutputStream(cliente.getOutputStream());
                                objetosalida.writeObject(null);
                            }else{
                                System.out.println("No hay productos disponibles");
                                Date date = new Date();
                                String correo= ControllerXML.getCorreo();
                                Mail.enviarConGMail(correo,"Sin stock de " +productoDAO.getNombreProducto(idProducto),"Nos hemos quedado" +
                                        " sin stock de "+ productoDAO.getNombreProducto(idProducto) + " con precio de proveedor de "+productoDAO.getProveedorTotalByProducto(idProducto) +"€ a las " +dateFormat.format(date));
                                objetosalida=new ObjectOutputStream(cliente.getOutputStream());
                                objetosalida.writeObject("Correo enviado a "+ correo +" dado que no hay stock");
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
                            for(int i=1;i<=numeroProductos;i++){
                                precioProveedor=productoDAO.getProveedorTotalByProducto(i);
                                precioVenta=productoDAO.getPrecioVentaTotalByProducto(i);
                                productoVendidos=compraDAO.getCantidadTotalComprasRealizadasHoyByProducto(i);
                                productoVendidosEmpleado=compraDAO.getCantidadTotalComprasRealizadasHoyByProductoAndEmpleado(i,idlogin);
                                total+=((precioVenta-precioProveedor)*productoVendidos);
                                totalEmpleado+=((precioVenta-precioProveedor)*productoVendidosEmpleado);
                            }

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
                            //flujoSalida.close();
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
// fin de Ejemplo1Servidor
