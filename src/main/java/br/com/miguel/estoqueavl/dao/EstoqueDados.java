package br.com.miguel.estoqueavl.dao;

import br.com.miguel.estoqueavl.model.estrutura.ArvoreAVL;

/**
 * Mantém a instância única da árvore de estoque para toda a aplicação,
 * funcionando como um ponto de acesso central aos dados.
 */
public class EstoqueDados {

    /**
     * Instância estática da árvore que armazena todos os produtos.
     * Acessível globalmente através de `EstoqueDados.arvore`.
     */
    public static ArvoreAVL arvore = new ArvoreAVL();
}
