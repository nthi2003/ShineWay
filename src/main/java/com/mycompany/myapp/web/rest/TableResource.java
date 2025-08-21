package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.service.TableQueryService;
import com.mycompany.myapp.service.TableService;
import com.mycompany.myapp.service.criteria.TableCriteria;
import com.mycompany.myapp.service.dto.TableDTO;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
public class TableResource {

    private final TableService tableService;
    private final TableQueryService tableQueryService;

    public TableResource(TableService tableService, TableQueryService tableQueryService) {
        this.tableService = tableService;
        this.tableQueryService = tableQueryService;
    }

    @PreAuthorize("@permissionEvaluator.hasAuthority('TABLE_ALL')")
    @GetMapping("/tables")
    public ResponseEntity<Page<TableDTO>> getAllTables(TableCriteria criteria, Pageable pageable) {
        Page<TableDTO> tables = tableQueryService.findByCriteria(criteria, pageable);
        return ResponseEntity.ok(tables);
    }

    @PreAuthorize("@permissionEvaluator.hasAuthority('TABLE_ID')")
    @GetMapping("/table/{id}")
    public ResponseEntity<TableDTO> getTableById(@PathVariable String id) {
        return ResponseEntity.ok(tableService.getTableById(id));
    }

    @PreAuthorize("@permissionEvaluator.hasAuthority('TABLE_CREATE')")
    @PostMapping("/table")
    public ResponseEntity<TableDTO> createTable(@RequestBody TableDTO tableDTO) {
        TableDTO createdTable = tableService.createTable(tableDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTable);
    }

    @PreAuthorize("@permissionEvaluator.hasAuthority('TABLE_UPDATE')")
    @PutMapping("/table/{id}")
    public ResponseEntity<TableDTO> updateTable(@PathVariable String id, @RequestBody TableDTO tableDTO) {
        tableDTO.setId(id);
        if (tableDTO.getId() == null) {
            return ResponseEntity.badRequest().build();
        }
        TableDTO updatedTable = tableService.updateTable(tableDTO);
        return ResponseEntity.ok(updatedTable);
    }

    @PreAuthorize("@permissionEvaluator.hasAuthority('TABLE_DELETE')")
    @DeleteMapping("/table/{id}")
    public ResponseEntity<Void> deleteTable(@PathVariable String id) {
        tableService.deleteTable(id);
        return ResponseEntity.noContent().build();
    }
}
