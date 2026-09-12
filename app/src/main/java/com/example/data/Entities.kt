package com.example.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "students")
data class StudentProfileEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val rollNo: String,
    val grade: String,
    val phone: String,
    val guardianPhone: String,
    val address: String,
    val email: String = "",
    val totalClasses: Int = 45,
    val attendedClasses: Int = 41,
    val attendanceNotes: String = "Regular student at Khwaab Sayedpura Centre",
    val mentorRemarks: String = "Shows keen interest in Hindi literature and logical mathematics",
    val isPrimary: Boolean = true
) {
    val attendancePercentage: Float
        get() = if (totalClasses > 0) (attendedClasses.toFloat() / totalClasses.toFloat()) * 100f else 0f
}

@Entity(tableName = "student_marks")
data class StudentSubjectMarkEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val studentId: Long,
    val subjectName: String,
    val marksObtained: Float,
    val totalMarks: Float,
    val term: String,
    val examDate: String = "Recent",
    val remarks: String = ""
) {
    val percentage: Float
        get() = if (totalMarks > 0) (marksObtained / totalMarks) * 100f else 0f

    val gradeBand: String
        get() = when {
            percentage >= 90f -> "A+"
            percentage >= 80f -> "A"
            percentage >= 70f -> "B+"
            percentage >= 60f -> "B"
            percentage >= 50f -> "C"
            else -> "Needs Focus"
        }
}

@Entity(tableName = "attendance_logs")
data class AttendanceLogEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val studentId: Long,
    val dateString: String,
    val status: String, // "Present", "Absent", "Late", "Excused"
    val subjectOrSession: String,
    val educatorName: String
)

@Entity(tableName = "subjects")
data class SubjectEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val teacherName: String,
    val targetPercentage: Float = 85.0f,
    val creditHours: Int = 4,
    val colorHex: String = "#F5C542"
)

@Entity(tableName = "assessments")
data class AssessmentEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val subjectId: Long,
    val subjectName: String,
    val title: String,
    val score: Float,
    val maxScore: Float,
    val dateString: String,
    val timestamp: Long = System.currentTimeMillis(),
    val notes: String = ""
) {
    val percentage: Float
        get() = if (maxScore > 0) (score / maxScore) * 100f else 0f

    val grade: String
        get() = when {
            percentage >= 90f -> "A+"
            percentage >= 80f -> "A"
            percentage >= 70f -> "B+"
            percentage >= 60f -> "B"
            percentage >= 50f -> "C"
            else -> "Needs Focus"
        }
}

@Entity(tableName = "workshops")
data class WorkshopEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val title: String,
    val topic: String,
    val instructor: String,
    val dateIso: String, // YYYY-MM-DD for calendar matching
    val formattedDate: String,
    val timeString: String,
    val venue: String,
    val description: String,
    val isEnrolled: Boolean = false,
    val capacity: Int = 30,
    val registeredCount: Int = 12,
    val isHindiFocused: Boolean = false
) {
    val seatsLeft: Int
        get() = (capacity - registeredCount).coerceAtLeast(0)
}

@Entity(tableName = "academic_goals")
data class AcademicGoalEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val title: String,
    val category: String,
    val isCompleted: Boolean = false,
    val targetDate: String
)

