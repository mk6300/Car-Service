package project.carservice.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.carservice.model.dto.addDTO.AddCarDTO;
import project.carservice.model.dto.CarDTO;
import project.carservice.model.entity.Car;
import project.carservice.repository.CarRepository;

import java.util.List;
import java.util.UUID;

@Service
public class CarServiceImpl implements CarService {

    private final CarRepository carRepository;
    private final ModelMapper modelMapper;
    private final UserService userService;


    public CarServiceImpl(CarRepository carRepository, ModelMapper modelMapper, UserService userService) {
        this.carRepository = carRepository;
        this.modelMapper = modelMapper;
        this.userService = userService;
    }


    @Override
    public void addCar(AddCarDTO addCarDTO) {
        this.carRepository.save(this.mapCar(addCarDTO));

    }

    @Override
    public List<CarDTO> allOwnedBy(String username) {
        return carRepository.getAllByOwner_Username(username)
                .stream()
                .map(this::mapCarDTO)
                .toList();
    }

    @Override
    public Car getById(UUID id) {
        return carRepository.findById(id).orElseThrow();
    }

    @Transactional
    @Override
    public void removeCar(UUID id) {
        Car car = carRepository.findById(id).orElseThrow();
        carRepository.delete(car);
    }


    private Car mapCar(AddCarDTO addCarDTO) {
        Car car = modelMapper.map(addCarDTO, Car.class);

        car.setOwner(userService.getCurrentUser());
        return car;
    }

    private CarDTO mapCarDTO(Car car) {

        return modelMapper.map(car, CarDTO.class);
    }
}
