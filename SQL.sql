-- tao database
CREATE DATABASE IF NOT EXISTS taskmanager CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE taskmanager;

-- bang project (danh muc)
CREATE TABLE IF NOT EXISTS project (
  id INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(150) NOT NULL
);

-- bang task
CREATE TABLE IF NOT EXISTS task (
  id INT AUTO_INCREMENT PRIMARY KEY,
  title VARCHAR(255) NOT NULL,
  description TEXT,
  priority ENUM('CAO','TRUNG_BINH','THAP') DEFAULT 'TRUNG_BINH',
  deadline DATETIME,
  completed TINYINT(1) DEFAULT 0,
  project_id INT,
  created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (project_id) REFERENCES project(id) ON DELETE SET NULL
);
