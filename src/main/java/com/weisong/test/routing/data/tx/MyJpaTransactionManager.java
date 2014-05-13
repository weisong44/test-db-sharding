package com.weisong.test.routing.data.tx;

import javax.persistence.EntityManagerFactory;

import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.TransactionDefinition;

import com.weisong.test.common.ContextHolder;

public class MyJpaTransactionManager extends JpaTransactionManager {

    private static final long serialVersionUID = 1L;

    public MyJpaTransactionManager(EntityManagerFactory emf) {
        super(emf);
    }

    @Override
    protected void doBegin(Object transaction, TransactionDefinition definition) {
        super.doBegin(transaction, definition);
        ContextHolder.get().beginTx();
    }
    
    @Override
    protected void doCleanupAfterCompletion(Object transaction) {
        ContextHolder.get().endTx();
        super.doCleanupAfterCompletion(transaction);
    }
}
