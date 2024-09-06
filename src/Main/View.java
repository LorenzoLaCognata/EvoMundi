package Main;

import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.geometry.Insets;
import javafx.scene.text.*;

public class View {

    private final String fontFamily = "Roboto";
    private final double fontSize = 24;

    private final TextArea textArea1 = new TextArea();
    private final TextArea textArea2 = new TextArea();
    private final TextArea textArea3 = new TextArea();
    private final TextArea textArea4 = new TextArea();

    GridPane gridPane = new GridPane();

    public View() {

        textArea1.setFont(Font.font(fontFamily, FontWeight.BOLD, fontSize));
        textArea2.setFont(Font.font(fontFamily, fontSize));
        textArea3.setFont(Font.font(fontFamily, fontSize));
        textArea4.setFont(Font.font(fontFamily, fontSize));

        gridPane.setPadding(new Insets(10, 10, 10, 10)); // Set padding around the grid
        gridPane.setVgap(10); // Set vertical gap between rows
        gridPane.setHgap(10); // Set horizontal gap between columns

        // Add TextAreas to the grid (row, column)
        gridPane.add(textArea1, 0, 0); // Top-left cell
        gridPane.add(textArea2, 1, 0); // Top-right cell
        gridPane.add(textArea3, 0, 1); // Bottom-left cell
        gridPane.add(textArea4, 1, 1); // Bottom-right cell

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

}
