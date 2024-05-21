package com.example.project_client.view.controller.Order.components;

    import atlantafx.base.theme.Styles;
    import com.example.project_client.model.Product;
    import com.example.project_client.view.controller.Order.interfaces.InitStyles;
    import javafx.geometry.Insets;
    import javafx.geometry.Pos;
    import javafx.scene.control.Button;
    import javafx.scene.control.Label;
    import javafx.scene.control.TextField;
    import javafx.scene.layout.HBox;
    import javafx.scene.layout.Priority;
    import javafx.scene.layout.Region;
    import javafx.scene.layout.VBox;
    import lombok.Getter;
    import org.kordamp.ikonli.javafx.FontIcon;
    import org.kordamp.ikonli.material2.Material2AL;
    import org.kordamp.ikonli.material2.Material2MZ;

    import java.text.NumberFormat;
    import java.util.Locale;

@Getter
public class ProductCount extends VBox implements InitStyles {
    private final Product product;
    private final TextField textField = new TextField("1");
    private final Button sub = new Button(null,new FontIcon(Material2MZ.REMOVE));
    private final Button add = new Button(null,new FontIcon(Material2AL.ADD));
    private  Label label2;

        public ProductCount(Product product) {
            this.product = product;
            initializeUI();
        }
        private void initializeUI() {
            initStyle();
            textField.setAlignment(Pos.CENTER);
            HBox hBox = new HBox();
            Label label1 = new Label(product.getName());
            Region region = new Region();
            HBox.setHgrow(region, Priority.ALWAYS);
            label2 =  new Label(NumberFormat.getNumberInstance(Locale.US).format(product.getPrice()) + " VND");
            hBox.getChildren().addAll(label1);
            VBox vBox = new VBox(textField);
            vBox.setPrefSize(40,25);
            vBox.setAlignment(Pos.CENTER);
            setPadding(new Insets(15));
            getChildren().addAll(hBox, new HBox(sub,vBox ,add,region,label2));
        }

    @Override
    public void initStyle() {
        sub.getStyleClass().addAll(Styles.BUTTON_CIRCLE,Styles.DANGER);
        add.getStyleClass().addAll(Styles.BUTTON_CIRCLE,Styles.SUCCESS);
    }
}
