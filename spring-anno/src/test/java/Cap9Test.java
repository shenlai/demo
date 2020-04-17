
import com.sl.anno.cap8.Bird;
import com.sl.anno.cap8.Cap8MainConfig;
import com.sl.anno.cap9.config.Cap9MainConfig;
import com.sl.anno.cap9.dao.TestDao;
import com.sl.anno.cap9.service.TestService;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

/**
 * @Value 注解工作原理：https://blog.csdn.net/wyzdwb/article/details/87458325
 * AutowiredAnnotationBeanPostProcessor: 装配bean中使用注解标注的成员变量，setter方法, 任意的配置方法。比较典型的是@Autowired注解和@Value注解。
 */
public class Cap9Test {


    @Test
    public void test01() {
        // 容器启动加载配置类--> refresh() --> finishBeanFactoryInitialization()--> getBean() -->doCreateBean()
        // -->beanWrapper(对象创建) -->populated(属性赋值，遍历所有后置处理器) -->inializate()
        AnnotationConfigApplicationContext app = new AnnotationConfigApplicationContext(Cap9MainConfig.class);

        System.out.println("IOC容器创建完成........");

        //测试1：比较TestService拿到testDao与直接从容器中拿到的testDao是否为同一个?
        //结果：同一个  Autowried默认按类型去容器中找对应的组件，相当于通过app.getBean(xxx.class)去获取bean
//        TestService testService = app.getBean(TestService.class);
//        testService.println();
//        TestDao testDao = app.getBean(TestDao.class);
//        System.out.println(testDao);

//      测试2：如果容器中存在多个bean呢？ 会加载哪个bean ？
        //结果 //如果使用Autowired, testDao2,优先按beanID找，找不到按类型找 找到TestDao类型的
        //    // 如果存在 @Primary 则直接取@Primary修饰的bean
        //    //@Qualifier, @Promary 同时存在，优先@Qualifier按名称找，@Qualifier是根据bean id指定获取testDao, 不受@Primary影响.
        TestService testService = app.getBean(TestService.class);
        testService.println();

        app.close();


    }
}