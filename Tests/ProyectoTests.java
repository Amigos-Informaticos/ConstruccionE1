package Tests;

import DAO.DAOProyecto;
import Models.Organizacion;
import Models.Proyecto;
import Models.ResponsableProyecto;
import org.junit.Test;

import java.sql.SQLException;

import static org.junit.Assert.assertTrue;

public class ProyectoTests {
    private Proyecto proyecto;

    public void instanciarProyecto() {
        proyecto = new Proyecto();
        Organizacion organizacion = new Organizacion();
        organizacion.setNombre("Efra y asociados");
        organizacion.setSector("Industrial");
        organizacion.setTelefono("2282105545");
        organizacion.setDireccion("Tulipanes","302","Jardines","Xalapa");
        ResponsableProyecto responsable = new ResponsableProyecto();
        responsable.setNombre("Gerardo");
        responsable.setApellido("Rendon");
        responsable.setEmail("yiragod@gmail.com");
        responsable.setPosicion("Guia");
        responsable.setOrganizacion(organizacion);
        proyecto.setNombre("Explorar Cuevas y ríos");
        proyecto.setDescripcion("Acompañar a Gerardo en una aventura");
        proyecto.setObjetivoGeneral("Encontrar bichos de colores");
        proyecto.setObjetivoMediato("Conocer la fauna de Xalapa");
        proyecto.setObjetivoInmediato("Salir de casa");
        proyecto.setMetodologia("SCRUM");
        proyecto.setRecursos("Unos tennis y una mochila");
        proyecto.setResponsabilidades("Pararse temprano");
        proyecto.setCapacidad(4);
        proyecto.setOrganization(organizacion);
        proyecto.setPeriodo("FEB-JUL");
        proyecto.setArea("Estudiantil");
        proyecto.setFechaInicio("2021-10-11");
        proyecto.setFechaFin("2021-10-13");
        proyecto.setResponsable(responsable);
    }

    @Test
    public void registrarProyecto(){
        instanciarProyecto();
        try {
            assertTrue(proyecto.registrar());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    @Test
    public  void actualizarProyecto() {
        instanciarProyecto();
        String nuevaDescripcion = "Nueva Descripción actualizada";
        proyecto.setDescripcion(nuevaDescripcion);
        try {
            String idProyecto = DAOProyecto.getIdConNombre(this.proyecto.getNombre());
            boolean actualizado = proyecto.actualizar(idProyecto);
            assertTrue(actualizado);

        } catch (SQLException throwables) {
            System.out.println(throwables.getMessage());
        }
    }
    @Test
    public  void eliminarProyecto(){
        instanciarProyecto();
        try {
            boolean eliminado = proyecto.eliminarProyecto();
            assertTrue(eliminado);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
