package br.com.alura.screenmatch.repository;

import br.com.alura.screenmatch.model.Categoria;
import br.com.alura.screenmatch.model.Serie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SerieRepository extends JpaRepository<Serie, Long> { // long = tipo do dado Id
    // Derivate Queries (Consultas Derivadas)
    // esse metodo/query serve para buscarmos pelo Titulo da Serie ignorando maiusculas e minusculas | Optional pois ele pode retornar uma Serie ou não caso não encontre
    Optional<Serie> findByTituloContainingIgnoreCase(String nomeSerie);

    List<Serie> findByAtoresContainingIgnoreCaseAndAvaliacaoGreaterThanEqual(String nomeAtor, Double avaliacao);

    // busca as 5 primeiras séries, ordenadas por avaliacao em ordem decrescente
    List<Serie> findTop5ByOrderByAvaliacaoDesc();

    List<Serie> findByGenero(Categoria categoria);

    List<Serie> findByTotalTemporadasLessThanEqualAndAvaliacaoGreaterThanEqualOrderByTotalTemporadasDesc(Integer tempodada, Double avaliacao);
}
