package br.com.fiap.locatech.locatech.entities;
import br.com.fiap.locatech.locatech.dtos.PessoaRequestDTO;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class Pessoa {
    private Long id;
    private String nome;
    private String cpf;
    private String telefone;
    private String email;

    public Pessoa(PessoaRequestDTO pessoa) {
        this.nome = pessoa.nome();
        this.cpf = pessoa.cpf();
        this.telefone = pessoa.telefone();
        this.email = pessoa.email();
    }
}
