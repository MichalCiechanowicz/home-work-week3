package com.poweremaboc.homeworkweek3.service;

import com.poweremaboc.homeworkweek3.model.Car;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CarService {

    private List<Car> carList;

    public CarService() {
        carList = new ArrayList<>();
        carList.add(new Car(1L, "Audi", "A3", "Silver"));
        carList.add(new Car(2L, "VW", "Jetta", "Red"));
        carList.add(new Car(3L, "Bmw", "i3", "Black"));
    }

    public List<Car> getCarList() {
        return carList;
    }

    public Car getCarById(long id) {
        Optional<Car> first = carList.stream().filter(car -> car.getId() == id).findFirst();
        return first.orElse(null);
    }

    public List<Car> getCarByColor(String color) {

        return carList.stream().filter(car -> car.getColor().equals(color)).collect(Collectors.toList());
    }

    public boolean addCar(Car newCar) {
        Optional<Car> first = carList.stream().filter(car -> car.getId() == newCar.getId()).findFirst();
        if (!first.isPresent()) {
            carList.add(newCar);
            return true;
        }
        return false;
    }

    public boolean modifyRecord(Car modCar) {
        Optional<Car> first = carList.stream().filter(car -> car.getId() == modCar.getId()).findFirst();
        if (first.isPresent()) {
            carList.set((int) first.get().getId()-1, modCar);
            return true;
        }
        return false;
    }

    public boolean modifyByColor(long id, String color) {
        Optional<Car> first = carList.stream().filter(car -> car.getId() == id).findFirst();
        if (first.isPresent()) {
            first.get().setColor(color);
            return true;
        }
        return false;
    }

    public boolean deleteRecord(long id) {
        Optional<Car> first = carList.stream().filter(car -> car.getId() == id).findFirst();
        if (first.isPresent()) {
            carList.remove(first.get());
            return true;
        }
        return false;
    }
}