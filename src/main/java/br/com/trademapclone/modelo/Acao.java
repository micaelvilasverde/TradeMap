package br.com.trademapclone.modelo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Acao {
    @JsonProperty("01. symbol")
    private String simbolo;

    @JsonProperty("02. open")
    private Double precoAbertura;

    @JsonProperty("03. high")
    private Double precoMaximo;

    @JsonProperty("04. low")
    private Double precoMinimo;

    @JsonProperty("05. price")
    private Double precoAtual;

    @JsonProperty("06. volume")
    private Long volume;

    @JsonProperty("07. latest trading day")
    private String ultimaDataNegociacao;

    @JsonProperty("08. previous close")
    private Double fechamentoAnterior;

    @JsonProperty("09. change")
    private Double variacao;

    @JsonProperty("10. change percent")
    private String variacaoPercentual;

    // Getters e Setters
    public String getSimbolo() {
        return simbolo;
    }

    public void setSimbolo(String simbolo) {
        this.simbolo = simbolo;
    }

    public Double getPrecoAbertura() {
        return precoAbertura;
    }

    public void setPrecoAbertura(Double precoAbertura) {
        this.precoAbertura = precoAbertura;
    }

    public Double getPrecoMaximo() {
        return precoMaximo;
    }

    public void setPrecoMaximo(Double precoMaximo) {
        this.precoMaximo = precoMaximo;
    }

    public Double getPrecoMinimo() {
        return precoMinimo;
    }

    public void setPrecoMinimo(Double precoMinimo) {
        this.precoMinimo = precoMinimo;
    }

    public Double getPrecoAtual() {
        return precoAtual;
    }

    public void setPrecoAtual(Double precoAtual) {
        this.precoAtual = precoAtual;
    }

    public Long getVolume() {
        return volume;
    }

    public void setVolume(Long volume) {
        this.volume = volume;
    }

    public String getUltimaDataNegociacao() {
        return ultimaDataNegociacao;
    }

    public void setUltimaDataNegociacao(String ultimaDataNegociacao) {
        this.ultimaDataNegociacao = ultimaDataNegociacao;
    }

    public Double getFechamentoAnterior() {
        return fechamentoAnterior;
    }

    public void setFechamentoAnterior(Double fechamentoAnterior) {
        this.fechamentoAnterior = fechamentoAnterior;
    }

    public Double getVariacao() {
        return variacao;
    }

    public void setVariacao(Double variacao) {
        this.variacao = variacao;
    }

    public String getVariacaoPercentual() {
        return variacaoPercentual;
    }

    public void setVariacaoPercentual(String variacaoPercentual) {
        this.variacaoPercentual = variacaoPercentual;
    }
} 