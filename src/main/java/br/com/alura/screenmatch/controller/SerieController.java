package br.com.alura.screenmatch.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController // notação para definir que essa classe é um controlador rest
public class SerieController {
    @GetMapping("/series")
    public String obterSeries() {
        return "List Series";
    }
}
