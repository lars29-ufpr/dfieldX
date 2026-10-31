package com.dfield.ui;

import com.dfield.core.EquationExample;
import com.dfield.core.ExampleLibrary;
import com.dfield.util.I18n;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.net.URL;
import java.util.*;

/**
 * Diálogo modal para navegação, inspeção didática e seleção de modelos de EDOs.
 */
public class GalleryDialog extends Dialog<EquationExample> {

    private final ComboBox<String> cmbCategories = new ComboBox<>();
    private final ListView<EquationExample> listView = new ListView<>();

    // Componentes de Detalhes
    private final Label lblDetailName = new Label();
    private final Label lblDetailCategory = new Label();
    private final TextArea txtDetailDescription = new TextArea() {{
        setPrefRowCount(4);
        setMinHeight(80);
        setWrapText(true);
        setEditable(false);
    }};
    private final Label lblDetailEquation = new Label();
    private final Label lblDetailVariables = new Label();
    private final Label lblDetailParameters = new Label();
    private final Label lblDetailWindow = new Label();

    private final ButtonType btnLoadType;
    private final EquationExample currentEquationState;

    public GalleryDialog(Stage owner, EquationExample currentEquationState) {
        this.currentEquationState = currentEquationState;
        setTitle(I18n.get("gallery.title"));
        initOwner(owner);
        initModality(Modality.WINDOW_MODAL);
        setResizable(true);

        DialogPane dialogPane = getDialogPane();
        dialogPane.setPrefSize(850, 560);

        // Estilos CSS
        URL cssUrl = getClass().getResource("/styles/dark-theme.css");
        if (cssUrl != null) {
            dialogPane.getStylesheets().add(cssUrl.toExternalForm());
        }
        dialogPane.getStyleClass().add("root-dialog");

        btnLoadType = new ButtonType(I18n.get("gallery.btn.load"), ButtonBar.ButtonData.OK_DONE);
        dialogPane.getButtonTypes().addAll(btnLoadType, ButtonType.CANCEL);

        // Estilizar botões do rodapé
        Button btnLoad = (Button) dialogPane.lookupButton(btnLoadType);
        if (btnLoad != null) {
            btnLoad.getStyleClass().addAll("btn", "btn-primary");
        }
        Button btnCancel = (Button) dialogPane.lookupButton(ButtonType.CANCEL);
        if (btnCancel != null) {
            btnCancel.getStyleClass().addAll("btn", "btn-secondary");
            btnCancel.setText(I18n.get("gallery.btn.cancel"));
        }

        initLayout(dialogPane);
        initStyles();
        initEvents();

        setResultConverter(dialogButton -> {
            if (dialogButton == btnLoadType) {
                return listView.getSelectionModel().getSelectedItem();
            }
            return null;
        });
    }

    private void initLayout(DialogPane dialogPane) {
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(14));

        // --- TOPO: Linha única limpa com Filtro de Categorias e Botão Salvar ---
        HBox topBox = new HBox(12);
        topBox.setAlignment(Pos.CENTER_LEFT);
        topBox.setPadding(new Insets(0, 0, 12, 0));

        Label lblFilter = new Label(I18n.get("gallery.filter"));

        cmbCategories.getItems().addAll(
                I18n.get("gallery.cat.biomath"),
                I18n.get("gallery.cat.physics"),
                I18n.get("gallery.cat.chemistry"),
                I18n.get("gallery.cat.math"),
                I18n.get("gallery.cat.custom")
        );
        cmbCategories.setValue(I18n.get("gallery.cat.biomath"));
        cmbCategories.setPrefWidth(340);
        cmbCategories.setMinWidth(260);

        Region topSpacer = new Region();
        HBox.setHgrow(topSpacer, Priority.ALWAYS);

        Button btnSaveCurrent = new Button(I18n.get("gallery.btn.save_current"));
        btnSaveCurrent.getStyleClass().addAll("btn", "btn-secondary");
        btnSaveCurrent.setOnAction(e -> handleSaveCurrent());

        topBox.getChildren().addAll(lblFilter, cmbCategories, topSpacer, btnSaveCurrent);
        root.setTop(topBox);

        // --- CENTRO: SplitPane com Lista (Esq) e Detalhes (Dir) ---
        SplitPane splitPane = new SplitPane();
        splitPane.getStyleClass().add("gallery-split");

        // Painel Esquerdo: Lista
        VBox leftBox = new VBox(listView);
        VBox.setVgrow(listView, Priority.ALWAYS);
        listView.getStyleClass().add("gallery-list");

        // Painel Direito: Cartão de Detalhes
        VBox rightBox = buildDetailPanel();
        rightBox.setPadding(new Insets(12, 16, 12, 16));

        splitPane.getItems().addAll(leftBox, rightBox);
        splitPane.setDividerPositions(0.42);
        root.setCenter(splitPane);

