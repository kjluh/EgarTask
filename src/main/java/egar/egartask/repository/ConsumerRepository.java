package egar.egartask.repository;

import egar.egartask.entites.Consumer;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public class ConsumerRepository {

    @Autowired
    private EntityManager entityManager;

   public Consumer findByLoginIgnoreCase(String login){
       TypedQuery<Consumer> query = entityManager.createQuery(
               "select c from Consumer c where c.login=:login", Consumer.class);
       return query.setParameter("login",login) .getSingleResult();
   };
}
