package com.geoinfo.gestiondon.controller;
import com.geoinfo.gestiondon.model.Commune;
import com.geoinfo.gestiondon.repository.CommuneRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/Commune")

public class CommuneController {

    @Autowired
    private CommuneRepository communeRepository;

    @GetMapping("/names")
    public List<String> getAllCommuneNames() {
        return communeRepository.findAllCommuneNames();
    }

    @GetMapping("/regions")
    public List<String> obtenirNomsRegions() {
        return communeRepository.findDistinctRegion();
    }

    @GetMapping("/provinces")
    public List<String> obtenirProvincesParRegion(@RequestParam String region) {
        return communeRepository.findByRegion(region);
    }

    @GetMapping("/communes")
    public List<String> communesParProvince(@RequestParam String province) {
        return communeRepository.findByProvince(province);
        
    }
    @GetMapping("/filterCom/{searchTerm}")
    public List<String> autocomplete(@RequestParam String searchTerm) {
     return communeRepository.findCommuneByCommuneContainingIgnoreCase(searchTerm);
    }
    
    @GetMapping("/getX")
    public List<Double> getXCentroidsOfCommunesWithAnnonces() {
        return communeRepository.findXCentroidsOfCommunesWithAnnonces();
    }
    @GetMapping("/getY")
    public List<Double> getYCentroidsOfCommunesWithAnnonces() {
        return communeRepository.findYCentroidsOfCommunesWithAnnonces();
    }
    @GetMapping("/affannonce")
    public List<Object[]> getCommunesDetail(){
    	
       return communeRepository.getCommuneDetailsWithCount();
    }
    @GetMapping("/complement")
    public List<String> trouverCommunesParPrefixe( @RequestParam("prefixe") String prefixe) {
        return communeRepository.trouverCommunesParPrefixeIgnoreCase(prefixe);
    }
    @GetMapping("/top-communes")
    public List<Object[]> getTopCommunes(){
        return communeRepository.getTopCommunesWithAnnonces();
    }
}