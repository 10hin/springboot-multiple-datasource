package com.example.multidatasource;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

@Component
public class TransactionalService {

    @Autowired
    @Qualifier("mySQLTransactionManager")
    private PlatformTransactionManager mySQLTransactionManager;

    @Autowired
    @Qualifier("postgreSQLTransactionManager")
    private PlatformTransactionManager postgreSQLTransactionManager;

    public Runnable withMySQLTransaction( //
         @NonNull final TransactionDefinition def, //
         @NonNull  final Runnable work, //
         @NonNull final Class<? extends Throwable>[] rollbackFor, //
         @NonNull final Class<? extends Throwable>[] noRollbackFor) {
        return () -> {
            final TransactionStatus status = this.mySQLTransactionManager.getTransaction(def);
            try {
                work.run();
                this.mySQLTransactionManager.commit(status);
            } catch (Error | RuntimeException t) {
                if (!status.isCompleted()) {
                    for (Class<? extends Throwable> exceptionForRollback : rollbackFor) {
                        if (exceptionForRollback.isInstance(t)) {
                            this.mySQLTransactionManager.rollback(status);
                            throw t;
                        }
                    }
                    for (Class<? extends Throwable> exceptionForNoRollbackFor : noRollbackFor) {
                        if (exceptionForNoRollbackFor.isInstance(t)) {
                            this.mySQLTransactionManager.commit(status);
                            throw t;
                        }
                    }
                    this.mySQLTransactionManager.rollback(status);
                    throw t;
                }
                // there are no action for transaction, because it's completed.
                throw t;
            } finally {
                if (!status.isCompleted()) {
                    this.mySQLTransactionManager.rollback(status);
                }
            }
        };
    }

    public Runnable withMySQLTransaction(
             @NonNull final Runnable work) {
        final TransactionDefinition def = new DefaultTransactionDefinition();
        final Class<? extends Throwable>[] emptyArray = new Class[0];
        return this.withMySQLTransaction(def, work, emptyArray, emptyArray);
    }

    public Runnable withPostgreSQLTransaction( //
             @NonNull final TransactionDefinition def, //
             @NonNull  final Runnable work, //
             @NonNull final Class<? extends Throwable>[] rollbackFor, //
             @NonNull final Class<? extends Throwable>[] noRollbackFor) {
        return () -> {
            final TransactionStatus status = this.postgreSQLTransactionManager.getTransaction(def);
            try {
                work.run();
                this.postgreSQLTransactionManager.commit(status);
            } catch (Error | RuntimeException t) {
                if (!status.isCompleted()) {
                    for (Class<? extends Throwable> exceptionForRollback : rollbackFor) {
                        if (exceptionForRollback.isInstance(t)) {
                            this.postgreSQLTransactionManager.rollback(status);
                            throw t;
                        }
                    }
                    for (Class<? extends Throwable> exceptionForNoRollbackFor : noRollbackFor) {
                        if (exceptionForNoRollbackFor.isInstance(t)) {
                            this.postgreSQLTransactionManager.commit(status);
                            throw t;
                        }
                    }
                    this.postgreSQLTransactionManager.rollback(status);
                    throw t;
                }
                // there are no action for transaction, because it's completed.
                throw t;
            } finally {
                if (!status.isCompleted()) {
                    this.postgreSQLTransactionManager.rollback(status);
                }
            }
        };
    }

    public Runnable withPostgreSQLTransaction(
             @NonNull final Runnable work) {
        final TransactionDefinition def = new DefaultTransactionDefinition();
        final Class<? extends Throwable>[] emptyArray = new Class[0];
        return this.withMySQLTransaction(def, work, emptyArray, emptyArray);
    }

}
