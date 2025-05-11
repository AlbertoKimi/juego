package com.enriquealberto.Controladores;

import com.enriquealberto.EscenaID;
import com.enriquealberto.ManagerEscenas;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class HistoriaControlador {

    @FXML
    AnchorPane historia;

    private TextFlow textFlow;

    @FXML
    public void initialize() {
        // Crear el ImageView para la imagen de fondo
        Image fondoImagen = new Image(getClass().getResourceAsStream("/com/enriquealberto/imagenes/pergamino.jpg"));
        ImageView fondo = new ImageView(fondoImagen);
        fondo.setPreserveRatio(false); // Permitir que ocupe toda la ventana
        /* fondo.setSmooth(true); // Suavizar la imagen */
        fondo.setCache(true); // Mejorar el rendimiento

        // Ajustar tamaño del video según el tamaño de la ventana
        fondo.sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (newScene != null) {
                fondo.fitWidthProperty().bind(newScene.widthProperty());
                fondo.fitHeightProperty().bind(newScene.heightProperty());
            }
        });

        // Añadir la imagen al AnchorPane
        historia.getChildren().add(fondo);

        // Crear el TextFlow
        textFlow = new TextFlow();
        textFlow.getStyleClass().add("text-flow"); // Asignar la clase CSS

        // Texto de la historia dividido en fragmentos
        String[] fragmentos = {
                "Una noche tranquila, mientras la luna velaba el cielo estrellado, la princesa del reino dormía profundamente en su habitación.\n\n",
                "Pero de pronto, unos susurros siniestros y pasos furtivos rompieron el silencio.\n\n",
                "A la mañana siguiente, el rey fue a despertar a su hija… y la encontró desaparecida.\n\n",
                "Las cortinas agitadas, la corona caída… señales de un rapto.\n\n",
                "La alarma se extendió por todo Frutaquia. El reino está en peligro.\n\n",
                "Sin perder tiempo, el rey convoca a sus héroes más valientes: el mago piña, el ninja fruta del dragón, el tanque sandía y el guerrero chirimoya.\n\n",
                "Su misión: encontrar a la princesa y devolver la paz al reino.\n\n",
                "Así comienza la gran aventura...\n\n"
        };

        // Crear nodos Text para cada fragmento
        for (String fragmento : fragmentos) {
            Text text = new Text(fragmento);
            text.setStyle("-fx-opacity: 0;"); // Inicialmente invisible

            // Evento para mostrar el fragmento al pasar el ratón
            text.setOnMouseEntered(event -> {
                text.setStyle("-fx-opacity: 1;"); // Hacer el texto visible permanentemente
            });

            textFlow.getChildren().add(text);
        }

        // Centrar el TextFlow dentro del AnchorPane
        AnchorPane.setTopAnchor(textFlow, 0.0);
        AnchorPane.setBottomAnchor(textFlow, 0.0);
        AnchorPane.setLeftAnchor(textFlow, 0.0);
        AnchorPane.setRightAnchor(textFlow, 0.0);

        // Añadir el TextFlow al AnchorPane
        historia.getChildren().add(textFlow);

        // Añadir la clase CSS al TextFlow
        historia.sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (newScene != null) {
                String cssPath = getClass().getResource("/com/enriquealberto/css/historia.css").toExternalForm();
                if (cssPath != null) {
                    System.out.println("CSS cargado desde: " + cssPath);
                    newScene.getStylesheets().add(cssPath);
                } else {
                    System.out.println("No se encontró el archivo CSS.");
                }

                // Configurar el cursor personalizado
                Image cursorImage = new Image(
                        getClass().getResourceAsStream("/com/enriquealberto/imagenes/Antorcha.png"));
                newScene.setCursor(new javafx.scene.ImageCursor(cursorImage));

                // Crear un círculo que simule el foco de luz
                Circle foco = new Circle(35, javafx.scene.paint.Color.rgb(255, 255, 200, 0.5)); // Luz amarilla
                                                                                                // semitransparente
                foco.setEffect(new javafx.scene.effect.DropShadow(100, javafx.scene.paint.Color.YELLOW)); // Efecto de
                                                                                                          // luz

                // Hacer que el círculo no intercepte eventos del ratón
                foco.setMouseTransparent(true);

                // Añadir el círculo al AnchorPane
                historia.getChildren().add(foco);

                // Actualizar la posición del círculo según el ratón
                newScene.setOnMouseMoved(event -> {
                    foco.setCenterX(event.getSceneX());
                    foco.setCenterY(event.getSceneY());
                });
            }
        });

        // Evento para cambiar a la vista SELECTION al hacer clic, pero solo si todo el texto es visible
        historia.setOnMouseClicked(event -> {
            boolean todoTextoVisible = textFlow.getChildren().stream()
                .filter(node -> node instanceof Text)
                .allMatch(node -> "-fx-opacity: 1;".equals(node.getStyle()));

            if (todoTextoVisible) {
                ManagerEscenas sm = ManagerEscenas.getInstance();
                sm.loadScene(EscenaID.SELECTION); // Cambiar a la vista SELECTION
            } else {
                System.out.println("Aún hay texto oculto. Haz visible todo el texto para continuar.");
            }
        });
    }
}
