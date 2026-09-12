package com.example.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.EventAvailable
import androidx.compose.material.icons.filled.Grade
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.School
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.AttendanceLogEntity
import com.example.data.StudentProfileEntity
import com.example.data.StudentSubjectMarkEntity
import com.example.ui.AcademicViewModel
import com.example.ui.components.GoldBadge
import com.example.ui.components.GoldButton
import com.example.ui.components.GoldCard
import com.example.ui.components.GoldProgressBar
import com.example.ui.components.SuratCenterNoticeBanner
import com.example.ui.theme.DarkCardBackground
import com.example.ui.theme.DarkSurfaceVariant
import com.example.ui.theme.GoldBorder
import com.example.ui.theme.GoldBorderLight
import com.example.ui.theme.GoldContainer
import com.example.ui.theme.GoldLight
import com.example.ui.theme.GoldPrimary
import com.example.ui.theme.GoldSecondary
import com.example.ui.theme.PureBlack
import com.example.ui.theme.TextPrimaryGold
import com.example.ui.theme.TextSecondaryMuted

@Composable
fun StudentProfileScreen(
    viewModel: AcademicViewModel,
    student: StudentProfileEntity?,
    marks: List<StudentSubjectMarkEntity>,
    attendanceLogs: List<AttendanceLogEntity>,
    onInfoClick: () -> Unit,
    onCallClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var showEditProfileDialog by remember { mutableStateOf(false) }
    var showAddMarkDialog by remember { mutableStateOf(false) }
    var showRecordAttendanceDialog by remember { mutableStateOf(false) }

    val currentStudent = student ?: StudentProfileEntity(
        name = "Aarav Patel",
        rollNo = "KF-SRT-2026-042",
        grade = "Class 10 (Secondary)",
        phone = "+91 98251 44321",
        guardianPhone = "+91 94280 11987",
        address = "Sayedpura, Surat"
    )

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(top = 12.dp, bottom = 88.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Surat Location Banner
        item {
            SuratCenterNoticeBanner(
                onInfoClick = onInfoClick,
                onCallClick = onCallClick
            )
        }

        // Student Profile Header Card
        item {
            GoldCard(
                modifier = Modifier.testTag("student_profile_card")
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            // Avatar with Gold Ring
                            Box(
                                modifier = Modifier
                                    .size(54.dp)
                                    .clip(CircleShape)
                                    .background(DarkSurfaceVariant)
                                    .border(2.dp, GoldPrimary, CircleShape),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Person,
                                    contentDescription = "Student Avatar",
                                    tint = GoldPrimary,
                                    modifier = Modifier.size(32.dp)
                                )
                            }

                            Column {
                                Text(
                                    text = currentStudent.name,
                                    color = GoldLight,
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = "${currentStudent.grade} • Roll: ${currentStudent.rollNo}",
                                    color = TextSecondaryMuted,
                                    fontSize = 12.sp
                                )
                                GoldBadge(
                                    text = "Khwaab Surat Student",
                                    isPrimary = true,
                                    icon = Icons.Default.School,
                                    modifier = Modifier.padding(top = 4.dp)
                                )
                            }
                        }

                        IconButton(
                            onClick = { showEditProfileDialog = true },
                            modifier = Modifier
                                .size(38.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(DarkSurfaceVariant)
                                .testTag("edit_student_profile_button")
                        ) {
                            Icon(
                                imageVector = Icons.Default.Edit,
                                contentDescription = "Edit Profile",
                                tint = GoldPrimary,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }

                    // Contact Details
                    Surface(
                        color = DarkSurfaceVariant,
                        shape = RoundedCornerShape(12.dp),
                        border = BorderStroke(1.dp, GoldBorder),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier.padding(12.dp),
                            verticalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Text(
                                text = "Contact Information",
                                color = GoldPrimary,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold
                            )

                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.Phone,
                                    contentDescription = null,
                                    tint = GoldSecondary,
                                    modifier = Modifier.size(14.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "Student: ${currentStudent.phone}",
                                    color = TextPrimaryGold,
                                    fontSize = 12.sp
                                )
                            }

                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.Call,
                                    contentDescription = null,
                                    tint = GoldSecondary,
                                    modifier = Modifier.size(14.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "Guardian: ${currentStudent.guardianPhone}",
                                    color = TextPrimaryGold,
                                    fontSize = 12.sp
                                )
                            }

                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.Email,
                                    contentDescription = null,
                                    tint = GoldSecondary,
                                    modifier = Modifier.size(14.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = currentStudent.email.ifBlank { "Not provided" },
                                    color = TextPrimaryGold,
                                    fontSize = 12.sp
                                )
                            }

                            Row(verticalAlignment = Alignment.Top) {
                                Icon(
                                    imageVector = Icons.Default.Home,
                                    contentDescription = null,
                                    tint = GoldSecondary,
                                    modifier = Modifier
                                        .size(14.dp)
                                        .padding(top = 2.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = currentStudent.address,
                                    color = TextSecondaryMuted,
                                    fontSize = 11.5.sp,
                                    lineHeight = 15.sp
                                )
                            }
                        }
                    }

                    // Mentor remarks
                    Surface(
                        color = Color(0x22F5C542),
                        shape = RoundedCornerShape(8.dp),
                        border = BorderStroke(0.5.dp, GoldBorderLight),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(10.dp)) {
                            Text(
                                text = "Educator / Mentor Remarks (Surat Faculty):",
                                color = GoldPrimary,
                                fontWeight = FontWeight.Bold,
                                fontSize = 11.sp
                            )
                            Text(
                                text = currentStudent.mentorRemarks,
                                color = GoldLight,
                                fontSize = 11.5.sp,
                                lineHeight = 15.sp,
                                modifier = Modifier.padding(top = 2.dp)
                            )
                        }
                    }
                }
            }
        }

        // Attendance Records Section
        item {
            GoldCard(
                modifier = Modifier.testTag("attendance_overview_card")
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.EventAvailable,
                                contentDescription = null,
                                tint = GoldPrimary,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Attendance Records",
                                color = GoldLight,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        OutlinedButton(
                            onClick = { showRecordAttendanceDialog = true },
                            shape = RoundedCornerShape(8.dp),
                            border = BorderStroke(1.dp, GoldBorderLight),
                            colors = ButtonDefaults.outlinedButtonColors(contentColor = GoldPrimary),
                            modifier = Modifier.testTag("educator_record_attendance_button")
                        ) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = null,
                                modifier = Modifier.size(15.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Mark Attendance", fontSize = 11.5.sp)
                        }
                    }

                    // Attendance Stat Summary
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        // Rate
                        Surface(
                            color = DarkSurfaceVariant,
                            shape = RoundedCornerShape(10.dp),
                            border = BorderStroke(1.dp, GoldBorder),
                            modifier = Modifier.weight(1f)
                        ) {
                            Column(
                                modifier = Modifier.padding(10.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = "${String.format("%.1f", currentStudent.attendancePercentage)}%",
                                    color = GoldPrimary,
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = "Attendance Rate",
                                    color = TextSecondaryMuted,
                                    fontSize = 10.sp
                                )
                            }
                        }

                        // Classes Attended
                        Surface(
                            color = DarkSurfaceVariant,
                            shape = RoundedCornerShape(10.dp),
                            border = BorderStroke(1.dp, GoldBorder),
                            modifier = Modifier.weight(1f)
                        ) {
                            Column(
                                modifier = Modifier.padding(10.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = "${currentStudent.attendedClasses} / ${currentStudent.totalClasses}",
                                    color = GoldLight,
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = "Classes Present",
                                    color = TextSecondaryMuted,
                                    fontSize = 10.sp
                                )
                            }
                        }
                    }

                    GoldProgressBar(progress = currentStudent.attendancePercentage / 100f)

                    Text(
                        text = currentStudent.attendanceNotes,
                        color = TextSecondaryMuted,
                        fontSize = 11.sp
                    )

                    // Recent logs list
                    if (attendanceLogs.isNotEmpty()) {
                        Text(
                            text = "Recent Attendance Logs:",
                            color = GoldLight,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 12.sp,
                            modifier = Modifier.padding(top = 4.dp)
                        )

                        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                            attendanceLogs.take(5).forEach { log ->
                                Surface(
                                    color = DarkSurfaceVariant,
                                    shape = RoundedCornerShape(8.dp),
                                    border = BorderStroke(0.5.dp, GoldBorder),
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(horizontal = 10.dp, vertical = 6.dp),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Column(modifier = Modifier.weight(1f)) {
                                            Text(
                                                text = log.subjectOrSession,
                                                color = TextPrimaryGold,
                                                fontSize = 12.sp,
                                                fontWeight = FontWeight.Medium
                                            )
                                            Text(
                                                text = "${log.dateString} • By ${log.educatorName}",
                                                color = TextSecondaryMuted,
                                                fontSize = 10.5.sp
                                            )
                                        }

                                        Surface(
                                            color = if (log.status == "Present") Color(0x334CAF50) else Color(0x33FFA726),
                                            shape = RoundedCornerShape(6.dp),
                                            border = BorderStroke(
                                                1.dp,
                                                if (log.status == "Present") Color(0xFF4CAF50) else Color(0xFFFFA726)
                                            )
                                        ) {
                                            Text(
                                                text = log.status,
                                                color = if (log.status == "Present") Color(0xFF81C784) else Color(0xFFFFB74D),
                                                fontSize = 10.5.sp,
                                                fontWeight = FontWeight.Bold,
                                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        // Academic Performance & Marks Section
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Grade,
                        contentDescription = null,
                        tint = GoldPrimary,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Subject Marks & Performance",
                        color = GoldLight,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                OutlinedButton(
                    onClick = { showAddMarkDialog = true },
                    shape = RoundedCornerShape(8.dp),
                    border = BorderStroke(1.dp, GoldBorderLight),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = GoldPrimary),
                    modifier = Modifier.testTag("educator_add_mark_button")
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = null,
                        modifier = Modifier.size(15.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Add Marks", fontSize = 11.5.sp)
                }
            }
        }

        if (marks.isEmpty()) {
            item {
                GoldCard {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "No recorded subject marks yet.",
                            color = TextSecondaryMuted,
                            fontSize = 13.sp
                        )
                    }
                }
            }
        } else {
            items(marks, key = { it.id }) { mark ->
                GoldCard(
                    modifier = Modifier.testTag("subject_mark_card_${mark.id}")
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = mark.subjectName,
                                    color = GoldLight,
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = "${mark.term} • Evaluated ${mark.examDate}",
                                    color = TextSecondaryMuted,
                                    fontSize = 11.sp
                                )
                            }

                            Surface(
                                color = GoldContainer,
                                shape = RoundedCornerShape(8.dp),
                                border = BorderStroke(1.dp, GoldBorderLight)
                            ) {
                                Column(
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(
                                        text = "${mark.marksObtained} / ${mark.totalMarks}",
                                        color = GoldPrimary,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 13.sp
                                    )
                                    Text(
                                        text = "${String.format("%.1f", mark.percentage)}% (${mark.gradeBand})",
                                        color = GoldLight,
                                        fontSize = 10.sp
                                    )
                                }
                            }
                        }

                        GoldProgressBar(progress = mark.percentage / 100f)

                        if (mark.remarks.isNotBlank()) {
                            Text(
                                text = "Remarks: ${mark.remarks}",
                                color = TextPrimaryGold,
                                fontSize = 11.5.sp
                            )
                        }
                    }
                }
            }
        }
    }

    // Dialog: Edit Student Profile
    if (showEditProfileDialog) {
        EditStudentProfileDialog(
            student = currentStudent,
            onDismiss = { showEditProfileDialog = false },
            onSave = { name, roll, grade, phone, guardPhone, addr, email, notes, remarks ->
                viewModel.updateStudentProfile(name, roll, grade, phone, guardPhone, addr, email, notes, remarks)
                showEditProfileDialog = false
            }
        )
    }

    // Dialog: Add Subject Marks (Educator Action)
    if (showAddMarkDialog) {
        AddSubjectMarkDialog(
            onDismiss = { showAddMarkDialog = false },
            onSave = { subject, obtained, total, term, remarks ->
                viewModel.addStudentMark(subject, obtained, total, term, remarks)
                showAddMarkDialog = false
            }
        )
    }

    // Dialog: Record Attendance (Educator Action)
    if (showRecordAttendanceDialog) {
        RecordAttendanceDialog(
            onDismiss = { showRecordAttendanceDialog = false },
            onSave = { status, session, educator, date ->
                viewModel.recordAttendance(status, session, educator, date)
                showRecordAttendanceDialog = false
            }
        )
    }
}

