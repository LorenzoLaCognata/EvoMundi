package Main;

import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.geometry.Insets;

public class View {

    private final String fontFamily = "Roboto";
    private final double fontSize = 24;

    private final BorderPane borderPane = new BorderPane();

    private final Label populationLabel1 = new Label();
    private final Label populationLabel2 = new Label();
    private final Label populationLabel3 = new Label();
    private final Label populationLabel4 = new Label();
    private final Label populationLabel5 = new Label();
    private final Label weekLabel = new Label();

    public View() {

        ToolBar topBar = createToolBar();
        borderPane.setTop(topBar);

        HBox statusBar = createStatusBar();
        borderPane.setBottom(statusBar);

        Label centerContent = createCenterContent();
        borderPane.setCenter(centerContent);

    }

    public BorderPane getBorderPane() {
        return borderPane;
    }

    public Label getPopulationLabel1() {
        return populationLabel1;
    }

    public void setPopulationLabel1(String string) {
        populationLabel1.setText(string);
    }

    public Label getPopulationLabel2() {
        return populationLabel2;
    }

    public void setPopulationLabel2(String string) {
        populationLabel2.setText(string);
    }

    public Label getPopulationLabel3() {
        return populationLabel3;
    }

    public void setPopulationLabel3(String string) {
        populationLabel3.setText(string);
    }

    public Label getPopulationLabel4() {
        return populationLabel4;
    }

    public void setPopulationLabel4(String string) {
        populationLabel4.setText(string);
    }

    public Label getPopulationLabel5() {
        return populationLabel5;
    }

    public void setPopulationLabel5(String string) {
        populationLabel5.setText(string);
    }

    public Label getWeekLabel() {
        return weekLabel;
    }

    public void setWeekLabel(String string) {
        weekLabel.setText(string);
    }

    private ToolBar createToolBar() {

        ToolBar toolBar = new ToolBar();

        VBox vbox1 = new VBox(5);
        vbox1.setAlignment(Pos.CENTER);
        vbox1.setPadding(new Insets(5));
        vbox1.setMinWidth(128);
        Label species1 = new Label("White-Tail Deer");
        ImageView imageView1 = new ImageView(new Image("File:C:\\Users\\vodev\\OneDrive\\Desktop\\whitetaildeer_64x64.png"));
        CheckBox checkBox1 = new CheckBox();
        checkBox1.setSelected(true);
        vbox1.getChildren().addAll(species1, populationLabel1, imageView1, checkBox1);

        VBox vbox2 = new VBox(5);
        vbox2.setAlignment(Pos.CENTER);
        vbox2.setPadding(new Insets(5));
        vbox2.setMinWidth(128);
        Label species2 = new Label("Moose");
        ImageView imageView2 = new ImageView(new Image("File:C:\\Users\\vodev\\OneDrive\\Desktop\\moose_64x64.png"));
        CheckBox checkBox2 = new CheckBox();
        checkBox2.setSelected(true);
        vbox2.getChildren().addAll(species2, populationLabel2, imageView2, checkBox2);

        VBox vbox3 = new VBox(5);
        vbox3.setAlignment(Pos.CENTER);
        vbox3.setPadding(new Insets(5));
        vbox3.setMinWidth(128);
        Label species3 = new Label("Gray Wolf");
        ImageView imageView3 = new ImageView(new Image("File:C:\\Users\\vodev\\OneDrive\\Desktop\\graywolf_64x64.png"));
        CheckBox checkBox3 = new CheckBox();
        checkBox3.setSelected(true);
        vbox3.getChildren().addAll(species3, populationLabel3, imageView3, checkBox3);

        VBox vbox4 = new VBox(5);
        vbox4.setAlignment(Pos.CENTER);
        vbox4.setPadding(new Insets(5));
        vbox4.setMinWidth(128);
        Label species4 = new Label("European Beaver");
        ImageView imageView4 = new ImageView(new Image("File:C:\\Users\\vodev\\OneDrive\\Desktop\\beaver_64x64.png"));
        CheckBox checkBox4 = new CheckBox();
        checkBox4.setSelected(true);
        vbox4.getChildren().addAll(species4, populationLabel4, imageView4, checkBox4);

        VBox vbox5 = new VBox(5);
        vbox5.setAlignment(Pos.CENTER);
        vbox5.setPadding(new Insets(5));
        vbox5.setMinWidth(128);
        Label species5 = new Label("Snowshoe Hare");
        ImageView imageView5 = new ImageView(new Image("File:C:\\Users\\vodev\\OneDrive\\Desktop\\snowshoehare_64x64.png"));
        CheckBox checkBox5 = new CheckBox();
        checkBox5.setSelected(true);
        vbox5.getChildren().addAll(species5, populationLabel5, imageView5, checkBox5);

        VBox vboxN = new VBox(5);
        vboxN.setAlignment(Pos.CENTER);
        vboxN.setPadding(new Insets(5));
        vboxN.setMinWidth(128);
        Button button4 = new Button();
        button4.setText("Start the Simulation");
    /*
        button4.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                new Thread(() -> controller.run()).start();
            }
        });
    */
        vboxN.getChildren().addAll(weekLabel, button4);

        toolBar.getItems().addAll(vbox1, vbox2, vbox3, vbox4, vbox5, vboxN);

        return toolBar;
    }

    private HBox createStatusBar() {

        HBox statusBar = new HBox(10);
        statusBar.setPadding(new Insets(10));
        statusBar.setStyle("-fx-background-color: #CCCCCC;");

        Label statusLabel = new Label("Status: Paused");
        statusLabel.setTextFill(Color.BLACK);

        statusBar.getChildren().add(statusLabel);

        return statusBar;
    }

    private Label createCenterContent() {
        return new Label("EvoMundi Simulation");
    }

}
