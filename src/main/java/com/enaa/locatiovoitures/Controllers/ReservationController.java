package com.enaa.locatiovoitures.Controllers;

import com.enaa.locatiovoitures.Dto.ReservationDto;
import com.enaa.locatiovoitures.Model.Reservation;
import com.enaa.locatiovoitures.Model.ReservationStatus;
import com.enaa.locatiovoitures.Model.User;
import com.enaa.locatiovoitures.Repositories.UserRepository;
import com.enaa.locatiovoitures.Services.ReservationService;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reservation")
@CrossOrigin(origins = "http://localhost:4200")
public class ReservationController {

    private final ReservationService reservationService;
    private final UserRepository userRepository;

    public ReservationController(ReservationService reservationService, UserRepository userRepository) {
        this.reservationService = reservationService;
        this.userRepository = userRepository;
    }
    @PreAuthorize("hasAnyAuthority('ADMIN', 'CLIENT')")
    @PostMapping
    public ReservationDto ajouter(@RequestBody ReservationDto reservationDto){
        return reservationService.ajouter(reservationDto);
    }
    @JsonIgnore
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @GetMapping("/all")
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
    @GetMapping("/my/{id}")
    public List<ReservationDto> getReservationByClientId(@PathVariable Long id){
        return reservationService.getReservationsByClientId(id);
    }

    @PreAuthorize("hasAuthority('CLIENT')")
    @GetMapping("/my-reservations")
    public List<ReservationDto> getMyReservations() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email = userDetails.getUsername();
        User user = userRepository.findByEmail(email);
        return reservationService.getReservationsByClientId(user.getId());
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @PatchMapping("statut/{id}")
    public ReservationDto changeStatu(@PathVariable Long id,@RequestParam ReservationStatus status){
        return reservationService.changeStatus(id,status);
    }
}
