package dao;

import model.Task;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TaskDaoTest {
    private static Connection conn;
    private TaskDao taskDao;
    private Task task;

    @BeforeAll
    static void setupDB() throws Exception {
//        taskDao = new TaskDao();
        conn = DriverManager.getConnection("jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1");
        Statement stmt = conn.createStatement();
        stmt.execute("CREATE TABLE task (task_id INT AUTO_INCREMENT PRIMARY KEY, user_id INT, title VARCHAR(255), description VARCHAR(255), status VARCHAR(50), dueDate TIMESTAMP)");
    }

    @AfterAll
    static void closeDatabase() throws Exception {
        conn.close();
    }

    @BeforeEach
    void setUp() {
        taskDao = new TaskDao(conn);
    }

    @AfterEach
    void cleanUp() throws Exception {
        Statement stmt = conn.createStatement();
        stmt.execute("DELETE FROM task");
    }

    @Test
    void testPersistAndFind() {
        Task task = new Task();
        task.setUserId(1);
        task.setTitle("Test Task");
        task.setDescription("Test Desc");
        task.setStatus("TODO");
        task.setDueDate(LocalDateTime.now());

        taskDao.persist(task);
        assertNotNull(task.getId());

        Task found = taskDao.find(task.getId());
        assertNotNull(found);
        assertEquals("Test Task", found.getTitle());
        assertEquals("Test Desc", found.getDescription());
        assertEquals("TODO", found.getStatus());
    }

    @Test
    void testUpdate() {
        Task task = new Task();
        task.setUserId(1);
        task.setTitle("Test Title");
        task.setDescription("Test Desc");
        task.setStatus("IN_PROGRESS");
        task.setDueDate(LocalDateTime.now());

        taskDao.persist(task);
        assertNotNull(task.getId());

        task.setTitle("Updated Title");
        taskDao.update(task);

        Task updated = taskDao.find(task.getId());
        assertEquals("Updated Title", updated.getTitle());
    }

    @Test
//    @DisplayName("Test to findAll user tasks")
    void testFindAll() {
        Task task1 = new Task();
        task1.setUserId(3);
        task1.setTitle("Task 1");
        task1.setDescription("Desc 1");
        task1.setStatus("TODO");
        task1.setDueDate(LocalDateTime.now());
        taskDao.persist(task1);

        Task task2 = new Task();
        task2.setUserId(2);
        task2.setTitle("Task 2");
        task2.setDescription("Desc 2");
        task2.setStatus("DONE");
        task2.setDueDate(LocalDateTime.now());
        taskDao.persist(task2);

        List<Task> allTasks = taskDao.findAll();
        assertNotNull(allTasks);
        assertEquals(2, allTasks.size());
    }

    @Test
    void testDelete() {
        Task task = new Task();
        task.setUserId(1);
        task.setTitle("Test for Delete");
        task.setDescription("Test Desc");
        task.setStatus("DONE");
        task.setDueDate(LocalDateTime.now());
        taskDao.persist(task);

        assertNotNull(task.getId());
        int taskId = task.getId();
        taskDao.delete(task);
        Task deleted = taskDao.find(taskId);
        assertNull(deleted);
    }

    @Test
    void testGetTasksByUserId() {
        Task task1 = new Task();
        task1.setUserId(1);
        task1.setTitle("Task 1");
        task1.setDescription("Desc 1");
        task1.setStatus("TODO");
        task1.setDueDate(LocalDateTime.now());
        taskDao.persist(task1);

        Task task2 = new Task();
        task2.setUserId(2);
        task2.setTitle("Task 2");
        task2.setDescription("Desc 2");
        task2.setStatus("DONE");
        task2.setDueDate(LocalDateTime.now());
        taskDao.persist(task2);

        List<Task> user1Tasks = taskDao.getTasksByUserId(1);
        assertNotNull(user1Tasks);
        assertEquals(1, user1Tasks.size());
        assertEquals(1, user1Tasks.get(0).getUserId());
        assertEquals("Task 1", user1Tasks.get(0).getTitle());

        List<Task> user2Tasks = taskDao.getTasksByUserId(2);
        assertNotNull(user2Tasks);
        assertEquals(1, user2Tasks.size());
        assertEquals(2, user2Tasks.get(0).getUserId());
        assertEquals("Task 2", user2Tasks.get(0).getTitle());

    }

}