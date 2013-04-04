package testgame1javaapplication.taskqueue;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;


@RunWith(Suite.class)
@Suite.SuiteClasses({
    testgame1javaapplication.taskqueue.TestGetTaskWithEmptyTaskList.class,
         testgame1javaapplication.taskqueue.TestGetTaskWithOneTask.class,
         testgame1javaapplication.taskqueue.TestAddWithRunnableTask.class,
         testgame1javaapplication.taskqueue.TestAddWithNull.class
})
public class TaskQueueTestSuite {}