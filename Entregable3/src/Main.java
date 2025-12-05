import controllers.LoginController;
import dao.DatosPersonalesDAO;
import dao.PeliculasDAO;
import dao.ReseñasDAO;
import dao.UsuariosDAO;
import db.Conexion;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import service.PeliculasService;
import service.PersonasService;
import service.ReseñasService;
import service.UsuariosService;
import ui.login.LoginView;

public class Main {

    
    public static void main(String[] args) {

        try {
            // Inicializar conexión a la BD
            Conexion.getCon();

            // DAOs
            DatosPersonalesDAO dpDao = FactoryDAO.getDatosPersonalesDAO();
            UsuariosDAO usuariosDAO = FactoryDAO.getUsuariosDAO(Conexion.getCon(), dpDao);
            PeliculasDAO peliculasDAO = FactoryDAO.getPeliculasDAO();
            ReseñasDAO reseñasDAO = FactoryDAO.getReseñasDAO();

            // Services
            PersonasService personasService = new PersonasService(dpDao);
            UsuariosService usuariosService = new UsuariosService(usuariosDAO, dpDao); // ← IMPORTANTE
            PeliculasService peliculasService = new PeliculasService(peliculasDAO);
            ReseñasService reseñasService = new ReseñasService(reseñasDAO, usuariosDAO, peliculasDAO);

            // Lanzar UI
            SwingUtilities.invokeLater(() -> {
                try {
                    LoginView vistaLogin = new LoginView();

                    new LoginController(
                        vistaLogin,
                        usuariosService,
                        personasService,
                        peliculasService,
                        reseñasService
                    );

                    vistaLogin.setVisible(true);

                } catch (Exception e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(
                        null,
                        "Error al iniciar la interfaz: " + e.getMessage()
                    );
                }
            });

        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                null,
                "ERROR FATAL DE INICIO: No se pudo conectar la aplicación a la BD. " + e.getMessage(),
                "Error Crítico",
                JOptionPane.ERROR_MESSAGE
            );
            e.printStackTrace();
        } finally {
            Runtime.getRuntime().addShutdownHook(new Thread(Conexion::close));
        }
    }
}
