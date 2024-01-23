package com.geoinfo.gestiondon.controller;


import com.geoinfo.gestiondon.model.Utilisateur;
import com.geoinfo.gestiondon.repository.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RequestMapping("/utilisateur")

@RestController
public class UtilisateurController {
    @Autowired
    private UtilisateurRepository utilisateurRepository;
    @GetMapping("/all_users1")
    public List<Utilisateur> getUsers() {
        return (List<Utilisateur>)utilisateurRepository.findAll();
    }
    @PostMapping("/add")
    public ResponseEntity add(@RequestBody Utilisateur utilisateur){
        utilisateurRepository.save(utilisateur);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public Utilisateur getUtilisateur(@PathVariable("id") int id){
        return utilisateurRepository.findById(id).get();
    }

   

   

    @GetMapping("/nombre-utilisateurs")
    public ResponseEntity<Long> obtenirNombreUtilisateurs() {
        long nombreUtilisateurs = utilisateurRepository.count();
        return new ResponseEntity<>(nombreUtilisateurs, HttpStatus.OK);
    }
    @GetMapping("/all_users")
    public ResponseEntity<Page<Utilisateur>> getAllUtilisateurs(Pageable pageable) {
        Page<Utilisateur> utilisateurs = utilisateurRepository.findAll(pageable);
        return new ResponseEntity<>(utilisateurs, HttpStatus.OK);
    }
    @PutMapping("/edit/{utilisateurId}")
    public ResponseEntity<Utilisateur> mettreAJourUtilisateur(@PathVariable Integer utilisateurId, @RequestBody Utilisateur utilisateur) {

        Utilisateur utilisateurExist = utilisateurRepository.findById(utilisateurId)
                .orElseThrow(() -> new NoSuchElementException("Utilisateur non trouvé avec l'ID : " + utilisateurId));

        utilisateurExist.setNom(utilisateur.getNom());
        utilisateurExist.setPrenom(utilisateur.getPrenom());

        utilisateurExist.setRole(utilisateur.getRole());
        utilisateurExist.setStatut(utilisateur.getStatut());
        Utilisateur utilisateurMaj = utilisateurRepository.save(utilisateurExist);
        return new ResponseEntity<>(utilisateurMaj, HttpStatus.OK);
    }
    @DeleteMapping("/delete/{utilisateurId}")
    public ResponseEntity<?> supprimerUtilisateur(@PathVariable Integer utilisateurId) {
        // Validation, logique métier, etc.
        Utilisateur utilisateur = utilisateurRepository.findById(utilisateurId)
                .orElseThrow(() -> new NoSuchElementException("Utilisateur non trouvé avec l'ID : " + utilisateurId));

        utilisateurRepository.delete(utilisateur);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
   
    @GetMapping("/search/{nom}")
    public ResponseEntity<List<Utilisateur>> rechercherUtilisateurParNom(@PathVariable String nom) {
        List<Utilisateur> utilisateurs = utilisateurRepository.findByNom(nom);
        return new ResponseEntity<>(utilisateurs, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Utilisateur loginRequest) {
        String email = loginRequest.getLogin();
        String password = loginRequest.getPassword();

        // Recherche de l'utilisateur par email (vous pouvez ajuster en fonction de votre modèle)
        Utilisateur utilisateur = utilisateurRepository.findByLogin(email);

        if (utilisateur != null && utilisateur.getPassword() != null && utilisateur.getPassword().equals(password)) {
            // Authentification réussie
            System.out.println("Authentification réussie");

        	return ResponseEntity.ok("Authentification réussie");
            
        } else {
            System.out.println(utilisateur.getLogin()+"Authentification non réussie");

            // Échec de l'authentification
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
        }
    }


}
