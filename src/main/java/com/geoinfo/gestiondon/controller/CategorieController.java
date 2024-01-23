package com.geoinfo.gestiondon.controller;


import com.geoinfo.gestiondon.model.Categorie;

import com.geoinfo.gestiondon.repository.CategorieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
@CrossOrigin("*")
public class CategorieController {
    @Autowired
    private CategorieRepository categorieRepository;

    @GetMapping("all_categories")
    public List<Categorie> getAllCategories() {
    	System.out.println("jkcbvld");
    
        return (List<Categorie>)  categorieRepository.findAll();

    }
    @PostMapping("ajouter_categorie")

    public ResponseEntity<Categorie> yaddCategory(@RequestBody Categorie categorie) {
        Categorie nouvelleCategorie = categorieRepository.save(categorie);
        return new ResponseEntity<>(nouvelleCategorie, HttpStatus.CREATED);
    }



}
