package game1javaapplication;


public class Logger
{
    public static void write(String message)
    {
        System.out.println("Thread=" + Thread.currentThread().getName() + ": " + message);
    }
}
