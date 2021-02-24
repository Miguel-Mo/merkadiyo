package DAO;

import modelos.Empleado;

import java.sql.*;
import java.time.LocalDate;


public class EmpleadoDAO extends AbstractDAO{

    LocalDate date =LocalDate.now();

    public EmpleadoDAO() {
       conectar();
    }

    /**
     * comprobamos que el empleado existe
     * @param id
     * @return devolvemos true si es que si false en caso contrario
     */
    public boolean comprobadorEmpleado(int id){
        try {
            rs = st.executeQuery("SELECT * FROM empleado WHERE id_empleado= " + id);
            if(rs.next()==false){
                return false;
            }else{
                return true;
            }
        } catch (SQLException ex) {
            System.out.println("Error en la consulta SQL");
            return false;
        }

    }

    /**
     * Cogemos el empleado por la id
     * @param id
     * @return devuelve un objeto de tipo empleado con la info del empleado que necesitamos
     */
    public Empleado getEmpleadoById(int id) {

        Empleado empleado=null;
        try {
            rs = st.executeQuery("SELECT * FROM empleado WHERE `id_empleado` = " + id);
            while(rs.next()) {
               empleado=new Empleado( rs.getInt(1), rs.getDate(2),rs.getDate(3));

            }
        }catch (SQLException ex) {
            System.out.println("Error en la consulta SQL");
        }
        return empleado;
    }


    /**
     * Método para updatear la fecha de última sesión del empleado
     * @param id
     */
    public void updateUltimaSesion(int id){
        try {
            System.out.println(date);
            st.executeUpdate("UPDATE empleado SET ultima_sesion='"+  date+ "' WHERE id_empleado="+id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
