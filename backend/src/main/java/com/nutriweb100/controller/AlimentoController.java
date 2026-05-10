package com.nutriweb100.controller;

import com.nutriweb100.model.Alimento;
import com.nutriweb100.repository.AlimentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController //Esta clase nos devuelve un Json (se hace prueba en Postman)
@RequestMapping("api/alimentos") //ruta
public class AlimentoController {
    //Conexión con BBDD
    @Autowired
    private AlimentoRepository alimentoRepository;

    //Nos devuelve la lista de alimentos
    @GetMapping
    public List<Alimento> getAllAlimentos(){
        return alimentoRepository.findAll();
    }
}
