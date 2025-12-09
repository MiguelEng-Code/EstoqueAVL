package br.com.miguel.estoqueavl;

import br.com.miguel.estoqueavl.dao.EstoqueDados;
import br.com.miguel.estoqueavl.service.PersistenciaServico;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Classe principal que inicia a aplicação JavaFX.
 * Responsável por carregar a interface gráfica e os dados do estoque.
 */
public class mainApp extends Application {

    private final PersistenciaServico servico = new PersistenciaServico();

    /**
     * Método principal do ciclo de vida do JavaFX, chamado ao iniciar a aplicação.
     * @param stage O palco principal da aplicação, fornecido pelo JavaFX.
     */
    @Override
    public void start(Stage stage) throws IOException {
        // Carrega os dados do arquivo JSON para a árvore de estoque.
        EstoqueDados.arvore = servico.carregar();

        // Carrega a interface gráfica a partir do arquivo FXML.
        FXMLLoader fxmlLoader = new FXMLLoader(mainApp.class.getResource("home-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);

        // Configura e exibe a janela principal.
        stage.setTitle("Sistema de Estoque AVL");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Método chamado quando a aplicação está prestes a ser fechada.
     * Garante que os dados do estoque sejam salvos.
     */
    @Override
    public void stop() {
        // Salva o estado atual da árvore de estoque no arquivo JSON.
        servico.salvar(EstoqueDados.arvore);
    }

    /**
     * Ponto de entrada da aplicação Java.
     * O método `launch()` inicia o toolkit JavaFX e o ciclo de vida da aplicação.
     */
    public static void main(String[] args) {
        launch();
    }
}
