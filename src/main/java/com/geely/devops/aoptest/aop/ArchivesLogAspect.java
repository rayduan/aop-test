package com.geely.devops.aoptest.aop;

import com.geely.devops.aoptest.Annotation.SysLog;
import javassist.*;
import javassist.bytecode.CodeAttribute;
import javassist.bytecode.LocalVariableAttribute;
import javassist.bytecode.MethodInfo;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @ProjectName: aop-test
 * @Package: com.geely.devops.aoptest.aop
 * @Description: 日志切面类
 * @Author: duanrui
 * @CreateDate: 2018-08-30 15:52
 * @Version: 1.0
 * <p>
 * Copyright: Copyright (c) 2018
 */
@Aspect
@Component
public class ArchivesLogAspect {

//    public static final Log logger = LogFactory.getLog(ArchivesLogAspect.class);
    private  static final Logger logger = LoggerFactory.getLogger(ArchivesLogAspect.class);
    @Pointcut("execution(public * com.geely.devops.aoptest.controller.UserController.*(..))")
    public void webRequestLog() {}




    /**
     * 前置通知 用于拦截Controller层记录用户的操作
     *
     * @param joinPoint 切点
     */
    @Before("webRequestLog()")
    public void doBefore(JoinPoint joinPoint) {
        System.out.println("==========执行controller前置通知===============");
        String targetName = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        Object[] arguments = joinPoint.getArgs();
        Class targetClass = null;

        try {
            targetClass = Class.forName(targetName);
            Method[] methods = targetClass.getMethods();
            String operationType = "";
            String operationName = "";
            for (Method method : methods) {
                if (method.getName().equals(methodName)) {
                    Class[] clazzs = method.getParameterTypes();
                    Map<String, Object> nameAndArgs = getFieldsName(this.getClass(), targetClass.getName(), methodName, arguments);
                    if (clazzs.length == arguments.length) {
                        SysLog annotation = method.getAnnotation(SysLog.class);
                        if(null != annotation){
                            operationType = annotation.operationType();
                            operationName = annotation.operationName();
                            break;
                        }

                    }
                }
            }
            if(logger.isInfoEnabled()){
                logger.info("before " + joinPoint);
            }
        } catch (ClassNotFoundException | NotFoundException e) {
            e.printStackTrace();
        }

    }

    //配置controller环绕通知,使用在方法aspect()上注册的切入点
    @Around("webRequestLog()")
    public Object around(JoinPoint joinPoint){
        Object rvt = null;
        System.out.println("==========开始执行controller环绕通知===============");
        long start = System.currentTimeMillis();
        try {
              rvt = ((ProceedingJoinPoint) joinPoint).proceed();
            long end = System.currentTimeMillis();
            if(logger.isInfoEnabled()){
                logger.info("around " + joinPoint + "\tUse time : " + (end - start) + " ms!");
                System.out.println("返回值"+rvt);
            }
            System.out.println("==========结束执行controller环绕通知===============");

        } catch (Throwable e) {
            long end = System.currentTimeMillis();
            if(logger.isInfoEnabled()){
                logger.info("around " + joinPoint + "\tUse time : " + (end - start) + " ms with exception : " + e.getMessage());
            }
        }
        return rvt;
    }

    /**
     * 后置通知 用于拦截Controller层记录用户的操作
     *
     * @param joinPoint 切点
     */
    @AfterReturning("webRequestLog()")
    public  void afterReturning(JoinPoint joinPoint) {

       /* HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpSession session = request.getSession();  */
        //读取session中的用户
        // User user = (User) session.getAttribute("user");
        //请求的IP
        //String ip = request.getRemoteAddr();
//        User user = new User();
//        user.setId(1);
//        user.setName("张三");
//        String ip = "127.0.0.1";
        try {
            String targetName = joinPoint.getTarget().getClass().getName();
            String methodName = joinPoint.getSignature().getName();
            Object[] arguments = joinPoint.getArgs();
            Class targetClass = Class.forName(targetName);
            Method[] methods = targetClass.getMethods();
            String operationType = "";
            String operationName = "";
            Map<String, Object> nameAndArgs = getFieldsName(this.getClass(), targetClass.getName(), methodName, arguments);
            for (Method method : methods) {
                if (method.getName().equals(methodName)) {
                    Class[] clazzs = method.getParameterTypes();
                    if (clazzs.length == arguments.length) {
                        operationType = method.getAnnotation(SysLog.class).operationType();
                        operationName = method.getAnnotation(SysLog.class).operationName();
                        break;
                    }
                }
            }
            //*========控制台输出=========*//
            System.out.println("=====controller后置通知开始=====");
            System.out.println("请求方法:" + (joinPoint.getTarget().getClass().getName() + "." + joinPoint.getSignature().getName() + "()")+"."+operationType);
            System.out.println("方法描述:" + operationName);
//            System.out.println("请求人:" + user.getName());
//            System.out.println("请求IP:" + ip);
            //*========数据库日志=========*//
//            SystemLog log = new SystemLog();
//            log.setId(UUID.randomUUID().toString());
//            log.setDescription(operationName);
//            log.setMethod((joinPoint.getTarget().getClass().getName() + "." + joinPoint.getSignature().getName() + "()")+"."+operationType);
//            log.setLogType((long)0);
//            log.setRequestIp(ip);
//            log.setExceptioncode( null);
//            log.setExceptionDetail( null);
//            log.setParams( null);
//            log.setCreateBy(user.getName());
//            log.setCreateDate(new Date());
            //保存数据库
//            systemLogService.insert(log);
            System.out.println("=====controller后置通知结束=====");
        }  catch (Exception e) {
            //记录本地异常日志
            logger.error("==后置通知异常==");
            logger.error("异常信息:{}", e.getMessage());
        }
    }

