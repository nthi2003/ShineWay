package com.mycompany.myapp.web.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mycompany.myapp.service.AttendanceQueryService;
import com.mycompany.myapp.service.AttendanceService;
import com.mycompany.myapp.service.criteria.AttendanceCriteria;
import com.mycompany.myapp.service.dto.AttendanceDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@RestController
@RequestMapping("/api")
public class AttendanceResource {
    private final AttendanceService attendanceService;
    private final AttendanceQueryService attendanceQueryService;

    public AttendanceResource(AttendanceService attendanceService, AttendanceQueryService attendanceQueryService) {
        this.attendanceService = attendanceService;
        this.attendanceQueryService = attendanceQueryService;
    }
    //check in
    @PreAuthorize("@permissionEvaluator.hasAuthority('ATTENDANCE_CREATE')")
    @PostMapping("/attendance")
    public ResponseEntity<AttendanceDTO> createAttendance(@Validated @RequestBody AttendanceDTO attendanceDTO) {
       AttendanceDTO result = attendanceService.createAttendance(attendanceDTO);
        return ResponseEntity.ok(result);
    }

    @PreAuthorize("@permissionEvaluator.hasAuthority('ATTENDANCE_ID')")
    @GetMapping("/attendance/{id}")
    public ResponseEntity<AttendanceDTO> getAttendance(@PathVariable String id ) {
        AttendanceDTO result = attendanceService.getAttendance(id);
        return ResponseEntity.ok(result);
    }
    @GetMapping("/attendance")
    public ResponseEntity<Page<AttendanceDTO>> getAllAttendance(AttendanceCriteria criteria , Pageable pageable) {
        Page<AttendanceDTO> result = attendanceQueryService.findByCriteria(criteria , pageable);
        return ResponseEntity.ok(result);
    }
    @PreAuthorize("@permissionEvaluator.hasAuthority('ATTENDANCE_DELETE')")
    @DeleteMapping("/attendance/{id}")
    public ResponseEntity<Void> deleteAttendance(@PathVariable String id) {
        attendanceService.deleteAttendance(id);
        return ResponseEntity.noContent().build();
    }//check out
    @PreAuthorize("@permissionEvaluator.hasAuthority('ATTENDANCE_UPDATE')")
    @PutMapping("/attendance/{id}")
    public ResponseEntity<AttendanceDTO> updateAttendance(@PathVariable String id, @Validated @RequestBody AttendanceDTO attendanceDTO) {
        attendanceDTO.setId(id);
        AttendanceDTO result = attendanceService.updateAttendance(attendanceDTO);
        return ResponseEntity.ok(result);
    }

}
