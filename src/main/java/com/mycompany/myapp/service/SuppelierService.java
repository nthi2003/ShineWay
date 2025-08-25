package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Suppelier;
import com.mycompany.myapp.repository.SuppelierRepository;
import com.mycompany.myapp.security.SecurityUtils;
import com.mycompany.myapp.service.dto.SuppelierDTO;
import com.mycompany.myapp.service.mapper.SuppelierMapper;
import jakarta.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
@Transactional
public class SuppelierService {

    private final SuppelierRepository repository;
    private final SuppelierMapper mapper;

    public SuppelierService(SuppelierRepository repository, SuppelierMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    // CREATE
    public SuppelierDTO create(SuppelierDTO dto) {
        boolean exists = repository
            .findAllByIsDeletedFalse()
            .stream()
            .anyMatch(t -> t.getName().equalsIgnoreCase(dto.getName()));
        if (exists) {
            throw new RuntimeException("Suppelier name already exists");
        }
        Suppelier suppelier = mapper.suppelierDTOToSuppelier(dto);
        Long currentUserId = SecurityUtils.getCurrentUserId().orElseThrow(() -> new RuntimeException("Not logged in"));
        suppelier.setCreatedBy(currentUserId);
        suppelier.setCreatedDate(java.time.LocalDateTime.now());
        suppelier.setIsDeleted(false);
        suppelier.setUserId(currentUserId);
        Suppelier savesuppelier = repository.save(suppelier);
        return mapper.suppelierToSuppelierDTO(savesuppelier);

    }

    //  UPDATE (full)
    public SuppelierDTO update(SuppelierDTO dto) {
        Suppelier e = repository.findByIdAndIsDeletedFalse(dto.getSuppelierId())
        .orElseThrow(() -> new RuntimeException("Suppelier not found"));
         e.setName(dto.getName());
         e.setAddress(dto.getAddress());
         e.setPhone(dto.getPhone());
         e.setUserId(dto.getUserId());

         Long currentUserId = SecurityUtils.getCurrentUserId().orElseThrow(() -> new RuntimeException("Not logged in"));
         e.setUpdatedBy(currentUserId);
         e.setUpdatedDate(LocalDateTime.now());

         return mapper.suppelierToSuppelierDTO(repository.save(e));
    }

    // VIEW
    public SuppelierDTO getById(String id) {
        return repository.findByIdAndIsDeletedFalse(id)
            .map(mapper::suppelierToSuppelierDTO)
            .orElseThrow(() -> new RuntimeException("Suppelier not found"));
    }

    // LIST
    public List<SuppelierDTO> getAll() {
        return mapper.suppelierToSuppelierDTOs(repository.findAllByIsDeletedFalse());
    }

    // DELETE (soft)
    public void delete(String id) {
        Suppelier e = repository.findByIdAndIsDeletedFalse(id)
            .orElseThrow(() -> new RuntimeException("Suppelier not found"));
        e.setIsDeleted(true);
        
        repository.save(e);
    }

    public List<SuppelierDTO> getAllUserId( Long userId ) {
        if(userId == null) {
         throw new RuntimeException("user not found");
        }
         return mapper.suppelierToSuppelierDTOs(repository.findAlLByUserId(userId));
    }
}

   