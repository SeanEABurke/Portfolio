package com.example.PlantStore.Service;

import com.example.PlantStore.Model.Plant;
import com.example.PlantStore.Repository.PlantRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PlantService {
    @Autowired
    private PlantRespository plantRepository;

    public Plant getPlant(int plantid) {
        return plantRepository.getByPlantid(plantid);
    }

    public int getQty(int plantid) {
       return plantRepository.findQtyByPlantid(plantid);
    }

    public void plantSale(int plantid, int qtysold) {
        Optional<Plant> p = plantRepository.findById(plantid);
        p.get().setQty(p.get().getQty() - qtysold);
        plantRepository.save(p.get());
    }

    public void removeFromCart(int plantid) {
        Optional<Plant> p = plantRepository.findById(plantid);
        p.get().setQty(p.get().getQty() + 1);
        plantRepository.save(p.get());
    }

    public void updateQty(int plantid, int qtyDiff) {
        Plant p = plantRepository.getByPlantid(plantid);
        int currQty = p.getQty();
        p.setQty(currQty - qtyDiff);
        plantRepository.save(p);
    }
}
