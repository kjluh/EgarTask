package egar.egartask.service;

import egar.egartask.entites.NotWorkingDays;
import egar.egartask.repository.NotWorkingDaysRepository;
import org.springframework.stereotype.Service;

import javax.xml.datatype.Duration;
import java.time.temporal.ChronoUnit;

@Service
public class NotWorkingDaysService {

    private NotWorkingDaysRepository repository;

    public NotWorkingDaysService(NotWorkingDaysRepository repository) {
        this.repository = repository;
    }

    //количество пропущеных дней
    public void notWorkingDays(Long id){
        int days = 0;
        try {
            NotWorkingDays notWorkingDays = repository.findById(id).orElseThrow();
            days = (int) ChronoUnit.DAYS.between(notWorkingDays.getStartDate(),notWorkingDays.getFinishDate());
        } catch (Exception e){

        }
    }
}
