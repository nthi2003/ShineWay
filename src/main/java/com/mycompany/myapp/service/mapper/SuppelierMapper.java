package com.mycompany.myapp.service.mapper;

import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Service;

import com.mycompany.myapp.domain.Suppelier;
import com.mycompany.myapp.service.dto.SuppelierDTO;

@Service
public class SuppelierMapper {

     public List<SuppelierDTO> suppelierToSuppelierDTOs(List<Suppelier> suppliers) {
        return suppliers.stream().filter(Objects::nonNull).map(this::suppelierToSuppelierDTO).toList();
     }
     public SuppelierDTO suppelierToSuppelierDTO (Suppelier suppelier)
     {
        if(suppelier == null) {
            return null;
        }
        SuppelierDTO dto = new SuppelierDTO();
        dto.setSuppelierId(suppelier.getSuppelierId());
        dto.setName(suppelier.getName());
        dto.setAddress(suppelier.getAddress());
        dto.setPhone(suppelier.getPhone());
        dto.setUserId(suppelier.getUserId());
        dto.setCreatedDate(suppelier.getCreatedDate());
        dto.setCreatedBy(suppelier.getCreatedBy());
        dto.setUpdatedBy(suppelier.getUpdatedBy());
        dto.setUpdatedDate(suppelier.getUpdatedDate());
        return dto;
        
     }
     public Suppelier suppelierDTOToSuppelier (SuppelierDTO dto)
     {
        if(dto == null) {
            return null;
        }
        Suppelier suppelier = new Suppelier();
        suppelier.setSuppelierId(dto.getSuppelierId());
        suppelier.setName(dto.getName());
        suppelier.setAddress(dto.getAddress());
        suppelier.setPhone(dto.getPhone());
        suppelier.setUserId(dto.getUserId());
        suppelier.setCreatedDate(dto.getCreatedDate());
        suppelier.setCreatedBy(dto.getCreatedBy());
        suppelier.setUpdatedBy(dto.getUpdatedBy());
        suppelier.setUpdatedDate(dto.getUpdatedDate());
        return suppelier;
        
     }
     public List<Suppelier> suppelierDTOToSuppeliers(List<SuppelierDTO> dtos) {
        return dtos.stream().filter(Objects::nonNull).map(this::suppelierDTOToSuppelier).toList();
     }
}
