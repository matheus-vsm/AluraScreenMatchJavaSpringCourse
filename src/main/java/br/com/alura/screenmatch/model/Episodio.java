package br.com.alura.screenmatch.model;

import jakarta.persistence.*;

import java.time.DateTimeException;
import java.time.LocalDate;

@Entity
@Table(name = "episodios")
public class Episodio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer temporada;
    private String titulo;
    private Integer numeroEpisodio;
    private Double avalicacao;
    private LocalDate dataLancamento;
    @ManyToOne // muitos episodios para uma serie
    private Serie serie;

    public Episodio() {
    }
    public Episodio(Integer numeroTemporada, DadosEpidodio dadosEpidodio) {
        this.temporada = numeroTemporada;
        this.titulo = dadosEpidodio.titulo();
        this.numeroEpisodio = dadosEpidodio.numero();
        try {
            this.avalicacao = Double.valueOf(dadosEpidodio.avalicacao());
        } catch (NumberFormatException e) {
            this.avalicacao = 0.0;
        }
        try {
            this.dataLancamento = LocalDate.parse(dadosEpidodio.dataLancamento()); //yyyy-mm-dd
        } catch (DateTimeException e){
            this.dataLancamento = null;
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Serie getSerie() {
        return serie;
    }

    public void setSerie(Serie serie) {
        this.serie = serie;
    }

    public Integer getTemporada() {
        return temporada;
    }

    public void setTemporada(Integer temporada) {
        this.temporada = temporada;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Integer getNumeroEpisodio() {
        return numeroEpisodio;
    }

    public void setNumeroEpisodio(Integer numeroEpisodio) {
        this.numeroEpisodio = numeroEpisodio;
    }

    public Double getAvalicacao() {
        return avalicacao;
    }

    public void setAvalicacao(Double avalicacao) {
        this.avalicacao = avalicacao;
    }

    public LocalDate getDataLancamento() {
        return dataLancamento;
    }

    public void setDataLancamento(LocalDate dataLancamento) {
        this.dataLancamento = dataLancamento;
    }

    @Override
    public String toString() {
        return "temporada=" + temporada +
                ", titulo='" + titulo + '\'' +
                ", numeroEpisodio=" + numeroEpisodio +
                ", avalicacao=" + avalicacao +
                ", dataLancamento=" + dataLancamento;
    }
}
