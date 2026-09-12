package com.example

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoGraph
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Stars
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.AcademicViewModel
import com.example.ui.components.SuratFoundationInfoDialog
import com.example.ui.screens.AcademicTrackerScreen
import com.example.ui.screens.StudentProfileScreen
import com.example.ui.screens.WorkshopCalendarScreen
import com.example.ui.theme.DarkCardBackground
import com.example.ui.theme.DarkSurfaceVariant
import com.example.ui.theme.GoldBorder
import com.example.ui.theme.GoldLight
import com.example.ui.theme.GoldPrimary
import com.example.ui.theme.GoldSecondary
import com.example.ui.theme.MyApplicationTheme
import com.example.ui.theme.PureBlack
import com.example.ui.theme.TextPrimaryGold
import com.example.ui.theme.TextSecondaryMuted

enum class AppTab(val title: String, val icon: ImageVector, val tag: String) {
    PROGRESS("Academic", Icons.Default.AutoGraph, "tab_academic_progress"),
    WORKSHOPS("Workshops", Icons.Default.DateRange, "tab_workshops_calendar"),
    PROFILE("Profile & Marks", Icons.Default.Person, "tab_student_profile")
}

class MainActivity : ComponentActivity() {
    private val viewModel: AcademicViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                KhwaabSuratApp(viewModel = viewModel)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun KhwaabSuratApp(
    viewModel: AcademicViewModel
) {
    val context = LocalContext.current
    var selectedTabIndex by remember { mutableIntStateOf(0) }
    var showInfoDialog by remember { mutableStateOf(false) }

    // State collections
    val studentProfile by viewModel.studentProfile.collectAsState()
    val studentMarks by viewModel.studentMarks.collectAsState()
    val attendanceLogs by viewModel.attendanceLogs.collectAsState()
    val subjects by viewModel.subjects.collectAsState()
    val assessments by viewModel.assessments.collectAsState()
    val filteredWorkshops by viewModel.filteredWorkshops.collectAsState()
    val allWorkshops by viewModel.workshops.collectAsState()
    val goals by viewModel.goals.collectAsState()
    val summary by viewModel.summary.collectAsState()
    val subjectPerformances by viewModel.subjectPerformances.collectAsState()
    val workshopSearchQuery by viewModel.workshopSearchQuery.collectAsState()
    val selectedWorkshopCategory by viewModel.selectedWorkshopCategory.collectAsState()
    val selectedCalendarDate by viewModel.selectedCalendarDate.collectAsState()

    val onCallFoundation = {
        val intent = Intent(Intent.ACTION_DIAL).apply {
            data = Uri.parse("tel:+917405068691")
        }
        context.startActivity(intent)
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = PureBlack,
        topBar = {
            TopAppBar(
                modifier = Modifier
                    .border(BorderStroke(0.5.dp, GoldBorder))
                    .testTag("app_top_bar"),
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = PureBlack,
                    titleContentColor = GoldLight,
                    actionIconContentColor = GoldPrimary
                ),
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(34.dp)
                                .clip(CircleShape)
                                .background(GoldPrimary),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.School,
                                contentDescription = "Khwaab Foundation Logo",
                                tint = PureBlack,
                                modifier = Modifier.size(20.dp)
                            )
                        }

                        Column {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = "KHWAAB FOUNDATION",
                                    color = GoldLight,
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Bold,
                                    letterSpacing = 0.8.sp
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Surface(
                                    color = Color(0x33F5C542),
                                    shape = RoundedCornerShape(4.dp),
                                    border = BorderStroke(0.5.dp, GoldPrimary)
                                ) {
                                    Text(
                                        text = "SURAT",
                                        color = GoldPrimary,
                                        fontSize = 9.sp,
                                        fontWeight = FontWeight.ExtraBold,
                                        modifier = Modifier.padding(horizontal = 4.dp, vertical = 1.dp)
                                    )
                                }
                            }
                            Text(
                                text = "Sayedpura Education Centre",
                                color = TextSecondaryMuted,
                                fontSize = 11.sp
                            )
                        }
                    }
                },
                actions = {
                    IconButton(
                        onClick = onCallFoundation,
                        modifier = Modifier.testTag("top_bar_call_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Call,
                            contentDescription = "Call Surat Centre",
                            tint = GoldPrimary
                        )
                    }

                    IconButton(
                        onClick = { showInfoDialog = true },
                        modifier = Modifier.testTag("top_bar_info_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Info,
                            contentDescription = "Surat Centre Details",
                            tint = GoldSecondary
                        )
                    }
                }
            )
        },
        bottomBar = {
            NavigationBar(
                containerColor = PureBlack,
                contentColor = GoldLight,
                tonalElevation = 8.dp,
                modifier = Modifier
                    .border(BorderStroke(0.5.dp, GoldBorder))
                    .testTag("app_navigation_bar")
            ) {
                AppTab.values().forEachIndexed { index, tab ->
                    val isSelected = selectedTabIndex == index
                    NavigationBarItem(
                        selected = isSelected,
                        onClick = { selectedTabIndex = index },
                        modifier = Modifier.testTag(tab.tag),
                        icon = {
                            if (tab == AppTab.WORKSHOPS) {
                                val enrolledCount = summary.enrolledWorkshopsCount
                                BadgedBox(
                                    badge = {
                                        if (enrolledCount > 0) {
                                            Badge(
                                                containerColor = GoldPrimary,
                                                contentColor = PureBlack
                                            ) {
                                                Text(
                                                    text = enrolledCount.toString(),
                                                    fontWeight = FontWeight.Bold,
                                                    fontSize = 10.sp
                                                )
                                            }
                                        }
                                    }
                                ) {
                                    Icon(
                                        imageVector = tab.icon,
                                        contentDescription = tab.title,
                                        modifier = Modifier.size(22.dp)
                                    )
                                }
                            } else {
                                Icon(
                                    imageVector = tab.icon,
                                    contentDescription = tab.title,
                                    modifier = Modifier.size(22.dp)
                                )
                            }
                        },
                        label = {
                            Text(
                                text = tab.title,
                                fontSize = 11.5.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                            )
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = PureBlack,
                            selectedTextColor = GoldPrimary,
                            indicatorColor = GoldPrimary,
                            unselectedIconColor = TextSecondaryMuted,
                            unselectedTextColor = TextSecondaryMuted
                        )
                    )
                }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(PureBlack)
        ) {
            when (selectedTabIndex) {
                0 -> AcademicTrackerScreen(
                    viewModel = viewModel,
                    summary = summary,
                    subjectPerformances = subjectPerformances,
                    assessments = assessments,
                    goals = goals,
                    subjects = subjects,
                    onInfoClick = { showInfoDialog = true },
                    onCallClick = onCallFoundation
                )
                1 -> WorkshopCalendarScreen(
                    viewModel = viewModel,
                    workshops = filteredWorkshops,
                    selectedCategory = selectedWorkshopCategory,
                    searchQuery = workshopSearchQuery,
                    selectedDate = selectedCalendarDate,
                    onInfoClick = { showInfoDialog = true },
                    onCallClick = onCallFoundation
                )
                2 -> StudentProfileScreen(
                    viewModel = viewModel,
                    student = studentProfile,
                    marks = studentMarks,
                    attendanceLogs = attendanceLogs,
                    onInfoClick = { showInfoDialog = true },
                    onCallClick = onCallFoundation
                )
            }
        }
    }

    if (showInfoDialog) {
        SuratFoundationInfoDialog(
            onDismiss = { showInfoDialog = false }
        )
    }
}
