package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.WorkshopEntity
import com.example.ui.AcademicViewModel
import com.example.ui.components.GoldBadge
import com.example.ui.components.GoldButton
import com.example.ui.components.GoldCard
import com.example.ui.components.GoldOutlinedButton
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

data class CalendarDay(
    val dayOfMonth: Int,
    val dayOfWeek: String,
    val dateIso: String,
    val hasWorkshop: Boolean
)

@Composable
fun WorkshopCalendarScreen(
    viewModel: AcademicViewModel,
    workshops: List<WorkshopEntity>,
    selectedCategory: String,
    searchQuery: String,
    selectedDate: String?,
    onInfoClick: () -> Unit,
    onCallClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var showAddWorkshopDialog by remember { mutableStateOf(false) }

    val calendarDays = remember(workshops) {
        val workshopDates = workshops.map { it.dateIso }.toSet()
        listOf(
            CalendarDay(14, "Mon", "2026-09-14", false),
            CalendarDay(15, "Tue", "2026-09-15", workshopDates.contains("2026-09-15")),
            CalendarDay(16, "Wed", "2026-09-16", false),
            CalendarDay(17, "Thu", "2026-09-17", false),
            CalendarDay(18, "Fri", "2026-09-18", workshopDates.contains("2026-09-18")),
            CalendarDay(19, "Sat", "2026-09-19", false),
            CalendarDay(20, "Sun", "2026-09-20", false),
            CalendarDay(21, "Mon", "2026-09-21", false),
            CalendarDay(22, "Tue", "2026-09-22", workshopDates.contains("2026-09-22")),
            CalendarDay(23, "Wed", "2026-09-23", false),
            CalendarDay(24, "Thu", "2026-09-24", false),
            CalendarDay(25, "Fri", "2026-09-25", false),
            CalendarDay(26, "Sat", "2026-09-26", workshopDates.contains("2026-09-26")),
            CalendarDay(27, "Sun", "2026-09-27", false),
            CalendarDay(28, "Mon", "2026-09-28", false),
            CalendarDay(29, "Tue", "2026-09-29", false),
            CalendarDay(30, "Wed", "2026-09-30", false),
            CalendarDay(1, "Thu", "2026-10-01", false),
            CalendarDay(2, "Fri", "2026-10-02", workshopDates.contains("2026-10-02"))
        )
    }

    val categories = listOf("All", "Registered", "Hindi Focused", "Mathematics", "Science", "Pedagogy")

    Box(modifier = modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            contentPadding = PaddingValues(top = 12.dp, bottom = 88.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Surat Centre Identity Banner
            item {
                SuratCenterNoticeBanner(
                    onInfoClick = onInfoClick,
                    onCallClick = onCallClick
                )
            }

            // Calendar Section Header
            item {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.CalendarMonth,
                                contentDescription = null,
                                tint = GoldPrimary,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Workshop Calendar",
                                color = GoldLight,
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp
                            )
                        }

                        if (selectedDate != null) {
                            TextButton(
                                onClick = { viewModel.selectCalendarDate(null) },
                                modifier = Modifier.testTag("clear_calendar_filter_button")
                            ) {
                                Text(
                                    text = "Show All Dates",
                                    color = GoldPrimary,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                        }
                    }

                    Text(
                        text = "September – October 2026 • Khwaab Sayedpura Academic Schedule",
                        color = TextSecondaryMuted,
                        fontSize = 12.sp
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    // Calendar Horizontal Strip
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        items(calendarDays) { calDay ->
                            val isSelected = selectedDate == calDay.dateIso
                            val hasWorkshop = calDay.hasWorkshop

                            Surface(
                                modifier = Modifier
                                    .width(54.dp)
                                    .clip(RoundedCornerShape(12.dp))
                                    .clickable {
                                        if (isSelected) {
                                            viewModel.selectCalendarDate(null)
                                        } else {
                                            viewModel.selectCalendarDate(calDay.dateIso)
                                        }
                                    }
                                    .testTag("calendar_day_${calDay.dayOfMonth}"),
                                color = when {
                                    isSelected -> GoldPrimary
                                    hasWorkshop -> GoldContainer
                                    else -> DarkCardBackground
                                },
                                border = BorderStroke(
                                    width = if (isSelected || hasWorkshop) 1.5.dp else 1.dp,
                                    color = if (isSelected) GoldPrimary else if (hasWorkshop) GoldBorderLight else GoldBorder
                                )
                            ) {
                                Column(
                                    modifier = Modifier.padding(vertical = 10.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(
                                        text = calDay.dayOfWeek,
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Medium,
                                        color = if (isSelected) PureBlack else TextSecondaryMuted
                                    )
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = calDay.dayOfMonth.toString(),
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = if (isSelected) PureBlack else GoldLight
                                    )
                                    Spacer(modifier = Modifier.height(4.dp))
                                    if (hasWorkshop) {
                                        Box(
                                            modifier = Modifier
                                                .size(6.dp)
                                                .clip(CircleShape)
                                                .background(if (isSelected) PureBlack else GoldPrimary)
                                        )
                                    } else {
                                        Spacer(modifier = Modifier.height(6.dp))
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // Search & Category Filters
            item {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    // Search Bar
                    OutlinedTextField(
                        value = searchQuery,
                        onValueChange = { viewModel.updateWorkshopSearch(it) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("workshop_search_input"),
                        placeholder = {
                            Text(
                                "Search workshops, topics, Hindi faculty...",
                                color = TextSecondaryMuted,
                                fontSize = 13.sp
                            )
                        },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = "Search",
                                tint = GoldPrimary
                            )
                        },
                        trailingIcon = {
                            if (searchQuery.isNotBlank()) {
                                IconButton(onClick = { viewModel.updateWorkshopSearch("") }) {
                                    Icon(
                                        imageVector = Icons.Default.Close,
                                        contentDescription = "Clear",
                                        tint = TextSecondaryMuted
                                    )
                                }
                            }
                        },
                        singleLine = true,
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = GoldPrimary,
                            unfocusedBorderColor = GoldBorder,
                            focusedTextColor = TextPrimaryGold,
                            unfocusedTextColor = TextPrimaryGold,
                            cursorColor = GoldPrimary,
                            focusedContainerColor = DarkCardBackground,
                            unfocusedContainerColor = DarkCardBackground
                        )
                    )

                    // Categories Scrollable Row
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .horizontalScroll(rememberScrollState()),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        categories.forEach { cat ->
                            val isSelected = selectedCategory == cat
                            FilterChip(
                                selected = isSelected,
                                onClick = { viewModel.selectWorkshopCategory(cat) },
                                label = {
                                    Text(
                                        text = cat,
                                        fontSize = 12.sp,
                                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                                    )
                                },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = GoldPrimary,
                                    selectedLabelColor = PureBlack,
                                    containerColor = DarkCardBackground,
                                    labelColor = GoldLight
                                ),
                                border = BorderStroke(
                                    width = 1.dp,
                                    color = if (isSelected) GoldPrimary else GoldBorder
                                ),
                                shape = RoundedCornerShape(20.dp),
                                modifier = Modifier.testTag("workshop_category_$cat")
                            )
                        }
                    }
                }
            }

            // Workshop List Header
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = if (selectedDate != null) "Workshops on $selectedDate" else "Upcoming Educational Workshops (${workshops.size})",
                        color = GoldLight,
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp
                    )

                    OutlinedButton(
                        onClick = { showAddWorkshopDialog = true },
                        shape = RoundedCornerShape(8.dp),
                        border = BorderStroke(1.dp, GoldBorderLight),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = GoldPrimary),
                        modifier = Modifier.testTag("educator_add_workshop_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Add Workshop", fontSize = 12.sp)
                    }
                }
            }

            if (workshops.isEmpty()) {
                item {
                    GoldCard {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(24.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                imageVector = Icons.Default.Event,
                                contentDescription = null,
                                tint = GoldPrimary,
                                modifier = Modifier.size(36.dp)
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                            Text(
                                text = "No workshops found for this filter",
                                color = GoldLight,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 14.sp
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = "Try resetting your search query or calendar date filter.",
                                color = TextSecondaryMuted,
                                fontSize = 12.sp
                            )
                        }
                    }
                }
            } else {
                items(workshops, key = { it.id }) { workshop ->
                    WorkshopCard(
                        workshop = workshop,
                        onToggleEnroll = { viewModel.toggleWorkshopEnrollment(workshop) }
                    )
                }
            }
        }
    }

    if (showAddWorkshopDialog) {
        AddWorkshopDialog(
            onDismiss = { showAddWorkshopDialog = false },
            onAdd = { title, topic, instructor, dateIso, formattedDate, time, venue, desc, isHindi, cap ->
                viewModel.addWorkshop(title, topic, instructor, dateIso, formattedDate, time, venue, desc, isHindi, cap)
                showAddWorkshopDialog = false
            }
        )
    }
}