@Composable
fun EditStudentProfileDialog(
    student: StudentProfileEntity,
    onDismiss: () -> Unit,
    onSave: (
        name: String,
        roll: String,
        grade: String,
        phone: String,
        guardPhone: String,
        addr: String,
        email: String,
        notes: String,
        remarks: String
    ) -> Unit
) {
    var name by remember { mutableStateOf(student.name) }
    var rollNo by remember { mutableStateOf(student.rollNo) }
    var grade by remember { mutableStateOf(student.grade) }
    var phone by remember { mutableStateOf(student.phone) }
    var guardianPhone by remember { mutableStateOf(student.guardianPhone) }
    var address by remember { mutableStateOf(student.address) }
    var email by remember { mutableStateOf(student.email) }
    var attendanceNotes by remember { mutableStateOf(student.attendanceNotes) }
    var mentorRemarks by remember { mutableStateOf(student.mentorRemarks) }

    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = DarkCardBackground,
        titleContentColor = GoldPrimary,
        textContentColor = TextPrimaryGold,
        title = {
            Text(
                text = "Edit Student Profile",
                fontWeight = FontWeight.Bold,
                fontSize = 17.sp
            )
        },
        text = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Student Full Name", color = GoldLight) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = GoldPrimary,
                        unfocusedBorderColor = GoldBorder,
                        focusedTextColor = TextPrimaryGold,
                        unfocusedTextColor = TextPrimaryGold
                    )
                )

                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = grade,
                        onValueChange = { grade = it },
                        label = { Text("Grade / Class", color = GoldLight) },
                        modifier = Modifier.weight(1f),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = GoldPrimary,
                            unfocusedBorderColor = GoldBorder,
                            focusedTextColor = TextPrimaryGold,
                            unfocusedTextColor = TextPrimaryGold
                        )
                    )
                    OutlinedTextField(
                        value = rollNo,
                        onValueChange = { rollNo = it },
                        label = { Text("Roll No", color = GoldLight) },
                        modifier = Modifier.weight(1f),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = GoldPrimary,
                            unfocusedBorderColor = GoldBorder,
                            focusedTextColor = TextPrimaryGold,
                            unfocusedTextColor = TextPrimaryGold
                        )
                    )
                }

                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = phone,
                        onValueChange = { phone = it },
                        label = { Text("Student Phone", color = GoldLight) },
                        modifier = Modifier.weight(1f),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = GoldPrimary,
                            unfocusedBorderColor = GoldBorder,
                            focusedTextColor = TextPrimaryGold,
                            unfocusedTextColor = TextPrimaryGold
                        )
                    )
                    OutlinedTextField(
                        value = guardianPhone,
                        onValueChange = { guardianPhone = it },
                        label = { Text("Guardian Phone", color = GoldLight) },
                        modifier = Modifier.weight(1f),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = GoldPrimary,
                            unfocusedBorderColor = GoldBorder,
                            focusedTextColor = TextPrimaryGold,
                            unfocusedTextColor = TextPrimaryGold
                        )
                    )
                }

                OutlinedTextField(
                    value = address,
                    onValueChange = { address = it },
                    label = { Text("Surat Residential Address", color = GoldLight) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = GoldPrimary,
                        unfocusedBorderColor = GoldBorder,
                        focusedTextColor = TextPrimaryGold,
                        unfocusedTextColor = TextPrimaryGold
                    )
                )

                OutlinedTextField(
                    value = mentorRemarks,
                    onValueChange = { mentorRemarks = it },
                    label = { Text("Mentor Remarks (Surat Faculty)", color = GoldLight) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = GoldPrimary,
                        unfocusedBorderColor = GoldBorder,
                        focusedTextColor = TextPrimaryGold,
                        unfocusedTextColor = TextPrimaryGold
                    )
                )
            }
        },
        confirmButton = {
            GoldButton(
                text = "Save Changes",
                enabled = name.isNotBlank(),
                onClick = {
                    onSave(name, rollNo, grade, phone, guardianPhone, address, email, attendanceNotes, mentorRemarks)
                }
            )
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel", color = TextSecondaryMuted)
            }
        }
    )
}

