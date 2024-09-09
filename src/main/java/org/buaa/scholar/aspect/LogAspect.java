package org.buaa.scholar.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.buaa.scholar.annotation.Log;
import org.buaa.scholar.utils.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

/**
 * <p>日志切面类</p>
 * <p>AOP本质上是动态代理</p>
 * <p>join point: 程序执行过程中的某个特定过程，如：方法调用。可以对join point进行拦截</p>
 * <p>advice: 告诉我们在什么时刻执行代码，是我们动态代理的具体实现方式，如：方法执行前、方法执行后、方法抛出异常后</p>
 * <p>point cut: 匹配join point的断言。确定在哪些方法上执行advice</p>
 */
@Component
@Aspect
public class LogAspect {
    private static final Logger logger = LoggerFactory.getLogger(LogAspect.class);
    // 统计操作时间
    private static final ThreadLocal<Long> TIME_THREAD_LOCAL = new ThreadLocal<>();
    // 时间格式化
    private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * 匹配自定义注解@Log的方法
     */
    @Pointcut("@annotation(org.buaa.scholar.annotation.Log)")
    public void logPointCut() {
    }

    @Before("logPointCut()")
    public void beforeLog(JoinPoint joinPoint) {
        TIME_THREAD_LOCAL.set(System.currentTimeMillis());
        logger.info("开始执行方法: {}", joinPoint.getSignature().getName());
    }

    @AfterReturning(pointcut = "logPointCut()", returning = "response")
    public void afterRunning(JoinPoint joinPoint, Object response) {
        handleLog(joinPoint, null, response);
    }

    @AfterThrowing(pointcut = "logPointCut()", throwing = "exception")
    public void afterThrowing(JoinPoint joinPoint, Exception exception) {
        handleLog(joinPoint, exception, null);
    }

    private void handleLog(JoinPoint joinPoint, Exception exception, Object response) {
        Optional.ofNullable(exception).ifPresent(e -> logger.error("方法执行异常: {}", e.getMessage()));

        // 处理请求时间
        Long executeTime = System.currentTimeMillis() - TIME_THREAD_LOCAL.get();
        logger.info("方法执行时间: {}ms", executeTime);
        // 移除ThreadLocal中的数据
        TIME_THREAD_LOCAL.remove();

        // 处理方法执行完成时间
        logger.info("方法执行完成时间: {}", SIMPLE_DATE_FORMAT.format(new Date()));

        // 方法信息
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        logger.info("方法所在类: {}", className);
        logger.info("方法名称: {}", methodName);

        // 方法返回值
        Optional.ofNullable(response).ifPresent(r -> {
            Response res = (Response) ((ResponseEntity) r).getBody();
            logger.info("方法返回值: {}", res);
        });

        // 获取方法上的注解
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Log log = signature.getMethod().getAnnotation(Log.class);
        getControllerMethodDescription(joinPoint, log);
    }

    private void getControllerMethodDescription(JoinPoint joinPoint, Log log) {
        // Log title
        logger.info("操作功能: {}", log.title());
    }
}
