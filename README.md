# Checkpoint 4 — Bug Hunt StreamFIAP

## Identificação

**Grupo:** Bug Killers

| Integrante | RM | Turma |
|---|---|---|
| Gabrielly Lorentz | 565806 | 2CCPO |
| Giovanna Praieiro | 565681 | 2CCPO |
| Heitor Barbosa | 563078 | 2CCPO |
| Julia Aparicio | 563623 | 2CCPO |
| Maria Eduarda de Oliveira | 565386 | 2CCPO |
| Nicolle Calasans | 564381 | 2CCPO |

| Campo | |
|---|---|
| **Total de bugs corrigidos** | 2 / 12 |
| **Total de ajustes de Clean Code** | 1 / 6 |

---

## Parte 1 — Bugs encontrados

| # | Sintoma observado (o que fiz/vi) | Causa raiz (arquivo e linha aproximada) | Correção aplicada | Conceito da disciplina |
|---|---|---|---|---|
| bug01 | A API permitia cadastrar conteúdos com duração igual a 0 ou menor que 0. | `Conteudo.java` — o construtor não possuía validação para `duracaoMinutos`. | Foi adicionada uma validação para rejeitar durações menores ou iguais a zero. | Validação de regras de negócio e programação defensiva. |
| bug02 | A consulta por categoria retornava uma lista vazia mesmo existindo conteúdos cadastrados naquela categoria. | `ConteudoController.java` — a comparação de Strings era realizada utilizando `==`. | A comparação foi alterada para `categoria.equals(c.getCategoria())`. | Comparação de objetos e Strings em Java. |

---

## Parte 2 — Ajustes de Clean Code

| # | Onde estava | Qual princípio/boas práticas era violado | O que eu mudei |
|---|---|---|---|
| clean01 | `Conteudo.java` — o atributo `duracaoMinutos` estava como `public` e era acessado diretamente nos controllers de Filme, Série e Documentário. | Violação do princípio de encapsulamento. | O atributo foi alterado para `private` e os controllers passaram a utilizar `getDuracaoMinutos()`. |

---

## Parte 3 — Perguntas de reflexão

### 1. Injeção de dependência (Aula 13)

Os controllers recebem os repositories via `@Autowired` (ex.: `ConteudoController` usa `ConteudoRepository`). Explique por que o Spring precisa gerenciar esses objetos em vez de criarmos com `new ConteudoRepository()`. O que exatamente o Spring faz ao injetar um bean, e por que isso não funcionaria com um `new` comum?

### 2. JDBC vs Spring Data JPA (Aulas 12 e 13)

Na Aula 12 escrevemos um `ProdutoDAO` na mão com `Connection`, `PreparedStatement` e `ResultSet`. Aqui o `ConteudoRepository` tem 2 linhas e faz CRUD completo. Compare as duas abordagens: o que o Spring Data JPA automatiza, o que o JDBC/DAO ainda resolve melhor, e como o `findByCategoria` consegue funcionar sem implementação.

### 3. Exceções checked vs unchecked (Aula 11)

A `ClassificacaoIndicativaException` estourava como um erro genérico do servidor, sem mensagem útil para o cliente. Explique a diferença entre `extends Exception` e `extends RuntimeException` no contexto desse bug, e como você fez a mensagem da regra (classificação indicativa) chegar de forma clara ao cliente da API.

### 4. Sobrescrita vs sobrecarga (Aula 7)

Um dos bugs compilava sem nenhum erro: o método da `Serie` parecia sobrescrever `calcularPrecoAluguel`, mas na verdade sobrecarregava. Explique a diferença entre override e overload nesse caso e por que a anotação `@Override` teria impedido o bug.

### 5. Onde blindar o objeto? (Aulas 3, 4 e 13)

Vimos bugs de dados inválidos aceitos (duração negativa, créditos negativos, campos nulos). Em quais lugares (construtor, setter, método do model) cada tipo de validação deve ficar? Justifique usando os bugs que você encontrou e explique por que validar só em um lugar não foi suficiente.

### 6. Abstração e interface (Aulas 8 e 9)

`Conteudo` é abstrata e `Promocionavel` é uma interface. Explique a diferença de propósito entre as duas nesse projeto e o que mudaria no código se o Documentário passasse a ter promoções — quais classes/linhas seriam tocadas e quais ficariam intactas? O que isso diz sobre o design do sistema?

---

## Parte 4 — Espaço livre

O checkpoint é complexo, mas muito interessante e ajuda a desenvolver habilidades importantes para situações reais do mercado de trabalho.