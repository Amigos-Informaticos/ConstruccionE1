package Tests;

import Models.Organizacion;
import org.junit.Test;

import java.sql.SQLException;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class TestOrganizacion {

    public Organizacion crearOrganizacion() {
        Organizacion organizacion = new Organizacion();
        organizacion.setNombre("Efra y asociados");
        organizacion.setSector("Industrial");
        organizacion.setDireccion(
                "Jardines",
                "1198",
                "Centro",
                "Xalapa"
        );
        organizacion.setTelefono("2291763687");
        return organizacion;
    }

    public Organizacion crearNuevaOrganizacion() {
        Organizacion organizacion = new Organizacion();
        organizacion.setNombre("Dafne y asociados");
        organizacion.setSector("Turístico");
        organizacion.setDireccion(
                "Revillagigedo",
                "3928",
                "Camaron",
                "Veracruz"
        );
        organizacion.setTelefono("2282763633");
        return organizacion;
    }

    @Test
    public void registrarOrganizacion(){
        try {
            assertTrue(crearOrganizacion().registrar());
        } catch (SQLException throwables) {
            System.out.println(throwables);
        }
    }

    @Test
    public void verOrganizacion(){
        try {
            Organizacion organizacion = Organizacion.obtenerPorNombre("Efra y asociados");
            System.out.println(organizacion.toString());
            assertNotNull(organizacion);
        } catch (SQLException throwables) {
            System.out.println(throwables);
        }
    }

    @Test
    public void actualizarOrganizacion(){
        try {
            assertTrue(crearNuevaOrganizacion().actualizar("Efra y asociados"));
        } catch (SQLException throwables) {
            System.out.println(throwables);
        }
    }

    @Test
    public void verNuevaOrganizacion(){
        try {
            Organizacion organizacion = Organizacion.obtenerPorNombre("Dafne y asociados");
            System.out.println(organizacion.toString());
            assertNotNull(organizacion);
        } catch (SQLException throwables) {
            System.out.println(throwables);
        }
    }

    @Test
    public void eliminarOrganizacion(){
        try {
            assertTrue(crearNuevaOrganizacion().eliminar());
        } catch (SQLException throwables) {
            System.out.println(throwables);
        }
    }
}
