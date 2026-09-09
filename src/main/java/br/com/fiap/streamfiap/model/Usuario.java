public void alugarConteudo(Conteudo conteudo) {

    // Bug 08: Impedir o aluguel de conteúdo indisponível

    if (!conteudo.isDisponivel()) {

        throw new IllegalStateException("O conteudo '" + conteudo.getTitulo() + "' esta indisponivel para aluguel.");

    }
 
    double preco = conteudo.calcularPrecoAluguel();
 
    // Bug 07: Validar creditos insuficientes antes de realizar o debito

    if (this.creditos < preco) {

        throw new IllegalArgumentException("Creditos insuficientes para alugar o conteudo.");

    }
 
    this.creditos -= preco;

    conteudo.setDisponivel(false);

}