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
}
