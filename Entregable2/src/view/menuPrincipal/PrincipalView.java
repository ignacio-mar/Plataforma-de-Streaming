package view.menuPrincipal;

import controller.PrincipalController;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.Pelicula;

import java.util.List;

public class PrincipalView extends Application {
    private PrincipalController controller;
    private Stage primaryStage;
    private BorderPane rootPane;
    private TableView<Pelicula> tablaPeliculas;
    private Label labelCargando;
    private VBox panelCarga;

    @Override
    public void start(Stage stage) {
        this.primaryStage = stage;
        this.controller = new PrincipalController();

        primaryStage.setTitle("Plataforma de Streaming - Bienvenida");
        primaryStage.setWidth(1000);
        primaryStage.setHeight(700);

        mostrarPantallaCarga();
        cargarPeliculasEnBackground();

        primaryStage.show();
    }

    private void mostrarPantallaCarga() {
        panelCarga = new VBox(20);
        panelCarga.setAlignment(Pos.CENTER);
        panelCarga.setStyle("-fx-background-color: #f0f0f0;");

        // Título
        Label titulo = new Label("Bienvenido a la plataforma de streaming");
        titulo.setStyle("-fx-font-size: 24; -fx-font-weight: bold;");

        // Imagen de carga
        ImageView imagenCarga = new ImageView();
        imagenCarga.setPrefWidth(100);
        imagenCarga.setPrefHeight(100);
        try {
            imagenCarga.setImage(new javafx.scene.image.Image("file:resources/imagenes/imagen-cargando.png"));
        } catch (Exception e) {
            // Si no existe la imagen, mostrar un círculo
            Circle circulo = new Circle(50);
            circulo.setStyle("-fx-fill: #3498db;");
        }

        Label labelCargandoTexto = new Label("Cargando imagen");
        labelCargandoTexto.setStyle("-fx-font-size: 14;");

        labelCargando = new Label("Un momento, por favor...");
        labelCargando.setStyle("-fx-font-size: 12;");

        panelCarga.getChildren().addAll(titulo, imagenCarga, labelCargandoTexto, labelCargando);

        Scene scene = new Scene(panelCarga);
        primaryStage.setScene(scene);
    }

    private void cargarPeliculasEnBackground() {
        new Thread(() -> {
            try {
                controller.cargarPeliculasDesdeCSV();
                Platform.runLater(this::mostrarPantallaPrincipal);
            } catch (Exception e) {
                e.printStackTrace();
                Platform.runLater(() -> mostrarError("Error al cargar películas: " + e.getMessage()));
            }
        }).start();
    }

    private void mostrarPantallaPrincipal() {
        rootPane = new BorderPane();
        rootPane.setStyle("-fx-background-color: #ffffff;");

        // Barra superior con buscador y botón cerrar sesión
        HBox barraSuper = crearBarraSuper();
        rootPane.setTop(barraSuper);

        // Contenido principal
        VBox contenido = new VBox(15);
        contenido.setPadding(new Insets(20));

        Label mensajePrincipal = new Label("¿Viste alguna de estas películas? Haznos saber qué te pareció dejando una reseña");
        mensajePrincipal.setStyle("-fx-font-size: 14; -fx-text-fill: #333;");

        tablaPeliculas = crearTablaPeliculas();

        contenido.getChildren().addAll(mensajePrincipal, tablaPeliculas);
        VBox.setVgrow(tablaPeliculas, javafx.scene.layout.Priority.ALWAYS);

        rootPane.setCenter(contenido);

        Scene scene = new Scene(rootPane);
        primaryStage.setScene(scene);

        cargarDatosEnTabla();
    }

    private HBox crearBarraSuper() {
        HBox barra = new HBox(10);
        barra.setPadding(new Insets(10, 20, 10, 20));
        barra.setStyle("-fx-background-color: #f8f9fa; -fx-border-color: #ddd; -fx-border-width: 0 0 1 0;");
        barra.setAlignment(Pos.CENTER_RIGHT);

        // Buscador (derecha)
        TextField textoBusqueda = new TextField();
        textoBusqueda.setPromptText("Buscar película...");
        textoBusqueda.setPrefWidth(250);

        Button btnBuscar = new Button("🔍");
        btnBuscar.setStyle("-fx-font-size: 14; -fx-padding: 8;");
        btnBuscar.setOnAction(e -> abrirBuscador(textoBusqueda.getText()));

        HBox buscador = new HBox(5);
        buscador.setAlignment(Pos.CENTER_RIGHT);
        buscador.getChildren().addAll(textoBusqueda, btnBuscar);

        // Botón cerrar sesión (izquierda)
        Button btnCerrarSesion = new Button("Cerrar Sesión");
        btnCerrarSesion.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; -fx-font-size: 12; -fx-padding: 8 15;");
        btnCerrarSesion.setOnAction(e -> cerrarSesion());

        // Espacio flexible
        Region espacioFlexible = new Region();
        HBox.setHgrow(espacioFlexible, javafx.scene.layout.Priority.ALWAYS);

        barra.getChildren().addAll(btnCerrarSesion, espacioFlexible, buscador);

        return barra;
    }

