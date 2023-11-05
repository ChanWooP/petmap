package com.cwpark.petmap.petmap.config.redis;
import java.util.concurrent.TimeUnit;

import org.redisson.api.RLock;
import org.redisson.api.RTransaction;
import org.redisson.api.RedissonClient;
import org.redisson.api.TransactionOptions;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Component
@Getter
@RequiredArgsConstructor
public class MyRedis {

    private final RedissonClient redissonClient;
    private final PlatformTransactionManager transactionManager;

    public RLock lock(String lockKey) {
        RLock lock = redissonClient.getLock(lockKey);
        lock.lock();
        return lock;
    }
    
    public RLock lock(String lockKey, long leaseTime) {
        RLock lock = redissonClient.getLock(lockKey);
        lock.lock(leaseTime, TimeUnit.SECONDS);
        return lock;
    }

    public RLock lock(String lockKey, TimeUnit unit, long timeout) {
        RLock lock = redissonClient.getLock(lockKey);
        lock.lock(timeout, unit);
        return lock;
    }

    public boolean tryLock(String lockKey, TimeUnit unit, long waitTime, long leaseTime) {
        RLock lock = redissonClient.getLock(lockKey);
        try {
            return lock.tryLock(waitTime, leaseTime, unit);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public void unlock(String lockKey) {
        RLock lock = redissonClient.getLock(lockKey);
        lock.unlock();
    }
    
    public void unlock(RLock lock) {
        lock.unlock();
    }

    public TransactionStatus startDBTransacton() {
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        return transactionManager.getTransaction(def);
    }

    public void commitDB(TransactionStatus status) {
        transactionManager.commit(status);
    }

    public void rollbackDB(TransactionStatus status) {
        transactionManager.rollback(status);
    }

    public RTransaction startRedisTransacton() {
        return redissonClient.createTransaction(TransactionOptions.defaults());
    }
    
    public void commitRedis(RTransaction transaction) {
        transaction.commit();
    }

    public void rollbackRedis(RTransaction transaction) {
        transaction.rollback();
    }

    public Object getValue(String key)
    {
        return redissonClient.getBucket(key).get();
    }

    public void setValue(String key, Object value)
    {
        redissonClient.getBucket(key).set(value);
    }

    public boolean canUnlock(String lockKey) {
        RLock lock = redissonClient.getLock(lockKey);
        if(lock != null && lock.isLocked() && lock.isHeldByCurrentThread())
        {
            return true;
        }
        return false;
    }
}