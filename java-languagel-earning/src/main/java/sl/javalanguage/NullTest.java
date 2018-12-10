package sl.javalanguage;

public class NullTest {

    private static  void test(){
        System.out.println("asd");
    }


    public  static  void main(String[] args){
        test();
        NullTest.test();

        /*java语言允许通过 类的实例调用静态方法*/
        NullTest test = null;
        test.test();



        new NullTest().test();
    }
}
