Checkpoint 4 — Bug Hunt StreamFIAP

Identificação

Grupo: Bug Killers

Integrante

RM

Turma

Gabrielly Lorentz

565806

2CCPO

Giovanna Praieiro

565681

2CCPO

Heitor Barbosa

563078

2CCPO

Julia Aparicio

563623

2CCPO

Maria Eduarda de Oliveira

565386

2CCPO

Nicolle Calasans

564381

2CCPO

Campo

Resultado

Total de bugs corrigidos

12 / 12

Total de ajustes de Clean Code

6 / 6

Parte 1 — Bugs encontrados

#

Sintoma observado

Causa raiz

Correção aplicada

Conceito da disciplina

bug01

A API permitia cadastrar conteúdos com duração igual a 0 ou menor que 0.

Conteudo.java — o construtor não possuía validação para duracaoMinutos.

Foi adicionada uma validação para rejeitar durações menores ou iguais a zero.

Validação de regras de negócio e programação defensiva.

bug02

A consulta por categoria retornava uma lista vazia mesmo existindo conteúdos cadastrados naquela categoria.

ConteudoController.java — a comparação de Strings era realizada utilizando ==.

A comparação foi alterada para categoria.equals(c.getCategoria()).

Comparação de objetos e Strings em Java.

bug03

A promoção do filme estava aumentando o preço em 20%, em vez de aplicar o desconto previsto no contrato.

Filme.java — o método aplicarPromocao() multiplicava o preço por 1.2.

A multiplicação foi alterada para 0.8, aplicando corretamente o desconto de 20%.

Polimorfismo, interface e regras de negócio.

bug04

O preço da série não era calculado corretamente porque o método da Serie não sobrescrevia o método de Conteudo.

Serie.java — o método calcularPrecoAluguel(double desconto) possuía uma assinatura diferente do método da classe pai.

O método foi corrigido para realizar a sobrescrita correta, utilizando @Override e calculando R$ 4,90 por temporada.

Herança, sobrescrita (override) e polimorfismo.

bug05

O nome do usuário não era salvo corretamente ao utilizar o construtor.

Usuario.java — a atribuição do nome não utilizava this.

Adicionada a atribuição this.nome = nome;.

Construtores, atributos e escopo de variáveis.

bug06

A entidade de usuário não possuía geração automática do ID no banco de dados.

Usuario.java — o atributo id possuía apenas a anotação @Id.

Adicionada @GeneratedValue(strategy = GenerationType.IDENTITY).

JPA e persistência de entidades.

bug07

O sistema permitia realizar aluguel mesmo quando o usuário não possuía créditos suficientes.

Usuario.java — não havia validação do saldo antes do débito.

Adicionada a validação if (this.creditos < preco), lançando exceção quando os créditos são insuficientes.

Validação de regras de negócio e tratamento de exceções.

bug08

Conteúdos que já estavam alugados podiam ser alugados novamente.

Usuario.java — não havia verificação da disponibilidade do conteúdo.

Adicionada a verificação if (!conteudo.isDisponivel()) antes do aluguel.

Encapsulamento e regras de negócio.

bug09

A exceção de classificação indicativa resultava em erro genérico 500 Internal Server Error.

GlobalExceptionHandler.java não possuía tratamento específico para ClassificacaoIndicativaException.

Adicionado @ExceptionHandler retornando 403 FORBIDDEN e a mensagem da exceção em JSON.

Tratamento global de exceções e HTTP Status.

bug10

Erros relacionados ao processo de aluguel não possuíam retorno HTTP padronizado.

GlobalExceptionHandler.java não tratava de forma centralizada as exceções de aluguel.

Adicionado tratamento para IllegalArgumentException e IllegalStateException, retornando 400 BAD_REQUEST com mensagem em JSON.

Tratamento de exceções e APIs REST.

bug11

A busca de um conteúdo por um ID inexistente não retornava corretamente o status HTTP esperado.

ConteudoController.java — o resultado do findById() não era tratado adequadamente.

Utilizado Optional com .map(ResponseEntity::ok) e .orElse(ResponseEntity.notFound().build()), retornando 404 NOT_FOUND.

Optional, ResponseEntity e HTTP Status.

bug12

A busca de conteúdos por categoria apresentava problemas quando havia diferença entre letras maiúsculas e minúsculas.

ConteudoController.java e consulta do Repository não tratavam a diferença de capitalização.

Utilizado findByCategoriaIgnoreCase(categoria) para realizar a busca ignorando maiúsculas e minúsculas.

Spring Data JPA e consultas derivadas.

Parte 2 — Ajustes de Clean Code

#

Onde estava

Princípio/boa prática

O que foi alterado

clean01

Conteudo.java — o atributo duracaoMinutos estava como public.

Violação do encapsulamento.

O atributo foi alterado para private e os controllers passaram a utilizar getDuracaoMinutos().

clean02

ConteudoController.java — havia método antigo de desconto e código comentado relacionado a cupons.

Código morto e comentários obsoletos prejudicando a manutenção.

O método calcularDescontoAntigo() e o bloco de código comentado foram removidos.

