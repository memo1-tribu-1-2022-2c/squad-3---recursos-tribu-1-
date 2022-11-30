package ar.uba.fi.recursos.service;

import ar.uba.fi.recursos.exceptions.InvalidDatesException;
import ar.uba.fi.recursos.exceptions.InvalidTypeException;
import ar.uba.fi.recursos.exceptions.InvalidHourDetailHoursException;
import ar.uba.fi.recursos.exceptions.InvalidHourDetailHoursException;
import ar.uba.fi.recursos.model.HourDetail;
import ar.uba.fi.recursos.repository.HourDetailRepository;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HourDetailService {

    @Autowired
    private HourDetailRepository hourDetailRepository;

    public HourDetail createHourDetail(HourDetail hourDetail) throws Throwable {
        // hourDetail.setId(-1L);
        if(hourDetail.getHours()<=0){
            throw new InvalidHourDetailHoursException();
        }
        Date startDate = Date.from(hourDetail.getStartTime().atStartOfDay(ZoneId.systemDefault()).toInstant());
        Calendar cal = Calendar.getInstance();
        cal.setTime(startDate);
        switch (hourDetail.getType()){
            case SEMANAL: {
                if(!((cal.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY))){
                    throw new InvalidDatesException();
                }
                cal.add(Calendar.DAY_OF_MONTH, 6);
                hourDetail.setEndTime(cal.getTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
                break;
            }
            case QUINCENAL: {
                if(!((cal.get(Calendar.DAY_OF_MONTH) == 1)) && !((cal.get(Calendar.DAY_OF_MONTH) == 16))){
                    throw new InvalidDatesException();
                }

                if((cal.get(Calendar.DAY_OF_MONTH) == 1)){
                    cal.add(Calendar.DAY_OF_MONTH, 14);
                    hourDetail.setEndTime(cal.getTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
                    break;
                }
                int max_day = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
                cal.set(Calendar.DAY_OF_MONTH, max_day);
                hourDetail.setEndTime(cal.getTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
                break;
            }
            case MENSUAL: {

                if(!((cal.get(Calendar.DAY_OF_MONTH) == 1))){
                    throw new InvalidDatesException();
                }
                int max_day = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
                cal.set(Calendar.DAY_OF_MONTH, max_day);
                hourDetail.setEndTime(cal.getTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
                break;
            }
            default: {
                throw new InvalidTypeException();
        }}
        Date sD = Date.from(hourDetail.getStartTime().atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date eD = Date.from(hourDetail.getEndTime().atStartOfDay(ZoneId.systemDefault()).toInstant());
        int difference_in_days = (int) (((eD.getTime() - sD.getTime()) / (1000*60*60*24))%365);
        if(hourDetail.getHours()>24*(difference_in_days+1)){
            throw new InvalidHourDetailHoursException();
        }

        // aca
        return hourDetailRepository.save(hourDetail);
    }

    public HourDetail save(HourDetail hourDetail) {
        return hourDetailRepository.save(hourDetail);
    }

}
