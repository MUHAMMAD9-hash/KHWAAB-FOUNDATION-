package com.example.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.AcademicGoalEntity
import com.example.data.AcademicRepository
import com.example.data.AppDatabase
import com.example.data.AssessmentEntity
import com.example.data.AttendanceLogEntity
import com.example.data.StudentProfileEntity
import com.example.data.StudentSubjectMarkEntity
import com.example.data.SubjectEntity
import com.example.data.WorkshopEntity
import com.example.data.WorkshopRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class SubjectPerformance(
    val subject: SubjectEntity,
    val assessmentCount: Int,
    val averagePercentage: Float,
    val highestScorePercentage: Float,
    val isTargetMet: Boolean
)

data class AcademicSummary(
    val overallAverage: Float = 0f,
    val totalAssessments: Int = 0,
    val totalSubjects: Int = 0,
    val completedGoals: Int = 0,
    val totalGoals: Int = 0,
    val enrolledWorkshopsCount: Int = 0,
    val attendancePercentage: Float = 92f
)

class AcademicViewModel(application: Application) : AndroidViewModel(application) {
    private val database = AppDatabase.getDatabase(application, viewModelScope)
    private val academicRepo = AcademicRepository(
        database.subjectDao(),
        database.assessmentDao(),
        database.goalDao(),
        database.studentDao(),
        database.studentMarkDao(),
        database.attendanceDao()
    )
    private val workshopRepo = WorkshopRepository(database.workshopDao())

    // Student Profiles & Management
    val allStudents: StateFlow<List<StudentProfileEntity>> = academicRepo.allStudents
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _selectedStudentId = MutableStateFlow<Long?>(null)
    val selectedStudentId: StateFlow<Long?> = _selectedStudentId.asStateFlow()

    private val _isEducatorMode = MutableStateFlow(false)
    val isEducatorMode: StateFlow<Boolean> = _isEducatorMode.asStateFlow()

    fun toggleEducatorMode() {
        _isEducatorMode.value = !_isEducatorMode.value
    }

    fun selectStudent(id: Long) {
        _selectedStudentId.value = id
    }

