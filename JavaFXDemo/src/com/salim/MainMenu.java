package com.salim;

import java.util.Arrays;
import java.util.List;

import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.util.Pair;

@SuppressWarnings("unused")
public class MainMenu extends Application {
	
	private static final int Width = 1024;
	private static final int Height = 600;
	
	private List<Pair<String, Runnable>> menuData = Arrays.asList(
            new Pair<String, Runnable>("Single Player", () -> {}),
            new Pair<String, Runnable>("Multiplayer", () -> {}),
            new Pair<String, Runnable>("Exit to Desktop", Platform::exit)
    );
	
	Button button;
	
	private Pane root = new Pane();
	private VBox menuBox = new VBox(-5);
    private Line line;
	
	private Parent createContent() {
		addBackground();
		addTitle();
		
		double lineX = Width / 2 ;
		double lineY = Height / 2 + 50;
		
		addLine(lineX,lineY);
		addMenu(lineX + 5 ,lineY + 5);
		
		startAnimation();
		
		return root;
	}
	
	private void startAnimation() {
		ScaleTransition st = new ScaleTransition(Duration.seconds(1), line);
        st.setToY(1);
        st.setOnFinished(e -> {

            for (int i = 0; i < menuBox.getChildren().size(); i++) {
                Node n = menuBox.getChildren().get(i);

                TranslateTransition tt = new TranslateTransition(Duration.seconds(1 + i * 0.15), n);
                tt.setToX(0);
                tt.setOnFinished(e2 -> n.setClip(null));
                tt.play();
            }
        });
        st.play();		
	}

	private void addMenu(double x, double y) {
		menuBox.setTranslateX(x);
        menuBox.setTranslateY(y);
        menuData.forEach(data -> {
            MainMenuItem item = new MainMenuItem(data.getKey());
            item.setOnAction(data.getValue());
            item.setTranslateX(-300);

            Rectangle clip = new Rectangle(300, 30);
            clip.translateXProperty().bind(item.translateXProperty().negate());

            item.setClip(clip);

            menuBox.getChildren().addAll(item);
        });

        root.getChildren().add(menuBox);		
	}

	private void addLine(double x, double y) {
		line = new Line(x, y, x, y + 120);
        line.setStrokeWidth(3);
        line.setStroke(Color.color(1, 1, 1, 0.75));
        line.setEffect(new DropShadow(5, Color.BLACK));
        line.setScaleY(0);

        root.getChildren().add(line);		
	}

	private void addTitle() {
		MainMenuTitle title = new MainMenuTitle("GREED IS NOT GOOD");
		title.setTranslateX(Width / 2 - title.getTitleWidth() / 2);
        title.setTranslateY(Height / 3);

        root.getChildren().add(title);
	}

	private void addBackground() {
		ImageView imageView = new ImageView(new Image(getClass().getResource("background.png").toExternalForm()));
        imageView.setFitWidth(Width);
        imageView.setFitHeight(Height);

	}
		
	@Override
	public void start(Stage primaryStage) throws Exception {
//		button = new Button();
//		button.setText("Click Me");
//		
//		StackPane layout = new StackPane();
//		layout.getChildren().add(button);
		
		Scene scene = new Scene(createContent());
		
		primaryStage.setTitle("Greed Is Not Good");
		primaryStage.setScene(scene);
		primaryStage.show();
		
	}
	
	public static void main(String[] args) {
		launch(args);

	}

}
