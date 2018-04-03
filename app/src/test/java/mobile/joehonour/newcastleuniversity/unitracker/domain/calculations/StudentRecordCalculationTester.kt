package mobile.joehonour.newcastleuniversity.unitracker.domain.calculations

import mobile.joehonour.newcastleuniversity.unitracker.domain.models.StudentRecord

class StudentRecordCalculationTester(builder: StudentRecordBuilder.() -> Unit)
{
    private val studentRecord: StudentRecord

    init {
        val studentRecordBuilder = StudentRecordBuilder()
        studentRecordBuilder.builder()
        studentRecord = studentRecordBuilder.build()
    }

    fun performCalculationOnStudentRecord(moduleProvider: (StudentRecord, StudentTargetCalculator) -> Unit)
    {
        moduleProvider(studentRecord, StudentTargetCalculator())
    }
}