package egar.egartask.repository;

import egar.egartask.entites.Consumer;
import egar.egartask.entites.Employee;
import egar.egartask.entites.NotWorkingDays;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
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

//    public List<NotWorkingDays> getNotWorkingDaysByEmployee_Id(Long id) {
//        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
//        CriteriaQuery<NotWorkingDays> criteriaQuery = criteriaBuilder.createQuery(NotWorkingDays.class);
//        Root<NotWorkingDays> root = criteriaQuery.from(NotWorkingDays.class);
//        criteriaQuery.select(root);
//
//        Join<NotWorkingDays, Employee> join = root.join("id");
//        Predicate p = criteriaBuilder.equal(join.get("id"), id);
//
//        criteriaQuery.where(p);
//        List<NotWorkingDays> list = entityManager.createQuery(criteriaQuery).getResultList();
////        List<NotWorkingDays> newList = list.stream().filter(e->e.getEmployee().getId()==id).toList();
//        return list;
//    }

    public List<NotWorkingDays> getNotWorkingDaysByEmployee_Id(Long id) {
        TypedQuery<NotWorkingDays> query = entityManager.createQuery(
                "select n from NotWorkingDays n where n.employee.id=:id", NotWorkingDays.class);
        return query.setParameter("id", id).getResultList();
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
