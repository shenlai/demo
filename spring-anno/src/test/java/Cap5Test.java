import com.sl.anno.cap1.model.Person;
import com.sl.anno.cap5.Cap5MainConfig;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Map;

public class Cap5Test {
    @Test
    public void test01() {
        AnnotationConfigApplicationContext app = new AnnotationConfigApplicationContext(Cap5MainConfig.class);

        System.out.println("IOC容器创建完成........");

        String[] beanNamesForType = app.getBeanNamesForType(Person.class);

        for (String name : beanNamesForType) {
            System.out.println(name);
        }

        Map<String, Person> personMap = app.getBeansOfType(Person.class);
        System.out.println(personMap);


    }
}
