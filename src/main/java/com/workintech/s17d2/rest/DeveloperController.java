package com.workintech.s17d2.rest;

import com.workintech.s17d2.model.Developer;
import com.workintech.s17d2.model.Experience;
import com.workintech.s17d2.tax.DeveloperTax;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class DeveloperController {
    public Map<Integer, Developer> developers;
    private final DeveloperTax developerTax;


    @PostConstruct
    public void init() {
        developers = new HashMap<>();
        developers.put(1, new Developer(1,"Ecem", 60000.0, Experience.MID ));
        developers.put(2, new Developer(2,"Soner", 100000.0, Experience.SENIOR ));
        developers.put(3, new Developer(3,"İpek", 40000.0, Experience.JUNIOR ));
    }

    @Autowired
    public DeveloperController(DeveloperTax developerTax){
        this.developerTax = developerTax;
    }

    @GetMapping("/developers")
    public List<Developer> findAll(){
        return developers.values().stream().toList();
    }

    @GetMapping("/developers/{id}")
    public Developer findDeveloperById(@PathVariable long id){
        Developer developer = developers.get(id);
        if(developer != null){
            return developer;
        }else{
            return null;
        }
    }

    @PostMapping("/developers")
    public ResponseEntity<Developer> save(@RequestBody Developer developer) {
        double taxedSalary = developerTax.applyTax(developer.getSalary(), developer.getExperience().toString());

        developer.setSalary(taxedSalary);
        developers.put(developer.getId(), developer);

        return new ResponseEntity<>(developer, HttpStatus.CREATED);
    }

    @PutMapping("/developers/{id}")
    public Developer update(@PathVariable int id, @RequestBody Developer developer){
        developers.put(id, new Developer(id, developer.getName(), developer.getSalary(), developer.getExperience()));
        return developers.get(id);
    }

    @DeleteMapping("/developers/{id}")
    public Developer delete(@PathVariable int id){
        Developer developer = developers.get(id);
        developers.remove(developer);
        return developer;
    }
}
