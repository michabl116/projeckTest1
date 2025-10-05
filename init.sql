-- Reset and initialize StudyPlanner (main database)
DROP DATABASE IF EXISTS StudyPlanner;
CREATE DATABASE StudyPlanner;
USE StudyPlanner;

-- Users table
CREATE TABLE Users (
                       id INT PRIMARY KEY AUTO_INCREMENT,
                       username VARCHAR(100) NOT NULL,
                       password VARCHAR(100) NOT NULL,
                       confirmPassword VARCHAR(100) NOT NULL
);

-- Sign-up records
CREATE TABLE SignUp (
                        id INT PRIMARY KEY AUTO_INCREMENT,
                        user_id INT NOT NULL,
                        sign_up_date DATE NOT NULL,
                        status VARCHAR(20) DEFAULT 'active',
                        FOREIGN KEY (user_id) REFERENCES Users(id)
);

-- Tasks assigned to users
CREATE TABLE Task (
                      id INT PRIMARY KEY AUTO_INCREMENT,
                      user_id INT NOT NULL,
                      title VARCHAR(100) NOT NULL,
                      description TEXT,
                      status VARCHAR(20),
                      dueDate DATETIME,
                      FOREIGN KEY (user_id) REFERENCES Users(id)
);

-- Task statistics per user
CREATE TABLE TaskStats (
                           id INT PRIMARY KEY AUTO_INCREMENT,
                           user_id INT NOT NULL,
                           total_tasks INT DEFAULT 0,
                           completed_tasks INT DEFAULT 0,
                           pending_tasks INT DEFAULT 0,
                           last_updated DATE,
                           FOREIGN KEY (user_id) REFERENCES Users(id)
);

-- Calendar events
CREATE TABLE CalendarEvent (
                               id INT PRIMARY KEY AUTO_INCREMENT,
                               user_id INT NOT NULL,
                               title VARCHAR(100),
                               description TEXT,
                               event_date DATE,
                               event_time TIME,
                               FOREIGN KEY (user_id) REFERENCES Users(id)
);

-- Task change history
CREATE TABLE TaskHistory (
                             id INT PRIMARY KEY AUTO_INCREMENT,
                             task_id INT NOT NULL,
                             changed_by INT NOT NULL,
                             change_type VARCHAR(50),
                             change_date DATETIME,
                             old_status VARCHAR(20),
                             new_status VARCHAR(20),
                             FOREIGN KEY (task_id) REFERENCES Task(id),
                             FOREIGN KEY (changed_by) REFERENCES Users(id)
);

-- Sample data for StudyPlanner
INSERT INTO Users (username, password, confirmPassword) VALUES
                                                            ('Alice', 'pass123', 'pass123'),
                                                            ('Bob', 'secure456', 'secure456');

INSERT INTO SignUp (user_id, sign_up_date) VALUES
                                               (1, '2025-09-01'),
                                               (2, '2025-09-02');

INSERT INTO Task (user_id, title, description, status, dueDate) VALUES
                                                                    (1, 'Math Homework', 'Complete exercises 1–10', 'pending', '2025-09-30 00:00:00'),
                                                                    (2, 'Science Project', 'Build a volcano model', 'in progress', '2025-10-05 00:00:00');

INSERT INTO TaskStats (user_id, total_tasks, completed_tasks, pending_tasks, last_updated) VALUES
                                                                                               (1, 5, 3, 2, '2025-09-26'),
                                                                                               (2, 4, 1, 3, '2025-09-26');

INSERT INTO CalendarEvent (user_id, title, description, event_date, event_time) VALUES
                                                                                    (1, 'Group Study', 'Meet at library', '2025-09-28', '15:00:00'),
                                                                                    (2, 'Presentation Day', 'Science project presentation', '2025-10-06', '10:00:00');

INSERT INTO TaskHistory (task_id, changed_by, change_type, change_date, old_status, new_status) VALUES
                                                                                                    (1, 1, 'update', '2025-09-25 14:00:00', 'pending', 'in progress'),
                                                                                                    (2, 2, 'update', '2025-09-26 09:30:00', 'in progress', 'completed');

-- Reset and initialize StudyPlannerTest (test database)
DROP DATABASE IF EXISTS StudyPlannerTest;
CREATE DATABASE StudyPlannerTest;
USE StudyPlannerTest;

-- Same structure as StudyPlanner, but without sample data
CREATE TABLE Users (
                       id INT PRIMARY KEY AUTO_INCREMENT,
                       username VARCHAR(100) NOT NULL,
                       password VARCHAR(100) NOT NULL,
                       confirmPassword VARCHAR(100) NOT NULL
);

CREATE TABLE SignUp (
                        id INT PRIMARY KEY AUTO_INCREMENT,
                        user_id INT NOT NULL,
                        sign_up_date DATE NOT NULL,
                        status VARCHAR(20) DEFAULT 'active',
                        FOREIGN KEY (user_id) REFERENCES Users(id)
);

CREATE TABLE Task (
                      id INT PRIMARY KEY AUTO_INCREMENT,
                      user_id INT NOT NULL,
                      title VARCHAR(100) NOT NULL,
                      description TEXT,
                      status VARCHAR(20),
                      dueDate DATETIME,
                      FOREIGN KEY (user_id) REFERENCES Users(id)
);

CREATE TABLE TaskStats (
                           id INT PRIMARY KEY AUTO_INCREMENT,
                           user_id INT NOT NULL,
                           total_tasks INT DEFAULT 0,
                           completed_tasks INT DEFAULT 0,
                           pending_tasks INT DEFAULT 0,
                           last_updated DATE,
                           FOREIGN KEY (user_id) REFERENCES Users(id)
);

CREATE TABLE CalendarEvent (
                               id INT PRIMARY KEY AUTO_INCREMENT,
                               user_id INT NOT NULL,
                               title VARCHAR(100),
                               description TEXT,
                               event_date DATE,
                               event_time TIME,
                               FOREIGN KEY (user_id) REFERENCES Users(id)
);

CREATE TABLE TaskHistory (
                             id INT PRIMARY KEY AUTO_INCREMENT,
                             task_id INT NOT NULL,
                             changed_by INT NOT NULL,
                             change_type VARCHAR(50),
                             change_date DATETIME,
                             old_status VARCHAR(20),
                             new_status VARCHAR(20),
                             FOREIGN KEY (task_id) REFERENCES Task(id),
                             FOREIGN KEY (changed_by) REFERENCES Users(id)
);


CREATE USER IF NOT EXISTS 'demo_user'@'%' IDENTIFIED BY 'demo_pass';
GRANT ALL PRIVILEGES ON *.* TO 'demo_user'@'%';
FLUSH PRIVILEGES;