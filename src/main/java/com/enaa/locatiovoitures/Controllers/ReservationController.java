package com.enaa.locatiovoitures.Controllers;

import com.enaa.locatiovoitures.Dto.ReservationDto;
import com.enaa.locatiovoitures.Services.ReservationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reservation")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping
    public ReservationDto ajouter(@RequestBody ReservationDto reservationDto){
        return reservationService.ajouter(reservationDto);
    }
    @GetMapping
    public List<ReservationDto> getAll(){
        return reservationService.getAll();
    }

    @DeleteMapping("/{id}")
    public void delet(@PathVariable Long id){
       reservationService.deletReservation(id);
    }

    @PutMapping("/{id}")
    public ReservationDto update(@PathVariable Long id, @RequestBody ReservationDto reservationDto){
        return reservationService.update(id,reservationDto);
    }
}
