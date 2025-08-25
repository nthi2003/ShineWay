package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.service.PositionService;
import com.mycompany.myapp.service.dto.PositionDTO;
import com.mycompany.myapp.web.rest.vm.ApiResponse;
import java.util.List;
import java.util.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class PositionResource {

    private final PositionService positionService;

    public PositionResource(PositionService positionService) {
        this.positionService = positionService;
    }

    @GetMapping("/positions")
    public ResponseEntity<ApiResponse<List<PositionDTO>>> getAllPositions() {
        List<PositionDTO> positions = positionService.getAllPositions();
        
        ApiResponse<List<PositionDTO>> response = ApiResponse.success("Positions retrieved successfully", positions);
        response.addMetadata("requestId", UUID.randomUUID().toString());
        
        return ResponseEntity.ok(response);
    }

    @GetMapping("/position/{id}")
    public ResponseEntity<ApiResponse<PositionDTO>> getPositionById(@PathVariable String id) {
        PositionDTO position = positionService.getPositionByID(id);
        
        ApiResponse<PositionDTO> response = ApiResponse.success("Position retrieved successfully", position);
        response.addMetadata("requestId", UUID.randomUUID().toString());
        response.addMetadata("positionId", id);
        
        return ResponseEntity.ok(response);
    }

    @PostMapping("/positions")
    public ResponseEntity<ApiResponse<PositionDTO>> createPosition(@RequestBody PositionDTO positionDTO) {
        PositionDTO result = positionService.createPosition(positionDTO);
        
        ApiResponse<PositionDTO> response = ApiResponse.success("Position created successfully", result);
        response.addMetadata("requestId", UUID.randomUUID().toString());
        response.addMetadata("positionId", result.getPositionId());
        
        return ResponseEntity.ok(response);
    }

    @PutMapping("/position/{id}")
    public ResponseEntity<ApiResponse<PositionDTO>> updatePosition(@PathVariable String id, @RequestBody PositionDTO positionDTO) {
        positionDTO.setPositionId(id);
        if (positionDTO.getPositionId() == null) {
            ApiResponse<PositionDTO> errorResponse = ApiResponse.error("Invalid position ID");
            errorResponse.addMetadata("requestId", UUID.randomUUID().toString());
            return ResponseEntity.badRequest().body(errorResponse);
        }
        
        PositionDTO result = positionService.updatePosition(positionDTO);
        
        ApiResponse<PositionDTO> response = ApiResponse.success("Position updated successfully", result);
        response.addMetadata("requestId", UUID.randomUUID().toString());
        response.addMetadata("positionId", id);
        
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/position/{id}")
    public ResponseEntity<ApiResponse<Void>> deletePosition(@PathVariable String id) {
        positionService.deletePosition(id);
        
        ApiResponse<Void> response = ApiResponse.success("Position deleted successfully", null);
        response.addMetadata("requestId", UUID.randomUUID().toString());
        response.addMetadata("positionId", id);
        
        return ResponseEntity.ok(response);
    }
}
