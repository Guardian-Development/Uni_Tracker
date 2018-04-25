package mobile.joehonour.newcastleuniversity.unitracker.domain.authentication

interface IProvideAuthentication
{
    fun authenticateWithTwitterSession(
            userToken: String,
            userSecret: String,
            callback: (status: Boolean, errorMessage: String?) -> Unit)

    fun authenticateWithFacebookSession(
            accessToken: String,
            callback: (status: Boolean, errorMessage: String?) -> Unit)

    val userLoggedIn: Boolean
    val userUniqueId: String?
}