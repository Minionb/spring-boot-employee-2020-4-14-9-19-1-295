package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.model.ParkingBoy;
import com.thoughtworks.springbootemployee.repository.ParkingBoyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParkingBoyService {
    @Autowired
    private ParkingBoyRepository parkingBoyRepository;

    public List<ParkingBoy> getAll() {
        return parkingBoyRepository.findAll();
    }

    public ParkingBoy createParkingBoy(ParkingBoy parkingBoy) {
        return parkingBoyRepository.save(parkingBoy);
    }

    public ParkingBoy getParkingBoyById(Integer employeeId) {
        return parkingBoyRepository.findById(employeeId).orElse(null);
    }

    public boolean deleteParkingBoyById(Integer parkingBoyId) {
        ParkingBoy targetParkingBoy = parkingBoyRepository.findById(parkingBoyId).orElse(null);
        if (targetParkingBoy == null) {
            return false;
        }
        parkingBoyRepository.delete(targetParkingBoy);
        return true;
    }

    public boolean updateParkingBoyById(Integer parkingBoyId, ParkingBoy newParkingBoy) {
        ParkingBoy targetParkingBoy = parkingBoyRepository.findById(parkingBoyId).orElse(null);
        if (targetParkingBoy == null) {
            return false;
        }
        if (newParkingBoy.getNickname() != null) {
            targetParkingBoy.setNickname(newParkingBoy.getNickname());
        }
        parkingBoyRepository.save(targetParkingBoy);
        return true;
    }

    public List<ParkingBoy> getParkingBoyByNickname(String nickname) {
        return parkingBoyRepository.findAllByNickname(nickname);
    }

    public List<ParkingBoy> getParkingBoyInPage(Integer page, Integer pageSize) {
        return parkingBoyRepository.findAll(PageRequest.of(page - 1, pageSize)).getContent();
    }
}
