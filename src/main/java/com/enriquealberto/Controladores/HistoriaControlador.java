package com.enriquealberto.Controladores;

import com.enriquealberto.EscenaID;
import com.enriquealberto.ManagerEscenas;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
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
        Image fondoImagen = new Image(getClass().getResourceAsStream("/com/enriquealberto/imagenes/Pergamino.png"));
        ImageView fondo = new ImageView(fondoImagen);
        fondo.setPreserveRatio(false);
        fondo.setCache(true); // Mejorar el rendimiento

        // Ajustar tamaño del video al de la ventana
        fondo.sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (newScene != null) {
                fondo.fitWidthProperty().bind(newScene.widthProperty());
                fondo.fitHeightProperty().bind(newScene.heightProperty());
            }
        });

        historia.getChildren().add(fondo);

        // Crear el MediaView para el video
        Media videoPortal = new Media(getClass().getResource("/com/enriquealberto/videos/portal.mp4").toExternalForm());
        MediaPlayer mediaPlayer = new MediaPlayer(videoPortal);
        MediaView mediaView = new MediaView(mediaPlayer);

        // Configurar ocupe toda la ventana
        mediaView.fitWidthProperty().bind(historia.widthProperty());
        mediaView.fitHeightProperty().bind(historia.heightProperty());
        mediaView.setPreserveRatio(false);

        // Ocultar el MediaView inicialmente
        mediaView.setVisible(false);

        historia.getChildren().add(mediaView);

      
        textFlow = new TextFlow();
        textFlow.getStyleClass().add("text-flow"); // Asignar la clase CSS

        // Texto de la historia
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

            // Evento para mostrar el fragmento al pasar el ratón
            text.setOnMouseEntered(event -> {
                text.setStyle("-fx-opacity: 1;");
            });

            textFlow.getChildren().add(text);
        }

        // Centrar el TextFlow
        AnchorPane.setTopAnchor(textFlow, 0.0);
        AnchorPane.setBottomAnchor(textFlow, 0.0);
        AnchorPane.setLeftAnchor(textFlow, 0.0);
        AnchorPane.setRightAnchor(textFlow, 0.0);
        historia.getChildren().add(textFlow);

        // Añadir css al texto
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

                // Luz antorcha
                Circle foco = new Circle(60); 
                foco.setFill(new javafx.scene.paint.RadialGradient(
                        0, 0, 0.5, 0.5, 1, true, javafx.scene.paint.CycleMethod.NO_CYCLE,
                        new javafx.scene.paint.Stop(0, javafx.scene.paint.Color.rgb(255, 255, 200, 0.4)), 
                        new javafx.scene.paint.Stop(1, javafx.scene.paint.Color.rgb(255, 255, 200, 0.1)) 
                ));

                // Aplicar un efecto de desenfoque para suavizar los bordes
                foco.setEffect(new javafx.scene.effect.GaussianBlur(40)); 

                // Hacer que el foco no intercepte eventos del ratón
                foco.setMouseTransparent(true);
                historia.getChildren().add(foco);

                // Actualizar la posición del foco según el ratón
                newScene.setOnMouseMoved(event -> {
                    foco.setCenterX(event.getSceneX());
                    foco.setCenterY(event.getSceneY());
                });
            }
        });

        // Evento para cambiar a la vista SELECTION al hacer clic con texto visible.
        historia.setOnMouseClicked(event -> {
            boolean todoTextoVisible = textFlow.getChildren().stream()
                    .filter(node -> node instanceof Text)
                    .allMatch(node -> "-fx-opacity: 1;".equals(node.getStyle()));

            if (todoTextoVisible) {
                mediaView.setVisible(true);
                mediaPlayer.play();

                // Asegurarse de que el MediaView esté al frente
                historia.getChildren().remove(mediaView); 
                historia.getChildren().add(mediaView);  

                mediaPlayer.setOnEndOfMedia(() -> {
                    mediaPlayer.pause(); // Pausar en el último cuadro
                });

                // Permitir hacer clic en el video para cambiar a la escena SELECTION
                mediaView.setOnMouseClicked(e -> {
                    ManagerEscenas sm = ManagerEscenas.getInstance();
                    sm.loadScene(EscenaID.SELECTION);
                });
            } else {
                System.out.println("Aún hay texto oculto. Haz visible todo el texto para continuar.");
            }
        });
    }
}
