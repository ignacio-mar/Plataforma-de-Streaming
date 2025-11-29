import dao.DatosPersonalesDAO;
import dao.PeliculasDAO;
import dao.ReseñasDAO;
import dao.UsuariosDAO;
import db.Conexion;
import service.PersonasService;
import service.UsuariosService;
import service.PeliculasService;
import service.ReseñasService;
import java.util.Scanner;
import ui.Menu;

public class Main {

    public static void main(String[] args) {
    
        Scanner sc = null; // Inicializar fuera del try
        try {
            
            // Cear DAOs usando Factory
            DatosPersonalesDAO datosPersonalesDAO = FactoryDAO.getDatosPersonalesDAO();
            UsuariosDAO usuariosDAO = FactoryDAO.getUsuariosDAO(Conexion.getCon(), datosPersonalesDAO);
            PeliculasDAO peliculasDAO = FactoryDAO.getPeliculasDAO();
            ReseñasDAO reseñasDAO = FactoryDAO.getReseñasDAO();
            
            // Crear Services pasando los DAOs
            PersonasService personasService = new PersonasService(datosPersonalesDAO);
            UsuariosService usuariosService = new UsuariosService(usuariosDAO, datosPersonalesDAO);
            PeliculasService peliculasService = new PeliculasService(peliculasDAO);
            ReseñasService reseñasService = new ReseñasService(reseñasDAO, usuariosDAO, peliculasDAO);
            
            // Crear menú y ejecutar
            sc = new Scanner(System.in);
            Menu menu = new Menu(sc, personasService, usuariosService, peliculasService, reseñasService);
            menu.iniciar();
        



        } catch (Exception e) {
            System.err.println("Error al iniciar la aplicación: " + e.getMessage());
            e.printStackTrace();
        }finally { 
            // Cierra el Scanner (si fue inicializado)
            if (sc != null) {
                sc.close();
            }
            Conexion.close();
        }
    }
}