        dialogPane.setContent(root);
    }

    private VBox buildDetailPanel() {
        VBox box = new VBox(10);
        box.setAlignment(Pos.TOP_LEFT);

        lblDetailName.getStyleClass().add("detail-title");
        lblDetailCategory.getStyleClass().add("detail-subtitle");

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(8);

        Label lblEqTitle = new Label(I18n.get("gallery.equation"));
        Label lblVarTitle = new Label(I18n.get("gallery.variables"));
        Label lblParamTitle = new Label(I18n.get("gallery.parameters"));
        Label lblWinTitle = new Label(I18n.get("gallery.window"));

        lblEqTitle.getStyleClass().add("detail-label");
        lblVarTitle.getStyleClass().add("detail-label");
        lblParamTitle.getStyleClass().add("detail-label");
        lblWinTitle.getStyleClass().add("detail-label");

        lblDetailEquation.getStyleClass().add("detail-value");
        lblDetailVariables.getStyleClass().add("detail-value");
        lblDetailParameters.getStyleClass().add("detail-value");
        lblDetailWindow.getStyleClass().add("detail-value");

        grid.add(lblEqTitle, 0, 0);
        grid.add(lblDetailEquation, 1, 0);
        grid.add(lblVarTitle, 0, 1);
        grid.add(lblDetailVariables, 1, 1);
        grid.add(lblParamTitle, 0, 2);
        grid.add(lblDetailParameters, 1, 2);
        grid.add(lblWinTitle, 0, 3);
        grid.add(lblDetailWindow, 1, 3);

        box.getChildren().addAll(
                lblDetailName,
                lblDetailCategory,
                new Label(I18n.get("gallery.desc_header")),
                txtDetailDescription,
                new Separator(),
                grid
        );

        return box;
    }

    private void initStyles() {
        lblDetailName.setStyle("-fx-font-size: 15px; -fx-font-weight: bold; -fx-text-fill: #f5f6fa;");
        // Permite que títulos muito grandes (como em ucraniano ou alemão) quebrem para a linha de baixo
        lblDetailName.setWrapText(true);
        lblDetailName.setMinHeight(javafx.scene.layout.Region.USE_PREF_SIZE);
        
        lblDetailCategory.setStyle("-fx-font-size: 12px; -fx-text-fill: #718093;");
    }

    private void initEvents() {
        cmbCategories.valueProperty().addListener((obs, oldVal, newCategory) -> refreshList(newCategory));

        listView.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, ex) -> {
            if (ex != null) {
                lblDetailName.setText(ex.name());
                lblDetailCategory.setText(I18n.get("gallery.category_prefix") + " " + ex.category());
                txtDetailDescription.setText(ex.description());
                lblDetailEquation.setText(String.format("d%s/d%s = %s", ex.dependentVar(), ex.independentVar(), ex.expression()));
                lblDetailVariables.setText(String.format("%s %s | %s %s", I18n.get("gallery.var.indep"), ex.independentVar(), I18n.get("gallery.var.dep"), ex.dependentVar()));
                
                if (ex.defaultParameters() == null || ex.defaultParameters().isBlank()) {
                    lblDetailParameters.setText(I18n.get("gallery.none_params"));
                } else {
                    lblDetailParameters.setText(ex.defaultParameters());
                }

                lblDetailWindow.setText(String.format(Locale.US,
                        "[%s: %.1f .. %.1f] × [%s: %.1f .. %.1f]",
                        ex.independentVar(), ex.tMin(), ex.tMax(),
                        ex.dependentVar(), ex.xMin(), ex.xMax()
                ));
            } else {
                lblDetailName.setText(I18n.get("gallery.none_selected"));
                lblDetailCategory.setText("");
                txtDetailDescription.setText("");
                lblDetailEquation.setText("");
                lblDetailVariables.setText("");
                lblDetailParameters.setText("");
                lblDetailWindow.setText("");
            }
        });

        listView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2 && listView.getSelectionModel().getSelectedItem() != null) {
                Button btnLoad = (Button) getDialogPane().lookupButton(btnLoadType);
                if (btnLoad != null) btnLoad.fire();
            }
        });

        refreshList(I18n.get("gallery.cat.biomath"));
    }

    private void refreshList(String category) {
        List<EquationExample> filtered = ExampleLibrary.getByCategory(category);
        listView.setItems(FXCollections.observableArrayList(filtered));
        if (!filtered.isEmpty()) {
            listView.getSelectionModel().select(0);
        }
    }

    private void handleSaveCurrent() {
        TextInputDialog saveDialog = new TextInputDialog("Modelo " + (ExampleLibrary.getCustomExamples().size() + 1));
        saveDialog.setTitle(I18n.get("gallery.save.title"));
        saveDialog.setHeaderText(I18n.get("gallery.save.header"));
        saveDialog.setContentText(I18n.get("gallery.save.content"));

        Optional<String> result = saveDialog.showAndWait();
        result.ifPresent(name -> {
            if (name.isBlank()) return;
            EquationExample customExample = new EquationExample(
                    name.trim(),
                    "Personalizados",
                    I18n.get("gallery.save.default_desc"),
                    currentEquationState.expression(),
                    currentEquationState.independentVar(),
                    currentEquationState.dependentVar(),
                    currentEquationState.defaultParameters(),
                    currentEquationState.tMin(),
                    currentEquationState.tMax(),
                    currentEquationState.xMin(),
                    currentEquationState.xMax(),
                    currentEquationState.defaultT0(),
                    currentEquationState.defaultX0()
            );

            ExampleLibrary.addCustomExample(customExample);
            cmbCategories.setValue("Personalizados");
            refreshList("Personalizados");
            listView.getSelectionModel().select(customExample);
        });
    }

    public static Optional<EquationExample> showGallery(Stage owner, EquationExample currentEquationState) {
        GalleryDialog dialog = new GalleryDialog(owner, currentEquationState);
        return dialog.showAndWait();
    }
}
