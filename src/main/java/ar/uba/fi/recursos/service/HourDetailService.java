package ar.uba.fi.recursos.service;

import ar.uba.fi.recursos.model.HourDetail;
import ar.uba.fi.recursos.repository.HourDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HourDetailService {

    @Autowired
    private HourDetailRepository hourDetailRepository;

    public HourDetail createHourDetail(HourDetail hourDetail) {
        // hourDetail.setId(-1L);
        return hourDetailRepository.save(hourDetail);
    }

    public HourDetail save(HourDetail hourDetail) {
        return hourDetailRepository.save(hourDetail);
    }
}
