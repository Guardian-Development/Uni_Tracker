package mobile.joehonour.newcastleuniversity.unitracker.domain.extensions

import java.math.BigDecimal

fun Double.roundToTwoDecimalPlaces() =
        BigDecimal(this).setScale(2, BigDecimal.ROUND_HALF_UP).toDouble()