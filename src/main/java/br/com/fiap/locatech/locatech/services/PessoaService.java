package br.com.fiap.locatech.locatech.services;

import br.com.fiap.locatech.locatech.dtos.PessoaRequestDTO;
import br.com.fiap.locatech.locatech.entities.Pessoa;
import br.com.fiap.locatech.locatech.repositories.PessoaRepository;
import br.com.fiap.locatech.locatech.services.exceptions.ResourceConflictException;
import br.com.fiap.locatech.locatech.services.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PessoaService {

    private final PessoaRepository pessoaRepository;
    public PessoaService(PessoaRepository pessoaRepository) {
        this.pessoaRepository = pessoaRepository;
    }

    public List<Pessoa> findAllPessoa (int page, int size) {
        int offset = (page - 1) * size;
        return this.pessoaRepository.findAll(size, offset);
    }

    public Optional<Pessoa> findPessoaById (Long id) {
        return Optional.ofNullable(this.pessoaRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pessoa não encontrada!")));
    }

    public Optional<Pessoa> findPessoaByCpf (String cpf) {
        return this.pessoaRepository.findByCpf(cpf);
    }

    public void savePessoa(PessoaRequestDTO pessoa) {
        var pessoaEntity = new Pessoa(pessoa);
        var existPessoa = this.findPessoaByCpf(pessoa.cpf());

        if(existPessoa.isPresent()) {
            throw new ResourceConflictException("Este CPF já esta cadastrado!!");
        }
        this.pessoaRepository.save(pessoaEntity);
    }

    public void updatePessoa(Pessoa pessoa, Long id) {
        var update = this.pessoaRepository.update(pessoa, id);
        if(update == 0) {
            throw new ResourceNotFoundException("Pessoa não encontrada");
        }
    }

    public void deletePessoa(Long id) {
        var delete = this.pessoaRepository.delete(id);
        if(delete == 0) {
            throw new ResourceNotFoundException("Pessoa não encontrada");
        }
    }
}
