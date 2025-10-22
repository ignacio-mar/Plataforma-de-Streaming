
public class Main  {
    public static void main(String[] args) {
   var conexion = Conexion.getCon();
        if (conexion !=null){
            System.out.println("Conectado correctamente");
        }else {
            System.out.println("Error en la conexión");
        }
    }

    }

