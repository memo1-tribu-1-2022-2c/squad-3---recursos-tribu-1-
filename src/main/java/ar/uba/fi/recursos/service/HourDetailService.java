package ar.uba.fi.recursos.service;

import ar.uba.fi.recursos.exceptions.InvalidDatesException;
import ar.uba.fi.recursos.exceptions.InvalidTypeException;
import ar.uba.fi.recursos.exceptions.OverlappingDatesException;
import ar.uba.fi.recursos.exceptions.InvalidHourDetailHoursException;
import ar.uba.fi.recursos.exceptions.InvalidHourDetailHoursException;
import ar.uba.fi.recursos.model.HourDetail;
import ar.uba.fi.recursos.repository.HourDetailRepository;


import java.time.ZoneId;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.tomcat.util.json.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class HourDetailService {

    @Autowired
    private HourDetailRepository hourDetailRepository;

    // TODO: ARREGLAR lo de las dates
    // TODO: checkear esto mismo al hacer un put, para eso tenemos q modularizar y llamar a la funcion en ambos endpoints

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
            }
        }

        Date sD = Date.from(hourDetail.getStartTime().atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date eD = Date.from(hourDetail.getEndTime().atStartOfDay(ZoneId.systemDefault()).toInstant());
        int difference_in_days = (int) (((eD.getTime() - sD.getTime()) / (1000*60*60*24))%365);
        if(hourDetail.getHours()>24*(difference_in_days+1)){
            throw new InvalidHourDetailHoursException();
        }


        this.hourDetailRepository.findByWorkerId(hourDetail.getWorkerId()).stream().forEach(hd -> {
            if(hd.getStartTime().isBefore(hourDetail.getEndTime()) && hd.getEndTime().isAfter(hourDetail.getStartTime())){ // si se solapan
                throw new OverlappingDatesException();
            }
        });
        return hourDetailRepository.save(hourDetail);
    }

    public HourDetail save(HourDetail hourDetail) {
        return hourDetailRepository.save(hourDetail);
    }

    public Integer getTotalProjectHours(Long projectId) {
        // TODO
        String url = "https://squad2-2022-2c.herokuapp.com/api/v1/projects/"+ projectId +"/tasks";
        RestTemplate restTemplate = new RestTemplate();
        
        // get json from url
        // String json = restTemplate.getForObject(url, Object.class);
        // parse json

        // Integer totalHours = 0;


        // necesitamos el id de las tasks para buscar los hourDetails con timeRegisters que tengan ese 
        // activityId y que su tipo sea TASK
        // y sumar las horas de esos timeRegisters


        // System.out.println("tasks: " + json);
        
        // try {
        //     JSONParser parser = new JSONParser( json );
            
        //     // get the value of the first name
        //     String firstName = parser.parseObject().get( "firstName" ).toString();
        //     System.out.println( "First Name: " + firstName );
        //     System.out.println("tasks mapping: " + mapping);
        // }
        // catch (Exception e) {
        //     System.out.println("Error: " + e);
        // }




        return 0;
    }

}
