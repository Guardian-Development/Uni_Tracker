package mobile.joehonour.newcastleuniversity.unitracker.domain.models

import java.util.*

data class Result(val resultId: UUID,
                  val resultName: String,
                  val resultWeighting: Int,
                  val resultPercentage: Double)
{
    //used by Firebase
    constructor() : this(UUID.randomUUID(), "", 0, 0.0)
}