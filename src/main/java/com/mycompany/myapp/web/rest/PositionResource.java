package com.mycompany.myapp.web.rest;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mycompany.myapp.service.PositionService;
import com.mycompany.myapp.service.dto.PositionDTO;


@RestController
@RequestMapping("/api")
public class PositionResource {

    private final PositionService positionService;

    public PositionResource(PositionService positionService) {
        this.positionService = positionService;
    }

    @GetMapping("/positions")
    public List<PositionDTO> getAllPositions() {
        return positionService.getAllPositions();
    }

    @GetMapping("/position/{id}")
    public PositionDTO getPositionById(@PathVariable String id) {
        return positionService.getPositionByID(id);
    }
    
    @PostMapping("/positions")
    public ResponseEntity<PositionDTO> createPosition(@RequestBody PositionDTO positionDTO) {
        PositionDTO result = positionService.createPosition(positionDTO);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/positions/{id}")
    public ResponseEntity<PositionDTO> updatePosition(@PathVariable String id, @RequestBody PositionDTO positionDTO) {
        positionDTO.setPositionId(id);;
        if (positionDTO.getPositionId() == null) {
            return ResponseEntity.badRequest().build();
        }
        PositionDTO result = positionService.updatePosition(positionDTO);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/positions/{id}")
    public ResponseEntity<Void> deletePosition(@PathVariable String id) {
        positionService.deletePosition(id);
        return ResponseEntity.noContent().build();
    }



  
}
