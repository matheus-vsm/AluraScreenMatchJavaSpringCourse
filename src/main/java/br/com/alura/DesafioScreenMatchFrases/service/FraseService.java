package br.com.alura.DesafioScreenMatchFrases.service;

import br.com.alura.DesafioScreenMatchFrases.dto.FraseDTO;
import br.com.alura.DesafioScreenMatchFrases.model.Frase;
import br.com.alura.DesafioScreenMatchFrases.repository.FraseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FraseService {
    @Autowired
    private FraseRepository repositorio;

    public FraseDTO obterFraseAleatoria() {
        Frase frase = repositorio.gerarFraseAleatoria();
        return new FraseDTO(frase.getTitulo(), frase.getTitulo(), frase.getPersonagem(), frase.getPoster());
    }
}
