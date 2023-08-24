package com.example.PlantStore.API;

import com.example.PlantStore.Model.Customer;
import com.example.PlantStore.Model.Plant;
import com.example.PlantStore.Repository.CustomerRepository;
import com.example.PlantStore.Repository.PlantRespository;
import com.example.PlantStore.Service.PlantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(path = "/plant")
public class PlantController {

    @Autowired
    private PlantService plantService;

//    @PutMapping
//    public Plant plantSale(@RequestParam int plantid, @RequestParam int qtysold) {
//
//        Optional<Plant> p = plantRepository.findById(plantid);
//        p.get().setQty(p.get().getQty() - qtysold);
//        plantRepository.save(p.get());
//        return plant;
//    }

//    @GetMapping
//    public @ResponseBody Iterable<Plant> getPlants() {
//        return plantRepository.findAll();
//    }

    @GetMapping(path="/getByID")
    public @ResponseBody Plant getPlantByID(@RequestParam int plantid) {
        return plantService.getPlant(plantid);
    }

    @GetMapping(path = "/getqty")
    public @ResponseBody int getQty(int plantid) {
        return plantService.getQty(plantid);
    }
}

