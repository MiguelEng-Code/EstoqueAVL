package br.com.miguel.estoqueavl.model.estrutura;

import br.com.miguel.estoqueavl.model.Produto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// Classe que representa um No da árvore AVL.

// Plugin Lombok
@Data // Gera automaticamente getters, setters, toString, equals e hashCode.
@AllArgsConstructor // Gera um construtor com todos os campos da classe.
@NoArgsConstructor // Gera um construtor vazio.

public class No {
    private Produto produto;
    private No esquerda;
    private No direita;
    private int altura;

    public No(Produto produto) {
        this.produto = produto;
        this.esquerda = null;
        this.direita = null;
        this.altura = 1; // Novo nó é uma folha com altura 1
    }
}
