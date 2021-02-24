package modelos;

import java.util.Date;

public class Compra {

    int id;
    Date fecha;
    int cantidadProducto;
    int idProducto;
    int idEmpleado;

    public Compra(int id, Date fecha, int cantidadProducto, int idProducto, int idEmpleado) {
        this.id = id;
        this.fecha = fecha;
        this.cantidadProducto = cantidadProducto;
        this.idProducto = idProducto;
        this.idEmpleado = idEmpleado;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public int getCantidadProducto() {
        return cantidadProducto;
    }

    public void setCantidadProducto(int cantidadProducto) {
        this.cantidadProducto = cantidadProducto;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public int getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(int idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    @Override
    public String toString() {
        return "Compra{" +
                "id=" + id +
                ", fecha=" + fecha +
                ", cantidadProducto=" + cantidadProducto +
                ", idProducto=" + idProducto +
                ", idEmpleado=" + idEmpleado +
                '}';
    }
}
