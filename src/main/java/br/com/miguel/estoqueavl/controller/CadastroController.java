package br.com.miguel.estoqueavl.controller;

import br.com.miguel.estoqueavl.mainApp;
import br.com.miguel.estoqueavl.dao.EstoqueDados;
import br.com.miguel.estoqueavl.model.Produto;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.math.BigDecimal;

/**
 * Controlador para a tela de cadastro de produtos (cadastro-view.fxml).
 */
public class CadastroController {

    // Injeção dos componentes da interface gráfica.
    @FXML private TextField txtId;
    @FXML private TextField txtNome;
    @FXML private TextField txtPreco;
    @FXML private TextField txtQtd;
    @FXML private Button btnSalvar;
    @FXML private Button btnCancelar;

    /**
     * Configura as ações dos botões "Salvar" e "Cancelar".
     */
    @FXML
    public void initialize() {
        btnSalvar.setOnAction(e -> salvarProduto());
        btnCancelar.setOnAction(e -> voltarParaHome());
    }

    /**
     * Valida os campos, cria um objeto Produto e o insere/atualiza na árvore.
     */
    private void salvarProduto() {
        try {
            // Validação de campos essenciais.
            if (txtId.getText().isEmpty() || txtNome.getText().isEmpty()) {
                mostrarAlerta(Alert.AlertType.WARNING, "Campos Obrigatórios", "Por favor, preencha pelo menos o ID e o Nome.");
                return;
            }

            // Conversão dos dados dos campos de texto.
            int id = Integer.parseInt(txtId.getText());
            String nome = txtNome.getText();
            // Converte o preço para BigDecimal, o tipo correto para valores monetários.
            BigDecimal preco = new BigDecimal(txtPreco.getText().replace(",", "."));
            int qtd = Integer.parseInt(txtQtd.getText());

            // Cria o produto e o insere na árvore.
            Produto novoProduto = new Produto(id, nome, preco, qtd);
            EstoqueDados.arvore.inserir(novoProduto);

            // Exibe mensagem de sucesso e limpa os campos para um novo cadastro.
            mostrarAlerta(Alert.AlertType.INFORMATION, "Sucesso", "Produto cadastrado com sucesso!");
            limparCampos();

        } catch (NumberFormatException e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Erro de Formato", "ID e Quantidade devem ser números inteiros.\nPreço deve ser um valor numérico.");
        } catch (Exception e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Erro Inesperado", "Ocorreu um erro: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Limpa todos os campos de texto e foca no campo de ID.
     */
    private void limparCampos() {
        txtId.clear();
        txtNome.clear();
        txtPreco.clear();
        txtQtd.clear();
        txtId.requestFocus(); // Move o cursor para o campo ID.
    }

    /**
     * Retorna para a tela inicial.
     */
    private void voltarParaHome() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(mainApp.class.getResource("home-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 800, 600);
            Stage stage = (Stage) btnCancelar.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Exibe um pop-up de alerta para o usuário.
     * @param tipo O tipo de alerta (ERRO, INFORMAÇÃO, AVISO).
     * @param titulo O título da janela do alerta.
     * @param mensagem A mensagem a ser exibida.
     */
    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensagem) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}
