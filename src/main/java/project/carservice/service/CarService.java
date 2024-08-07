package project.carservice.service;

import project.carservice.model.dto.addDTO.AddCarDTO;
import project.carservice.model.dto.CarDTO;
import project.carservice.model.entity.Car;

import java.util.List;
import java.util.UUID;

public interface CarService {

    void addCar(AddCarDTO addCarDTO);

    List<CarDTO> allOwnedBy(String username);

    Car getById(UUID id);

    void removeCar(java.util.UUID id);
    
}