    //配置后置返回通知,使用在方法aspect()上注册的切入点
//    @AfterReturning("webRequestLog()")
//    public void afterReturn(JoinPoint joinPoint){
//        System.out.println("=====执行controller后置返回通知=====");
//        if(logger.isInfoEnabled()){
//            logger.info("afterReturn " + joinPoint);
//        }
//    }


    /**
     * 通过反射机制 获取被切参数名以及参数值
     *
     * @param cls
     * @param clazzName
     * @param methodName
     * @param args
     * @return
     * @throws NotFoundException
     */
    private Map<String, Object> getFieldsName(Class cls, String clazzName, String methodName, Object[] args) throws NotFoundException {
        Map<String, Object> map = new HashMap<String, Object>();

        ClassPool pool = ClassPool.getDefault();
        //ClassClassPath classPath = new ClassClassPath(this.getClass());
        ClassClassPath classPath = new ClassClassPath(cls);
        pool.insertClassPath(classPath);

        CtClass cc = pool.get(clazzName);
        CtMethod cm = cc.getDeclaredMethod(methodName);
        MethodInfo methodInfo = cm.getMethodInfo();
        CodeAttribute codeAttribute = methodInfo.getCodeAttribute();
        LocalVariableAttribute attr = (LocalVariableAttribute) codeAttribute.getAttribute(LocalVariableAttribute.tag);
        if (attr == null) {
            // exception
        }
        // String[] paramNames = new String[cm.getParameterTypes().length];
        int pos = Modifier.isStatic(cm.getModifiers()) ? 0 : 1;
        for (int i = 0; i < cm.getParameterTypes().length; i++) {
            map.put(attr.variableName(i + pos), args[i]);//paramNames即参数名
        }
        return map;
    }
    /**
     * 异常通知 用于拦截记录异常日志
     *
     * @param joinPoint
     * @param e
     */
    @AfterThrowing(pointcut = "webRequestLog()", throwing="e")
    public  void doAfterThrowing(JoinPoint joinPoint, Throwable e) {
        /*HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        //读取session中的用户
        User user = (User) session.getAttribute(WebConstants.CURRENT_USER);
        //获取请求ip
        String ip = request.getRemoteAddr(); */
        //获取用户请求方法的参数并序列化为JSON格式字符串

//        User user = new User();
//        user.setId(1);
//        user.setName("张三");
//        String ip = "127.0.0.1";
//
//        String params = "";
//        if (joinPoint.getArgs() !=  null && joinPoint.getArgs().length > 0) {
//            for ( int i = 0; i < joinPoint.getArgs().length; i++) {
//                params += JsonUtil.getJsonStr(joinPoint.getArgs()[i]) + ";";
//            }
//        }
//        try {
//
//            String targetName = joinPoint.getTarget().getClass().getName();
//            String methodName = joinPoint.getSignature().getName();
//            Object[] arguments = joinPoint.getArgs();
//            Class targetClass = Class.forName(targetName);
//            Method[] methods = targetClass.getMethods();
//            String operationType = "";
//            String operationName = "";
//            for (Method method : methods) {
//                if (method.getName().equals(methodName)) {
//                    Class[] clazzs = method.getParameterTypes();
//                    if (clazzs.length == arguments.length) {
//                        operationType = method.getAnnotation(Log.class).operationType();
//                        operationName = method.getAnnotation(Log.class).operationName();
//                        break;
//                    }
//                }
//            }
//            /*========控制台输出=========*/
//            System.out.println("=====异常通知开始=====");
//            System.out.println("异常代码:" + e.getClass().getName());
//            System.out.println("异常信息:" + e.getMessage());
//            System.out.println("异常方法:" + (joinPoint.getTarget().getClass().getName() + "." + joinPoint.getSignature().getName() + "()")+"."+operationType);
//            System.out.println("方法描述:" + operationName);
//            System.out.println("请求人:" + user.getName());
//            System.out.println("请求IP:" + ip);
//            System.out.println("请求参数:" + params);
//            /*==========数据库日志=========*/
//            SystemLog log = new SystemLog();
//            log.setId(UUID.randomUUID().toString());
//            log.setDescription(operationName);
//            log.setExceptioncode(e.getClass().getName());
//            log.setLogType((long)1);
//            log.setExceptionDetail(e.getMessage());
//            log.setMethod((joinPoint.getTarget().getClass().getName() + "." + joinPoint.getSignature().getName() + "()"));
//            log.setParams(params);
//            log.setCreateBy(user.getName());
//            log.setCreateDate(new Date());
//            log.setRequestIp(ip);
//            //保存数据库
//            systemLogService.insert(log);
//            System.out.println("=====异常通知结束=====");
//        }  catch (Exception ex) {
//            //记录本地异常日志
//            logger.error("==异常通知异常==");
//            logger.error("异常信息:{}", ex.getMessage());
//        }
//        /*==========记录本地异常日志==========*/
//        logger.error("异常方法:{}异常代码:{}异常信息:{}参数:{}", joinPoint.getTarget().getClass().getName() + joinPoint.getSignature().getName(), e.getClass().getName(), e.getMessage(), params);
//
//    }
//    out.println(retVal);
    }


}