clean03

UsuarioController.java — havia variáveis com nomes genéricos e pouco descritivos.

Falta de nomes significativos.

As variáveis foram renomeadas para usuarioParaCadastrar e usuarioSalvo, tornando o código mais claro.

clean04

Usuario.java — o processo de aluguel concentrava diversas regras de negócio.

Método com múltiplas responsabilidades e baixa legibilidade.

A lógica do processo de aluguel foi reorganizada para deixar as validações e o débito mais claros e facilitar a manutenção.

clean05

GlobalExceptionHandler.java — diferentes erros poderiam possuir estruturas de resposta diferentes.

Falta de padronização nas respostas da API.

A resposta foi padronizada utilizando Map.of("erro", ex.getMessage()) dentro de um tratamento global.

clean06

ConteudoController.java — havia lógica de verificação do resultado da busca de conteúdo.

Código verboso e verificações desnecessárias.

Utilizada sintaxe funcional com Optional, Lambda e Method Reference, como map(ResponseEntity::ok) e orElse(...).

Parte 3 — Cadastro de Usuário

Bug 05

O problema ocorria porque o parâmetro nome do construtor possuía o mesmo nome do atributo da classe. Sem o uso de this, a atribuição não alterava corretamente o atributo do objeto.

A correção foi:

this.nome = nome;

Dessa forma, this.nome representa o atributo do objeto, enquanto nome representa o parâmetro recebido pelo construtor.

Bug 06

O atributo id possuía @Id, porém não estava configurado para ser gerado automaticamente pelo banco.

Foi adicionada:

@GeneratedValue(strategy = GenerationType.IDENTITY)

Assim, o JPA passa a utilizar a estratégia de geração automática do identificador.

Clean Code 03

No UsuarioController, as variáveis foram renomeadas para representar melhor sua finalidade:

Usuario usuarioSalvo = usuarioRepository.save(usuarioParaCadastrar);

Os nomes deixam explícito que usuarioParaCadastrar representa o usuário recebido pela API e usuarioSalvo representa o resultado da persistência.

Parte 4 — Regras de Aluguel

Bug 07

Antes de realizar o débito dos créditos, o sistema passou a calcular o preço do aluguel e verificar se o usuário possui saldo suficiente:

if (this.creditos < preco) {
    throw new IllegalArgumentException(
        "Creditos insuficientes para alugar o conteudo."
    );
}

Isso impede que o saldo do usuário fique negativo.

Bug 08

Também foi adicionada uma validação para impedir que um conteúdo indisponível seja alugado novamente:

if (!conteudo.isDisponivel()) {
    throw new IllegalStateException(
        "O conteudo '" + conteudo.getTitulo() +
        "' esta indisponivel para aluguel."
    );
}

Somente após passar pelas validações o sistema realiza o débito e altera a disponibilidade:

this.creditos -= preco;
conteudo.setDisponivel(false);

Clean Code 04

A lógica do processo de aluguel foi reorganizada para deixar as regras de negócio mais claras, separando conceitualmente as etapas de:

Verificação da disponibilidade;

Cálculo do preço;

Verificação dos créditos;

Débito dos créditos;

Atualização da disponibilidade.

Essa organização facilita a leitura e manutenção do método.

Parte 5 — Exceções e Tratamento de Erros

Bug 09

Foi criado um tratamento específico para ClassificacaoIndicativaException:

@ExceptionHandler(ClassificacaoIndicativaException.class)
public ResponseEntity<Map<String, String>>
handleClassificacaoIndicativa(ClassificacaoIndicativaException ex) {
    return ResponseEntity.status(HttpStatus.FORBIDDEN)
            .body(Map.of("erro", ex.getMessage()));
}

Com isso, uma violação da classificação indicativa passa a retornar HTTP 403 — FORBIDDEN, juntamente com uma mensagem clara para o cliente.

Bug 10

Foram tratadas as exceções relacionadas ao processo de aluguel:

@ExceptionHandler({
    IllegalArgumentException.class,
    IllegalStateException.class
})

Essas exceções passam a retornar:

HTTP 400 — BAD_REQUEST

com uma estrutura JSON padronizada:

{
    "erro": "mensagem do erro"
}

Clean Code 05

Foi utilizado um @RestControllerAdvice para centralizar o tratamento das exceções da aplicação.

Isso evita duplicação de código nos controllers e mantém as respostas de erro padronizadas.

Parte 6 — Controllers e Consultas

Bug 11

A busca por ID passou a utilizar o retorno opcional do repository:

return conteudoRepository.findById(id)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());

Quando o conteúdo existe, a API retorna 200 OK.

Quando não existe, retorna 404 NOT_FOUND.

Bug 12

A busca por categoria foi implementada utilizando:

conteudoRepository.findByCategoriaIgnoreCase(categoria);

Dessa maneira, a consulta não diferencia letras maiúsculas e minúsculas.

Por exemplo, buscas por:

Filme
filme
FILME

podem encontrar conteúdos da mesma categoria.

Clean Code 06