    private TableView<Pelicula> crearTablaPeliculas() {
        TableView<Pelicula> tabla = new TableView<>();
        tabla.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // Columna Póster
        TableColumn<Pelicula, String> colPoster = new TableColumn<>("Póster");
        colPoster.setPrefWidth(100);
        colPoster.setCellValueFactory(new PropertyValueFactory<>("posterUrl"));
        colPoster.setCellFactory(col -> new TableCell<Pelicula, String>() {
            @Override
            protected void updateItem(String url, boolean empty) {
                super.updateItem(url, empty);
                if (empty || url == null) {
                    setGraphic(null);
                } else {
                    try {
                        ImageView img = new ImageView(new javafx.scene.image.Image(url));
                        img.setFitWidth(80);
                        img.setFitHeight(120);
                        setGraphic(img);
                    } catch (Exception e) {
                        setText("No disponible");
                    }
                }
            }
        });

        // Columna Título
        TableColumn<Pelicula, String> colTitulo = new TableColumn<>("Título");
        colTitulo.setCellValueFactory(new PropertyValueFactory<>("titulo"));

        // Columna Género
        TableColumn<Pelicula, String> colGenero = new TableColumn<>("Género");
        colGenero.setCellValueFactory(new PropertyValueFactory<>("genero"));

        // Columna Resumen
        TableColumn<Pelicula, String> colResumen = new TableColumn<>("Resumen");
        colResumen.setCellValueFactory(new PropertyValueFactory<>("resumen"));
        colResumen.setCellFactory(col -> new TableCell<Pelicula, String>() {
            @Override
            protected void updateItem(String resumen, boolean empty) {
                super.updateItem(resumen, empty);
                if (empty || resumen == null) {
                    setGraphic(null);
                } else {
                    VBox celda = new VBox();
                    Label labelResumen = new Label(resumen.length() > 50 ? resumen.substring(0, 50) + "..." : resumen);
                    labelResumen.setWrapText(true);
                    Button btnVerMas = new Button("Ver más");
                    btnVerMas.setStyle("-fx-font-size: 11;");
                    btnVerMas.setOnAction(e -> abrirResumen(getTableView().getItems().get(getIndex())));
                    celda.getChildren().addAll(labelResumen, btnVerMas);
                    setGraphic(celda);
                }
            }
        });

        // Columna Calificar
        TableColumn<Pelicula, Void> colCalificar = new TableColumn<>("Calificar");
        colCalificar.setCellFactory(col -> new TableCell<Pelicula, Void>() {
            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || getIndex() < 0) {
                    setGraphic(null);
                } else {
                    Button btnCalificar = new Button("⭐ Calificar");
                    btnCalificar.setStyle("-fx-font-size: 11;");
                    btnCalificar.setOnAction(e -> abrirCalificacion(getTableView().getItems().get(getIndex())));
                    setGraphic(btnCalificar);
                }
            }
        });

        tabla.getColumns().addAll(colPoster, colTitulo, colGenero, colResumen, colCalificar);

        return tabla;
    }

    private void cargarDatosEnTabla() {
        List<Pelicula> top10 = controller.obtenerTop10();
        tablaPeliculas.getItems().addAll(top10);
    }

    private void abrirResumen(Pelicula pelicula) {
        Stage ventanaResumen = new Stage();
        ventanaResumen.initModality(Modality.APPLICATION_MODAL);
        ventanaResumen.setTitle("Plataforma de Streaming - Información");
        ventanaResumen.setWidth(500);
        ventanaResumen.setHeight(400);

        VBox contenido = new VBox(15);
        contenido.setPadding(new Insets(20));

        Label titulo = new Label(pelicula.getTitulo());
        titulo.setStyle("-fx-font-size: 18; -fx-font-weight: bold;");

        Label año = new Label("Año: " + pelicula.getAño());
        año.setStyle("-fx-font-size: 12;");

        TextArea resumen = new TextArea(pelicula.getResumen());
        resumen.setWrapText(true);
        resumen.setEditable(false);
        resumen.setPrefHeight(200);

        Button btnContinuar = new Button("Continuar");
        btnContinuar.setStyle("-fx-padding: 8 20; -fx-font-size: 12;");
        btnContinuar.setOnAction(e -> ventanaResumen.close());

        contenido.getChildren().addAll(titulo, año, new Separator(), resumen, btnContinuar);

        Scene scene = new Scene(contenido);
        ventanaResumen.setScene(scene);
        ventanaResumen.showAndWait();
    }

    private void abrirCalificacion(Pelicula pelicula) {
        Stage ventanaCalificacion = new Stage();
        ventanaCalificacion.initModality(Modality.APPLICATION_MODAL);
        ventanaCalificacion.setTitle("Plataforma de Streaming - Calificar Películas");
        ventanaCalificacion.setWidth(500);
        ventanaCalificacion.setHeight(450);

        VBox contenido = new VBox(15);
        contenido.setPadding(new Insets(20));

        Label titulo = new Label("Título de la película");
        titulo.setStyle("-fx-font-size: 12; -fx-font-weight: bold;");
        TextField txtTitulo = new TextField(pelicula.getTitulo());
        txtTitulo.setEditable(false);

        Label labelCalificacion = new Label("Calificación (0-10 estrellas)");
        labelCalificacion.setStyle("-fx-font-size: 12; -fx-font-weight: bold;");
        Slider sliderCalificacion = new Slider(0, 10, 5);
        sliderCalificacion.setShowTickLabels(true);
        sliderCalificacion.setShowTickMarks(true);
        sliderCalificacion.setMajorTickUnit(1);

        Label labelComentario = new Label("Comentario");
        labelComentario.setStyle("-fx-font-size: 12; -fx-font-weight: bold;");
        TextArea txtComentario = new TextArea();
        txtComentario.setWrapText(true);
        txtComentario.setPrefHeight(150);

        Button btnGuardar = new Button("Guardar");
        btnGuardar.setStyle("-fx-padding: 8 20; -fx-font-size: 12;");
        btnGuardar.setOnAction(e -> {
            abrirConfirmacion(ventanaCalificacion);
        });

        contenido.getChildren().addAll(
                titulo, txtTitulo,
                labelCalificacion, sliderCalificacion,
                labelComentario, txtComentario,
                btnGuardar
        );

        Scene scene = new Scene(contenido);
        ventanaCalificacion.setScene(scene);
        ventanaCalificacion.showAndWait();
    }

    private void abrirConfirmacion(Stage ventanaCalificacion) {
        Stage ventanaConfirmacion = new Stage();
        ventanaConfirmacion.initModality(Modality.APPLICATION_MODAL);
        ventanaConfirmacion.setTitle("Plataforma de Streaming - Información");
        ventanaConfirmacion.setWidth(400);
        ventanaConfirmacion.setHeight(200);

        VBox contenido = new VBox(20);
        contenido.setAlignment(Pos.CENTER);
        contenido.setPadding(new Insets(20));

        Label mensaje = new Label("Se registró correctamente su calificación\nMuchas gracias");
        mensaje.setStyle("-fx-font-size: 14; -fx-text-alignment: center;");

        Button btnContinuar = new Button("Continuar");
        btnContinuar.setStyle("-fx-padding: 8 20; -fx-font-size: 12;");
        btnContinuar.setOnAction(e -> {
            ventanaConfirmacion.close();
            ventanaCalificacion.close();
        });

        contenido.getChildren().addAll(mensaje, btnContinuar);

        Scene scene = new Scene(contenido);
        ventanaConfirmacion.setScene(scene);
        ventanaConfirmacion.showAndWait();
    }

    private void abrirBuscador(String textoBusqueda) {
        if (textoBusqueda.trim().isEmpty()) {
            mostrarError("Por favor ingresa un título para buscar");
            return;
        }

        Pelicula pelicula = controller.buscarPorTitulo(textoBusqueda);

        Stage ventanaBusqueda = new Stage();
        ventanaBusqueda.initModality(Modality.APPLICATION_MODAL);
        ventanaBusqueda.setTitle("Plataforma de Streaming - Información");
        ventanaBusqueda.setWidth(500);
        ventanaBusqueda.setHeight(300);

        VBox contenido = new VBox(15);
        contenido.setPadding(new Insets(20));

        if (pelicula != null) {
            Label titulo = new Label(pelicula.getTitulo());
            titulo.setStyle("-fx-font-size: 18; -fx-font-weight: bold;");

            Label año = new Label("Año: " + pelicula.getAño());
            año.setStyle("-fx-font-size: 12;");

            TextArea resumen = new TextArea(pelicula.getResumen());
            resumen.setWrapText(true);
            resumen.setEditable(false);
            resumen.setPrefHeight(150);

            Button btnContinuar = new Button("Continuar");
            btnContinuar.setStyle("-fx-padding: 8 20;");
            btnContinuar.setOnAction(e -> ventanaBusqueda.close());

            contenido.getChildren().addAll(titulo, año, new Separator(), resumen, btnContinuar);
        } else {
            Label noEncontrada = new Label("No se encuentra disponible");
            noEncontrada.setStyle("-fx-font-size: 14; -fx-text-alignment: center;");
            contenido.setAlignment(Pos.CENTER);
            contenido.getChildren().add(noEncontrada);
        }

        Scene scene = new Scene(contenido);
        ventanaBusqueda.setScene(scene);
        ventanaBusqueda.showAndWait();
    }

    private void cerrarSesion() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Cerrar Sesión");
        alert.setHeaderText("¿Estás seguro?");
        alert.setContentText("¿Deseas cerrar sesión?");

        if (alert.showAndWait().get() == ButtonType.OK) {
            primaryStage.close();
        }
    }

    private void mostrarError(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}