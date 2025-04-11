package br.com.fiap.locatech.locatech.services;

import br.com.fiap.locatech.locatech.dtos.AluguelRequestDto;
import br.com.fiap.locatech.locatech.entities.Aluguel;
import br.com.fiap.locatech.locatech.repositories.AluguelRepository;
import br.com.fiap.locatech.locatech.repositories.VeiculoRepository;
import br.com.fiap.locatech.locatech.services.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class AluguelService {

    private final AluguelRepository aluguelRepository;
    private final VeiculoRepository veiculoRepository;

    public AluguelService(AluguelRepository aluguelRepository, VeiculoRepository veiculoRepository) {
        this.aluguelRepository = aluguelRepository;
        this.veiculoRepository = veiculoRepository;
    }

    public List<Aluguel> findAllAluguel (int page, int size) {
        int offset = (page - 1) * size;
        return this.aluguelRepository.findAll(size, offset);
    }

    public Optional<Aluguel> findAluguelById (Long id) {
        return Optional.ofNullable(this.aluguelRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Aluguel não encontrado!")));
    }

    public void saveAluguel(AluguelRequestDto aluguel) {
        var aluguelEntity = calculaAluguel(aluguel);
        var save = this.aluguelRepository.save(aluguelEntity);
        Assert.state(save == 1, "Erro ao salvar aluguel");
    }

    public void updateAluguel(Aluguel aluguel, Long id) {
        var update = this.aluguelRepository.update(aluguel, id);
        if(update == 0) {
            throw new RuntimeException("Aluguel não encontrado");
        }
    }

    public void deleteAluguel(Long id) {
        var delete = this.aluguelRepository.delete(id);
        if(delete == 0) {
            throw new RuntimeException("Aluguel não encontrado");
        }
    }

    private Aluguel calculaAluguel(AluguelRequestDto aluguelRequestDto) {
        var veiculo = this.veiculoRepository
                .findById(aluguelRequestDto.veiculoId())
                .orElseThrow(() -> new RuntimeException("Veículo não encontrado"));

        var quantidadeDias = BigDecimal.valueOf(aluguelRequestDto.dataFim().getDayOfYear() - aluguelRequestDto.dataInicio().getDayOfYear());
        var valor = veiculo.getValorDiaria().multiply(quantidadeDias);

        return new Aluguel(aluguelRequestDto, valor);
    }
}
