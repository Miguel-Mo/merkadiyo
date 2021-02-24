package DAO;

import java.sql.*;
import java.time.LocalDate;

public class CompraDAO extends AbstractDAO {

   LocalDate date =LocalDate.now();

    public CompraDAO() {
      conectar();
    }


    /**
     * Método para insertar la nueva compra en la tabla compra  y en la tabla intermedia
     * @param idProducto
     * @param cantidadProducto
     * @param idEmpleado
     */
    public void insertCompra(int idProducto, int cantidadProducto, int idEmpleado) {
        try {
            st.executeUpdate("INSERT INTO compra (`fecha_compra`, `empleado_id_empleado`) VALUES ('" + date + "', "+ idEmpleado + ")",Statement.RETURN_GENERATED_KEYS);
            rs=st.getGeneratedKeys();//recuperamos la key autogenerada en compra
            rs.next();
            int idCompra=rs.getInt(1);
            st.executeUpdate("INSERT INTO cantidad_producto_compra (`producto_idproducto`, `compra_id_compra`, `cantidad`) VALUES ('" + idProducto + "', "+ idCompra+ ", "+ cantidadProducto  + ")");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /**
     * Método para saber la cantidad de compras que se ha realizado de un solo producto en el dia de hoy
     * @param idProducto
     * @return
     */
    public float getCantidadTotalComprasRealizadasHoyByProducto(int idProducto) {
        float precioCantidadTotal=0;
        try {
            rs=st.executeQuery("SELECT SUM(cantidad_producto_compra.cantidad) FROM cantidad_producto_compra LEFT JOIN compra ON cantidad_producto_compra.compra_id_compra=compra.id_compra WHERE compra.fecha_compra='"+date+"' AND cantidad_producto_compra.producto_idproducto="+idProducto);
            rs.next();
            precioCantidadTotal=rs.getInt(1);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return precioCantidadTotal;
    }


    /**
     * Método para saber la cantidad de compras que se ha realizado de un solo producto en el dia de hoy y por empleado
     * @param idProducto
     * @param idEmpleado
     * @return
     */
    public float getCantidadTotalComprasRealizadasHoyByProductoAndEmpleado(int idProducto,int idEmpleado){
        float precioCantidadTotal=0;
        try {
            rs=st.executeQuery("SELECT SUM(cantidad_producto_compra.cantidad) FROM cantidad_producto_compra LEFT JOIN compra ON cantidad_producto_compra.compra_id_compra=compra.id_compra WHERE compra.fecha_compra='"+date+"' AND cantidad_producto_compra.producto_idproducto="+idProducto+
                    " AND cantidad_producto_compra.compra_id_compra IN(SELECT compra.id_compra FROM compra WHERE compra.empleado_id_empleado="+idEmpleado+")");
            rs.next();
            precioCantidadTotal=rs.getInt(1);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return precioCantidadTotal;
    }




}
