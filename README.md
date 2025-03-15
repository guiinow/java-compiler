// Guilherme Ferreira 19.2.8981
//Breno Rotte 20.1.8124

# Analisador Léxico para a Linguagem Lang

Este projeto é um analisador léxico desenvolvido para a linguagem fictícia "Lang". Ele utiliza a ferramenta **JFlex** para gerar um lexer (analisador léxico) que reconhece os tokens da linguagem com base na gramática definida.

## Estrutura dos Arquivos

- **lang_lexer.flex**: Arquivo fonte do JFlex contendo as definições das expressões regulares que identificam os tokens da linguagem.
- **Lang_lexer.java**: Arquivo gerado automaticamente pelo JFlex, responsável pelo processamento dos tokens.
- **TK.java**: Enumeração que define os tipos de tokens reconhecidos pela linguagem.
- **Token.java**: Classe que representa os tokens, armazenando informações como tipo e posição no código.
- **Main.java**: Arquivo principal que processa o arquivo de entrada e imprime os tokens reconhecidos.
- **Makefile**: Automação de tarefas de compilação, execução e limpeza.
- **input.txt**: Arquivo de entrada contendo o código-fonte da linguagem Lang para análise.

## Uso do Analisador Léxico

### Pré-requisitos
- **Java** (JDK instalado e configurado no PATH).
- **JFlex** (arquivo `jflex.jar`).

### Como Rodar
Para executar o analisador léxico, basta utilizar o comando `make`:

```bash
make 
```
Não é necessário passar argumentos, pois o arquivo de entrada (`teste.txt`) já está definido no código.

### O que Acontece?
O arquivo **lang_lexer.flex** é processado pelo JFlex para gerar o lexer (**Lang_lexer.java**).
Os arquivos Java são compilados.
O programa **Main** é executado, analisando o arquivo de entrada (`teste.txt`) e imprimindo os tokens reconhecidos.

### Limpar Arquivos Gerados
Para remover os arquivos compilados e outros artefatos temporários, execute:
    
```bash
make clean
```

### Saída do Programa
O analisador lê o arquivo especificado e imprime os tokens reconhecidos, incluindo o tipo e a posição no código. Se houver algum erro, ele é reportado no terminal.

#### Exemplo de Entrada e Saída
Entrada (`teste.txt`):
```java
Int x = 10;
if (x > 5) {
    print(x);
}
```

#### Saída:
```
(0,0) TK: INT
(0,4) TK: IDENTIFIER  val: x
(0,6) TK: ASSIGN
(0,8) TK: INT_LITERAL  val: 10
(0,10) TK: SEMICOLON
(1,0) TK: IF
(1,3) TK: OPEN_PARENTHESIS
(1,4) TK: IDENTIFIER  val: x
(1,6) TK: GREATER
(1,8) TK: INT_LITERAL  val: 5
(1,9) TK: CLOSE_PARENTHESIS
(1,11) TK: OPEN_BRACES
(2,4) TK: PRINT
(2,9) TK: OPEN_PARENTHESIS
(2,10) TK: IDENTIFIER  val: x
(2,11) TK: CLOSE_PARENTHESIS
(2,12) TK: SEMICOLON
(3,0) TK: CLOSE_BRACES
Token: (4,0) TK: EOF
```

---
# Estrutura do Projeto

├── **exemplos/**                 # Exemplos de código na linguagem Lang  
├── **lang/**                     # Código-fonte do compilador  
│   ├── **ast/**                  # Representação da Árvore Sintática Abstrata  
│   ├── **parser/**               # Arquivos do analisador léxico e sintático  
├── **testes/**                   # Casos de teste para sintaxe e semântica  
│   ├── **sintaxe/**              # Testes sintáticos  
│   ├── **semantica/**            # Testes semânticos  
├── **tools/**                    # Ferramentas auxiliares (CUP, JFlex, etc.)  
├── **makefile**                  # Automação da compilação  
├── **langc.sh**                  # Script para rodar o compilador  
└── **README.md**                 # Documentação do projeto  

## Compilação e Execução

### Compilar o Compilador:
Para compilar o compilador, execute o seguinte comando:
bash
make

Isso irá gerar os arquivos .class necessários.

### Rodar o Compilador:
O compilador pode ser executado com diferentes opções:
bash
./langc.sh -lex <arquivo.lan>   # Apenas listar tokens
./langc.sh -syn <arquivo.lan>   # Apenas verificar sintaxe
./langc.sh -i <arquivo.lan>     # Interpretar o código Lang


## Decisões de Projeto

### Representação da Linguagem
A linguagem Lang é representada por uma Árvore Sintática Abstrata (AST), onde cada nó da árvore representa uma estrutura da linguagem (expressões, comandos, funções, etc.).

### Analisador Léxico (JFlex)
O analisador léxico (lang/parser/lang.flex) converte o código-fonte em tokens reconhecíveis pelo analisador sintático. Ele identifica palavras-chave, operadores, literais, identificadores e símbolos especiais.

### Analisador Sintático (CUP)
O analisador sintático (lang/parser/lang.cup) usa os tokens gerados pelo léxico para construir a AST e verificar a validade da estrutura do programa. Ele lida com ambiguidades usando regras de precedência e associatividade.

### Estratégia de Interpretação
O interpretador (lang/ast/visitors/Interp.java) percorre a AST e executa o código interpretando cada nó. Para expressões, ele avalia e retorna valores, e para comandos, ele altera o estado do programa.

### Execução dos Testes
Os testes são organizados em testes/sintaxe/ e testes/semantica/, verificando tanto a estrutura sintática quanto a execução semântica dos programas Lang.

### Extensibilidade
O projeto foi estruturado de forma modular, permitindo a adição de novos recursos na linguagem Lang. Para adicionar um novo comando, é necessário:
- Criar um token no léxico (lang.flex)
- Criar uma regra gramatical no parser (lang.cup)
- Criar uma classe na AST para representar a nova construção
- Implementar o comportamento no interpretador (Interp.java)
- Criar testes para validar o novo recurso

## Ferramentas Utilizadas
- Java CUP para geração do analisador sintático
- JFlex para geração do analisador léxico
- Java como linguagem principal
- Makefile para automação da compilação

## Considerações Finais
Este projeto serve como um estudo de caso para a construção de compiladores educacionais, cobrindo aspectos fundamentais da análise léxica, sintática e semântica. Ele pode ser expandido com novas funcionalidades, como suporte a novos operadores, estruturas de controle e otimizações.