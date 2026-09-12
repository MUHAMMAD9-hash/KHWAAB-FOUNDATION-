package com.example.data

import kotlinx.coroutines.flow.Flow

class AcademicRepository(
    private val subjectDao: SubjectDao,
    private val assessmentDao: AssessmentDao,
    private val goalDao: GoalDao,
    private val studentDao: StudentDao,
    private val markDao: StudentMarkDao,
    private val attendanceDao: AttendanceDao
) {
    val primaryStudent: Flow<StudentProfileEntity?> = studentDao.getPrimaryStudent()
    val allStudents: Flow<List<StudentProfileEntity>> = studentDao.getAllStudents()
    val allSubjects: Flow<List<SubjectEntity>> = subjectDao.getAllSubjects()
    val allAssessments: Flow<List<AssessmentEntity>> = assessmentDao.getAllAssessments()
    val allGoals: Flow<List<AcademicGoalEntity>> = goalDao.getAllGoals()

    fun getMarksForStudent(studentId: Long): Flow<List<StudentSubjectMarkEntity>> {
        return markDao.getMarksForStudent(studentId)
    }

    fun getAttendanceForStudent(studentId: Long): Flow<List<AttendanceLogEntity>> {
        return attendanceDao.getAttendanceForStudent(studentId)
    }

    suspend fun updateStudentProfile(student: StudentProfileEntity) {
        studentDao.updateStudent(student)
    }

    suspend fun insertStudent(student: StudentProfileEntity): Long {
        return studentDao.insertStudent(student)
    }

    suspend fun addStudentMark(mark: StudentSubjectMarkEntity): Long {
        return markDao.insertMark(mark)
    }

    suspend fun deleteStudentMark(mark: StudentSubjectMarkEntity) {
        markDao.deleteMark(mark)
    }

    suspend fun logAttendance(
        studentId: Long,
        status: String,
        subjectOrSession: String,
        educatorName: String,
        dateString: String
    ) {
        attendanceDao.insertLog(
            AttendanceLogEntity(
                studentId = studentId,
                dateString = dateString,
                status = status,
                subjectOrSession = subjectOrSession,
                educatorName = educatorName
            )
        )
    }

    fun getAssessmentsForSubject(subjectId: Long): Flow<List<AssessmentEntity>> {
        return assessmentDao.getAssessmentsForSubject(subjectId)
    }

    suspend fun addSubject(subject: SubjectEntity): Long {
        return subjectDao.insertSubject(subject)
    }

    suspend fun deleteSubject(subject: SubjectEntity) {
        subjectDao.deleteSubject(subject)
    }

    suspend fun addAssessment(assessment: AssessmentEntity): Long {
        return assessmentDao.insertAssessment(assessment)
    }

    suspend fun deleteAssessment(assessment: AssessmentEntity) {
        assessmentDao.deleteAssessment(assessment)
    }

    suspend fun addGoal(goal: AcademicGoalEntity): Long {
        return goalDao.insertGoal(goal)
    }

    suspend fun toggleGoalCompletion(goal: AcademicGoalEntity) {
        goalDao.updateGoal(goal.copy(isCompleted = !goal.isCompleted))
    }

    suspend fun deleteGoal(goal: AcademicGoalEntity) {
        goalDao.deleteGoal(goal)
    }
}

class WorkshopRepository(
    private val workshopDao: WorkshopDao
) {
    val allWorkshops: Flow<List<WorkshopEntity>> = workshopDao.getAllWorkshops()
    val enrolledWorkshops: Flow<List<WorkshopEntity>> = workshopDao.getEnrolledWorkshops()

    fun getWorkshopsForDate(dateIso: String): Flow<List<WorkshopEntity>> {
        return workshopDao.getWorkshopsForDate(dateIso)
    }

    suspend fun setEnrollment(workshopId: Long, isEnrolled: Boolean) {
        workshopDao.setEnrollment(workshopId, isEnrolled)
    }

    suspend fun addWorkshop(workshop: WorkshopEntity): Long {
        return workshopDao.insertWorkshop(workshop)
    }

    suspend fun deleteWorkshop(workshop: WorkshopEntity) {
        workshopDao.deleteWorkshop(workshop)
    }
}
