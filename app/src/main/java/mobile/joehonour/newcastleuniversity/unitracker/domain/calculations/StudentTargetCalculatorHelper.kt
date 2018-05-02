package mobile.joehonour.newcastleuniversity.unitracker.domain.calculations

import mobile.joehonour.newcastleuniversity.unitracker.domain.extensions.roundToTwoDecimalPlaces
import mobile.joehonour.newcastleuniversity.unitracker.domain.models.ConfigurationYearWeighting
import mobile.joehonour.newcastleuniversity.unitracker.domain.models.Module
import mobile.joehonour.newcastleuniversity.unitracker.domain.models.ModuleResult
import mobile.joehonour.newcastleuniversity.unitracker.domain.models.StudentRecord

/**
 * Responsible for converting a module to a Pair containing the modules credits to the completion
 * percentage of the module, calculated by summing the weight of all results recorded for that module.
 *
 * @param module the module to perform the calculation on
 *
 * @return Pair<moduleCredits, ModuleCompletionPercentage>
 */
fun moduleCreditsToModuleCompletionPercentage(module: Module) : Pair<Int, Int>
{
    return module.moduleCredits to module.results.values.map { it.resultWeighting }.sum()
}

/**
 * Responsible for calculating the credits completed of a module based on the completion percentage.
 *
 * @param creditsToCompletionPercentage Pair<moduleCredits, ModuleCompletionPercentage>
 *
 * @return the amount of credits completed of the module.
 */
fun creditsCompletedOfModuleBasedOnCompletionPercentage(
        creditsToCompletionPercentage: Pair<Int, Int>) : Double
{
    return creditsToCompletionPercentage.first.toDouble() *
            (creditsToCompletionPercentage.second.toDouble() / 100)
}

/**
 * Responsible for calculating the percentage of credits completed out of the set of total credits.
 *
 * @param totalCredits the amount of all credits.
 * @param completedCredits the amount of credits currently completed.
 */
fun convertCreditsCompletedToPercentage(totalCredits: Int, completedCredits: Double) : Double
{
    val total = (completedCredits / totalCredits) * 100
    return total.roundToTwoDecimalPlaces()
}

/**
 * Responsible for transforming the years within the students degree programme to an amount of
 * weighting credits. This is done by looking at the amount of credits completed in each year
 * accompanied by the weighting that year holds within the students degree programme.
 *
 * @param yearWeightings the list of years configured by the student.
 *
 * @return the weighting credits available in this students degree programme.
 */
fun totalWeightedCreditsAvailable(yearWeightings: List<ConfigurationYearWeighting>) : Double
{
    return yearWeightings.map { it.creditsCompletedWithinYear * it.weighting }.sum().toDouble()
}

/**
 * Responsible for calculating the percentage of weighted credits needed out of the remaining weighted
 * credits, in order to meet a target percentage.
 *
 * @param targetPercentage the percentage you want to achieve.
 * @param totalWeightedCreditsAvailable the amount of weighted credits still up for grabs within the
 * degree programme.
 *
 *
 * @return the amount of weighted credits needed to meet the target percentage.
 */
fun weightedCreditsRequiredToHitTargetPercentage(targetPercentage: Int,
                                                 totalWeightedCreditsAvailable: Double) : Double
{
    return totalWeightedCreditsAvailable * (targetPercentage.toDouble() / 100)
}

/**
 * Responsible for calculating the amount of weighted credits a student has currently achieved.
 * This is done by calculating the weighted credits achieved by a student in each configured module.
 *
 * @return the amount of weighted credits a student has currently achieved.
 */
fun currentlyAchievedWeightedCredits(studentRecord: StudentRecord) : Double
{
    return studentRecord.modules.values
            .map { module -> moduleToYearStudied(module, studentRecord) }
            .map { computeWeightedCreditsAchievedWithinModule(it.first, it.second) }
            .sum()
            .takeIf { it.isFinite() } ?: 0.0
}

/**
 * Responsible for computing the amount of weighted credits achieved within a module, based on the
 * year weighting the module is taken in, along with the results recorded in that module.
 *
 * @param module the module to compute this over.
 * @param year the year in which this module was completed.
 *
 * @return the amount of weighted credits a student has completed within the module.
 */
