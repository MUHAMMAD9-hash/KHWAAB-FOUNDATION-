package com.example.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [
        StudentProfileEntity::class,
        StudentSubjectMarkEntity::class,
        AttendanceLogEntity::class,
        SubjectEntity::class,
        AssessmentEntity::class,
        WorkshopEntity::class,
        AcademicGoalEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun studentDao(): StudentDao
    abstract fun studentMarkDao(): StudentMarkDao
    abstract fun attendanceDao(): AttendanceDao
    abstract fun subjectDao(): SubjectDao
    abstract fun assessmentDao(): AssessmentDao
    abstract fun workshopDao(): WorkshopDao
    abstract fun goalDao(): GoalDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "khwaab_surat_database"
                )
                    .fallbackToDestructiveMigration()
                    .addCallback(DatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                instance
            }
        }

        private class DatabaseCallback(
            private val scope: CoroutineScope
        ) : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                INSTANCE?.let { database ->
                    scope.launch(Dispatchers.IO) {
                        seedInitialData(database)
                    }
                }
            }
        }

        suspend fun seedInitialData(database: AppDatabase) {
            val studentDao = database.studentDao()
            val markDao = database.studentMarkDao()
            val attendanceDao = database.attendanceDao()
            val subjectDao = database.subjectDao()
            val assessmentDao = database.assessmentDao()
            val workshopDao = database.workshopDao()
            val goalDao = database.goalDao()

            // 1. Seed Student Profile
            val defaultStudent = StudentProfileEntity(
                id = 1,
                name = "Aarav Patel",
                rollNo = "KF-SRT-2026-042",
                grade = "Class 10 (Secondary)",
                phone = "+91 98251 44321",
                guardianPhone = "+91 94280 11987",
                address = "Sayedpura, Near Pumping Char Rasta, Surat",
                email = "aarav.student@khwaabsurat.org",
                totalClasses = 50,
                attendedClasses = 46,
                attendanceNotes = "Punctual student with 92% attendance at Khwaab Sayedpura Centre.",
                mentorRemarks = "Dedicated learner. Consistently excels in Hindi vyakaran and mathematical problem solving.",
                isPrimary = true
            )
            studentDao.insertStudent(defaultStudent)

            // 2. Student Marks
            val studentMarks = listOf(
                StudentSubjectMarkEntity(
                    studentId = 1,
                    subjectName = "Hindi Literature & Grammar",
                    marksObtained = 46.5f,
                    totalMarks = 50f,
                    term = "Mid-Term Exam",
                    examDate = "Sep 08, 2026",
                    remarks = "Top score in Hindi vyakaran and essay writing"
                ),
                StudentSubjectMarkEntity(
                    studentId = 1,
                    subjectName = "Core Mathematics",
                    marksObtained = 82f,
                    totalMarks = 100f,
                    term = "Mid-Term Exam",
                    examDate = "Sep 05, 2026",
                    remarks = "Strong geometry and algebraic formula applications"
                ),
                StudentSubjectMarkEntity(
                    studentId = 1,
                    subjectName = "General Science",
                    marksObtained = 42f,
                    totalMarks = 50f,
                    term = "Mid-Term Exam",
                    examDate = "Aug 28, 2026",
                    remarks = "Solid performance in Physics & Biology experiments"
                ),
                StudentSubjectMarkEntity(
                    studentId = 1,
                    subjectName = "English Communication",
                    marksObtained = 39f,
                    totalMarks = 50f,
                    term = "Unit Evaluation",
                    examDate = "Aug 20, 2026",
                    remarks = "Good reading comprehension, improve essay cohesion"
                ),
                StudentSubjectMarkEntity(
                    studentId = 1,
                    subjectName = "Social Science & Ethics",
                    marksObtained = 44f,
                    totalMarks = 50f,
                    term = "Unit Evaluation",
                    examDate = "Aug 12, 2026",
                    remarks = "Well-researched civics and historical context"
                )
            )
            markDao.insertAll(studentMarks)

            // 3. Attendance Logs
            val attendanceLogs = listOf(
                AttendanceLogEntity(
                    studentId = 1,
                    dateString = "Sep 11, 2026",
                    status = "Present",
                    subjectOrSession = "Hindi Literature & Grammar Lecture",
                    educatorName = "Khwaab Hindi Faculty (Surat)"
                ),
                AttendanceLogEntity(
                    studentId = 1,
                    dateString = "Sep 10, 2026",
                    status = "Present",
                    subjectOrSession = "Core Mathematics Problem Lab",
                    educatorName = "Sayedpura Academic Mentor"
                ),
                AttendanceLogEntity(
                    studentId = 1,
                    dateString = "Sep 09, 2026",
                    status = "Present",
                    subjectOrSession = "Science Practical Experiments",
                    educatorName = "Khwaab Science Faculty"
                ),
                AttendanceLogEntity(
                    studentId = 1,
                    dateString = "Sep 08, 2026",
                    status = "Present",
                    subjectOrSession = "Hindi Mid-Term Evaluation",
                    educatorName = "Khwaab Hindi Faculty (Surat)"
                ),
                AttendanceLogEntity(
                    studentId = 1,
                    dateString = "Sep 05, 2026",
                    status = "Present",
                    subjectOrSession = "Mathematics Examination",
                    educatorName = "Sayedpura Academic Mentor"
                ),
                AttendanceLogEntity(
                    studentId = 1,
                    dateString = "Sep 03, 2026",
                    status = "Excused",
                    subjectOrSession = "English Language Session",
                    educatorName = "Sayedpura Language Mentor"
                )
            )
            attendanceDao.insertAll(attendanceLogs)

            // 4. Subjects
            val subjects = listOf(
                SubjectEntity(
                    id = 1,
                    name = "Hindi Literature & Grammar",
                    teacherName = "Khwaab Hindi Faculty (Surat)",
                    targetPercentage = 85f,
                    creditHours = 5,
                    colorHex = "#F5C542"
                ),
                SubjectEntity(
                    id = 2,
                    name = "Core Mathematics",
                    teacherName = "Sayedpura Academic Mentor",
                    targetPercentage = 80f,
                    creditHours = 4,
                    colorHex = "#E5A823"
                ),
                SubjectEntity(
                    id = 3,
                    name = "General Science & Environment",
                    teacherName = "Khwaab Science Faculty",
                    targetPercentage = 85f,
                    creditHours = 4,
                    colorHex = "#F1C40F"
                ),
                SubjectEntity(
                    id = 4,
                    name = "English Language & Communication",
                    teacherName = "Sayedpura Language Mentor",
                    targetPercentage = 75f,
                    creditHours = 3,
                    colorHex = "#E6CA65"
                ),
                SubjectEntity(
                    id = 5,
                    name = "Social Science & Ethics",
                    teacherName = "Foundation Educator (Surat)",
                    targetPercentage = 80f,
                    creditHours = 3,
                    colorHex = "#D4AF37"
                )
            )
            subjectDao.insertAll(subjects)

            // 5. Assessments
            val assessments = listOf(
                AssessmentEntity(
                    subjectId = 1,
                    subjectName = "Hindi Literature & Grammar",
                    title = "Hindi Vyakaran & Creative Prose",
                    score = 23.5f,
                    maxScore = 25f,
                    dateString = "Sep 08, 2026",
                    timestamp = System.currentTimeMillis() - 86400000L * 3,
                    notes = "Excellent grasp of Hindi sandhi, samas and creative essay structuring."
                ),
                AssessmentEntity(
                    subjectId = 2,
                    subjectName = "Core Mathematics",
                    title = "Algebra & Geometry Unit Test",
                    score = 42f,
                    maxScore = 50f,
                    dateString = "Sep 05, 2026",
                    timestamp = System.currentTimeMillis() - 86400000L * 6,
                    notes = "Step-by-step proofs completed accurately."
                ),
                AssessmentEntity(
                    subjectId = 1,
                    subjectName = "Hindi Literature & Grammar",
                    title = "Kavita Paath & Comprehension",
                    score = 18f,
                    maxScore = 20f,
                    dateString = "Aug 29, 2026",
                    timestamp = System.currentTimeMillis() - 86400000L * 13,
                    notes = "Expressive recital and precise stanza analysis."
                ),
                AssessmentEntity(
                    subjectId = 3,
                    subjectName = "General Science & Environment",
                    title = "Science Lab Experiment Check",
                    score = 28f,
                    maxScore = 30f,
                    dateString = "Aug 24, 2026",
                    timestamp = System.currentTimeMillis() - 86400000L * 18,
                    notes = "Demonstrated clear understanding of botanical taxonomy."
                ),
                AssessmentEntity(
                    subjectId = 4,
                    subjectName = "English Language & Communication",
                    title = "Reading Comprehension & Grammar",
                    score = 38f,
                    maxScore = 50f,
                    dateString = "Aug 18, 2026",
                    timestamp = System.currentTimeMillis() - 86400000L * 24,
                    notes = "Focus on active/passive voice transformation."
                )
            )
            assessmentDao.insertAll(assessments)

            // 6. Workshops
            val workshops = listOf(
                WorkshopEntity(
                    id = 1,
                    title = "Mastering Hindi Grammar & Creative Expression",
                    topic = "Hindi Vyakaran & Literature",
                    instructor = "Senior Hindi Faculty, Khwaab Surat",
                    dateIso = "2026-09-15",
                    formattedDate = "Tue, Sep 15, 2026",
                    timeString = "10:30 AM - 12:30 PM",
                    venue = "2nd Floor, Pumping Char Rasta, above Mubin Cycle Wala, Sayedpura, Surat",
                    description = "Interactive deep dive into Hindi prosody, vyakaran rules, and expressive prose writing for secondary students.",
                    isEnrolled = true,
                    capacity = 30,
                    registeredCount = 22,
                    isHindiFocused = true
                ),
                WorkshopEntity(
                    id = 2,
                    title = "Mathematics Problem-Solving & Mental Calculation",
                    topic = "Speed Math & Euclidean Geometry",
                    instructor = "Surat Academic Mentorship Team",
                    dateIso = "2026-09-18",
                    formattedDate = "Fri, Sep 18, 2026",
                    timeString = "03:00 PM - 05:00 PM",
                    venue = "Hall A, 2nd Fl., Pumping Char Rasta, Sayedpura, Surat",
                    description = "Speed calculation techniques, algebraic shortcuts, and step-by-step geometric proofs for board exams.",
                    isEnrolled = false,
                    capacity = 35,
                    registeredCount = 19,
                    isHindiFocused = false
                ),
                WorkshopEntity(
                    id = 3,
                    title = "Teaching Opportunities & Educator Orientation",
                    topic = "Pedagogy & Faculty Recruitment",
                    instructor = "Khwaab Foundation Academic Recruitment Desk",
                    dateIso = "2026-09-22",
                    formattedDate = "Tue, Sep 22, 2026",
                    timeString = "11:00 AM - 01:00 PM",
                    venue = "Conference Room, 2nd Fl., Navi Chal, Sayedpura, Surat",
                    description = "Information session for educators and aspiring teachers regarding part-time and full-time teaching positions, including Hindi-teaching roles.",
                    isEnrolled = false,
                    capacity = 40,
                    registeredCount = 20,
                    isHindiFocused = true
                ),
                WorkshopEntity(
                    id = 4,
                    title = "Hands-On Science Experiments & Critical Thinking",
                    topic = "Applied Physics & Chemistry",
                    instructor = "Khwaab Science Faculty",
                    dateIso = "2026-09-26",
                    formattedDate = "Sat, Sep 26, 2026",
                    timeString = "02:00 PM - 04:30 PM",
                    venue = "Science Lab, 2nd Fl., Pumping Char Rasta, Sayedpura, Surat",
                    description = "Practical demonstrations of optics, chemical reactions, and physical mechanics tailored for school learners.",
                    isEnrolled = false,
                    capacity = 25,
                    registeredCount = 14,
                    isHindiFocused = false
                ),
                WorkshopEntity(
                    id = 5,
                    title = "Exam Readiness & Time-Management Masterclass",
                    topic = "Study Habits & Mind-Mapping",
                    instructor = "Surat Education Mentors",
                    dateIso = "2026-10-02",
                    formattedDate = "Fri, Oct 02, 2026",
                    timeString = "10:00 AM - 12:00 PM",
                    venue = "2nd Floor, Pumping Char Rasta, above Mubin Cycle Wala, Sayedpura, Surat",
                    description = "Effective revision schedules, mind-mapping methods, and stress management techniques for academic milestones.",
                    isEnrolled = false,
                    capacity = 50,
                    registeredCount = 25,
                    isHindiFocused = false
                )
            )
            workshopDao.insertAll(workshops)

            // 7. Academic Goals
            val goals = listOf(
                AcademicGoalEntity(
                    title = "Score above 88% in Hindi Mid-Term Examination",
                    category = "Hindi",
                    isCompleted = true,
                    targetDate = "Sep 08, 2026"
                ),
                AcademicGoalEntity(
                    title = "Solve 25 advanced algebra problem sets",
                    category = "Mathematics",
                    isCompleted = true,
                    targetDate = "Sep 10, 2026"
                ),
                AcademicGoalEntity(
                    title = "Attend Hindi Grammar Workshop at Sayedpura Centre",
                    category = "Workshop",
                    isCompleted = false,
                    targetDate = "Sep 15, 2026"
                ),
                AcademicGoalEntity(
                    title = "Maintain >90% monthly attendance in all sessions",
                    category = "Attendance",
                    isCompleted = false,
                    targetDate = "Sep 30, 2026"
                ),
                AcademicGoalEntity(
                    title = "Complete Science Lab Botanical Taxonomy notes",
                    category = "Science",
                    isCompleted = false,
                    targetDate = "Oct 05, 2026"
                )
            )
            goalDao.insertAll(goals)
        }
    }
}