@Composable
fun AddSubjectMarkDialog(
    onDismiss: () -> Unit,
    onSave: (subject: String, obtained: Float, total: Float, term: String, remarks: String) -> Unit
) {
    var subject by remember { mutableStateOf("Hindi Literature & Grammar") }
    var obtainedText by remember { mutableStateOf("") }
    var totalText by remember { mutableStateOf("50") }
    var term by remember { mutableStateOf("Monthly Assessment") }
    var remarks by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = DarkCardBackground,
        titleContentColor = GoldPrimary,
        textContentColor = TextPrimaryGold,
        title = {
            Text(
                text = "Add Student Subject Marks",
                fontWeight = FontWeight.Bold,
                fontSize = 17.sp
            )
        },
        text = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = subject,
                    onValueChange = { subject = it },
                    label = { Text("Subject Name", color = GoldLight) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = GoldPrimary,
                        unfocusedBorderColor = GoldBorder,
                        focusedTextColor = TextPrimaryGold,
                        unfocusedTextColor = TextPrimaryGold
                    )
                )

                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = obtainedText,
                        onValueChange = { obtainedText = it },
                        label = { Text("Marks Obtained", color = GoldLight) },
                        modifier = Modifier.weight(1f),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = GoldPrimary,
                            unfocusedBorderColor = GoldBorder,
                            focusedTextColor = TextPrimaryGold,
                            unfocusedTextColor = TextPrimaryGold
                        )
                    )
                    OutlinedTextField(
                        value = totalText,
                        onValueChange = { totalText = it },
                        label = { Text("Total Marks", color = GoldLight) },
                        modifier = Modifier.weight(1f),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = GoldPrimary,
                            unfocusedBorderColor = GoldBorder,
                            focusedTextColor = TextPrimaryGold,
                            unfocusedTextColor = TextPrimaryGold
                        )
                    )
                }

                OutlinedTextField(
                    value = term,
                    onValueChange = { term = it },
                    label = { Text("Exam / Evaluation Term", color = GoldLight) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = GoldPrimary,
                        unfocusedBorderColor = GoldBorder,
                        focusedTextColor = TextPrimaryGold,
                        unfocusedTextColor = TextPrimaryGold
                    )
                )

                OutlinedTextField(
                    value = remarks,
                    onValueChange = { remarks = it },
                    label = { Text("Educator Remarks", color = GoldLight) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = GoldPrimary,
                        unfocusedBorderColor = GoldBorder,
                        focusedTextColor = TextPrimaryGold,
                        unfocusedTextColor = TextPrimaryGold
                    )
                )
            }
        },
        confirmButton = {
            GoldButton(
                text = "Save Marks",
                enabled = subject.isNotBlank() && obtainedText.toFloatOrNull() != null && totalText.toFloatOrNull() != null,
                onClick = {
                    val obt = obtainedText.toFloatOrNull() ?: 0f
                    val tot = totalText.toFloatOrNull() ?: 100f
                    onSave(subject, obt, tot, term, remarks)
                }
            )
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel", color = TextSecondaryMuted)
            }
        }
    )
}

