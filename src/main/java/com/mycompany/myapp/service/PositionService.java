package com.mycompany.myapp.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mycompany.myapp.domain.Position;
import com.mycompany.myapp.repository.PositionRepository;
import com.mycompany.myapp.security.SecurityUtils;
import com.mycompany.myapp.service.dto.PositionDTO;
import com.mycompany.myapp.service.mapper.PositionMapper;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class PositionService {

    private final PositionRepository positionRepository;
    private final PositionMapper positionMapper;

    public PositionService(PositionRepository positionRepository, PositionMapper positionMapper) {
        this.positionRepository = positionRepository;
        this.positionMapper = positionMapper;
    }

    public PositionDTO createPosition(PositionDTO positionDTO) {
        Position position = positionMapper.positionDTOToPosition(positionDTO);
        Long currentUserId = SecurityUtils.getCurrentUserId()
                .orElseThrow(() -> new RuntimeException("Not logged in"));

        position.setCreatedBy(currentUserId);
        position = positionRepository.save(position);
        return positionMapper.positionToPositionDTO(position);
    }

    
    public PositionDTO updatePosition(PositionDTO positionDTO) {

        Position position = positionRepository.findById(positionDTO.getPositionId())
                .orElseThrow(() -> new RuntimeException("Position not found"));
        
        // Check if the position is deleted
        if(position.getIsDeleted()) {
            throw new RuntimeException("Cannot update a deleted position");
        }
        position.setName(positionDTO.getName());
        position.setDescription(positionDTO.getDescription());
        // Set updatedBy and updatedDate if needed
        position = positionRepository.save(position);
        return positionMapper.positionToPositionDTO(position);
    }

    public PositionDTO getPositionByID(String positionId) {
        return positionRepository.findByIdAndIsDeletedFalse(positionId)
                .map(positionMapper::positionToPositionDTO)
                .orElseThrow(() -> new RuntimeException("Position not found"));
    }

    public List<PositionDTO> getAllPositions() {
        List<Position> positions = positionRepository.findAllByIsDeletedFalse();
        return positionMapper.positionsToPositionDTOs(positions);
    }

    public void deletePosition(String positionId) {
        Position position = positionRepository.findByIdAndIsDeletedFalse(positionId)
                .orElseThrow(() -> new RuntimeException("Position not found"));
        position.setIsDeleted(true); // Assuming isdeleted is a boolean field in Position
        positionRepository.save(position);
    }
}