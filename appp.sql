-- Tạo database
CREATE DATABASE taskmanager CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- Dùng database này
USE taskmanager;

-- Bảng Project (nếu có chia theo dự án)
CREATE TABLE project (
    id INT AUTO_INCREMENT PRIMARY KEY,
    ten VARCHAR(100) NOT NULL
);

-- Bảng Task (nhiệm vụ)
CREATE TABLE task (
    id INT AUTO_INCREMENT PRIMARY KEY,
    tieude VARCHAR(255) NOT NULL,
    mota TEXT,
    priority ENUM('THAP', 'TRUNG_BINH', 'CAO') DEFAULT 'TRUNG_BINH',
    deadline DATETIME,
    project_id INT,
    hoanthanh BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (project_id) REFERENCES project(id) ON DELETE SET NULL
);
