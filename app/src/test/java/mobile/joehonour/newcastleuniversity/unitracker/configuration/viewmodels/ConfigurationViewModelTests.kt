package mobile.joehonour.newcastleuniversity.unitracker.configuration.viewmodels

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import mobile.joehonour.newcastleuniversity.unitracker.configuration.model.ConfigurationDataModelValidator
import mobile.joehonour.newcastleuniversity.unitracker.support.FieldAssert
import mobile.joehonour.newcastleuniversity.unitracker.configuration.viewmodels.ConfigurationViewModelTester.Companion.configurationViewModelTester
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.ArgumentMatchers


class ConfigurationViewModelTests
{
    // allows live data to work outside of an android environment
    @Rule @JvmField
    val rule: TestRule = InstantTaskExecutorRule()

    @Test
    fun canBuildConfigurationDataModelFromLiveDataSuccess()
    {
        val validator = mock<ConfigurationDataModelValidator> {
            on { validate(
                    "Newcastle",
                    2017,
                    3,
                    75,
                    50) } doReturn true }

        configurationViewModelTester(validator)
                .performActions {
                    universityName.value = "Newcastle"
                    yearStarted.value = 2017
                    courseLength.value = 3
                    targetPercentage.value = 75
                    totalCredits.value = 50 }
                .buildAndAssertConfiguration {
                    universityName = FieldAssert("Newcastle")
                    yearStarted = FieldAssert(2017)
                    courseLength = FieldAssert(3)
                    targetPercentage = FieldAssert(75)
                    totalCredits = FieldAssert(50)
                }
    }

    @Test
    fun buildingConfigurationDataCorrectlyIncludesUniversityName()
    {
        val validator = mock<ConfigurationDataModelValidator> {
            on { validate(
                    ArgumentMatchers.eq("Edinburgh"),
                    ArgumentMatchers.any(),
                    ArgumentMatchers.any(),
                    ArgumentMatchers.any(),
                    ArgumentMatchers.any()) } doReturn true }

        configurationViewModelTester(validator)
                .fillAllFieldsWithDefaultValues()
                .performActions { universityName.value = "Edinburgh" }
                .buildAndAssertConfiguration {
                    universityName = FieldAssert("Edinburgh") }
    }

    @Test
    fun buildingConfigurationDataCorrectlyIncludesYearStarted()
    {
        val validator = mock<ConfigurationDataModelValidator> {
            on { validate(
                    ArgumentMatchers.any(),
                    ArgumentMatchers.eq(2015),
                    ArgumentMatchers.any(),
                    ArgumentMatchers.any(),
                    ArgumentMatchers.any()) } doReturn true }

        configurationViewModelTester(validator)
                .fillAllFieldsWithDefaultValues()
                .performActions { yearStarted.value = 2015 }
                .buildAndAssertConfiguration {
                    yearStarted = FieldAssert(2015) }
    }

    @Test
    fun buildingConfigurationDataCorrectlyIncludesCourseLength()
    {
        val validator = mock<ConfigurationDataModelValidator> {
            on { validate(
                    ArgumentMatchers.any(),
                    ArgumentMatchers.any(),
                    ArgumentMatchers.eq(4),
                    ArgumentMatchers.any(),
                    ArgumentMatchers.any()) } doReturn true }

        configurationViewModelTester(validator)
                .fillAllFieldsWithDefaultValues()
                .performActions { courseLength.value = 4 }
                .buildAndAssertConfiguration {
                    courseLength = FieldAssert(4) }
    }

    @Test
    fun buildingConfigurationDataCorrectlyIncludesTargetPercentage()
    {
        val validator = mock<ConfigurationDataModelValidator> {
            on { validate(
                    ArgumentMatchers.any(),
                    ArgumentMatchers.any(),
                    ArgumentMatchers.any(),
                    ArgumentMatchers.eq(89),
                    ArgumentMatchers.any()) } doReturn true }

        configurationViewModelTester(validator)
                .fillAllFieldsWithDefaultValues()
                .performActions { targetPercentage.value = 89 }
                .buildAndAssertConfiguration {
                    targetPercentage = FieldAssert(89) }
    }

    @Test
    fun buildingConfigurationDataCorrectlyIncludesTotalCredits()
    {
        val validator = mock<ConfigurationDataModelValidator> {
            on { validate(
                    ArgumentMatchers.any(),
                    ArgumentMatchers.any(),
                    ArgumentMatchers.any(),
                    ArgumentMatchers.any(),
                    ArgumentMatchers.eq(260)) } doReturn true }

        configurationViewModelTester(validator)
                .fillAllFieldsWithDefaultValues()
                .performActions { totalCredits.value = 260 }
                .buildAndAssertConfiguration {
                    totalCredits = FieldAssert(260) }
    }

    @Test
    fun validDataEnteredReturnsTrueWithCorrectFields()
    {
        val validator = mock<ConfigurationDataModelValidator> {
            on { validate(
                    "Leeds",
                    2014,
                    2,
                    46,
                    150) } doReturn true }

        configurationViewModelTester(validator)
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
        val validator = mock<ConfigurationDataModelValidator> {
            on { validate(
                    "Leeds",
                    2014,
                    2,
                    46,
                    150) } doReturn false }

        configurationViewModelTester(validator)
                .performActions {
                    universityName.value = "Leeds"
                    yearStarted.value = 2014
                    courseLength.value = 2
                    targetPercentage.value = 45
                    totalCredits.value = 150 }
                .assertValidDataFalse()
    }
}