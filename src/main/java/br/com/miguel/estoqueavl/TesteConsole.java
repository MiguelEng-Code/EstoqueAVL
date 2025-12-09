package br.com.miguel.estoqueavl;

import br.com.miguel.estoqueavl.model.Produto;
import br.com.miguel.estoqueavl.model.estrutura.ArvoreAVL;
import java.math.BigDecimal;
import java.util.List;

public class TesteConsole {

    public static void main(String[] args) {
        System.out.println("=========================================");
        System.out.println(">>>> INICIANDO TESTE DE ESTRESSE DA AVL");
        System.out.println("=========================================\n");

        ArvoreAVL arvore = new ArvoreAVL();

        // 1. INSERÇÃO E BALANCEAMENTO
        System.out.println(">>> 1. Inserindo dados desordenados...");

        arvore.inserir(new Produto(10, "Mouse", BigDecimal.valueOf(50.00), 5));
        arvore.inserir(new Produto(20, "Teclado", BigDecimal.valueOf(150.00), 10));
        arvore.inserir(new Produto(30, "Monitor", BigDecimal.valueOf(1200.00), 2));
        arvore.inserir(new Produto(5,  "Cabo HDMI", BigDecimal.valueOf(25.00), 20));
        arvore.inserir(new Produto(15, "Mousepad", BigDecimal.valueOf(15.00), 15));

        imprimirEstoque(arvore);

        // 2. BUSCA
        System.out.println("\n>>> 2. Testando Busca...");
        int idBusca = 20;
        Produto encontrado = arvore.buscar(idBusca);
        if (encontrado != null) {
            System.out.println(" Sucesso! Encontrei: " + encontrado.getNome());
        } else {
            System.out.println(" Erro! Não encontrei o ID " + idBusca);
        }

        // 3. REMOÇÃO
        System.out.println("\n>>> 3. Removendo o ID 20 (Teclado)...");
        boolean removeu = arvore.remover(20);
        System.out.println("Operação de remover retornou: " + removeu);

        System.out.println("Listagem após remover:");
        imprimirEstoque(arvore);
    }

    // Método auxiliar para mostrar a lista bonitinha
    private static void imprimirEstoque(ArvoreAVL avl) {
        List<Produto> lista = avl.listar();
        System.out.println("--- ESTOQUE ATUAL (" + lista.size() + " itens) ---");
        for (Produto p : lista) {
            System.out.printf(String.format("ID: %d | %-10s | R$ %.2f%n",
                    p.getId(), p.getNome(), p.getPreco().doubleValue()));
        }
    }
}
