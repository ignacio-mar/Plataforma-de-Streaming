
public class App {
    public static void main(String[] args) {
   var conexion = Conexion.getConection();
        if (conexion !=null){
            System.out.println("Conectado correctamente");
        }else {
            System.out.println("Error en la conexión");
        }
    }

    }

