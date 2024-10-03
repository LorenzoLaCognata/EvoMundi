package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class ToolbarSection {

    private final VBox vBox = new VBox(5);
    private final Label name = new Label();
    private final Label value = new Label();
    private final ImageView imageView = new ImageView();
    private final CheckBox checkBox = new CheckBox();

    public ToolbarSection(String name, Image image) {
        this.vBox.setAlignment(Pos.CENTER);
        this.vBox.setPadding(new Insets(5));
        this.vBox.setMinWidth(128);
        this.name.setText(name);
        this.name.setTextFill(Color.WHITE);
        this.value.setTextFill(Color.WHITE);
        this.imageView.setImage(image);
        this.checkBox.setSelected(Boolean.TRUE);
        this.vBox.getChildren().addAll(this.name, this.value, this.imageView, this.checkBox);
    }

    public VBox getvBox() {
        return vBox;
    }

    public CheckBox getCheckBox() {
        return checkBox;
    }

    public void setValue(String string) {
        this.value.setText(string);
    }

}
