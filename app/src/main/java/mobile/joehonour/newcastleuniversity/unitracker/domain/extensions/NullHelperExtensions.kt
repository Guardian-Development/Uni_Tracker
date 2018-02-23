package mobile.joehonour.newcastleuniversity.unitracker.domain.extensions

fun <T> T.notNull(): Boolean {
    return this != null
}