package com.poweremaboc.homeworkweek3.controller;

import com.poweremaboc.homeworkweek3.model.Car;
import com.poweremaboc.homeworkweek3.service.CarService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cars")
public class CarApi {

    private CarService carService;

    public CarApi() {
        carService = new CarService();
    }

    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<List<Car>> getCars() {

        return new ResponseEntity<>(carService.getCarList(), HttpStatus.OK);
    }

    @GetMapping(path = "/{id}",produces = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<Car> getCarById(@PathVariable long id) {
        Car carById = carService.getCarById(id);
        if (carById != null) {
            return new ResponseEntity<>(carById, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(params = "/colors", produces = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE} )
    public ResponseEntity<List<Car>> getCarByColor(@RequestParam String color) {
        List<Car> carByColor = carService.getCarByColor(color);
        if (!carByColor.isEmpty()) {
            return new ResponseEntity<>(carByColor, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping(produces = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<Car> addCar(@RequestBody Car newCar) {
        boolean add = carService.addCar(newCar);
        if (add) {
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @PutMapping(produces = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<Car> modifyRecord(@RequestBody Car modCar) {
        boolean mod = carService.modifyRecord(modCar);
        if (mod) {
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PatchMapping(path = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
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
    @DeleteMapping(path = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<Car> removeById(@PathVariable long id) {
        boolean removed = carService.deleteRecord(id);
        if (removed) {
            return new ResponseEntity<>(carService.getCarById(id), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
