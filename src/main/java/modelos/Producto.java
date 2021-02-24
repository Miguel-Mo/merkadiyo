package modelos;

public class Producto {

    int id;
    String nombreProducto;
    float precioVenta;
    float precioProveedor;
    float cantidadStock;


    public Producto(int id, String nombreProducto, float precioVenta, float precioProveedor, float cantidadStock) {
        this.id = id;
        this.nombreProducto = nombreProducto;
        this.precioVenta = precioVenta;
        this.precioProveedor = precioProveedor;
        this.cantidadStock = cantidadStock;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public float getPrecioVenta() {
        return precioVenta;
    }

    public void setPrecioVenta(float precioVenta) {
        this.precioVenta = precioVenta;
    }

    public float getPrecioProveedor() {
        return precioProveedor;
    }

    public void setPrecioProveedor(float precioProveedor) {
        this.precioProveedor = precioProveedor;
    }

    public float getCantidadStock() {
        return cantidadStock;
    }

    public void setCantidadStock(float cantidadStock) {
        this.cantidadStock = cantidadStock;
    }

    @Override
    public String toString() {
        return "Producto{" +
                "id=" + id +
                ", nombreProducto='" + nombreProducto + '\'' +
                ", precioVenta=" + precioVenta +
                ", precioProveedor=" + precioProveedor +
                ", cantidadStock=" + cantidadStock +
                '}';
    }
}