fun computeWeightedCreditsAchievedWithinModule(module: Module, year: ConfigurationYearWeighting) : Double
{
    val moduleAverageGradePercentage = weightedAverageOfResultsWithinModule(
            module.results.values.toList())

    return when {
        !moduleAverageGradePercentage.isFinite() -> 0.0
        else -> {
            val creditsToModuleCompletionPercentage = moduleCreditsToModuleCompletionPercentage(module)

            //convert module credits to weighted credits based on the year it was taken in.
            val weightedCreditsAvailableForModule = creditsToModuleCompletionPercentage.first * year.weighting

            //calculate the amount of weighted credits the student has recorded in all their results.
            val completedAmountOfCredits = weightedCreditsAvailableForModule *
                    (creditsToModuleCompletionPercentage.second.toDouble() / 100)

            //calculate the amount of weighted credits the student has achieved based on the weighted
            //credits available to them in their completed credits against the weighted percentage they achieved.
             completedAmountOfCredits * (moduleAverageGradePercentage / 100)
        }
    }
}

/**
 * Responsible for calculating a students weighted average within a module based on the results they
 * have recorded and their respective weightings.
 *
 * @param results the list of results recorded against a module.
 *
 * @return the weighted average based on the results recorded in this module.
 */
fun weightedAverageOfResultsWithinModule(results: List<ModuleResult>) : Double
{
    val totalWeighting = results.map { it.resultWeighting }.sum()
    val result = results.map { it.resultWeighting * it.resultPercentage }.sum()
    return result / totalWeighting
}

/**
 * Responsible for calculating the difference between the target weighted credits and the currently
 * achieved weighted credits.
 *
 * @param targetWeightedCreditsNeeded the amount of weighted credits needed to hit the target.
 * @param currentlyAchievedWeightedCredits the amount of weighted credits currently achieved by the student.
 *
 * @return the amount of weighted credits needed to hit their target.
 */
fun weightedCreditsNeededToHitTargetCredits(targetWeightedCreditsNeeded: Double,
                                            currentlyAchievedWeightedCredits: Double) : Double
{
    return targetWeightedCreditsNeeded - currentlyAchievedWeightedCredits
}

/**
 * Responsible for calculating the remaining amount of weighted credits available based on the total
 * amount of weighted credits and the amount of weighted credits already taken.
 *
 * @param totalWeightedCredits the amount of weighted credits available.
 * @param amountOfWeightedCreditsTaken the amount of weighted credits already taken.
 *
 * @return the amount of weighted credits still available.
 */
fun remainingAmountOfWeightedCreditsAvailable(totalWeightedCredits: Double,
                                              amountOfWeightedCreditsTaken: Double) : Double
{
    return totalWeightedCredits - amountOfWeightedCreditsTaken
}

/**
 * Responsible for calculating the total amount of weighted credits a student has taken.
 * This is done by registering each module with the year it was completed within.
 * Then the amount of credits taken in each module is calculated, multiplied by the modules weighting
 * based on the year it was completed within.
 * The sum of all modules weighted credits is then taken and returned.
 *
 * @param studentRecord the student configuration you wish to perform the calculation on.
 *
 * @return the amount of weighted credits a student has taken.
 */
fun totalAmountOfWeightedCreditsTaken(studentRecord: StudentRecord) : Double
{
    return studentRecord.modules.values
            .map { module -> moduleToYearStudied(module, studentRecord) }
            .map { moduleCreditsToModuleCompletionPercentage(it.first) to it.second }
            .map { creditsCompletedOfModuleBasedOnCompletionPercentage(it.first) * it.second.weighting }
            .sum()
            .takeIf { it.isFinite() } ?: 0.0
}

/**
 * Responsible for calculating the percentage required in the remaining credits available to the student,
 * in order to meet their target grade.
 *
 * @param weightedCreditsNeededToHitTarget the amount of weighted credits needed to hit their target.
 * @param remainingAmountOfWeightedCredits the amount of weighted credits still available to be taken.
 *
 * @return the percentage the student requires to meet their target.
 */
fun percentageRequiredInRemainingWeightedCreditsToHitTarget(
        weightedCreditsNeededToHitTarget: Double,
        remainingAmountOfWeightedCredits: Double) : Double
{
    return when
    {
        weightedCreditsNeededToHitTarget <= 0 -> 0.0
        weightedCreditsNeededToHitTarget > remainingAmountOfWeightedCredits -> Double.POSITIVE_INFINITY
        else -> (weightedCreditsNeededToHitTarget / remainingAmountOfWeightedCredits) * 100
    }
}

/**
 * Responsible for finding the year a module was studied within.
 *
 * @param module the module you wish to find the year for.
 * @param studentRecord the configuration you wish to search for the module's year studied.
 *
 * @return Pair<Module, YearStudied (Int)>
 */
private fun moduleToYearStudied(module: Module, studentRecord: StudentRecord) =
        module to studentRecord.configuration.yearWeightings
                .first { it.year == module.moduleYearStudied }
