package sl.javalanguage;

public class ExceptionTest {

    public  static  void main(String[] args){
        try {
             //boolean result= testEx3();
             //System.out.println(result);
            testEx5();
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            //System.out.println("main, finally");
        }
    }

    /**
     * finally中使用 return 将覆盖方法的返回值
     * @return
     */
  static  boolean testEx1(){
        boolean ret = true;
        try {
            throw  new Exception();  //1
            //return true;           //2
        } catch (Exception e) {
            System.out.println("testEx1, catch exception");
            return true;
        } finally {
            System.out.println("testEx1, finally; return value=" + ret);
            return false;
        }

    }


    /**
     * finally中使用return 将抑制异常冒泡
     * @return
     * @throws Exception
     */
   static boolean testEx2() throws Exception {
        boolean ret = true;
        try {
            int result = 1/0;
            return true;
        } catch (Exception e) {
            System.out.println("testEx2, catch exception");
            ret = false;
            throw e;
        } finally {
            System.out.println("testEx2, finally; return value=" + ret);
            return ret;
        }

    }

    /**
     * finally中抛出异常，将覆盖try-catch中的异常
     * @return
     * @throws Exception
     */
    static boolean testEx3() throws Exception {
        boolean ret = true;
        try {
            int result = 1/0;
            return true;
        } catch (Exception e) {
            System.out.println("testEx2, catch exception");
            ret = false;
            throw new Exception("testEx2 catch: throw exception");
        } finally {
            System.out.println("testEx2, finally: return value=" + ret);
            throw new Exception("testEx2, finally: throw exception");
        }

    }


    static void testEx4() throws  Exception{

        try {
            int result = 1/0;
        } catch (Exception e) {
            throw new Exception("testEx4发生异常",e);
        }
    }

    static void testEx5() throws  Exception{

        try {
            testEx4();
        } catch (Exception e) {
            throw new Exception("testEx5发生异常",e);
        }
    }

}


