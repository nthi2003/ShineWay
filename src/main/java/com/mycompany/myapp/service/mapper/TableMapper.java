package com.mycompany.myapp.service.mapper;

import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Service;

import com.mycompany.myapp.service.dto.TableDTO;
import com.mycompany.myapp.domain.Tables;

@Service
public class TableMapper {
    public List<TableDTO> tablesToTableDTOs(List<Tables> tables){
        return tables.stream().filter(Objects::nonNull).map(this::tableToTableDTO).toList();
    }

    public TableDTO tableToTableDTO(Tables table) {
        if (table == null) {
            return null;
        }
        TableDTO tableDTO = new TableDTO();
        tableDTO.setId(table.getId());
        tableDTO.setName(table.getName());
        tableDTO.setCapacity(table.getCapacity());
        tableDTO.setStatus(table.getStatus());
        tableDTO.setArea(table.getArea());
        tableDTO.setIsDeleted(table.getIsDeleted());
        tableDTO.setCreatedBy(table.getCreatedBy());
        tableDTO.setUpdatedBy(table.getUpdatedBy());
        tableDTO.setCreatedDate(table.getCreatedDate());
        tableDTO.setUpdatedDate(table.getUpdatedDate());
        return tableDTO;
    }
    public List<Tables> tableDTOsToTables(List<TableDTO> tableDTOs) {
        return tableDTOs.stream().filter(Objects::nonNull).map(this::tableDTOToTable).toList();
    }

    public Tables tableDTOToTable(TableDTO tableDTO) {
        if (tableDTO == null) {
            return null;
        }
        Tables table = new Tables();
        table.setName(tableDTO.getName());
        table.setCapacity(tableDTO.getCapacity());
        table.setStatus(tableDTO.getStatus());
        table.setArea(tableDTO.getArea());
        table.setIsDeleted(false);
        table.setCreatedBy(tableDTO.getCreatedBy());
        table.setUpdatedBy(tableDTO.getUpdatedBy());
        table.setCreatedDate(java.time.LocalDateTime.now());
        table.setUpdatedDate(tableDTO.getUpdatedDate());
        return table;
    }
    public Tables tableFromId(String id) {
        if (id == null) {
            return null;
        }
        Tables table = new Tables();
        table.setId(id);
        return table;
    }
}
