package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Tables;
import com.mycompany.myapp.repository.TablesRepository;
import com.mycompany.myapp.security.SecurityUtils;
import com.mycompany.myapp.service.dto.TableDTO;
import com.mycompany.myapp.service.mapper.TableMapper;
import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class TableService {

    private final TablesRepository tablesRepository;
    private final TableMapper tableMapper;

    public TableService(TablesRepository tablesRepository, TableMapper tableMapper) {
        this.tablesRepository = tablesRepository;
        this.tableMapper = tableMapper;
    }

    public List<TableDTO> getAllTables() {
        List<Tables> tables = tablesRepository.findAllByIsDeletedFalse();
        return tableMapper.tablesToTableDTOs(tables);
    }

    public TableDTO getTableById(String id) {
        return tablesRepository
            .findByIdAndIsDeletedFalse(id)
            .map(tableMapper::tableToTableDTO)
            .orElseThrow(() -> new RuntimeException("Table not found"));
    }

    public TableDTO createTable(TableDTO tableDTO) {
        boolean exists = tablesRepository
            .findAllByIsDeletedFalse()
            .stream()
            .anyMatch(t -> t.getName().equalsIgnoreCase(tableDTO.getName()));
        if (exists) {
            throw new RuntimeException("Table name already exists");
        }
        Tables table = tableMapper.tableDTOToTable(tableDTO);
        Long currentUserId = SecurityUtils.getCurrentUserId().orElseThrow(() -> new RuntimeException("Not logged in"));

        table.setCreatedBy(currentUserId);
        table.setCreatedDate(java.time.LocalDateTime.now());
        table.setStatus(1);
        Tables savedTable = tablesRepository.save(table);
        return tableMapper.tableToTableDTO(savedTable);
    }

    public TableDTO updateTable(TableDTO tableDTO) {
        Tables table = tablesRepository
            .findByIdAndIsDeletedFalse(tableDTO.getId())
            .orElseThrow(() -> new RuntimeException("Table not found"));
        table.setName(tableDTO.getName());
        table.setCapacity(tableDTO.getCapacity());
        table.setStatus(tableDTO.getStatus());
        table.setArea(tableDTO.getArea());
        table.setUpdatedBy(SecurityUtils.getCurrentUserId().orElse(null));
        table.setUpdatedDate(java.time.LocalDateTime.now());

        Tables updatedTable = tablesRepository.save(table);
        return tableMapper.tableToTableDTO(updatedTable);
    }

    public void deleteTable(String id) {
        Tables tables = tablesRepository.findByIdAndIsDeletedFalse(id).orElseThrow(() -> new RuntimeException("Table not found"));
        tables.setIsDeleted(true);
        tablesRepository.save(tables);
    }
}
