package com.mycompany.myapp.service;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mycompany.myapp.domain.Attendance;
import com.mycompany.myapp.domain.User;
import com.mycompany.myapp.repository.AttendanceRepository;
import com.mycompany.myapp.security.SecurityUtils;
import com.mycompany.myapp.service.dto.AttendanceDTO;
import com.mycompany.myapp.service.mapper.AttendanceMapper;

@Service
@Transactional
public class AttendanceService {
    private final AttendanceRepository attendanceRepository;
    private final AttendanceMapper attendanceMapper;

    public AttendanceService(AttendanceRepository attendanceRepository, AttendanceMapper attendanceMapper) {
        this.attendanceRepository = attendanceRepository;
        this.attendanceMapper = attendanceMapper;
    }
   public AttendanceDTO createAttendance(AttendanceDTO attendanceDTO) {
        Attendance attendance = attendanceMapper.attendanceDTOToAttendance(attendanceDTO);
        Long currentUserId = SecurityUtils.getCurrentUserId()
                .orElseThrow(() -> new RuntimeException("Not logged in"));
        User user = new User();
        user.setId(currentUserId);
        attendance.setUser(user);
        attendance.setCheckIn(LocalTime.now());
        attendance.setDay(LocalDate.now());
        attendance.setCreatedBy(currentUserId);
        attendance.setIsDeleted(false);
        attendance = attendanceRepository.save(attendance);
        return attendanceMapper.attendanceToAttendanceDTO(attendance);
    }
    public AttendanceDTO updateAttendance(AttendanceDTO attendanceDTO) {
        Attendance attendance = attendanceRepository.findById(attendanceDTO.getId())
                .orElseThrow(() -> new RuntimeException("Attendance not found"));
        if (attendance.getIsDeleted()) {
            throw new RuntimeException("Attendance is deleted");
        }
        attendance.setCheckOut(LocalTime.now());
        attendance.setIsDeleted(false);
        attendance.setUpdatedDate(LocalDateTime.now());
        attendance.setUpdatedBy(
                SecurityUtils.getCurrentUserId().orElseThrow(() -> new RuntimeException("Not logged in")));
                attendance = attendanceRepository.save(attendance);
        return attendanceMapper.attendanceToAttendanceDTO(attendance);
    }
    public AttendanceDTO getAttendance(String id) {
        return attendanceRepository.findByIdAndIsDeletedFalse(id)
                .map(attendanceMapper::attendanceToAttendanceDTO)
                .orElseThrow(() -> new RuntimeException("Attendance not found"));
    }
    public void deleteAttendance(String id) 
    {
        Attendance attendance = attendanceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Attendance not found"));
        attendance.setIsDeleted(true);
        attendanceRepository.save(attendance);
    }
}