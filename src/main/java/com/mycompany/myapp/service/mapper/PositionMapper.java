package com.mycompany.myapp.service.mapper;

import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Service;

import com.mycompany.myapp.domain.Position;
import com.mycompany.myapp.service.dto.PositionDTO;
@Service
public class PositionMapper {

    public List<PositionDTO> positionsToPositionDTOs(List<Position> positions) {
        return positions.stream()
                .filter(Objects::nonNull)
                .map(this::positionToPositionDTO).toList();
    }

    public PositionDTO positionToPositionDTO(Position position) {
        if (position == null) {
            return null;
        }
        PositionDTO dto = new PositionDTO();
        dto.setPositionId(position.getId());
        dto.setName(position.getName());
        dto.setDescription(position.getDescription());
        dto.setIsDeleted(position.getIsDeleted());
        dto.setUpdatedBy(position.getUpdatedBy());
        dto.setCreatedBy(position.getCreatedBy());
        dto.setCreatedDate(position.getCreatedDate());
        dto.setUpdatedDate(position.getUpdatedDate());
        return dto;
    }

    public Position positionDTOToPosition(PositionDTO dto) {
        if (dto == null) {
            return null;
        }
        Position position = new Position();
        position.setName(dto.getName());
        position.setDescription(dto.getDescription());
        position.setCreatedBy(dto.getCreatedBy());
        position.setCreatedDate(java.time.LocalDateTime.now());
        position.setIsDeleted(false); // Default value for isdeleted
        // UpdatedBy and UpdatedDate can be set later
        position.setUpdatedBy(dto.getUpdatedBy());
        position.setUpdatedDate(dto.getUpdatedDate());
        return position;
    }

    public List<Position> positionDTOsToPositions(List<PositionDTO> dtos) {
        return dtos.stream()
                .filter(Objects::nonNull)
                .map(this::positionDTOToPosition)
                .toList();
    }

    public Position positionFromId(String id) {
        if (id == null) {
            return null;
        }
        Position position = new Position();
        position.setId(id);
        return position;
    }
}
