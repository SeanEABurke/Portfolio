package com.example.PlantStore.Repository;

import com.example.PlantStore.Model.Customer;
import com.example.PlantStore.Model.Plant;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface PlantRespository extends CrudRepository<Plant, Integer> {

    @Query("SELECT p.qty FROM Plant p WHERE p.plantid = :plantid")
    int findQtyByPlantid(@Param("plantid") int plantid);

    Plant getByPlantid(@Param("plantid") int plantid);

}
