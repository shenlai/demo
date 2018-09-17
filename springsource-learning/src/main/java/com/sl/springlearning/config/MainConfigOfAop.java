package com.sl.springlearning.config;
import com.sl.springlearning.aop.LogAspects;
import com.sl.springlearning.aop.MathCaculator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;


/**
 * AOP：【动态代理】
 * 		指在程序运行期间动态的将某段代码切入到指定方法指定位置进行运行的编程方式；
 *
 * 1、导入aop模块；Spring AOP：(spring-aspects)
 * 2、定义一个业务逻辑类（MathCalculator）；在业务逻辑运行的时候将日志进行打印（方法之前、方法运行结束、方法出现异常，xxx）
 * 3、定义一个日志切面类（LogAspects）：切面类里面的方法需要动态感知MathCalculator.div运行到哪里然后执行；
 * 		通知方法：
 *          前置通知@Before：在目标方法运行之前之前
 *          后置通知@After：目标方法之后执行
 *          返回通知@AfterReturning：目标方法正常返回后执行
 *          异常通知@AfterReturning：目标方法抛出异常时执行
 *          环绕通知@Around：动态代理，手动推进目标方法运行（joinPoint.procced()）
 *
 * 4、给切面类的目标方法标注何时何地运行（通知注解）；
 * 5、将切面类和业务逻辑类（目标方法所在类）都加入到容器中;
 * 6、必须告诉Spring哪个类是切面类(给切面类上加一个注解：@Aspect)
 * 7、给配置类中加 @EnableAspectJAutoProxy 【开启基于注解的aop模式】
 *
 * AOP原理：【看给容器中注册了什么组件，这个组件什么时候工作，这个组件的功能是什么？】
 *@EnableAspectJAutoProxy；
 *  1、@EnableAspectJAutoProxy是什么？
 *   		@Import(AspectJAutoProxyRegistrar.class)：给容器中导入AspectJAutoProxyRegistrar
 *   			利用AspectJAutoProxyRegistrar自定义给容器中注册bean；BeanDefinetion
 *   			internalAutoProxyCreator=AnnotationAwareAspectJAutoProxyCreator
 *
 *   		给容器中注册一个AnnotationAwareAspectJAutoProxyCreator；
 *  2、 AnnotationAwareAspectJAutoProxyCreator：
 *
 *
 */


@EnableAspectJAutoProxy  //<aop:aspectj-autoproxy></aop:aspectj-autoproxy>
@Configuration
@ComponentScan("com.sl.springlearning.aop")
public class MainConfigOfAop {

    //业务逻辑类加入容器中
    @Bean
    public MathCaculator calculator(){
        return new MathCaculator();
    }

    //切面类加入到容器中
    @Bean
    public LogAspects logAspects(){
        return new LogAspects();
    }

}
