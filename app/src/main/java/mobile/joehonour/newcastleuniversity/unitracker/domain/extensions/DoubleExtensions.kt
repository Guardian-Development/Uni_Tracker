package mobile.joehonour.newcastleuniversity.unitracker.domain.extensions

import java.math.BigDecimal

/**
 * Responsible for rounding a double to 2 decimal places with the rounding up point being 0.5
 */
fun Double.roundToTwoDecimalPlaces() =
        BigDecimal(this).setScale(2, BigDecimal.ROUND_HALF_UP).toDouble()