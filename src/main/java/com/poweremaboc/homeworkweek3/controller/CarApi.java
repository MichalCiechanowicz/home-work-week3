package com.poweremaboc.homeworkweek3.controller;

import com.poweremaboc.homeworkweek3.model.Car;
import com.poweremaboc.homeworkweek3.service.CarService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//Zadanie podstawowe:
//        Napisz REST API dla listy pojazdów. Pojazd będzie miał pola: id, mark, model, color.
//        API które będzie obsługiwało metody webowe:
//
//        do pobierania wszystkich pozycji
//        do pobierania elementu po jego id
//        do pobierania elementów w określonym kolorze (query)
//        do dodawania pozycji
//        do modyfikowania pozycji
//        do modyfikowania jednego z pól pozycji
//        do usuwania jeden pozycji
//        Przy starcie aplikacji mają dodawać się 3 pozycje.

@RestController
@RequestMapping("/cars")
public class CarApi {

    private CarService carService;

    public CarApi() {
        carService = new CarService();
    }

    @GetMapping
    public ResponseEntity<List<Car>> getCars() {

        return new ResponseEntity<>(carService.getCarList(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Car> getCarById(@PathVariable long id) {
        Car carById = carService.getCarById(id);
        if (carById != null) {
            return new ResponseEntity<>(carById, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/colors")
    public ResponseEntity<List<Car>> getCarByColor(@RequestParam String color) {
        List<Car> carByColor = carService.getCarByColor(color);
        if (!carByColor.isEmpty()) {
            return new ResponseEntity<>(carByColor, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<Car> addCar(@RequestBody Car newCar) {
        boolean add = carService.addCar(newCar);
        if (add) {
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @PutMapping
    public ResponseEntity<Car> modifyRecord(@RequestBody Car modCar) {
        boolean mod = carService.modifyRecord(modCar);
        if (mod) {
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Car> modifyByColor(@PathVariable long id, @RequestHeader String color) {
        boolean modifyByColor = carService.modifyByColor(id, color);
        if (modifyByColor) {
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    //    @PatchMapping("/{id}/model")
//    public ResponseEntity<Car> modifyByModel(@PathVariable long id, @RequestParam String model) {
//        Optional<Car> first = carList.stream().filter(car -> car.getId() == id).findFirst();
//        if (first.isPresent()) {
//            first.get().setColor(model);
//            return new ResponseEntity<>(HttpStatus.CREATED);
//        }
//        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Car> removeById(@PathVariable long id) {
        boolean removed = carService.deleteRecord(id);
        if (removed) {
            return new ResponseEntity<>(carService.getCarById(id), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
