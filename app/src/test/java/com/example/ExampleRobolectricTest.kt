package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.data.StudentProfileEntity
import com.example.data.StudentSubjectMarkEntity
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class ExampleRobolectricTest {

  @Test
  fun `read string from context`() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val appName = context.getString(R.string.app_name)
    assertEquals("Khwaab Surat", appName)
  }

  @Test
  fun `calculate student mark percentage correctly`() {
    val mark = StudentSubjectMarkEntity(
      studentId = 1L,
      subjectName = "Hindi Literature",
      marksObtained = 44f,
      totalMarks = 50f,
      term = "Term 1",
      examDate = "2026-09-10"
    )
    assertEquals(88f, mark.percentage, 0.01f)
    assertEquals("A", mark.gradeBand)
  }

  @Test
  fun `student attendance percentage calculates properly`() {
    val student = StudentProfileEntity(
      id = 1L,
      name = "Amina Sheikh",
      rollNo = "KH-SRT-2026-01",
      grade = "Grade 9 (Sayedpura Batch)",
      phone = "+91 98765 43210",
      guardianPhone = "+91 98765 11223",
      address = "Navi Chal, Sayedpura, Surat",
      email = "amina.khwaab@gmail.com",
      totalClasses = 40,
      attendedClasses = 38
    )
    assertEquals(95f, student.attendancePercentage, 0.01f)
    assertTrue(student.phone.isNotBlank())
  }
}
