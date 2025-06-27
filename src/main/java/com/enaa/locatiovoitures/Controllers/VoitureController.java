package com.enaa.locatiovoitures.Controllers;

import com.enaa.locatiovoitures.Dto.VoitureDto;
import com.enaa.locatiovoitures.Services.VoitureService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/voiture")
public class VoitureController {

    private final VoitureService voitureService;

    public VoitureController(VoitureService voitureService) {
        this.voitureService = voitureService;
    }

    @PostMapping
    public VoitureDto ajouterVoiture(@RequestBody VoitureDto voitureDto){
        return voitureService.ajouterVoiture(voitureDto);
    }
}
