package br.com.miguel.estoqueavl.model.estrutura;

import br.com.miguel.estoqueavl.model.Produto;

import java.util.ArrayList;
import java.util.List;

// Classe que representa uma AVL

/**
 * Implementação de uma Árvore AVL para armazenar e gerenciar Produtos.
 * A Árvore AVL é uma árvore de busca binária auto-balanceada, que garante
 * que as operações de inserção, busca e remoção tenham complexidade de tempo
 * logarítmica (O(log n)).
 */
public class ArvoreAVL {

    private No raiz;

    // Construtor da árvore AVL. Inicializa a raiz como nula
    public ArvoreAVL() {
        this.raiz = null;
    }

    // ###### MÉTODOS PÚBLICOS ######

    /**
     * Busca um produto na árvore pelo seu ID.
     * @param id O ID do produto a ser buscado.
     * @return O objeto Produto se encontrado, caso contrário, null.
     */
    public Produto buscar(int id) {
        return buscarRecursivo(this.raiz, id);
    }

    /**
     * Insere um novo produto na árvore.
     * @param produto O produto a ser inserido.
     * @throws RuntimeException Se já existir um produto com o mesmo ID na árvore.
     */
    public void inserir(Produto produto) {
        if (buscar(produto.getId()) != null) {
            throw new RuntimeException("Já existe um produto cadastrado com o ID " + produto.getId());
        }
        this.raiz = inserirRecursivo(this.raiz, produto);
    }

    /**
     * Remove um produto da árvore pelo seu ID.
     * @param id O ID do produto a ser removido.
     * @return true se o produto foi removido com sucesso, false se o produto não foi encontrado.
     */
    public boolean remover(int id) {
        if (buscar(id) == null) return false;
        this.raiz = removerRecursivo(this.raiz, id);
        return true;
    }

    /**
     * Retorna uma lista com todos os produtos da árvore ordenados pelo ID.
     * Útil para exibir na tabela da interface gráfica e para salvar em arquivo.
     * @return Uma lista ordenada de produtos.
     */
    public List<Produto> listar() {
        List<Produto> lista = new ArrayList<>();
        percorrerEmOrdem(this.raiz, lista);
        return lista;
    }

    // ###### MÉTODOS AUXILIARES ######

    /**
     * Retorna a altura de um nó.
     * @param n O nó para calcular a altura.
     * @return A altura do nó, ou 0 se o nó for nulo.
     */
    private int altura(No n) {
        return (n == null) ? 0 : n.getAltura();
    }

    /**
     * Calcula o fator de balanceamento de um nó.
     * @param n O nó para calcular o fator de balanceamento.
     * @return O fator de balanceamento do nó.
     */
    private int getFatorBalanceamento(No n) {
        return (n == null) ? 0 : altura(n.getEsquerda()) - altura(n.getDireita());
    }

    /**
     * Encontra o nó com o menor valor (mais à esquerda) a partir de um nó.
     * @param atual O nó inicial da busca.
     * @return O nó com o menor valor.
     */
    private No encontrarMenorNo(No atual) {
        while (atual.getEsquerda() != null) {
            atual = atual.getEsquerda();
        }
        return atual;
    }

    /**
     * Atualiza a altura de um nó com base na altura de seus filhos.
     * @param no O nó a ter sua altura atualizada.
     */
    private void atualizarAltura(No no) {
        if (no != null)
            no.setAltura(1 + Math.max(altura(no.getEsquerda()), altura(no.getDireita())));
    }

    /**
     * Realiza o balanceamento de um nó, aplicando as rotações necessárias.
     * @param no O nó a ser balanceado.
     * @return O nó balanceado (a nova raiz da subárvore).
     */
    private No balancear(No no) {
        if (no == null) return null;

        atualizarAltura(no);
        int saldo = getFatorBalanceamento(no);

        // Rotação Simples à Direita
        if (saldo > 1 && getFatorBalanceamento(no.getEsquerda()) >= 0)
            return rotacaoDireita(no);

        // Rotação Dupla à Direita (Esquerda-Direita)
        if (saldo > 1 && getFatorBalanceamento(no.getEsquerda()) < 0) {
            no.setEsquerda(rotacaoEsquerda(no.getEsquerda()));
            return rotacaoDireita(no);
        }

        // Rotação Simples à Esquerda
        if (saldo < -1 && getFatorBalanceamento(no.getDireita()) <= 0)
            return rotacaoEsquerda(no);

        // Rotação Dupla à Esquerda (Direita-Esquerda)
        if (saldo < -1 && getFatorBalanceamento(no.getDireita()) > 0) {
            no.setDireita(rotacaoDireita(no.getDireita()));
            return rotacaoEsquerda(no);
        }

        return no;
    }

