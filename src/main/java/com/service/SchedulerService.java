package com.service;

import com.dto.*;
import com.entity.Scheduler;
import com.repository.SchedulerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SchedulerService {

    // ######################################## 속성 필드 ########################################
    // 하위 Layer
    private final SchedulerRepository schedulerRepository;



    // ######################################## 메서드 ########################################
    // ---------------------------------------- 저장 - POST ----------------------------------------
    @Transactional
    public CreateScheduleResponse createSchedule(CreateScheduleRequest request) {
        // Entity에 리퀘스트 dto 넣기
        Scheduler schedule = new Scheduler(
                request.getTitle(),
                request.getWriter(),
                request.getContent(),
                request.getPassword()
        );
        Scheduler savedSchedule = schedulerRepository.save(schedule);

        return new CreateScheduleResponse(
                savedSchedule.getId(),

                savedSchedule.getTitle(),
                savedSchedule.getWriter(),
                savedSchedule.getContent(),
                savedSchedule.getPassword(),

                savedSchedule.getCreatedAt(),
                savedSchedule.getUpdatedAt()
                );
    }

    // ---------------------------------------- 단건 조회 - GET ----------------------------------------
    @Transactional(readOnly = true)
    public GetScheduleResponse getSchedule(Long scheduleId) {
        Scheduler scheduler = schedulerRepository.findById(scheduleId).orElseThrow(
                () -> new IllegalStateException("Scheduler with id " + scheduleId + " not found")
        );
        return new GetScheduleResponse(
                scheduler.getId(),

                scheduler.getTitle(),
                scheduler.getWriter(),
                scheduler.getContent(),
                //scheduler.getPassword(),

                scheduler.getCreatedAt(),
                scheduler.getUpdatedAt()
        );
    }

    // ---------------------------------------- 다건 조회 - GET ----------------------------------------
    @Transactional(readOnly = true)
    public List<GetScheduleResponse> getAllWritersSchedules(String writer) {
        List<Scheduler> schedules = schedulerRepository.findAllByWriter(writer);

        return schedules.stream()
                .map(s -> new GetScheduleResponse(
                        s.getId(),
                        s.getTitle(),
                        s.getWriter(),
                        s.getContent(),
                        s.getCreatedAt(),
                        s.getUpdatedAt()
                ))
                .toList();
    }

    // ---------------------------------------- 수정 - PUT ----------------------------------------
    @Transactional
    public UpdateScheduleResponse updateSchedule(Long scheduleId, UpdateScheduleRequest request) {
        // Id에 대응되는 행 찾기
        Scheduler scheduler = schedulerRepository.findById(scheduleId).orElseThrow(
                () -> new IllegalStateException("Scheduler with id " + scheduleId + " not found")
        );
        // 비밀번호 검증 - request와 기존 scheduler의 password를 비교
        if (!scheduler.getPassword().equals(request.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
        // 선택한 일정 내용 중 일정 제목, 작성자명 만 수정 가능
        scheduler.updateSchedule(request.getTitle(), request.getWriter());
        return new UpdateScheduleResponse(
                scheduler.getId(),
                scheduler.getTitle(),
                scheduler.getWriter(),
                scheduler.getContent(),
                //scheduler.getPassword(),
                scheduler.getCreatedAt(),
                scheduler.getUpdatedAt()
        );
    }

    // ---------------------------------------- 삭제 - DELETE ----------------------------------------
    @Transactional
    public void deleteSchedule(Long scheduleId) {
        boolean exists = schedulerRepository.existsById(scheduleId);
        if (!exists) {
            throw new IllegalArgumentException("Scheduler with id " + scheduleId + " not found.");
        }
        schedulerRepository.deleteById(scheduleId);
    }

}
