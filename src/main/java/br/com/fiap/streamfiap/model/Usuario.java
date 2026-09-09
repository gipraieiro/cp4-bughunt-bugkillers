package br.com.fiap.streamfiap.model;
 
import jakarta.persistence.*;
 
@Entity

@Table(name = "usuarios")

public class Usuario {
 
    // Bug 06: Adicionada estratégia de geração automática de ID

    @Id

    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;
 
    private String nome;

    private int idade;

    private double creditos;
 
    public Usuario() {}
 
    public Usuario(String nome, int idade, double creditos) {

        // Bug 05: Corrigida a atribuição do nome utilizando o operador 'this'

        this.nome = nome;

        this.idade = idade;

        this.creditos = creditos;

    }
 
    // Getters e Setters

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }

    public void setNome(String nome) { this.nome = nome; }

    public int getIdade() { return idade; }

    public void setIdade(int idade) { this.idade = idade; }

    public double getCreditos() { return creditos; }

    public void setCreditos(double creditos) { this.creditos = creditos; }

}
 