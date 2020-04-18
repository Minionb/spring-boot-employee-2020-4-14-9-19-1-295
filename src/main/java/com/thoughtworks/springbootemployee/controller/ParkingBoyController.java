package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.model.ParkingBoy;
import com.thoughtworks.springbootemployee.service.ParkingBoyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/parking-boys")
public class ParkingBoyController {
    @Autowired
    private ParkingBoyService parkingBoyService;

    @GetMapping
    public List<ParkingBoy> getAll() {
        return parkingBoyService.getAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ParkingBoy createParkingBoy(@RequestBody ParkingBoy parkingBoy) {
        return parkingBoyService.createParkingBoy(parkingBoy);
    }

    @GetMapping("/{parkingBoyId}")
    public ResponseEntity<Object> getParkingBoyById(@PathVariable Integer parkingBoyId) {
        ParkingBoy targetParkingBoy = parkingBoyService.getParkingBoyById(parkingBoyId);
        if (targetParkingBoy == null) {
            return new ResponseEntity<>("Parking Boy doesn't exist", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(targetParkingBoy, HttpStatus.OK);

    }

    @DeleteMapping("/{parkingBoyId}")
    public ResponseEntity<Object> deleteParkingBoyById(@PathVariable Integer parkingBoyId) {
        boolean isNotNull = parkingBoyService.deleteParkingBoyById(parkingBoyId);
        if (!isNotNull) {
            return new ResponseEntity<>("Parking Boy doesn't exist", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Remove Parking boy with ID " + parkingBoyId + " successfully", HttpStatus.OK);
    }

    @PutMapping("/{parkingBoyId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> updateParkingBoyById(@PathVariable Integer parkingBoyId, @RequestBody ParkingBoy newParkingBoy) {
        boolean isNotNull = parkingBoyService.updateParkingBoyById(parkingBoyId, newParkingBoy);
        if (!isNotNull) {
            return new ResponseEntity<>("Parking boy doesn't exist", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(newParkingBoy, HttpStatus.OK);
    }

    @GetMapping(params = "nickname")
    public ResponseEntity<Object> getParkingBoyByNickname(@RequestParam String nickname) {
        List<ParkingBoy> parkingBoys = parkingBoyService.getParkingBoyByNickname(nickname);
        if (parkingBoys == null) {
            return new ResponseEntity<>("Parking Boy's nickname doesn't exist", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(parkingBoys, HttpStatus.OK);
    }

    @GetMapping(params = {"page","pageSize"})
    public ResponseEntity<Object> getParkingBoyInPage(@RequestParam Integer page, Integer pageSize){
        List<ParkingBoy> parkingBoys = parkingBoyService.getParkingBoyInPage(page,pageSize);
        return new ResponseEntity<>(parkingBoys, HttpStatus.OK);
    }
}
