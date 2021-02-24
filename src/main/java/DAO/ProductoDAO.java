package DAO;


import java.sql.*;


public class ProductoDAO extends AbstractDAO {

    public ProductoDAO() {
        conectar();
    }


    /**
     * Metodo para saber el stock de u producto
     * @param idProduct
     * @return un int con el numero de stock de ese producto
     */
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

    /**
     * metodo para updatear la cantidad de stock de un producto
     * @param id
     * @param amount
     */
    public void updateProduct(int id, int amount) {
        try {
            st.executeUpdate("UPDATE producto SET cantidad_stock = cantidad_stock -" + amount + " WHERE idproducto = " + id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Método para obtener el nombre de un producto
     * @param id
     * @return string con el nombre del producto
     */
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


    /**
     * Obtenemos el precio de venta total de un producto
     * @param idProducto
     * @return float con el precio de venta total de un producto
     */
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

    /**
     * Obtenemos el precio de proveedor total de un producto
     * @param idProducto
     * @return float con el precio de proveedor total de un producto
     */
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

    /**
     * Cuenta cuantos productos diferentes hay en la bbdd
     * @return int con el numero de productos diferentes que hay en la bbdd
     */
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
