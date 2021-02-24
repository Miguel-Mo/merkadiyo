package DAO;

import modelos.Empleado;

import java.sql.*;
import java.time.LocalDate;


public class EmpleadoDAO extends AbstractDAO{

    LocalDate date =LocalDate.now();

    public EmpleadoDAO() {
       conectar();
    }

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

    public void updateUltimaSesion(int id){
        try {
            System.out.println(date);
            st.executeUpdate("UPDATE empleado SET ultima_sesion='"+  date+ "' WHERE id_empleado="+id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