@Composable
fun WorkshopCard(
    workshop: WorkshopEntity,
    onToggleEnroll: () -> Unit,
    modifier: Modifier = Modifier
) {
    GoldCard(
        modifier = modifier.testTag("workshop_card_${workshop.id}")
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            // Badges row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    GoldBadge(
                        text = workshop.topic,
                        isPrimary = false
                    )
                    if (workshop.isHindiFocused) {
                        GoldBadge(
                            text = "Hindi Focused",
                            isPrimary = true,
                            icon = Icons.Default.Star
                        )
                    }
                }

                if (workshop.isEnrolled) {
                    Surface(
                        color = Color(0x334CAF50),
                        shape = RoundedCornerShape(12.dp),
                        border = BorderStroke(1.dp, Color(0xFF4CAF50))
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.CheckCircle,
                                contentDescription = null,
                                tint = Color(0xFF81C784),
                                modifier = Modifier.size(13.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "Enrolled",
                                color = Color(0xFF81C784),
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }

            // Title
            Text(
                text = workshop.title,
                color = GoldLight,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                lineHeight = 21.sp
            )

            // Instructor & Schedule
            Column(verticalArrangement = Arrangement.spacedBy(5.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "Instructor",
                        tint = GoldPrimary,
                        modifier = Modifier.size(15.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "Instructor: ${workshop.instructor}",
                        color = TextPrimaryGold,
                        fontSize = 12.5.sp,
                        fontWeight = FontWeight.Medium
                    )
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.DateRange,
                        contentDescription = "Date",
                        tint = GoldSecondary,
                        modifier = Modifier.size(15.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "${workshop.formattedDate} • ${workshop.timeString}",
                        color = TextPrimaryGold,
                        fontSize = 12.sp
                    )
                }

                Row(verticalAlignment = Alignment.Top) {
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = "Venue",
                        tint = GoldSecondary,
                        modifier = Modifier
                            .size(15.dp)
                            .padding(top = 1.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = workshop.venue,
                        color = TextSecondaryMuted,
                        fontSize = 11.5.sp,
                        lineHeight = 15.sp
                    )
                }
            }

            // Description
            Text(
                text = workshop.description,
                color = TextSecondaryMuted,
                fontSize = 12.5.sp,
                lineHeight = 17.sp
            )

            // Capacity & Registration Bar
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                val fillRatio = if (workshop.capacity > 0) workshop.registeredCount.toFloat() / workshop.capacity.toFloat() else 0f
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Seats Status: ${workshop.seatsLeft} available (${workshop.registeredCount}/${workshop.capacity} filled)",
                        color = if (workshop.seatsLeft <= 5) Color(0xFFFF9800) else GoldLight,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
                GoldProgressBar(progress = fillRatio)
            }

            // Action Button
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                if (workshop.isEnrolled) {
                    GoldOutlinedButton(
                        text = "Cancel Registration",
                        onClick = onToggleEnroll,
                        modifier = Modifier.testTag("cancel_enroll_button_${workshop.id}")
                    )
                } else {
                    GoldButton(
                        text = "Register for Workshop",
                        icon = Icons.Default.Check,
                        onClick = onToggleEnroll,
                        modifier = Modifier.testTag("enroll_button_${workshop.id}")
                    )
                }
            }
        }
    }
}