A lógica de busca por ID foi simplificada utilizando recursos funcionais do Java, principalmente:

Optional;

Lambda;

Method Reference;

map();

orElse().

A utilização de:

.map(ResponseEntity::ok)

evita a necessidade de escrever manualmente uma estrutura de if/else para verificar se o conteúdo foi encontrado.

Parte 7 — Perguntas de reflexão

1. Injeção de dependência

Os controllers recebem os repositories por meio do @Autowired. O Spring gerencia esses objetos porque o Repository é um bean controlado pelo container do Spring.

Ao injetar um bean, o Spring cria e gerencia a instância do objeto e fornece essa instância ao controller. Isso permite que o controller utilize o repository sem precisar conhecer os detalhes de sua criação.

Se utilizássemos:

new ConteudoRepository()

não teríamos o gerenciamento do Spring sobre esse objeto. Além disso, um Repository do Spring Data JPA não é simplesmente uma classe comum que deve ser instanciada manualmente: o framework cria sua implementação em tempo de execução.

2. JDBC vs Spring Data JPA

No JDBC tradicional, o desenvolvedor precisa escrever grande parte do código responsável pelo acesso ao banco, utilizando elementos como Connection, PreparedStatement e ResultSet.

No Spring Data JPA, grande parte desse trabalho é automatizada. O Repository pode herdar métodos como:

findAll()
findById()
save()
delete()

Além disso, consultas podem ser criadas pelo próprio nome do método. Por exemplo:

findByCategoriaIgnoreCase(String categoria)

O Spring Data interpreta o nome do método e cria a consulta correspondente automaticamente, sem que seja necessário implementar o método manualmente.

O JDBC/DAO continua sendo útil quando precisamos de maior controle sobre consultas SQL específicas ou operações que não se encaixam bem na abstração do JPA.

3. Exceções checked vs unchecked

Uma exceção que herda de Exception é normalmente uma checked exception, exigindo tratamento ou declaração no método.

Já uma exceção que herda de RuntimeException é uma unchecked exception, não exigindo que o método declare ou trate obrigatoriamente a exceção.

No projeto, o problema da ClassificacaoIndicativaException foi resolvido utilizando um tratamento global com @RestControllerAdvice e @ExceptionHandler.

Dessa forma, quando a exceção ocorre, o Spring direciona o erro para:

handleClassificacaoIndicativa(...)

e a API retorna:

{
    "erro": "mensagem da exceção"
}

com status 403 FORBIDDEN.

4. Sobrescrita vs sobrecarga

Override (sobrescrita) acontece quando uma classe filha redefine um método herdado mantendo a mesma assinatura.

Overload (sobrecarga) acontece quando existem métodos com o mesmo nome, mas parâmetros diferentes.

No bug da Serie, o método possuía uma assinatura diferente da definida na classe pai. Por isso, o Java interpretava como outro método, e não como uma sobrescrita.

A anotação:

@Override

teria impedido esse problema porque o compilador verificaria se realmente existe um método correspondente na classe pai.

Se a assinatura estivesse incorreta, o código apresentaria erro de compilação.

5. Onde blindar o objeto?

As validações devem ficar próximas do ponto em que a regra de negócio precisa ser garantida.

No construtor, podemos impedir a criação de objetos com dados inválidos, como uma duração menor ou igual a zero.

Nos setters, podemos proteger alterações posteriores nos atributos.

Nos métodos de negócio do model, devem ficar regras relacionadas ao comportamento do objeto. No caso do aluguel, por exemplo, é necessário verificar se existem créditos suficientes e se o conteúdo está disponível.

Os bugs encontrados mostram que validar somente em um ponto pode não ser suficiente, pois os objetos podem ser criados ou alterados por diferentes caminhos dentro da aplicação.

6. Abstração e interface

Conteudo é uma classe abstrata utilizada para representar características e comportamentos comuns aos diferentes tipos de conteúdo.

Já Promocionavel é uma interface que representa uma capacidade que pode ser adicionada às classes que precisam oferecer promoções.

Se o Documentario passasse a ter promoções, seria necessário alterar principalmente a classe Documentario, fazendo-a implementar Promocionavel e implementando o comportamento exigido pela interface.

As classes que já possuem o comportamento de promoção não precisariam ser modificadas.

Isso demonstra uma vantagem do uso de interfaces: podemos adicionar determinados comportamentos a classes diferentes sem precisar alterar toda a hierarquia de classes.

Parte 8 — Espaço livre

O checkpoint permitiu identificar e corrigir diferentes tipos de problemas presentes em uma aplicação Java com Spring Boot. Os bugs envolveram regras de negócio, persistência, tratamento de exceções, consultas e respostas HTTP.

Além das correções funcionais, os ajustes de Clean Code contribuíram para melhorar a organização, legibilidade, encapsulamento e manutenção do projeto.

A atividade também permitiu relacionar conceitos estudados durante as aulas, como JPA, Spring Data, injeção de dependência, herança, polimorfismo, interfaces, exceções, Optional e boas práticas de programação.

Resultado final: 12 bugs corrigidos e 6 ajustes de Clean Code realizados.