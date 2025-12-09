package br.com.miguel.estoqueavl.controller;

import br.com.miguel.estoqueavl.mainApp;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Controlador para a tela inicial (home-view.fxml).
 * Gerencia a navegação para as telas de cadastro e busca, e a saída da aplicação.
 */
public class HomeController {

    // Injeção dos componentes da interface gráfica definidos no FXML.
    @FXML private Button btnIrCadastro;
    @FXML private Button btnIrBusca;
    @FXML private Button btnSair;

    /**
     * Método executado automaticamente após o FXML ser carregado.
     * Configura as ações dos botões da tela inicial.
     */
    @FXML
    public void initialize() {
        // Define a ação do botão para navegar para a tela de cadastro.
        btnIrCadastro.setOnAction(e -> trocarTela("cadastro-view.fxml"));
        // Define a ação do botão para navegar para a tela de busca.
        btnIrBusca.setOnAction(e -> trocarTela("busca-view.fxml"));

        // Define a ação do botão para fechar a aplicação.
        btnSair.setOnAction(e -> {
            Platform.exit(); // Encerra a thread do JavaFX de forma segura.
        });
    }

    /**
     * Método auxiliar para carregar e exibir uma nova tela (cena) na janela atual.
     * @param fxml O nome do arquivo FXML da tela a ser carregada.
     */
    private void trocarTela(String fxml) {
        try {
            // Carrega o novo FXML.
            FXMLLoader fxmlLoader = new FXMLLoader(mainApp.class.getResource(fxml));
            Scene scene = new Scene(fxmlLoader.load(), 800, 600);

            // Obtém a janela atual a partir de qualquer um dos botões.
            Stage stage = (Stage) btnIrCadastro.getScene().getWindow();

            // Define a nova cena na janela e a exibe.
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Erro ao carregar a tela: " + fxml);
        }
    }
}
