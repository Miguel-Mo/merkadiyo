package modelos;

import java.io.Serializable;
import java.util.Date;

/**
 * Modelo de empleado, utilizo el tostring para el logueo
 */
public class Empleado implements Serializable {

    int id;
    Date ultimaSesion;
    Date fecha_contratacion;

    public Empleado(int id, Date ultimaSesion,Date fecha_contratacion){
        this.id=id;
        this.ultimaSesion=ultimaSesion;
        this.fecha_contratacion=fecha_contratacion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getUltimaSesion() {
        return ultimaSesion;
    }

    public void setUltimaSesion(Date ultimaSesion) {
        this.ultimaSesion = ultimaSesion;
    }

    public Date getFecha_contratacion() {
        return fecha_contratacion;
    }

    public void setFecha_contratacion(Date fecha_contratacion) {
        this.fecha_contratacion = fecha_contratacion;
    }

    @Override
    public String toString() {
        return  "id=" + id +", ultimaSesion=" + ultimaSesion + ", fecha_contratacion=" + fecha_contratacion ;
    }
}
