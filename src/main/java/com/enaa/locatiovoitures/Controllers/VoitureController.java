package com.enaa.locatiovoitures.Controllers;

import com.enaa.locatiovoitures.Dto.VoitureDto;
import com.enaa.locatiovoitures.Services.VoitureService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/voiture")
@CrossOrigin(origins = "http://localhost:4200")
public class VoitureController {

    private final VoitureService voitureService;

    public VoitureController(VoitureService voitureService) {

        this.voitureService = voitureService;
    }

    @PostMapping
    public VoitureDto ajouterVoiture(@RequestBody VoitureDto voitureDto){
        return voitureService.ajouterVoiture(voitureDto);
    }

    @GetMapping
    public List<VoitureDto> getAllVoitures(){
        return voitureService.getAllVoitures();
    }

    @DeleteMapping("/{id}")
    public void deletVoiture( @PathVariable Long id){
        voitureService.deletVoiture(id);
    }

    @PutMapping("/{id}")
    public VoitureDto update(@PathVariable Long id, @RequestBody VoitureDto dto){
       return voitureService.update(id, dto);
    }

    @GetMapping("/{id}")
    public VoitureDto getById(@PathVariable Long id){

        return voitureService.getById(id);
    }
}