@Composable
fun RecordAttendanceDialog(
    onDismiss: () -> Unit,
    onSave: (status: String, session: String, educator: String, date: String) -> Unit
) {
    var status by remember { mutableStateOf("Present") }
    var session by remember { mutableStateOf("Hindi Literature & Grammar Class") }
    var educator by remember { mutableStateOf("Khwaab Hindi Faculty (Surat)") }
    var dateString by remember { mutableStateOf("Sep 12, 2026") }

    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = DarkCardBackground,
        titleContentColor = GoldPrimary,
        textContentColor = TextPrimaryGold,
        title = {
            Text(
                text = "Record Session Attendance",
                fontWeight = FontWeight.Bold,
                fontSize = 17.sp
            )
        },
        text = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = session,
                    onValueChange = { session = it },
                    label = { Text("Subject / Class Session", color = GoldLight) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = GoldPrimary,
                        unfocusedBorderColor = GoldBorder,
                        focusedTextColor = TextPrimaryGold,
                        unfocusedTextColor = TextPrimaryGold
                    )
                )

                OutlinedTextField(
                    value = educator,
                    onValueChange = { educator = it },
                    label = { Text("Educator Name", color = GoldLight) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = GoldPrimary,
                        unfocusedBorderColor = GoldBorder,
                        focusedTextColor = TextPrimaryGold,
                        unfocusedTextColor = TextPrimaryGold
                    )
                )

                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = dateString,
                        onValueChange = { dateString = it },
                        label = { Text("Date", color = GoldLight) },
                        modifier = Modifier.weight(1f),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = GoldPrimary,
                            unfocusedBorderColor = GoldBorder,
                            focusedTextColor = TextPrimaryGold,
                            unfocusedTextColor = TextPrimaryGold
                        )
                    )

                    OutlinedTextField(
                        value = status,
                        onValueChange = { status = it },
                        label = { Text("Status (Present/Absent/Excused)", color = GoldLight, fontSize = 11.sp) },
                        modifier = Modifier.weight(1f),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = GoldPrimary,
                            unfocusedBorderColor = GoldBorder,
                            focusedTextColor = TextPrimaryGold,
                            unfocusedTextColor = TextPrimaryGold
                        )
                    )
                }
            }
        },
        confirmButton = {
            GoldButton(
                text = "Mark Record",
                enabled = session.isNotBlank() && educator.isNotBlank(),
                onClick = {
                    onSave(status, session, educator, dateString)
                }
            )
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel", color = TextSecondaryMuted)
            }
        }
    )
}