    // ###### ROTAÇÕES ######

    /**
     * Realiza uma rotação simples à direita.
     * @param y O nó pivô da rotação.
     * @return A nova raiz da subárvore após a rotação.
     */
    private No rotacaoDireita(No y) {
        No x = y.getEsquerda();
        No T2 = x.getDireita();

        x.setDireita(y);
        y.setEsquerda(T2);

        atualizarAltura(y);
        atualizarAltura(x);

        return x;
    }

    /**
     * Realiza uma rotação simples à esquerda.
     * @param x O nó pivô da rotação.
     * @return A nova raiz da subárvore após a rotação.
     */
    private No rotacaoEsquerda(No x) {
        No y = x.getDireita();
        No T2 = y.getEsquerda();

        y.setEsquerda(x);
        x.setDireita(T2);

        atualizarAltura(x);
        atualizarAltura(y);

        return y;
    }

    // ###### MÉTODOS DE RECURSÃO ######

    /**
     * Método recursivo para inserir um produto na árvore de forma balanceada.
     * @param atual   O nó atual na recursão.
     * @param produto O produto a ser inserido.
     * @return O nó raiz da subárvore modificada.
     */
    private No inserirRecursivo(No atual, Produto produto) {
        if (atual == null) return new No(produto);

        if (produto.getId() < atual.getProduto().getId())
            atual.setEsquerda(inserirRecursivo(atual.getEsquerda(), produto));
        else if (produto.getId() > atual.getProduto().getId())
            atual.setDireita(inserirRecursivo(atual.getDireita(), produto));
        else return atual; // Produto com mesmo ID já existe

        return balancear(atual);
    }

    /**
     * Método recursivo para remover um produto da árvore de forma balanceada.
     * @param atual O nó atual na recursão.
     * @param id    O ID do produto a ser removido.
     * @return O nó raiz da subárvore modificada.
     */
    private No removerRecursivo(No atual, int id) {
        if (atual == null) return null;

        if (id < atual.getProduto().getId())
            atual.setEsquerda(removerRecursivo(atual.getEsquerda(), id));
        else if (id > atual.getProduto().getId())
            atual.setDireita(removerRecursivo(atual.getDireita(), id));
        else {
            // Nó com um ou nenhum filho
            if (atual.getEsquerda() == null) return atual.getDireita();
            if (atual.getDireita() == null) return atual.getEsquerda();

            // Nó com dois filhos: pega o sucessor in-order (menor na subárvore direita)
            No sucessor = encontrarMenorNo(atual.getDireita());
            atual.setProduto(sucessor.getProduto());
            atual.setDireita(removerRecursivo(atual.getDireita(), sucessor.getProduto().getId()));
        }
        return balancear(atual);
    }

    /**
     * Método recursivo para buscar um produto na árvore.
     * @param atual O nó atual na recursão.
     * @param id    O ID do produto a ser buscado.
     * @return O Produto se encontrado, caso contrário, null.
     */
    private Produto buscarRecursivo(No atual, int id) {
        if (atual == null) return null;
        if (id == atual.getProduto().getId()) return atual.getProduto();

        return (id < atual.getProduto().getId())
                ? buscarRecursivo(atual.getEsquerda(), id)
                : buscarRecursivo(atual.getDireita(), id);
    }

    /**
     * Método auxiliar recursivo para percorrer a árvore em ordem (In-Order).
     * @param no O nó atual.
     * @param lista A lista onde os produtos serão adicionados.
     */
    private void percorrerEmOrdem(No no, List<Produto> lista) {
        if (no != null) {
            percorrerEmOrdem(no.getEsquerda(), lista); // Visita filhos menores
            lista.add(no.getProduto());                // Adiciona o atual
            percorrerEmOrdem(no.getDireita(), lista);  // Visita filhos maiores
        }
    }
}