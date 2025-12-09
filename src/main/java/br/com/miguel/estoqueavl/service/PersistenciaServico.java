package br.com.miguel.estoqueavl.service;

import br.com.miguel.estoqueavl.model.Produto;
import br.com.miguel.estoqueavl.model.estrutura.ArvoreAVL;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Serviço para salvar e carregar os dados do estoque em um arquivo JSON.
 * Garante que os dados persistam após o fechamento da aplicação.
 */
public class PersistenciaServico {

    private static final String ARQUIVO_DADOS = "estoque_dados.json";
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    /**
     * Salva todos os produtos da árvore em um arquivo JSON.
     * @param arvore A árvore com os produtos a serem salvos.
     */
    public void salvar(ArvoreAVL arvore) {
        try (Writer writer = new FileWriter(ARQUIVO_DADOS)) {
            // Converte a árvore para lista e depois para JSON, salvando no arquivo.
            List<Produto> listaDeProdutos = arvore.listar();
            gson.toJson(listaDeProdutos, writer);
            System.out.println("Backup realizado! " + listaDeProdutos.size() + " produtos salvos.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Carrega os produtos de um arquivo JSON para a árvore.
     * @return Uma nova ArvoreAVL com os dados carregados do arquivo.
     */
    public ArvoreAVL carregar() {
        ArvoreAVL novaArvore = new ArvoreAVL();
        File arquivo = new File(ARQUIVO_DADOS);

        if (!arquivo.exists()) {
            return novaArvore; // Retorna árvore vazia se o arquivo não existe.
        }

        try (Reader reader = new FileReader(ARQUIVO_DADOS)) {
            // Define o tipo como uma lista de produtos para o Gson.
            Type tipoLista = new TypeToken<ArrayList<Produto>>() {}.getType();
            List<Produto> listaRecuperada = gson.fromJson(reader, tipoLista);

            // Insere cada produto da lista na nova árvore.
            if (listaRecuperada != null) {
                for (Produto p : listaRecuperada) {
                    novaArvore.inserir(p);
                }
            }
            System.out.println("Sucesso! " + (listaRecuperada != null ? listaRecuperada.size() : 0) + " produtos carregados.");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return novaArvore;
    }
}
