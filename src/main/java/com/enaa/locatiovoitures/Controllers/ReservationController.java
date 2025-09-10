package com.enaa.locatiovoitures.Controllers;

import com.enaa.locatiovoitures.Dto.ReservationDto;
import com.enaa.locatiovoitures.Services.ReservationService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reservation")
@CrossOrigin(origins = "http://localhost:4200")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }
    @PreAuthorize("hasAnyAuthority('ADMIN', 'CLIENT')")
    @PostMapping
    public ReservationDto ajouter(@RequestBody ReservationDto reservationDto){
        return reservationService.ajouter(reservationDto);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'CLIENT')")
    @GetMapping
    public List<ReservationDto> getAll(){

        return reservationService.getAll();
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','CLIENT')")
    @DeleteMapping("/{id}")
    public void delet(@PathVariable Long id){

        reservationService.deletReservation(id);
    }
    @PreAuthorize("hasAnyAuthority('ADMIN','CLIENT')")
    @PutMapping("/{id}")
    public ReservationDto update(@PathVariable Long id, @RequestBody ReservationDto reservationDto){
        return reservationService.update(id,reservationDto);
    }

    @GetMapping("{id}")
    public ReservationDto getById(@PathVariable Long id){
        return reservationService.getById(id);
    }
}
