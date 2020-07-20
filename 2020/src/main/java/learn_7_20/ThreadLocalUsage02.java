package learn_7_20;

/**
 * @author liguo
 * @Description 每个线程内需要保存线程内的全局变量，线程在执行多个方法的时候，可以在多个方法中获取这个线程内的全局变量，避免了过度参数传递的问题
 * @date 2020/7/20/020 14:43
 */
public class ThreadLocalUsage02 {
    public static void main(String[] args) {
        /*1.未使用threadlocal Student student = init();
        new StuNumberService().getStuNumber(student);
        new NameService().getName(student);
        new SexService().getSex(student);*/
        //使用ThreadLocal后
        init();
        new StuNumberService().getStuNumber();
        new NameService().getName();
        new SexService().getSex();
    }

    /*private static Student init(){
        Student student = new Student();
        student.stuNumber="268";
        student.name = "ll";
        student.sex = "man";
        return  student;
    }*/

    /**
     * 使用ThreadLocal
     */
    private static void init(){
        Student student = new Student();
        student.stuNumber="268";
        student.name = "ll";
        student.sex = "man";
        ThreadLocalProcessor.studentThreadLocal.set(student);
    }
}

class ThreadLocalProcessor{
    public static ThreadLocal<Student> studentThreadLocal = new ThreadLocal<Student>();
}

class Student{
    String stuNumber;
    String name;
    String sex;
}

class StuNumberService{
    public void getStuNumber(Student student){
        System.out.println(student.stuNumber);
    }
    /**
     * 使用ThreadLocal
     */
    public void getStuNumber(){
        System.out.println(ThreadLocalProcessor.studentThreadLocal.get().stuNumber);
    }
}

class NameService{
    public void getName(Student student){
        System.out.println(student.name);
    }
    /**
     * 使用ThreadLocal
     */
    public void getName(){
        System.out.println(ThreadLocalProcessor.studentThreadLocal.get().name);
    }
}

class SexService{
    public void getSex(Student student){
        System.out.println(student.sex);
    }

    /**
     * 使用ThreadLocal
     */
    public void getSex(){
        System.out.println(ThreadLocalProcessor.studentThreadLocal.get().sex);
    }
}
