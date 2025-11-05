package com.controller;

import com.dto.*;
import com.service.SchedulerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class SchedulerController {

    // ######################################## 속성 필드 ########################################
    // 하위 Layer
    private final SchedulerService schedulerService;

    // ######################################## 메서드 ########################################
    // ---------------------------------------- 일정 생성 - POST ----------------------------------------
    @PostMapping("/scheduler")
    public ResponseEntity<CreateScheduleResponse> createSchedule(@RequestBody CreateScheduleRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(schedulerService.createSchedule(request));
    }
    
    // ---------------------------------------- 일정 단건 조회 - GET ----------------------------------------
    @GetMapping("/scheduler/{scheduleId}")
    public ResponseEntity<GetScheduleResponse> getSchedule(@PathVariable Long scheduleId) {
        return ResponseEntity.status(HttpStatus.OK).body(schedulerService.getSchedule(scheduleId));
    }
    
    // ---------------------------------------- 일정 다건 조회 (작성자명을 기준) - GET ----------------------------------------
    @GetMapping("/scheduler")
    public ResponseEntity<List<GetScheduleResponse>> getAllWritersSchedules(@RequestParam String writer) {
        return ResponseEntity.status(HttpStatus.OK).body(schedulerService.getAllWritersSchedules(writer));
    }
    
    // ---------------------------------------- 일정 수정 - PUT ----------------------------------------
    @PutMapping("/scheduler/{scheduleId}")
    public ResponseEntity<UpdateScheduleResponse> updateSchedule(@PathVariable Long scheduleId, @RequestBody UpdateScheduleRequest request) {
        return ResponseEntity.status(HttpStatus.OK).body(schedulerService.updateSchedule(scheduleId, request));
    }
    
    // ---------------------------------------- 일정 삭제 - DELETE ----------------------------------------
    @DeleteMapping("/scheduler/{scheduleId}")
    public ResponseEntity<Void> deleteSchedule(@PathVariable Long scheduleId) {
        schedulerService.deleteSchedule(scheduleId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
