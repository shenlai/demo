import com.sl.anno.cap4.config.Cap4MainConfig;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Cap4Test {
	@Test
	public void test01(){
		AnnotationConfigApplicationContext app = new AnnotationConfigApplicationContext(Cap4MainConfig.class);
		
		System.out.println("IOC创建完成........");
		//
		app.getBean("person");
		
	}
}
