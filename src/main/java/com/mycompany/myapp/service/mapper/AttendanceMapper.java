package com.mycompany.myapp.service.mapper;

import java.time.LocalTime;
import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Component;

import com.mycompany.myapp.domain.Attendance;
import com.mycompany.myapp.service.dto.AttendanceDTO;

@Component
public class AttendanceMapper {
    public List<AttendanceDTO> attendancesToAttendanceDTOs(List<Attendance> attendances) {
        return attendances.stream()
            .filter(Objects::nonNull)
            .map(this::attendanceToAttendanceDTO)
            .toList();
    }

    public AttendanceDTO attendanceToAttendanceDTO(Attendance attendance) {
        if (attendance == null) {
            return null;
        }

        AttendanceDTO dto = new AttendanceDTO();
        dto.setId(attendance.getId());
        dto.setUserId(attendance.getUser() != null ? attendance.getUser().getId() : null);
        dto.setCheckIn(attendance.getCheckIn() != null ? attendance.getCheckIn().toString() : null);
        dto.setCheckOut(attendance.getCheckOut() != null ? attendance.getCheckOut().toString() : null);
        dto.setDay(attendance.getDay());
        dto.setIsDeleted(attendance.getIsDeleted());
        dto.setCreatedBy(attendance.getCreatedBy());
        dto.setUpdatedBy(attendance.getUpdatedBy());
        dto.setCreatedDate(attendance.getCreatedDate());
        dto.setUpdatedDate(attendance.getUpdatedDate());

        return dto;
    }

    public List<Attendance> attendanceDTOsToAttendances(List<AttendanceDTO> dtos) {
        return dtos.stream()
            .filter(Objects::nonNull)
            .map(this::attendanceDTOToAttendance)
            .toList();
    }

    public Attendance attendanceDTOToAttendance(AttendanceDTO dto) {
        if (dto == null) {
            return null;
        }

        Attendance attendance = new Attendance();
        attendance.setId(dto.getId());
        attendance.setCheckIn(dto.getCheckIn() != null ? LocalTime.parse(dto.getCheckIn()) : null);
        attendance.setCheckOut(dto.getCheckOut() != null ? LocalTime.parse(dto.getCheckOut()) : null);
        attendance.setDay(dto.getDay());
        attendance.setIsDeleted(false);
        attendance.setCreatedBy(dto.getCreatedBy());
        attendance.setUpdatedBy(dto.getUpdatedBy());
        attendance.setCreatedDate(java.time.LocalDateTime.now());
        attendance.setUpdatedDate(dto.getUpdatedDate());

        return attendance;
    }

    public Attendance attendanceFromId(String id) {
        if (id == null) {
            return null;
        }
        Attendance attendance = new Attendance();
        attendance.setId(id);
        return attendance;
    }
}