package Main;

import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.geometry.Insets;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.*;

public class View {

    private final String fontFamily = "Roboto";
    private final double fontSize = 24;

    private final TextArea textArea1 = new TextArea();
    private final TextArea textArea2 = new TextArea();
    private final TextArea textArea3 = new TextArea();
    private final TextArea textArea4 = new TextArea();

    private final CheckBox checkBox = new CheckBox();

    private final Image image1 = new Image("File:C:\\Users\\vodev\\OneDrive\\Desktop\\whitetaildeer_64x64.png");
    private final Image image2 = new Image("File:C:\\Users\\vodev\\OneDrive\\Desktop\\graywolf_64x64.png");
    private final Image image3 = new Image("File:C:\\Users\\vodev\\OneDrive\\Desktop\\moose_64x64.png");
    private final Image image4 = new Image("File:C:\\Users\\vodev\\OneDrive\\Desktop\\beaver_64x64.png");
    private final Image image5 = new Image("File:C:\\Users\\vodev\\OneDrive\\Desktop\\snowshoehare_64x64.png");

    GridPane gridPane = new GridPane();

    public View() {

        textArea1.setFont(Font.font(fontFamily, FontWeight.BOLD, fontSize));
        textArea2.setFont(Font.font(fontFamily, fontSize));
        textArea3.setFont(Font.font(fontFamily, fontSize));
        textArea4.setFont(Font.font(fontFamily, fontSize));

        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.setVgap(10);
        gridPane.setHgap(10);

        gridPane.add(textArea1, 0, 0);

//        gridPane.add(checkBox, 0, 1);
//        gridPane.add(textArea2, 0, 2);
//        gridPane.add(textArea3, 1, 1);
//        gridPane.add(textArea4, 1, 2);

        gridPane.add(new ImageView(image1), 0, 1);
        gridPane.add(new ImageView(image2), 0, 2);
        gridPane.add(new ImageView(image3), 1, 0);
        gridPane.add(new ImageView(image4), 1, 1);
        gridPane.add(new ImageView(image5), 1, 2);

    }

    public double getFontSize() {
        return fontSize;
    }

    public String getFontFamily() {
        return fontFamily;
    }

    public TextArea getTextArea1() {
        return textArea1;
    }

    public TextArea getTextArea2() {
        return textArea2;
    }

    public TextArea getTextArea3() {
        return textArea3;
    }

    public TextArea getTextArea4() {
        return textArea4;
    }

    public CheckBox getCheckBox() {
        return checkBox;
    }

    public GridPane getGridPane() {
        return gridPane;
    }

    public void setTextArea1(String string) {
        this.textArea1.setText(string);
    }

    public void setTextArea2(String string) {
        this.textArea2.setText(string);
    }

    public void setTextArea3(String string) {
        this.textArea3.setText(string);
    }

    public void setTextArea4(String string) {
        this.textArea4.setText(string);
    }

    public void setCheckBox(String string) {
        this.checkBox.setText(string);
    }

}
