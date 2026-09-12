package com.example.ui.screens

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
import androidx.compose.material.icons.filled.Assignment
import androidx.compose.material.icons.filled.AutoGraph
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Flag
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.AcademicGoalEntity
import com.example.data.AssessmentEntity
import com.example.data.SubjectEntity
import com.example.ui.AcademicSummary
import com.example.ui.AcademicViewModel
import com.example.ui.SubjectPerformance
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
import com.example.ui.theme.GoldGradientBrush
import com.example.ui.theme.GoldLight
import com.example.ui.theme.GoldPrimary
import com.example.ui.theme.GoldSecondary
import com.example.ui.theme.PureBlack
import com.example.ui.theme.TextPrimaryGold
import com.example.ui.theme.TextSecondaryMuted

@Composable
fun AcademicTrackerScreen(
    viewModel: AcademicViewModel,
    summary: AcademicSummary,
    subjectPerformances: List<SubjectPerformance>,
    assessments: List<AssessmentEntity>,
    goals: List<AcademicGoalEntity>,
    subjects: List<SubjectEntity>,
    onInfoClick: () -> Unit,
    onCallClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var showAddAssessmentDialog by remember { mutableStateOf(false) }
    var showAddGoalDialog by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(top = 12.dp, bottom = 88.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Surat Location & Foundation Banner
        item {
            SuratCenterNoticeBanner(
                onInfoClick = onInfoClick,
                onCallClick = onCallClick
            )
        }

        // Overview KPI Card
        item {
            GoldCard(
                modifier = Modifier.testTag("academic_overview_card")
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
                        Column {
                            Text(
                                text = "Academic Progress Dashboard",
                                color = GoldLight,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "Khwaab Foundation • Sayedpura Education Centre",
                                color = TextSecondaryMuted,
                                fontSize = 11.sp
                            )
                        }

                        GoldBadge(
                            text = "Class 10",
                            isPrimary = true
                        )
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        // Overall Average Box
                        Surface(
                            color = DarkSurfaceVariant,
                            shape = RoundedCornerShape(12.dp),
                            border = BorderStroke(1.dp, GoldBorder),
                            modifier = Modifier.weight(1f)
                        ) {
                            Column(
                                modifier = Modifier.padding(12.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = Icons.Default.AutoGraph,
                                        contentDescription = null,
                                        tint = GoldPrimary,
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(
                                        text = "${String.format("%.1f", summary.overallAverage)}%",
                                        color = GoldPrimary,
                                        fontSize = 22.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                                Text(
                                    text = "Overall Average",
                                    color = TextSecondaryMuted,
                                    fontSize = 11.sp
                                )
                            }
                        }

                        // Completed Goals Box
                        Surface(
                            color = DarkSurfaceVariant,
                            shape = RoundedCornerShape(12.dp),
                            border = BorderStroke(1.dp, GoldBorder),
                            modifier = Modifier.weight(1f)
                        ) {
                            Column(
                                modifier = Modifier.padding(12.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = Icons.Default.Flag,
                                        contentDescription = null,
                                        tint = GoldSecondary,
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(
                                        text = "${summary.completedGoals} / ${summary.totalGoals}",
                                        color = GoldLight,
                                        fontSize = 22.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                                Text(
                                    text = "Milestones Met",
                                    color = TextSecondaryMuted,
                                    fontSize = 11.sp
                                )
                            }
                        }
                    }

                    // Progress bar
                    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "Cumulative Academic Standing",
                                color = TextPrimaryGold,
                                fontSize = 11.5.sp
                            )
                            Text(
                                text = "Target: 85%",
                                color = GoldPrimary,
                                fontSize = 11.5.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        GoldProgressBar(progress = summary.overallAverage / 100f)
                    }
                }
            }
        }

        // Subjects Section Header
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.School,
                        contentDescription = null,
                        tint = GoldPrimary,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Curriculum Subjects (${subjectPerformances.size})",
                        color = GoldLight,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                }
            }
        }

        // Subjects Cards
        items(subjectPerformances, key = { it.subject.id }) { perf ->
            GoldCard(
                modifier = Modifier.testTag("subject_card_${perf.subject.id}")
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
                                text = perf.subject.name,
                                color = GoldLight,
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "Faculty: ${perf.subject.teacherName}",
                                color = TextSecondaryMuted,
                                fontSize = 11.5.sp
                            )
                        }

                        Surface(
                            color = if (perf.isTargetMet) Color(0x334CAF50) else GoldContainer,
                            shape = RoundedCornerShape(8.dp),
                            border = BorderStroke(1.dp, if (perf.isTargetMet) Color(0xFF4CAF50) else GoldBorder)
                        ) {
                            Text(
                                text = if (perf.assessmentCount > 0) "${String.format("%.1f", perf.averagePercentage)}%" else "New",
                                color = if (perf.isTargetMet) Color(0xFF81C784) else GoldPrimary,
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }
                    }

                    GoldProgressBar(progress = perf.averagePercentage / 100f)

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Target: ${perf.subject.targetPercentage.toInt()}% • ${perf.assessmentCount} tests",
                            color = TextSecondaryMuted,
                            fontSize = 11.sp
                        )
                        if (perf.assessmentCount > 0) {
                            Text(
                                text = "Highest: ${String.format("%.1f", perf.highestScorePercentage)}%",
                                color = GoldLight,
                                fontSize = 11.sp
                            )
                        }
                    }
                }
            }
        }

        // Assessments & Tests Header
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Assignment,
                        contentDescription = null,
                        tint = GoldPrimary,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Evaluations & Tests (${assessments.size})",
                        color = GoldLight,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                }

                OutlinedButton(
                    onClick = { showAddAssessmentDialog = true },
                    shape = RoundedCornerShape(8.dp),
                    border = BorderStroke(1.dp, GoldBorderLight),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = GoldPrimary),
                    modifier = Modifier.testTag("add_assessment_button")
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Add Test", fontSize = 12.sp)
                }
            }
        }

        // Assessments items
        items(assessments, key = { it.id }) { assess ->
            GoldCard(
                modifier = Modifier.testTag("assessment_card_${assess.id}")
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = assess.title,
                                color = GoldLight,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "${assess.subjectName} • ${assess.dateString}",
                                color = TextSecondaryMuted,
                                fontSize = 11.sp
                            )
                        }

                        Surface(
                            color = GoldContainer,
                            shape = RoundedCornerShape(8.dp),
                            border = BorderStroke(1.dp, GoldBorder)
                        ) {
                            Text(
                                text = "${assess.score} / ${assess.maxScore} (${String.format("%.1f", assess.percentage)}%)",
                                color = GoldPrimary,
                                fontWeight = FontWeight.Bold,
                                fontSize = 12.sp,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }
                    }

                    if (assess.notes.isNotBlank()) {
                        Text(
                            text = "Teacher Feedback: ${assess.notes}",
                            color = TextPrimaryGold,
                            fontSize = 11.5.sp
                        )
                    }
                }
            }
        }

        // Goals Header
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Flag,
                        contentDescription = null,
                        tint = GoldPrimary,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Academic Goals & Milestones",
                        color = GoldLight,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                }

                OutlinedButton(
                    onClick = { showAddGoalDialog = true },
                    shape = RoundedCornerShape(8.dp),
                    border = BorderStroke(1.dp, GoldBorderLight),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = GoldPrimary),
                    modifier = Modifier.testTag("add_goal_button")
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("New Goal", fontSize = 12.sp)
                }
            }
        }

        // Goals List
        items(goals, key = { it.id }) { goal ->
            GoldCard(
                modifier = Modifier.testTag("goal_card_${goal.id}")
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = goal.isCompleted,
                        onCheckedChange = { viewModel.toggleGoal(goal) },
                        colors = CheckboxDefaults.colors(
                            checkedColor = GoldPrimary,
                            uncheckedColor = GoldBorder
                        )
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = goal.title,
                            color = if (goal.isCompleted) TextSecondaryMuted else GoldLight,
                            fontSize = 13.5.sp,
                            fontWeight = if (goal.isCompleted) FontWeight.Normal else FontWeight.SemiBold
                        )
                        Text(
                            text = "Discipline: ${goal.category} • Target: ${goal.targetDate}",
                            color = TextSecondaryMuted,
                            fontSize = 11.sp
                        )
                    }
                }
            }
        }
    }

    if (showAddAssessmentDialog) {
        AddAssessmentDialog(
            subjects = subjects,
            onDismiss = { showAddAssessmentDialog = false },
            onAdd = { subject, title, score, max, date, notes ->
                viewModel.addAssessment(subject, title, score, max, date, notes)
                showAddAssessmentDialog = false
            }
        )
    }

    if (showAddGoalDialog) {
        AddGoalDialog(
            onDismiss = { showAddGoalDialog = false },
            onAdd = { title, cat, date ->
                viewModel.addGoal(title, cat, date)
                showAddGoalDialog = false
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddAssessmentDialog(
    subjects: List<SubjectEntity>,
    onDismiss: () -> Unit,
    onAdd: (SubjectEntity, String, Float, Float, String, String) -> Unit
) {
    var selectedSubject by remember { mutableStateOf(subjects.firstOrNull()) }
    var title by remember { mutableStateOf("") }
    var scoreText by remember { mutableStateOf("") }
    var maxScoreText by remember { mutableStateOf("50") }
    var dateString by remember { mutableStateOf("Sep 12, 2026") }
    var notes by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }

    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = DarkCardBackground,
        titleContentColor = GoldPrimary,
        textContentColor = TextPrimaryGold,
        title = {
            Text(
                text = "Log Academic Evaluation",
                fontWeight = FontWeight.Bold,
                fontSize = 17.sp
            )
        },
        text = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Subject Dropdown
                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = { expanded = !expanded }
                ) {
                    OutlinedTextField(
                        value = selectedSubject?.name ?: "Select Subject",
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Subject", color = GoldLight) },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = GoldPrimary,
                            unfocusedBorderColor = GoldBorder,
                            focusedTextColor = TextPrimaryGold,
                            unfocusedTextColor = TextPrimaryGold
                        )
                    )
                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false },
                        modifier = Modifier.background(DarkCardBackground)
                    ) {
                        subjects.forEach { sub ->
                            DropdownMenuItem(
                                text = { Text(sub.name, color = TextPrimaryGold) },
                                onClick = {
                                    selectedSubject = sub
                                    expanded = false
                                }
                            )
                        }
                    }
                }

                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Evaluation / Test Title", color = GoldLight) },
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
                        value = scoreText,
                        onValueChange = { scoreText = it },
                        label = { Text("Score", color = GoldLight) },
                        modifier = Modifier.weight(1f),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = GoldPrimary,
                            unfocusedBorderColor = GoldBorder,
                            focusedTextColor = TextPrimaryGold,
                            unfocusedTextColor = TextPrimaryGold
                        )
                    )
                    OutlinedTextField(
                        value = maxScoreText,
                        onValueChange = { maxScoreText = it },
                        label = { Text("Max Score", color = GoldLight) },
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
                    value = notes,
                    onValueChange = { notes = it },
                    label = { Text("Teacher Feedback / Remarks", color = GoldLight) },
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
                text = "Add Evaluation",
                enabled = selectedSubject != null && title.isNotBlank() && scoreText.toFloatOrNull() != null,
                onClick = {
                    val sub = selectedSubject ?: return@GoldButton
                    val s = scoreText.toFloatOrNull() ?: 0f
                    val m = maxScoreText.toFloatOrNull() ?: 50f
                    onAdd(sub, title, s, m, dateString, notes)
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
fun AddGoalDialog(
    onDismiss: () -> Unit,
    onAdd: (title: String, category: String, date: String) -> Unit
) {
    var title by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("Hindi") }
    var date by remember { mutableStateOf("Oct 15, 2026") }

    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = DarkCardBackground,
        titleContentColor = GoldPrimary,
        textContentColor = TextPrimaryGold,
        title = {
            Text(
                text = "Add Academic Goal",
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
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Goal / Target Description", color = GoldLight) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = GoldPrimary,
                        unfocusedBorderColor = GoldBorder,
                        focusedTextColor = TextPrimaryGold,
                        unfocusedTextColor = TextPrimaryGold
                    )
                )

                OutlinedTextField(
                    value = category,
                    onValueChange = { category = it },
                    label = { Text("Discipline / Subject", color = GoldLight) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = GoldPrimary,
                        unfocusedBorderColor = GoldBorder,
                        focusedTextColor = TextPrimaryGold,
                        unfocusedTextColor = TextPrimaryGold
                    )
                )

                OutlinedTextField(
                    value = date,
                    onValueChange = { date = it },
                    label = { Text("Target Completion Date", color = GoldLight) },
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
                text = "Set Goal",
                enabled = title.isNotBlank(),
                onClick = { onAdd(title, category, date) }
            )
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel", color = TextSecondaryMuted)
            }
        }
    )
}
