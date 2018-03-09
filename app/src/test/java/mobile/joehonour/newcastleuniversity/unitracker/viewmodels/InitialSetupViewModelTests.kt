package mobile.joehonour.newcastleuniversity.unitracker.viewmodels

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import mobile.joehonour.newcastleuniversity.unitracker.model.InitialSetupDataModelValidator
import mobile.joehonour.newcastleuniversity.unitracker.support.FieldAssert
import mobile.joehonour.newcastleuniversity.unitracker.viewmodels.InitialSetupViewModelTester.Companion.initialSetupViewModelTester
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.ArgumentMatchers


class InitialSetupViewModelTests
{
    // allows live data to work outside of an android environment
    @Rule @JvmField
    val rule: TestRule = InstantTaskExecutorRule()

    @Test
    fun canBuildInitialSetupDataModelFromLiveDataSuccess()
    {
        val validator = mock<InitialSetupDataModelValidator> {
            on { validate(
                    "Newcastle",
                    2017,
                    3,
                    75,
                    50) } doReturn true }

        initialSetupViewModelTester(validator)
                .performActions {
                    universityName.value = "Newcastle"
                    yearStarted.value = 2017
                    courseLength.value = 3
                    targetPercentage.value = 75
                    totalCredits.value = 50 }
                .buildAndAssertInitialSetupData {
                    universityName = FieldAssert("Newcastle")
                    yearStarted = FieldAssert(2017)
                    courseLength = FieldAssert(3)
                    targetPercentage = FieldAssert(75)
                    totalCredits = FieldAssert(50)
                }
    }

    @Test
    fun buildingInitialSetupDataCorrectlyIncludesUniversityName()
    {
        val validator = mock<InitialSetupDataModelValidator> {
            on { validate(
                    ArgumentMatchers.eq("Edinburgh"),
                    ArgumentMatchers.any(),
                    ArgumentMatchers.any(),
                    ArgumentMatchers.any(),
                    ArgumentMatchers.any()) } doReturn true }

        initialSetupViewModelTester(validator)
                .fillAllFieldsWithDefaultValues()
                .performActions { universityName.value = "Edinburgh" }
                .buildAndAssertInitialSetupData {
                    universityName = FieldAssert("Edinburgh") }
    }

    @Test
    fun buildingInitialSetupDataCorrectlyIncludesYearStarted()
    {
        val validator = mock<InitialSetupDataModelValidator> {
            on { validate(
                    ArgumentMatchers.any(),
                    ArgumentMatchers.eq(2015),
                    ArgumentMatchers.any(),
                    ArgumentMatchers.any(),
                    ArgumentMatchers.any()) } doReturn true }

        initialSetupViewModelTester(validator)
                .fillAllFieldsWithDefaultValues()
                .performActions { yearStarted.value = 2015 }
                .buildAndAssertInitialSetupData {
                    yearStarted = FieldAssert(2015) }
    }

    @Test
    fun buildingInitialSetupDataCorrectlyIncludesCourseLength()
    {
        val validator = mock<InitialSetupDataModelValidator> {
            on { validate(
                    ArgumentMatchers.any(),
                    ArgumentMatchers.any(),
                    ArgumentMatchers.eq(4),
                    ArgumentMatchers.any(),
                    ArgumentMatchers.any()) } doReturn true }

        initialSetupViewModelTester(validator)
                .fillAllFieldsWithDefaultValues()
                .performActions { courseLength.value = 4 }
                .buildAndAssertInitialSetupData {
                    courseLength = FieldAssert(4) }
    }

    @Test
    fun buildingInitialSetupDataCorrectlyIncludesTargetPercentage()
    {
        val validator = mock<InitialSetupDataModelValidator> {
            on { validate(
                    ArgumentMatchers.any(),
                    ArgumentMatchers.any(),
                    ArgumentMatchers.any(),
                    ArgumentMatchers.eq(89),
                    ArgumentMatchers.any()) } doReturn true }

        initialSetupViewModelTester(validator)
                .fillAllFieldsWithDefaultValues()
                .performActions { targetPercentage.value = 89 }
                .buildAndAssertInitialSetupData {
                    targetPercentage = FieldAssert(89) }
    }

    @Test
    fun buildingInitialSetupDataCorrectlyIncludesTotalCredits()
    {
        val validator = mock<InitialSetupDataModelValidator> {
            on { validate(
                    ArgumentMatchers.any(),
                    ArgumentMatchers.any(),
                    ArgumentMatchers.any(),
                    ArgumentMatchers.any(),
                    ArgumentMatchers.eq(260)) } doReturn true }

        initialSetupViewModelTester(validator)
                .fillAllFieldsWithDefaultValues()
                .performActions { totalCredits.value = 260 }
                .buildAndAssertInitialSetupData {
                    totalCredits = FieldAssert(260) }
    }

    @Test
    fun validDataEnteredReturnsTrueWithCorrectFields()
    {
        val validator = mock<InitialSetupDataModelValidator> {
            on { validate(
                    "Leeds",
                    2014,
                    2,
                    46,
                    150) } doReturn true }

        initialSetupViewModelTester(validator)
                .performActions {
                    universityName.value = "Leeds"
                    yearStarted.value = 2014
                    courseLength.value = 2
                    targetPercentage.value = 46
                    totalCredits.value = 150 }
                .assertValidDataTrue()
    }

    @Test
    fun validDataEnteredReturnsFalseWithCorrectFields()
    {
        val validator = mock<InitialSetupDataModelValidator> {
            on { validate(
                    "Leeds",
                    2014,
                    2,
                    46,
                    150) } doReturn false }

        initialSetupViewModelTester(validator)
                .performActions {
                    universityName.value = "Leeds"
                    yearStarted.value = 2014
                    courseLength.value = 2
                    targetPercentage.value = 45
                    totalCredits.value = 150 }
                .assertValidDataFalse()
    }
}