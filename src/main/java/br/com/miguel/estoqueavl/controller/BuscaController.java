package br.com.miguel.estoqueavl.controller;

import br.com.miguel.estoqueavl.mainApp;
import br.com.miguel.estoqueavl.dao.EstoqueDados;
import br.com.miguel.estoqueavl.model.Produto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

/**
 * Controlador para a tela de busca e listagem de produtos (busca-view.fxml).
 */
public class BuscaController {

    // Injeção dos componentes da interface gráfica.
    @FXML private TextField campoBusca;
    @FXML private Button btnPesquisar;
    @FXML private Button btnVoltarMenu;
    @FXML private Button btnExcluir;

    // Tabela e colunas para exibir os produtos.
    @FXML private TableView<Produto> tabelaProdutos;
    @FXML private TableColumn<Produto, Integer> colId;
    @FXML private TableColumn<Produto, String> colNome;
    @FXML private TableColumn<Produto, BigDecimal> colPreco; // Corrigido para BigDecimal
    @FXML private TableColumn<Produto, Integer> colQtd;

    /**
     * Configura a tabela, as ações dos botões e carrega os dados iniciais.
     */
    @FXML
    public void initialize() {
        // Associa cada coluna a um atributo da classe Produto.
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colPreco.setCellValueFactory(new PropertyValueFactory<>("preco"));
        colQtd.setCellValueFactory(new PropertyValueFactory<>("quantidade"));

        // Carrega todos os produtos na tabela ao iniciar a tela.
        atualizarTabela();

        // Define as ações dos botões.
        btnVoltarMenu.setOnAction(e -> voltarParaHome());
        btnPesquisar.setOnAction(e -> pesquisarProduto());
        btnExcluir.setOnAction(e -> excluirProduto());
    }

    /**
     * Pega a lista de produtos da árvore e a exibe na tabela.
     */
    private void atualizarTabela() {
        List<Produto> listaDeProdutos = EstoqueDados.arvore.listar();
        ObservableList<Produto> listaObservavel = FXCollections.observableArrayList(listaDeProdutos);
        tabelaProdutos.setItems(listaObservavel);
    }

    /**
     * Filtra a tabela para exibir apenas o produto com o ID pesquisado.
     */
    private void pesquisarProduto() {
        String textoBusca = campoBusca.getText();

        // Se a busca for vazia, exibe todos os produtos.
        if (textoBusca.isEmpty()) {
            atualizarTabela();
            return;
        }

        try {
            int idBusca = Integer.parseInt(textoBusca);
            Produto produtoEncontrado = EstoqueDados.arvore.buscar(idBusca);

            if (produtoEncontrado != null) {
                // Se encontrou, exibe apenas ele na tabela.
                tabelaProdutos.setItems(FXCollections.observableArrayList(produtoEncontrado));
            } else {
                // Se não encontrou, limpa a tabela e informa o usuário.
                tabelaProdutos.getItems().clear();
                mostrarAlerta(Alert.AlertType.INFORMATION, "Busca", "Nenhum produto encontrado com o ID " + idBusca);
            }
        } catch (NumberFormatException e) {
            mostrarAlerta(Alert.AlertType.WARNING, "ID Inválido", "A busca deve ser feita pelo número do ID.");
        }
    }

    /**
     * Remove o produto selecionado na tabela da árvore de estoque.
     */
    private void excluirProduto() {
        Produto produtoSelecionado = tabelaProdutos.getSelectionModel().getSelectedItem();

        if (produtoSelecionado == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Nenhum Produto Selecionado", "Por favor, selecione um produto na tabela para excluir.");
            return;
        }

        // Remove o produto da árvore.
        boolean foiRemovido = EstoqueDados.arvore.remover(produtoSelecionado.getId());

        if (foiRemovido) {
            mostrarAlerta(Alert.AlertType.INFORMATION, "Sucesso", "Produto removido com sucesso.");
            atualizarTabela(); // Atualiza a tabela para refletir a remoção.
        } else {
            mostrarAlerta(Alert.AlertType.ERROR, "Erro", "Não foi possível remover o produto selecionado.");
        }
    }

    /**
     * Retorna para a tela inicial.
     */
    private void voltarParaHome() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(mainApp.class.getResource("home-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 800, 600);
            Stage stage = (Stage) btnVoltarMenu.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Exibe um pop-up de alerta para o usuário.
     */
    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensagem) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}
