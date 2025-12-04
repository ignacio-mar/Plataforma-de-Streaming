package Main_Factory;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import db.Conexion;
import dao.DatosPersonalesDAO;
import dao.PeliculasDAO;
import dao.ReseñasDAO;
import dao.UsuariosDAO;
import service.PersonasService;
import service.UsuariosService;
import service.PeliculasService;
import service.ReseñasService;
import view.login.LoginView;
import controllers.LoginController;

public class Main {

    public static void main(String[] args) {
        
        try {
            Conexion.getCon(); 
            
            DatosPersonalesDAO dpDao = FactoryDAO.getDatosPersonalesDAO();
            UsuariosDAO usuariosDAO = FactoryDAO.getUsuariosDAO(Conexion.getCon(), dpDao);
            PeliculasDAO peliculasDAO = FactoryDAO.getPeliculasDAO();
            ReseñasDAO reseñasDAO = FactoryDAO.getReseñasDAO();
            
            PersonasService personasService = new PersonasService(dpDao);
            UsuariosService usuariosService = new UsuariosService(usuariosDAO, dpDao);
            PeliculasService peliculasService = new PeliculasService(peliculasDAO);
            ReseñasService reseñasService = new ReseñasService(reseñasDAO, usuariosDAO, peliculasDAO);
            
            SwingUtilities.invokeLater(() -> {
                try {
                    LoginView vistaLogin = new LoginView();
                    
                    new LoginController(vistaLogin, 
                        usuariosService, 
                        personasService,
                        peliculasService,
                        reseñasService
                    );

                    vistaLogin.setVisible(true);

                } catch (Exception e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error al iniciar la interfaz: " + e.getMessage());
                }
            });

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, 
                "ERROR FATAL DE INICIO: No se pudo conectar la aplicación a la BD. " + e.getMessage(), 
                "Error Crítico", 
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        } finally {
             Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                Conexion.close();
             }));
        }
    }
}