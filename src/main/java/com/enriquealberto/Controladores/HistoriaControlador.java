package com.enriquealberto.Controladores;

import javafx.fxml.FXML;

import javafx.scene.image.Image;
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

        // Configurar el cursor personalizado con efecto de luz
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
                Image cursorImage = new Image(getClass().getResourceAsStream("/com/enriquealberto/imagenes/Antorcha.png"));
                newScene.setCursor(new javafx.scene.ImageCursor(cursorImage));

                // Crear un círculo que simule el foco de luz
                Circle foco = new Circle(35, javafx.scene.paint.Color.rgb(255, 255, 200, 0.5)); // Luz amarilla semitransparente
                foco.setEffect(new javafx.scene.effect.DropShadow(100, javafx.scene.paint.Color.YELLOW)); // Efecto de luz

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
    }
}
