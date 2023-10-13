package egar.egartask.repository;

import egar.egartask.entites.Employee;
import egar.egartask.entites.NotWorkingDays;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class NotWorkingDaysRepository {

    @Autowired
    private EntityManager entityManager;

    //save + find
    public List<NotWorkingDays> getNotWorkingDaysByEmployee_Id(Long id) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<NotWorkingDays> criteriaQuery = criteriaBuilder.createQuery(NotWorkingDays.class);
        Root<NotWorkingDays> root = criteriaQuery.from(NotWorkingDays.class);
        Join<NotWorkingDays, Employee> join =  root.join("employee_id");
        criteriaQuery.select(root);

//        criteriaQuery.where(criteriaBuilder.equal(root.get("employee_id"), id));
//        List<NotWorkingDays> list = entityManager.createQuery(criteriaQuery).getResultList();
        List<NotWorkingDays> list = entityManager.createQuery(criteriaQuery).getResultList();
        List<NotWorkingDays> newList = list.stream().filter(e->e.getEmployee().getId()==id).toList();
        return newList;
    }

    public List<NotWorkingDays> getNotWorkingDaysByComments(String com) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<NotWorkingDays> criteriaQuery = criteriaBuilder.createQuery(NotWorkingDays.class);
        Root<NotWorkingDays> root = criteriaQuery.from(NotWorkingDays.class);

        Predicate comment = criteriaBuilder.like(root.get("comment"), "%" + com + "%");
        criteriaQuery.where(comment);
        List<NotWorkingDays> list = entityManager.createQuery(criteriaQuery).getResultList();
        return list;
    }

    @Transactional
    public void save(NotWorkingDays notWorkingDays) {
        entityManager.persist(notWorkingDays);
    }
}
