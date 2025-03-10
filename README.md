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
## Neste parte serão feitas as documentações acerca do Parser e Interpreter

# **Árvore Sintática Abstrata (AST) para a Linguagem Lang**  

Este projeto implementa a **AST (Abstract Syntax Tree)** da linguagem Lang, estruturando o código-fonte e representando a sintaxe da linguagem.  

## **Como Rodar**  

1. **Acesse o diretório do parser**  

2. **Compile e execute a AST:**  
   ```bash
   make AST
   ```

3. **Limpe os arquivos gerados:**  
   ```bash
   make clean
   ```  

O programa exibe no terminal a estrutura da AST gerada a partir do código definido em **AST.java**.
o Mafefile que se deve usar é o no diretório raíz


Pra construção dos nós da AST:
1. cria nó no pacote apropriado
2. adiciona-o no nodes Visitor.java
3. adiciona-o na implementação do interpretador, no interp.java


preciso representar, com nós, if, print, iterate, operadores binarios (todos), função, um nó pra representar bind de tipo, um nó pra representar
tipos (int, bool, char...), arr, userDataType, declaração de tipo de dados (bind de tipo), nó de atribuição.