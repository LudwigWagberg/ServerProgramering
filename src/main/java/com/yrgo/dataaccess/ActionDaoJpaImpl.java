package com.yrgo.dataaccess;


import com.yrgo.domain.Action;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;


@Transactional
public class ActionDaoJpaImpl implements ActionDao{


    @PersistenceContext
    private EntityManager em;


    public void create(Action newAction) {
        em.persist(newAction);
    }


    public List<Action> getIncompleteActions(String userId) {
        return em.createQuery("select action from Action as action where action.owningUser=:userId and action.complete=false", Action.class)
                .setParameter("userId", userId)
                .getResultList();
    }


    public void update(Action actionToUpdate) throws RecordNotFoundException {
        em.merge(actionToUpdate);
    }


    public void delete(Action oldAction) throws RecordNotFoundException {
        Action action = em.find(Action.class, oldAction.getActionId());
        em.remove(action);
    }
}