package br.com.miguel.estoqueavl.model;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// Classe que representa um Produto no estoque.

// Plugin Lombok
@Data // Gera automaticamente getters, setters, toString, equals e hashCode.
@AllArgsConstructor // Gera um construtor com todos os campos da classe.
@NoArgsConstructor // Gera um construtor vazio.

public class Produto implements Comparable<Produto> {

    private int id;
    private String nome;
    private BigDecimal preco;
    private int quantidade;

    /**
     * Compara dois produtos com base em seus IDs.
     * Essencial para ordenação da árvore AVL.
     * @param o O outro produto a ser comparado.
     * @return um valor negativo, zero ou positivo se o ID deste produto for menor, igual ou maior que o do outro produto.
     */
    @Override
    public int compareTo(Produto o) {
        return Integer.compare(this.id, o.getId());
    }
}