@Composable
fun AddWorkshopDialog(
    onDismiss: () -> Unit,
    onAdd: (
        title: String,
        topic: String,
        instructor: String,
        dateIso: String,
        formattedDate: String,
        time: String,
        venue: String,
        desc: String,
        isHindi: Boolean,
        capacity: Int
    ) -> Unit
) {
    var title by remember { mutableStateOf("") }
    var topic by remember { mutableStateOf("Hindi Education") }
    var instructor by remember { mutableStateOf("Khwaab Hindi Faculty (Surat)") }
    var dateIso by remember { mutableStateOf("2026-09-25") }
    var formattedDate by remember { mutableStateOf("Fri, Sep 25, 2026") }
    var timeString by remember { mutableStateOf("11:00 AM - 01:00 PM") }
    var venue by remember { mutableStateOf("2nd Floor, Pumping Char Rasta, above Mubin Cycle Wala, Sayedpura, Surat") }
    var description by remember { mutableStateOf("") }
    var isHindiFocused by remember { mutableStateOf(true) }
    var capacityText by remember { mutableStateOf("30") }

    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = DarkCardBackground,
        titleContentColor = GoldPrimary,
        textContentColor = TextPrimaryGold,
        title = {
            Text(
                text = "Add Educational Workshop",
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
                    label = { Text("Workshop Title", color = GoldLight) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = GoldPrimary,
                        unfocusedBorderColor = GoldBorder,
                        focusedTextColor = TextPrimaryGold,
                        unfocusedTextColor = TextPrimaryGold
                    )
                )

                OutlinedTextField(
                    value = topic,
                    onValueChange = { topic = it },
                    label = { Text("Topic / Discipline", color = GoldLight) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = GoldPrimary,
                        unfocusedBorderColor = GoldBorder,
                        focusedTextColor = TextPrimaryGold,
                        unfocusedTextColor = TextPrimaryGold
                    )
                )

                OutlinedTextField(
                    value = instructor,
                    onValueChange = { instructor = it },
                    label = { Text("Instructor / Speaker Name", color = GoldLight) },
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
                        value = dateIso,
                        onValueChange = {
                            dateIso = it
                            formattedDate = it
                        },
                        label = { Text("Date (YYYY-MM-DD)", color = GoldLight, fontSize = 11.sp) },
                        modifier = Modifier.weight(1f),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = GoldPrimary,
                            unfocusedBorderColor = GoldBorder,
                            focusedTextColor = TextPrimaryGold,
                            unfocusedTextColor = TextPrimaryGold
                        )
                    )
                    OutlinedTextField(
                        value = timeString,
                        onValueChange = { timeString = it },
                        label = { Text("Time Slot", color = GoldLight, fontSize = 11.sp) },
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
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Description & Syllabus", color = GoldLight) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = GoldPrimary,
                        unfocusedBorderColor = GoldBorder,
                        focusedTextColor = TextPrimaryGold,
                        unfocusedTextColor = TextPrimaryGold
                    )
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Checkbox(
                        checked = isHindiFocused,
                        onCheckedChange = { isHindiFocused = it },
                        colors = CheckboxDefaults.colors(
                            checkedColor = GoldPrimary,
                            uncheckedColor = GoldBorder
                        )
                    )
                    Text(
                        text = "Hindi-Teaching / Language Focus",
                        color = GoldLight,
                        fontSize = 12.sp
                    )
                }
            }
        },
        confirmButton = {
            GoldButton(
                text = "Save Workshop",
                enabled = title.isNotBlank() && instructor.isNotBlank(),
                onClick = {
                    val cap = capacityText.toIntOrNull() ?: 30
                    onAdd(
                        title,
                        topic,
                        instructor,
                        dateIso,
                        formattedDate,
                        timeString,
                        venue,
                        description.ifBlank { "Educational workshop at Khwaab Foundation Surat Centre." },
                        isHindiFocused,
                        cap
                    )
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
