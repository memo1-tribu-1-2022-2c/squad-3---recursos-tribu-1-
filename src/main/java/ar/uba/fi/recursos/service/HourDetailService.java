package ar.uba.fi.recursos.service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import ar.uba.fi.recursos.exceptions.OverlappingDatesException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import ar.uba.fi.recursos.dtos.TaskData;
import ar.uba.fi.recursos.exceptions.InvalidDateException;
import ar.uba.fi.recursos.exceptions.InvalidTypeException;
import ar.uba.fi.recursos.model.HourDetail;
import ar.uba.fi.recursos.model.HourDetailStatus;
import ar.uba.fi.recursos.model.TimeRegister;
import ar.uba.fi.recursos.model.TimeRegisterTypeOfActivity;
import ar.uba.fi.recursos.repository.HourDetailRepository;

import javax.transaction.Transactional;

@Service
public class HourDetailService {

    @Autowired
    private HourDetailRepository hourDetailRepository;
    @Autowired
    private ResourceService resourceService;

    public HourDetail createHourDetail(HourDetail hourDetail) {
        checkValidPeriod(hourDetail);
        checkOverlapping(hourDetail);

        if (resourceService.findById(hourDetail.getWorkerId()).isEmpty()) {
            throw new InvalidTypeException("El recurso no existe");
        }

        hourDetail.setStatus(HourDetailStatus.DRAFT);
        hourDetail.setTimeRegisters(new ArrayList<>());
        return hourDetailRepository.save(hourDetail);
    }

    public HourDetail modifyHourDetail(HourDetail old, HourDetail hourDetail) {
        hourDetail.setId(old.getId());

        checkValidPeriod(hourDetail);
        checkOverlapping(hourDetail);

        if (resourceService.findById(hourDetail.getWorkerId()).isEmpty()) {
            throw new InvalidTypeException("El recurso no existe");
        }

        return hourDetailRepository.save(hourDetail);
    }

    protected void checkValidPeriod(HourDetail hourDetail) {
        LocalDate startDate = hourDetail.getStartTime();

        switch (hourDetail.getType()) {
            case WEEKLY -> {
                if (startDate.getDayOfWeek() != DayOfWeek.MONDAY) {
                    throw new InvalidDateException("La fecha especificada no es un lunes: " + startDate);
                }
                hourDetail.setEndTime(startDate.plusDays(6));
            }
            case BIWEEKLY -> {
                if (startDate.getDayOfMonth() == 1) {
                    hourDetail.setEndTime(startDate.plusDays(14));
                } else if (startDate.getDayOfMonth() == 16) {
                    hourDetail.setEndTime(YearMonth.from(startDate).atEndOfMonth());
                } else {
                    throw new InvalidDateException("La fecha especificada no es un 1 o 16 del mes: " + startDate);
                }
            }
            case MONTHLY -> {
                if (startDate.getDayOfMonth() != 1) {
                    throw new InvalidDateException("La fecha especificada no es el primer día del mes: " + startDate);
                }
                hourDetail.setEndTime(YearMonth.from(startDate).atEndOfMonth());
            }
            default -> {
                throw new InvalidTypeException("El tipo especificado es inválido: " + hourDetail.getType());
            }
        }
    }

    @Transactional
    protected void checkOverlapping(HourDetail hourDetail) {
        List<String> overlapping = this.hourDetailRepository.findAllWithOverlappingDates(
                hourDetail.getWorkerId(), hourDetail.getStartTime(), hourDetail.getEndTime()
        ).stream().map(hd -> hd.getId().toString()).toList();

        if (!overlapping.isEmpty()) {
            String message = "Las horas se solapan con ";
            if (overlapping.size() > 1) {
                message += "los partes: ";
            } else {
                message += "el parte: ";
            }
            throw new OverlappingDatesException(message + String.join(", ", overlapping));
        }
    }

    public HourDetail save(HourDetail hourDetail) {
        return hourDetailRepository.save(hourDetail);
    }

    public Double getTotalProjectHours(Long projectId) {
        String url = "https://squad2-2022-2c.herokuapp.com/api/v1/projects/" + projectId + "/tasks";
        RestTemplate restTemplate = new RestTemplate();

        TaskData[] tasks_raw = restTemplate.getForObject(url, TaskData[].class);
        if (tasks_raw == null) {
            return 0D;
        }

        List<Long> tasks = Arrays.asList(tasks_raw).stream().map(task -> task.getId()).toList();

        Double totalHours = 0D;
        for (HourDetail hd : this.hourDetailRepository.findAll()) {
            for (TimeRegister tr : hd.getTimeRegisters()) {
                if (tr.getTypeOfActivity() == TimeRegisterTypeOfActivity.TASK) {
                    if (tasks.contains(tr.getActivityId())) {
                        totalHours += tr.getHours();
                    }
                }
            }
        }

        return totalHours;
    }

    public Optional<HourDetail> findById(Long id) {
        return hourDetailRepository.findById(id);
    }

    public List<HourDetail> findByWorkerId(Long workerId) {
        return hourDetailRepository.findByWorkerIdOrderByStartTime(workerId);
    }

    public List<HourDetail> findAll() {
        return hourDetailRepository.findAll();
    }

    public void deleteById(Long id) {
        hourDetailRepository.deleteById(id);
    }
}