    @OptIn(kotlinx.coroutines.ExperimentalCoroutinesApi::class)
    val studentProfile: StateFlow<StudentProfileEntity?> = combine(
        academicRepo.allStudents,
        _selectedStudentId
    ) { list, selId ->
        if (selId != null) list.find { it.id == selId } else list.firstOrNull { it.isPrimary } ?: list.firstOrNull()
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    @OptIn(kotlinx.coroutines.ExperimentalCoroutinesApi::class)
    val studentMarks: StateFlow<List<StudentSubjectMarkEntity>> = studentProfile.flatMapLatest { profile ->
        if (profile != null) {
            academicRepo.getMarksForStudent(profile.id)
        } else {
            flowOf(emptyList())
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    @OptIn(kotlinx.coroutines.ExperimentalCoroutinesApi::class)
    val attendanceLogs: StateFlow<List<AttendanceLogEntity>> = studentProfile.flatMapLatest { profile ->
        if (profile != null) {
            academicRepo.getAttendanceForStudent(profile.id)
        } else {
            flowOf(emptyList())
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val subjects: StateFlow<List<SubjectEntity>> = academicRepo.allSubjects
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val assessments: StateFlow<List<AssessmentEntity>> = academicRepo.allAssessments
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val workshops: StateFlow<List<WorkshopEntity>> = workshopRepo.allWorkshops
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val goals: StateFlow<List<AcademicGoalEntity>> = academicRepo.allGoals
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Workshop Calendar & Search Filters
    private val _selectedCalendarDate = MutableStateFlow<String?>(null)
    val selectedCalendarDate: StateFlow<String?> = _selectedCalendarDate.asStateFlow()

    private val _workshopSearchQuery = MutableStateFlow("")
    val workshopSearchQuery: StateFlow<String> = _workshopSearchQuery.asStateFlow()

    private val _selectedWorkshopCategory = MutableStateFlow("All")
    val selectedWorkshopCategory: StateFlow<String> = _selectedWorkshopCategory.asStateFlow()

    val filteredWorkshops: StateFlow<List<WorkshopEntity>> = combine(
        workshops,
        _workshopSearchQuery,
        _selectedWorkshopCategory,
        _selectedCalendarDate
    ) { list, query, category, calDate ->
        list.filter { item ->
            val matchesCalendar = calDate == null || item.dateIso == calDate

            val matchesQuery = query.isBlank() ||
                    item.title.contains(query, ignoreCase = true) ||
                    item.topic.contains(query, ignoreCase = true) ||
                    item.instructor.contains(query, ignoreCase = true) ||
                    item.description.contains(query, ignoreCase = true) ||
                    item.venue.contains(query, ignoreCase = true)

            val matchesCategory = category == "All" ||
                    (category == "Registered" && item.isEnrolled) ||
                    (category == "Hindi Focused" && item.isHindiFocused) ||
                    item.topic.contains(category, ignoreCase = true)

            matchesCalendar && matchesQuery && matchesCategory
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Summary calculation
    val summary: StateFlow<AcademicSummary> = combine(
        assessments,
        studentMarks,
        studentProfile,
        goals,
        workshops
    ) { assessList, marksList, profile, goalList, workshopList ->
        val totalAssessList = assessList.map { it.percentage } + marksList.map { it.percentage }
        val avg = if (totalAssessList.isNotEmpty()) {
            totalAssessList.average().toFloat()
        } else {
            0f
        }
        val compGoals = goalList.count { it.isCompleted }
        val enrolledCount = workshopList.count { it.isEnrolled }
        val attPerc = profile?.attendancePercentage ?: 92f

        AcademicSummary(
            overallAverage = avg,
            totalAssessments = totalAssessList.size,
            totalSubjects = 5,
            completedGoals = compGoals,
            totalGoals = goalList.size,
            enrolledWorkshopsCount = enrolledCount,
            attendancePercentage = attPerc
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), AcademicSummary())

    // Subject breakdown calculation
    val subjectPerformances: StateFlow<List<SubjectPerformance>> = combine(
        subjects,
        assessments,
        studentMarks
    ) { subList, assessList, marksList ->
        subList.map { sub ->
            val subAssessments = assessList.filter { it.subjectId == sub.id }
            val subMarks = marksList.filter { it.subjectName.contains(sub.name, ignoreCase = true) || sub.name.contains(it.subjectName, ignoreCase = true) }
            val percentages = subAssessments.map { it.percentage } + subMarks.map { it.percentage }
            val count = percentages.size
            val avg = if (count > 0) percentages.average().toFloat() else 0f
            val max = if (count > 0) percentages.maxOrNull() ?: 0f else 0f
            SubjectPerformance(
                subject = sub,
                assessmentCount = count,
                averagePercentage = avg,
                highestScorePercentage = max,
                isTargetMet = avg >= sub.targetPercentage && count > 0
            )
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun updateWorkshopSearch(query: String) {
        _workshopSearchQuery.value = query
    }

    fun selectWorkshopCategory(category: String) {
        _selectedWorkshopCategory.value = category
    }

    fun selectCalendarDate(dateIso: String?) {
        _selectedCalendarDate.value = dateIso
    }

    // Student Profile Management (for Students and Educators)
    fun updateStudentProfile(
        name: String,
        rollNo: String,
        grade: String,
        phone: String,
        guardianPhone: String,
        address: String,
        email: String,
        attendanceNotes: String,
        mentorRemarks: String
    ) {
        viewModelScope.launch {
            val current = studentProfile.value ?: StudentProfileEntity(
                name = name,
                rollNo = rollNo,
                grade = grade,
                phone = phone,
                guardianPhone = guardianPhone,
                address = address
            )
            academicRepo.updateStudentProfile(
                current.copy(
                    name = name.trim(),
                    rollNo = rollNo.trim(),
                    grade = grade.trim(),
                    phone = phone.trim(),
                    guardianPhone = guardianPhone.trim(),
                    address = address.trim(),
                    email = email.trim(),
                    attendanceNotes = attendanceNotes.trim(),
                    mentorRemarks = mentorRemarks.trim()
                )
            )
        }
    }

    fun addStudentMark(
        subjectName: String,
        marksObtained: Float,
        totalMarks: Float,
        term: String,
        remarks: String
    ) {
        val studentId = studentProfile.value?.id ?: 1L
        viewModelScope.launch {
            academicRepo.addStudentMark(
                StudentSubjectMarkEntity(
                    studentId = studentId,
                    subjectName = subjectName.trim(),
                    marksObtained = marksObtained,
                    totalMarks = totalMarks,
                    term = term.trim(),
                    remarks = remarks.trim()
                )
            )
        }
    }

    fun deleteStudentMark(mark: StudentSubjectMarkEntity) {
        viewModelScope.launch {
            academicRepo.deleteStudentMark(mark)
        }
    }

    fun recordAttendance(
        status: String,
        subjectOrSession: String,
        educatorName: String,
        dateString: String
    ) {
        val student = studentProfile.value ?: return
        viewModelScope.launch {
            academicRepo.logAttendance(
                studentId = student.id,
                status = status,
                subjectOrSession = subjectOrSession.trim(),
                educatorName = educatorName.trim(),
                dateString = dateString.trim()
            )
            val newTotal = student.totalClasses + 1
            val newAttended = if (status == "Present") student.attendedClasses + 1 else student.attendedClasses
            academicRepo.updateStudentProfile(
                student.copy(
                    totalClasses = newTotal,
                    attendedClasses = newAttended
                )
            )
        }
    }

    fun updateAttendanceCounts(total: Int, attended: Int) {
        val student = studentProfile.value ?: return
        viewModelScope.launch {
            academicRepo.updateStudentProfile(
                student.copy(
                    totalClasses = total.coerceAtLeast(0),
                    attendedClasses = attended.coerceIn(0, total.coerceAtLeast(0))
                )
            )
        }
    }

    fun addNewStudent(
        name: String,
        rollNo: String,
        grade: String,
        phone: String,
        guardianPhone: String,
        address: String,
        email: String
    ) {
        viewModelScope.launch {
            val newId = academicRepo.insertStudent(
                StudentProfileEntity(
                    name = name.trim(),
                    rollNo = rollNo.trim(),
                    grade = grade.trim(),
                    phone = phone.trim(),
                    guardianPhone = guardianPhone.trim(),
                    address = address.trim(),
                    email = email.trim(),
                    totalClasses = 0,
                    attendedClasses = 0,
                    isPrimary = false
                )
            )
            _selectedStudentId.value = newId
        }
    }

    // Workshop Registration
    fun toggleWorkshopEnrollment(workshop: WorkshopEntity) {
        viewModelScope.launch {
            workshopRepo.setEnrollment(workshop.id, !workshop.isEnrolled)
        }
    }

    fun addWorkshop(
        title: String,
        topic: String,
        instructor: String,
        dateIso: String,
        formattedDate: String,
        timeString: String,
        venue: String,
        description: String,
        isHindi: Boolean,
        capacity: Int = 30
    ) {
        viewModelScope.launch {
            workshopRepo.addWorkshop(
                WorkshopEntity(
                    title = title.trim(),
                    topic = topic.trim(),
                    instructor = instructor.trim(),
                    dateIso = dateIso.trim(),
                    formattedDate = formattedDate.trim(),
                    timeString = timeString.trim(),
                    venue = venue.trim(),
                    description = description.trim(),
                    isEnrolled = false,
                    capacity = capacity,
                    registeredCount = 0,
                    isHindiFocused = isHindi
                )
            )
        }
    }

    // Assessments & Subjects
    fun addAssessment(
        subject: SubjectEntity,
        title: String,
        score: Float,
        maxScore: Float,
        dateString: String,
        notes: String
    ) {
        viewModelScope.launch {
            academicRepo.addAssessment(
                AssessmentEntity(
                    subjectId = subject.id,
                    subjectName = subject.name,
                    title = title.trim(),
                    score = score,
                    maxScore = maxScore,
                    dateString = dateString.ifBlank { "Recent" },
                    timestamp = System.currentTimeMillis(),
                    notes = notes.trim()
                )
            )
        }
    }

    fun deleteAssessment(assessment: AssessmentEntity) {
        viewModelScope.launch {
            academicRepo.deleteAssessment(assessment)
        }
    }

    fun addSubject(name: String, teacherName: String, targetPercentage: Float, creditHours: Int) {
        viewModelScope.launch {
            academicRepo.addSubject(
                SubjectEntity(
                    name = name.trim(),
                    teacherName = teacherName.trim(),
                    targetPercentage = targetPercentage,
                    creditHours = creditHours,
                    colorHex = "#F5C542"
                )
            )
        }
    }

    fun deleteSubject(subject: SubjectEntity) {
        viewModelScope.launch {
            academicRepo.deleteSubject(subject)
        }
    }

    fun toggleGoal(goal: AcademicGoalEntity) {
        viewModelScope.launch {
            academicRepo.toggleGoalCompletion(goal)
        }
    }

    fun addGoal(title: String, category: String, targetDate: String) {
        viewModelScope.launch {
            academicRepo.addGoal(
                AcademicGoalEntity(
                    title = title.trim(),
                    category = category.trim(),
                    targetDate = targetDate.trim(),
                    isCompleted = false
                )
            )
        }
    }

    fun deleteGoal(goal: AcademicGoalEntity) {
        viewModelScope.launch {
            academicRepo.deleteGoal(goal)
        }
    }
}
