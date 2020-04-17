
import com.sl.anno.cap7.config.Cap7MainConfigOfLifeCycle;
import com.sl.anno.cap8.Bird;
import com.sl.anno.cap8.Cap8MainConfig;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

/**
 * @Value 注解工作原理：https://blog.csdn.net/wyzdwb/article/details/87458325
 * AutowiredAnnotationBeanPostProcessor: 装配bean中使用注解标注的成员变量，setter方法, 任意的配置方法。比较典型的是@Autowired注解和@Value注解。
 */
public class Cap8Test {


    @Test
    public void test01() {
            // 容器启动加载配置类--> refresh() --> finishBeanFactoryInitialization()--> getBean() -->doCreateBean()
            // -->beanWrapper(对象创建) -->populated(属性赋值，遍历所有后置处理器) -->inializate()
        AnnotationConfigApplicationContext app = new AnnotationConfigApplicationContext(Cap8MainConfig.class);

        System.out.println("IOC容器创建完成........");

        String[] names = app.getBeanDefinitionNames();

        for (String name : names) {
            System.out.println(name); //打印出所有的bean的名字
        }

        Bird bird = (Bird) app.getBean("bird");
        System.out.println(bird);

        //确认properties会加载到环境变量中，那么可以直接从环境变量里获取
        ConfigurableEnvironment environment = app.getEnvironment();
        System.out.println(environment.getProperty("bird.color"));

        app.close();


    }
}
