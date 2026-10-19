package com.dfield.ui;

import com.dfield.core.EquationExample;
import com.dfield.core.ExampleLibrary;
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
    private final TextArea txtDetailDescription = new TextArea();
    private final Label lblDetailEquation = new Label();
    private final Label lblDetailVariables = new Label();
    private final Label lblDetailParameters = new Label();
    private final Label lblDetailWindow = new Label();

    private final ButtonType btnLoadType;
    private final EquationExample currentEquationState;

    public GalleryDialog(Stage owner, EquationExample currentEquationState) {
        this.currentEquationState = currentEquationState;

        initOwner(owner);
        initModality(Modality.APPLICATION_MODAL);
        setTitle("Galeria Didática de EDOs");
        setResizable(true);

        DialogPane dialogPane = getDialogPane();
        dialogPane.setPrefSize(840, 560);
        dialogPane.getStyleClass().add("gallery-dialog");

        // Carrega folha de estilos
        URL cssResource = getClass().getResource("/css/style.css");
        if (cssResource != null) {
            dialogPane.getStylesheets().add(cssResource.toExternalForm());
        }

        // Botoes do DialogPane
        this.btnLoadType = new ButtonType("Carregar Modelo", ButtonBar.ButtonData.OK_DONE);
        dialogPane.getButtonTypes().addAll(btnLoadType, ButtonType.CANCEL);

        initLayout(dialogPane);
        initEvents();

        // Conversor de resultado
        setResultConverter(buttonType -> {
            if (buttonType == btnLoadType) {
                return listView.getSelectionModel().getSelectedItem();
            }
            return null;
        });
    }

    private void initLayout(DialogPane dialogPane) {
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(14));

        // --- TOPO: Título e Filtro de Categorias ---
        VBox topBox = new VBox(10);
        topBox.setPadding(new Insets(0, 0, 12, 0));

        Label lblTitle = new Label("Galeria Didática de EDOs");
        lblTitle.getStyleClass().add("section-title");

        HBox filterBox = new HBox(10);
        filterBox.setAlignment(Pos.CENTER_LEFT);

        Label lblFilter = new Label("Categoria:");
        cmbCategories.setItems(FXCollections.observableArrayList(
                "Dinâmica Populacional",
                "Física e Circuitos",
                "Finanças e Economia",
                "Clima e Geociências",
                "Fenômenos Matemáticos",
                "Personalizados"
        ));
        cmbCategories.setValue("Dinâmica Populacional");
        cmbCategories.setPrefWidth(220);

        Region topSpacer = new Region();
        HBox.setHgrow(topSpacer, Priority.ALWAYS);

        Button btnSaveCurrent = new Button("Salvar Atual como Modelo...");
        btnSaveCurrent.getStyleClass().addAll("btn", "btn-secondary");
        btnSaveCurrent.setOnAction(e -> handleSaveCurrent());

        filterBox.getChildren().addAll(lblFilter, cmbCategories, topSpacer, btnSaveCurrent);
        topBox.getChildren().addAll(lblTitle, filterBox);
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
        rightBox.getStyleClass().add("detail-card");

        splitPane.getItems().addAll(leftBox, rightBox);
        splitPane.setDividerPositions(0.38);

        root.setCenter(splitPane);
        dialogPane.setContent(root);

        // Estiliza os botões nativos no rodapé
        Button btnLoad = (Button) dialogPane.lookupButton(btnLoadType);
        btnLoad.getStyleClass().addAll("btn", "btn-purple");
        btnLoad.setDisable(true);

        Button btnCancel = (Button) dialogPane.lookupButton(ButtonType.CANCEL);
        btnCancel.setText("Cancelar");
        btnCancel.getStyleClass().addAll("btn", "btn-secondary");

        // Habilita "Carregar" apenas se houver seleção
        listView.getSelectionModel().selectedItemProperty().addListener((obs, oldV, selected) -> {
            btnLoad.setDisable(selected == null);
            updateDetails(selected);
        });
    }

    private VBox buildDetailPanel() {
        VBox box = new VBox(10);

        lblDetailName.getStyleClass().add("detail-name");
        lblDetailCategory.getStyleClass().add("detail-category");

        txtDetailDescription.setEditable(false);
        txtDetailDescription.setWrapText(true);
        txtDetailDescription.setPrefRowCount(3);
        txtDetailDescription.getStyleClass().add("detail-description");

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(8);

        grid.addRow(0, new Label("Equação:"), lblDetailEquation);
        grid.addRow(1, new Label("Variáveis:"), lblDetailVariables);
        grid.addRow(2, new Label("Parâmetros:"), lblDetailParameters);
        grid.addRow(3, new Label("Janela sugerida:"), lblDetailWindow);

        lblDetailEquation.getStyleClass().add("detail-highlight");
        lblDetailVariables.getStyleClass().add("detail-value");
        lblDetailParameters.getStyleClass().add("detail-value");
        lblDetailWindow.getStyleClass().add("detail-value");

        box.getChildren().addAll(
                lblDetailName,
                lblDetailCategory,
                new Label("Descrição e Contexto Didático:"),
                txtDetailDescription,
                new Separator(),
                grid
        );

        return box;
    }

    private void initEvents() {
        // Filtragem por categoria
        cmbCategories.valueProperty().addListener((obs, oldVal, newCategory) -> refreshList(newCategory));

        // Duplo clique na lista confirma o carregamento
        listView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2 && listView.getSelectionModel().getSelectedItem() != null) {
                Button btnLoad = (Button) getDialogPane().lookupButton(btnLoadType);
                btnLoad.fire();
            }
        });

        // Carga inicial
        refreshList("Dinâmica Populacional");
    }

    private void refreshList(String category) {
        List<EquationExample> filtered = ExampleLibrary.getByCategory(category);
        listView.setItems(FXCollections.observableArrayList(filtered));

        if (!filtered.isEmpty()) {
            listView.getSelectionModel().selectFirst();
        } else {
            updateDetails(null);
        }
    }

    private void updateDetails(EquationExample ex) {
        if (ex == null) {
            lblDetailName.setText("Nenhum modelo selecionado");
            lblDetailCategory.setText("");
            txtDetailDescription.setText("");
            lblDetailEquation.setText("-");
            lblDetailVariables.setText("-");
            lblDetailParameters.setText("-");
            lblDetailWindow.setText("-");
            return;
        }

        lblDetailName.setText(ex.name());
        lblDetailCategory.setText("Categoria: " + ex.category());
        txtDetailDescription.setText(ex.description());
        lblDetailEquation.setText(String.format("d%s/d%s = %s", ex.dependentVar(), ex.independentVar(), ex.expression()));
        lblDetailVariables.setText(String.format("Indep: %s | Dep: %s", ex.independentVar(), ex.dependentVar()));

if (ex.defaultParameters() == null || ex.defaultParameters().isBlank()) {
            lblDetailParameters.setText("(Nenhum)");
        } else {
            lblDetailParameters.setText(ex.defaultParameters());
        }

        lblDetailWindow.setText(String.format(Locale.US,
                "[%s: %.1f a %.1f] × [%s: %.1f a %.1f]",
                ex.independentVar(), ex.tMin(), ex.tMax(),
                ex.dependentVar(), ex.xMin(), ex.xMax()
        ));
    }

    /**
     * Diálogo para salvar o modelo atualmente em uso na MainWindow como exemplo customizado.
     */
    private void handleSaveCurrent() {
        if (currentEquationState == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Não há equação ativa para salvar.", ButtonType.OK);
            alert.initOwner(getOwner());
            alert.showAndWait();
            return;
        }

        TextInputDialog inputDialog = new TextInputDialog(currentEquationState.name());
        inputDialog.initOwner(getOwner());
        inputDialog.setTitle("Salvar Modelo Customizado");
        inputDialog.setHeaderText("Salvar Equação na Biblioteca Pessoal");
        inputDialog.setContentText("Nome do Modelo:");

        inputDialog.showAndWait().ifPresent(name -> {
            String trimmedName = name.trim();
            if (trimmedName.isEmpty()) return;

            EquationExample customExample = new EquationExample(
                    trimmedName,
                    "Personalizados",
                    "Modelo customizado adicionado pelo usuário.",
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

    /**
     * Ponto de entrada estático para exibição modal.
     */
    public static Optional<EquationExample> showGallery(Stage owner, EquationExample currentEquationState) {
        GalleryDialog dialog = new GalleryDialog(owner, currentEquationState);
        return dialog.showAndWait();
    }
}
