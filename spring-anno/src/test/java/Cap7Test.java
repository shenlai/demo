
import com.sl.anno.cap7.config.Cap7MainConfigOfLifeCycle;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Cap7Test {
    @Test
    public void test01() {
        AnnotationConfigApplicationContext app = new AnnotationConfigApplicationContext(Cap7MainConfigOfLifeCycle.class);

        System.out.println("IOC容器创建完成........");

        app.close();


    }
}
