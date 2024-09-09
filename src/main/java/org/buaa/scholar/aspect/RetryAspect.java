package org.buaa.scholar.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class RetryAspect {
    private static final Logger logger = LoggerFactory.getLogger(RetryAspect.class);
    private static final ThreadLocal<Integer> RETRY_THREAD_LOCAL = ThreadLocal.withInitial(() -> 0);

    @Pointcut("@annotation(org.buaa.scholar.annotation.Retryable)")
    public void retryPointCut() {

    }

    @Around("retryPointCut()")
    public void aroundAdvice(ProceedingJoinPoint joinPoint) {
        try {
            joinPoint.proceed();
        } catch (Throwable throwable) {
            int retry = RETRY_THREAD_LOCAL.get();
            if (retry < 3) {
                RETRY_THREAD_LOCAL.set(retry + 1);
                logger.info("方法执行失败, 正在进行第{}次重试", retry + 1);
                aroundAdvice(joinPoint);
            } else {
                logger.error("方法执行失败, 重试次数已达上限");
            }
        } finally {
            RETRY_THREAD_LOCAL.remove();
        }
    }
}
