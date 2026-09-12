package com.example.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface StudentDao {
    @Query("SELECT * FROM students ORDER BY id ASC")
    fun getAllStudents(): Flow<List<StudentProfileEntity>>

    @Query("SELECT * FROM students WHERE isPrimary = 1 LIMIT 1")
    fun getPrimaryStudent(): Flow<StudentProfileEntity?>

    @Query("SELECT * FROM students WHERE id = :id LIMIT 1")
    suspend fun getStudentById(id: Long): StudentProfileEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStudent(student: StudentProfileEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(students: List<StudentProfileEntity>)

    @Update
    suspend fun updateStudent(student: StudentProfileEntity)

    @Delete
    suspend fun deleteStudent(student: StudentProfileEntity)
}

@Dao
interface StudentMarkDao {
    @Query("SELECT * FROM student_marks WHERE studentId = :studentId ORDER BY id ASC")
    fun getMarksForStudent(studentId: Long): Flow<List<StudentSubjectMarkEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMark(mark: StudentSubjectMarkEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(marks: List<StudentSubjectMarkEntity>)

    @Update
    suspend fun updateMark(mark: StudentSubjectMarkEntity)

    @Delete
    suspend fun deleteMark(mark: StudentSubjectMarkEntity)
}

@Dao
interface AttendanceDao {
    @Query("SELECT * FROM attendance_logs WHERE studentId = :studentId ORDER BY id DESC")
    fun getAttendanceForStudent(studentId: Long): Flow<List<AttendanceLogEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLog(log: AttendanceLogEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(logs: List<AttendanceLogEntity>)

    @Delete
    suspend fun deleteLog(log: AttendanceLogEntity)
}

@Dao
interface SubjectDao {
    @Query("SELECT * FROM subjects ORDER BY name ASC")
    fun getAllSubjects(): Flow<List<SubjectEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSubject(subject: SubjectEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(subjects: List<SubjectEntity>)

    @Update
    suspend fun updateSubject(subject: SubjectEntity)

    @Delete
    suspend fun deleteSubject(subject: SubjectEntity)
}

@Dao
interface AssessmentDao {
    @Query("SELECT * FROM assessments ORDER BY timestamp DESC")
    fun getAllAssessments(): Flow<List<AssessmentEntity>>

    @Query("SELECT * FROM assessments WHERE subjectId = :subjectId ORDER BY timestamp DESC")
    fun getAssessmentsForSubject(subjectId: Long): Flow<List<AssessmentEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAssessment(assessment: AssessmentEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(assessments: List<AssessmentEntity>)

    @Delete
    suspend fun deleteAssessment(assessment: AssessmentEntity)
}

@Dao
interface WorkshopDao {
    @Query("SELECT * FROM workshops ORDER BY dateIso ASC, id ASC")
    fun getAllWorkshops(): Flow<List<WorkshopEntity>>

    @Query("SELECT * FROM workshops WHERE isEnrolled = 1 ORDER BY dateIso ASC")
    fun getEnrolledWorkshops(): Flow<List<WorkshopEntity>>

    @Query("SELECT * FROM workshops WHERE dateIso = :dateIso ORDER BY id ASC")
    fun getWorkshopsForDate(dateIso: String): Flow<List<WorkshopEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWorkshop(workshop: WorkshopEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(workshops: List<WorkshopEntity>)

    @Update
    suspend fun updateWorkshop(workshop: WorkshopEntity)

    @Query("UPDATE workshops SET isEnrolled = :enrolled, registeredCount = CASE WHEN :enrolled = 1 THEN registeredCount + 1 ELSE CASE WHEN registeredCount > 0 THEN registeredCount - 1 ELSE 0 END END WHERE id = :workshopId")
    suspend fun setEnrollment(workshopId: Long, enrolled: Boolean)

    @Delete
    suspend fun deleteWorkshop(workshop: WorkshopEntity)
}

@Dao
interface GoalDao {
    @Query("SELECT * FROM academic_goals ORDER BY isCompleted ASC, id DESC")
    fun getAllGoals(): Flow<List<AcademicGoalEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGoal(goal: AcademicGoalEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(goals: List<AcademicGoalEntity>)

    @Update
    suspend fun updateGoal(goal: AcademicGoalEntity)

    @Delete
    suspend fun deleteGoal(goal: AcademicGoalEntity)
}

