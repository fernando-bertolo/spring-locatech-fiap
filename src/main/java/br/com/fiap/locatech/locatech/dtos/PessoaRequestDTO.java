package br.com.fiap.locatech.locatech.dtos;

import jakarta.validation.constraints.Email;
import org.hibernate.validator.constraints.br.CPF;

public record PessoaRequestDTO (
        String nome,
        @CPF(message = "CPF inválido")
        String cpf,
        String telefone,
        @Email(message = "Email inválido")
        String email
){
}
