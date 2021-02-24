package DAO;


import java.sql.*;


public class ProductoDAO extends AbstractDAO {

    public ProductoDAO() {
        conectar();
    }


    public int getStockProductos(int idProduct) {
        int stockProduct = 0;
        try {
            rs = st.executeQuery("SELECT cantidad_stock FROM producto WHERE idproducto = " + idProduct);
            while (rs.next()) {
                stockProduct = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return stockProduct;
    }

    public void updateProduct(int id, int amount) {
        try {
            st.executeUpdate("UPDATE producto SET cantidad_stock = cantidad_stock -" + amount + " WHERE idproducto = " + id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String getNombreProducto(int id) {
        String nombreProducto = "";
        try {
            rs = st.executeQuery("SELECT nombre_producto FROM producto WHERE idproducto = " + id);
            while (rs.next()) {
                nombreProducto = rs.getString(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return nombreProducto;
    }

    public float getPrecioVentaTotalByProducto(int idProducto) {
        float precioVentaTotal = 0;
        try {
            rs = st.executeQuery("SELECT producto.precio_venta FROM producto WHERE producto.idproducto=" + idProducto);

            rs.next();
            precioVentaTotal = rs.getFloat(1);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return precioVentaTotal;
    }

    public float getProveedorTotalByProducto(int idProducto) {
        float precioProveedorTotal = 0;
        try {
            String consulta = "SELECT producto.precio_proveedor FROM producto WHERE producto.idproducto=" + idProducto;
            rs = st.executeQuery(consulta);
            rs.next();
            precioProveedorTotal = rs.getFloat(1);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return precioProveedorTotal;
    }

    public int getContadorProductos() {
        int contador = 0;
        try {
            rs = st.executeQuery("SELECT COUNT(producto.idproducto) FROM producto");
            rs.next();
            contador = rs.getInt(1);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return contador;
    }


}
