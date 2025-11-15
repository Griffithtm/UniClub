-- =============================
-- 1. TẠO BẢNG
-- =============================

CREATE TABLE Roles (
    RoleID INT PRIMARY KEY IDENTITY(1,1),
    RoleName NVARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE Clubs (
    ClubID INT PRIMARY KEY IDENTITY(1,1),
    ClubName NVARCHAR(100) NOT NULL,
    Description NVARCHAR(MAX) NULL,
    EstablishedDate DATE NULL
);

CREATE TABLE Users (
    UserID INT PRIMARY KEY IDENTITY(1,1),
    FullName NVARCHAR(100) NOT NULL,
    Email NVARCHAR(100) NOT NULL UNIQUE,
    PasswordHash NVARCHAR(255) NOT NULL,
    RoleID INT NOT NULL,
    ClubID INT NULL,
    FOREIGN KEY (RoleID) REFERENCES Roles(RoleID),
    FOREIGN KEY (ClubID) REFERENCES Clubs(ClubID)
);

CREATE TABLE Events (
    EventID INT PRIMARY KEY IDENTITY(1,1),
    EventName NVARCHAR(100) NOT NULL,
    Description NVARCHAR(MAX) NULL,
    EventDate DATETIME NOT NULL,
    Location NVARCHAR(200) NOT NULL,
    Status NVARCHAR(50) NOT NULL DEFAULT 'Scheduled',
    ClubID INT NOT NULL,
    CreatedBy INT NULL,
    FOREIGN KEY (ClubID) REFERENCES Clubs(ClubID),
    FOREIGN KEY (CreatedBy) REFERENCES Users(UserID)
);

CREATE TABLE EventParticipants (
    EventParticipantID INT PRIMARY KEY IDENTITY(1,1),
    EventID INT NOT NULL,
    UserID INT NOT NULL,
    Status NVARCHAR(20) NOT NULL CHECK (Status IN ('Registered','Attended','Absent')),
    RegisterDate DATETIME DEFAULT GETDATE(),
    FOREIGN KEY (EventID) REFERENCES Events(EventID),
    FOREIGN KEY (UserID) REFERENCES Users(UserID)
);

CREATE TABLE MemberParticipation (
    ParticipationID INT PRIMARY KEY IDENTITY(1,1),
    UserID INT NOT NULL,
    EventID INT NOT NULL,
    Semester NVARCHAR(20) NOT NULL,
    Status NVARCHAR(20) NOT NULL CHECK (Status IN ('Registered','Attended','Absent')),
    Score INT NULL,
    FOREIGN KEY (UserID) REFERENCES Users(UserID),
    FOREIGN KEY (EventID) REFERENCES Events(EventID)
);

CREATE TABLE Reports (
    ReportID INT PRIMARY KEY IDENTITY(1,1),
    ClubID INT NOT NULL,
    Semester NVARCHAR(20) NOT NULL,
    MemberChanges NVARCHAR(MAX) NULL,
    EventSummary NVARCHAR(MAX) NULL,
    ParticipationStats NVARCHAR(MAX) NULL,
    CreatedDate DATETIME DEFAULT GETDATE(),
    FOREIGN KEY (ClubID) REFERENCES Clubs(ClubID)
);

CREATE TABLE Notifications (
    NotificationID INT PRIMARY KEY IDENTITY(1,1),
    ClubID INT NOT NULL,
    SenderID INT NOT NULL,
    Title NVARCHAR(200) NOT NULL,
    Message NVARCHAR(MAX) NOT NULL,
    CreatedAt DATETIME DEFAULT GETDATE(),
    FOREIGN KEY (ClubID) REFERENCES Clubs(ClubID),
    FOREIGN KEY (SenderID) REFERENCES Users(UserID)
);

CREATE TABLE Assignments (
    AssignmentID INT PRIMARY KEY IDENTITY(1,1),
    EventID INT NOT NULL,
    UserID INT NOT NULL,
    TaskDescription NVARCHAR(MAX) NOT NULL,
    Status NVARCHAR(20) NOT NULL DEFAULT 'Pending',
    AssignedDate DATETIME DEFAULT GETDATE(),
    FOREIGN KEY (EventID) REFERENCES Events(EventID),
    FOREIGN KEY (UserID) REFERENCES Users(UserID)
);

-- =============================
-- 2. SEED DATA
-- =============================

-- Roles
INSERT INTO Roles (RoleName) VALUES
(N'Admin'), (N'Chairman'), (N'ViceChairman'), (N'TeamLeader'), (N'Member');

-- Clubs
INSERT INTO Clubs (ClubName, Description, EstablishedDate) VALUES
(N'Câu lạc bộ CNTT', N'CLB dành cho sinh viên yêu thích công nghệ thông tin', '2021-09-01'),
(N'Câu lạc bộ Văn nghệ', N'Nơi giao lưu, biểu diễn và học hỏi nghệ thuật', '2020-05-10');

-- Users
INSERT INTO Users (FullName, Email, PasswordHash, RoleID, ClubID) VALUES
(N'Nguyễn Văn A', 'a@univ.edu.vn', 'hash123456', 2, 1), -- Chairman CNTT
(N'Trần Thị B', 'b@univ.edu.vn', 'hash123456', 3, 1), -- ViceChairman CNTT
(N'Lê Văn C', 'c@univ.edu.vn', 'hash123456', 5, 1), -- Member CNTT
(N'Phạm Thị D', 'd@univ.edu.vn', 'hash123456', 5, 2); -- Member Văn nghệ

-- Events
INSERT INTO Events (EventName, Description, EventDate, Location, Status, ClubID, CreatedBy) VALUES
(N'Hội thảo AI', N'Hội thảo về trí tuệ nhân tạo', '2025-03-15 09:00:00', N'Phòng A1', 'Scheduled', 1, 1),
(N'Chương trình ca nhạc Xuân', N'Giao lưu văn nghệ chào xuân', '2025-02-05 18:30:00', N'Hội trường lớn', 'Scheduled', 2, 4);

-- EventParticipants
INSERT INTO EventParticipants (EventID, UserID, Status) VALUES
(1, 2, 'Registered'),
(1, 3, 'Attended'),
(2, 4, 'Registered');

-- MemberParticipation
INSERT INTO MemberParticipation (UserID, EventID, Semester, Status, Score) VALUES
(2, 1, '2025-HK1', 'Registered', NULL),
(3, 1, '2025-HK1', 'Attended', NULL),
(4, 2, '2025-HK1', 'Registered', NULL);

-- Reports
INSERT INTO Reports (ClubID, Semester, MemberChanges, EventSummary, ParticipationStats)
VALUES
(1, '2025-HK1', N'Tăng 5 thành viên mới', N'Tổ chức 2 sự kiện CNTT', N'80% thành viên tham gia'),
(2, '2025-HK1', N'Giảm 2 thành viên', N'Tổ chức 1 sự kiện văn nghệ', N'60% thành viên tham gia');

-- Notifications
INSERT INTO Notifications (ClubID, SenderID, Title, Message) VALUES
(1, 1, N'Thông báo họp CLB CNTT', N'Các bạn tham dự họp CLB vào 20h ngày 10/3.'),
(2, 4, N'Thông báo biểu diễn', N'CLB Văn nghệ diễn tập lúc 17h ngày 3/2.');

-- Assignments
INSERT INTO Assignments (EventID, UserID, TaskDescription, Status) VALUES
(1, 2, N'Chuẩn bị slide trình bày', 'Pending'),
(2, 4, N'Tập luyện tiết mục văn nghệ', 'InProgress');

-- =============================
-- 3. STORED PROCEDURE
-- =============================
GO
CREATE OR ALTER PROCEDURE sp_GetMemberParticipationStats
    @ClubID INT,
    @Semester NVARCHAR(20)
AS
BEGIN
    SET NOCOUNT ON;

    DECLARE @TotalEvents INT;
    SELECT @TotalEvents = COUNT(*)
    FROM Events e
    INNER JOIN MemberParticipation mp ON e.EventID = mp.EventID
    WHERE e.ClubID = @ClubID AND mp.Semester = @Semester;

    IF @TotalEvents = 0
    BEGIN
        PRINT N'Không có sự kiện nào trong học kỳ này.';
        RETURN;
    END

    SELECT 
        u.UserID,
        u.FullName,
        COUNT(CASE WHEN mp.Status = 'Attended' THEN 1 END) AS AttendedEvents,
        @TotalEvents AS TotalEvents,
        CAST(COUNT(CASE WHEN mp.Status = 'Attended' THEN 1 END) * 100.0 / @TotalEvents AS DECIMAL(5,2)) AS ParticipationRate,
        CASE 
            WHEN COUNT(CASE WHEN mp.Status = 'Attended' THEN 1 END) * 100.0 / @TotalEvents >= 80 THEN N'Tích cực'
            WHEN COUNT(CASE WHEN mp.Status = 'Attended' THEN 1 END) * 100.0 / @TotalEvents >= 50 THEN N'Bình thường'
            ELSE N'Không tích cực'
        END AS Classification
    FROM Users u
    LEFT JOIN MemberParticipation mp ON u.UserID = mp.UserID AND mp.Semester = @Semester
    WHERE u.ClubID = @ClubID
    GROUP BY u.UserID, u.FullName;
END;
