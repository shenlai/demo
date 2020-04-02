import com.sl.anno.cap6.bean.Monkey;
import com.sl.anno.cap6.config.Cap6MainConfig;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Cap6Test {
    @Test
    public void test01() {

        AnnotationConfigApplicationContext app = new AnnotationConfigApplicationContext(Cap6MainConfig.class);

        System.out.println("IOC容器创建完成........");


        //当声明Object bean1 = anno.getBean("&jamesFactoryBean");, 获取到的bean为jamesFactoryBean对象
        Object bean1 = app.getBean("&jamesFactoryBean");
        Object bean2 = app.getBean("jamesFactoryBean");//取Money
        Monkey bean = app.getBean(Monkey.class);
        System.out.println("bean的类型=" + bean1.getClass());
        System.out.println(bean1 == bean2);  //false

        System.out.println(bean2 == bean2);  //true


        String[] beanDefinitionNames = app.getBeanDefinitionNames();
        for (String name : beanDefinitionNames) {
            System.out.println(name);
        }

    }
}
