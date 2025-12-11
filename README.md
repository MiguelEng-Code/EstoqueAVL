| Sistema de Gerenciamento de Estoque com Árvore AVL |	

1. Descrição da Aplicação
   
  Este projeto consiste em uma aplicação Desktop desenvolvida em Java (JDK 21) utilizando a biblioteca gráfica JavaFX. O objetivo do software é simular um sistema eficiente de controle de estoque, permitindo o cadastro, visualização, busca e remoção de produtos.
  A arquitetura do projeto segue o padrão MVC (Model-View-Controller), garantindo a separação de responsabilidades entre a interface do usuário (.fxml), a lógica de controle e a estrutura de dados.
  

•	Principais Funcionalidades:
  * Interface Gráfica Moderna: Telas desenhadas com Scene Builder, utilizando design responsivo e ícones.
  *	CRUD de Produtos: Cadastro de itens com validação de dados (ID numérico, preço, etc.).
  *	Busca Otimizada: Localização instantânea de produtos pelo ID ou filtragem por nome.
  *	Persistência de Dados: O sistema utiliza a biblioteca GSON para salvar os dados automaticamente em um arquivo JSON (estoque_dados.json) ao fechar a aplicação, e recarregá-los ao iniciar, garantindo que nenhum registro seja perdido.
    

2. Justificativa do Uso da Estrutura de Dados (Árvore AVL)
   
  A escolha da Árvore AVL (Adelson-Velsky e Landis) como estrutura de dados central do projeto deve-se à necessidade de alta performance nas operações de busca e manutenção da ordem dos dados.
  Em um sistema de estoque, a operação mais crítica é a busca de um produto pelo seu código (ID).
  Em uma Lista/Array: A busca teria complexidade linear O(n). Para encontrar um item em 1 milhão de registros, o sistema poderia ter que verificar 1 milhão de posições.
  Em uma Árvore Binária Comum: No pior caso (inserção ordenada: 1, 2, 3...), a árvore degenera para uma lista ligada, mantendo a complexidade O(n).

•	A Solução AVL:
  A Árvore AVL é uma árvore binária de busca auto-balanceada. Ela garante que a diferença de altura entre as subárvores esquerda e direita de qualquer nó seja no máximo 1. Isso assegura que a altura da árvore permaneça logarítmica.

•	Benefícios Práticos no Projeto:
  Complexidade Garantida: Todas as operações (Inserção, Remoção e Busca) têm complexidade de tempo O(\log n). Mesmo com milhões de produtos, a busca é quase instantânea.
  Ordenação Automática: Ao percorrer a árvore "em ordem" (In-Order Traversal), os produtos são listados automaticamente em ordem crescente de ID na tabela da interface, sem necessidade de algoritmos de ordenação adicionais como QuickSort ou MergeSort.


3. Instruções de Execução
Para rodar este projeto em sua máquina local, siga os passos abaixo.
Pré-requisitos:
- Java JDK 21 instalado.
- Maven (geralmente incluso nas IDEs modernas).
- IntelliJ IDEA (Recomendado) ou outra IDE com suporte a JavaFX.
  
1)	Abrir no IntelliJ:
Abra a IDE e selecione Open.
Navegue até a pasta do projeto e selecione o arquivo pom.xml ou a pasta raiz.
Selecione Open as Project.

2)	Carregar Dependências:
O IntelliJ deve detectar o arquivo pom.xml automaticamente. Clique no ícone de Reload Maven (o "M" com setas girando na aba lateral direita) para baixar as bibliotecas necessárias (JavaFX, ControlsFX, GSON, Lombok).

3)	Executar:
Navegue até a classe principal:
src/main/java/br/com/miguel/estoqueavl/mainApp.java 
Clique com o botão direito e selecione Run 'mainApp'.
