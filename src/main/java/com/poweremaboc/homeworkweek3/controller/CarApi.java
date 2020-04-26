package com.poweremaboc.homeworkweek3.controller;

import com.poweremaboc.homeworkweek3.model.Car;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    private List<Car> carList;

    public CarApi() {
        this.carList = new ArrayList<>();
        carList.add(new Car(1L, "Audi", "A3", "Silver"));
        carList.add(new Car(2L, "VW", "Jetta", "Red"));
        carList.add(new Car(3L, "Bmw", "i3", "Black"));
    }

    @GetMapping
    public ResponseEntity<List<Car>> getCars() {
        if (!carList.isEmpty()) {
            return new ResponseEntity<>(carList, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Car> getCarById(@PathVariable long id) {
        Optional<Car> first = carList.stream().filter(car -> car.getId() == id).findFirst();
        if (first.isPresent()) {
            return new ResponseEntity<>(first.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/colors")
    public ResponseEntity<List<Car>> getCarByColor(@RequestParam String color) {
        List<Car> collect = carList.stream().filter(car -> car.getColor().equals(color)).collect(Collectors.toList());
        if (!collect.isEmpty()) {
            return new ResponseEntity<>(collect, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<Car> addCar(@RequestBody Car newCar) {
        Optional<Car> first = carList.stream().filter(car -> car.getId() == newCar.getId()).findFirst();
        if (!first.isPresent()) {
            boolean add = carList.add(newCar);
            if (add) {
                return new ResponseEntity<>(HttpStatus.CREATED);
            }
        }
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @PutMapping
    public ResponseEntity<Car> modifyRecord(@RequestBody Car modCar) {
        Optional<Car> first = carList.stream().filter(car -> car.getId() == modCar.getId()).findFirst();
        if (first.isPresent()) {
            carList.remove(first.get());
            carList.add(modCar);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Car> modifyByColor(@PathVariable long id, @RequestHeader String color) {
        Optional<Car> first = carList.stream().filter(car -> car.getId() == id).findFirst();
        if (first.isPresent()) {
            first.get().setColor(color);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    //    @PatchMapping("/{id}/color")
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
        Optional<Car> first = carList.stream().filter(car -> car.getId() == id).findFirst();
        if (first.isPresent()) {
            boolean remove = carList.remove(first.get());
            if (remove) {
                return new ResponseEntity<>(first.get(), HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
