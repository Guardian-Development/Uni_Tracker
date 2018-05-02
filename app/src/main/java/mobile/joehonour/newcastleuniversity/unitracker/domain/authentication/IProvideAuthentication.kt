package mobile.joehonour.newcastleuniversity.unitracker.domain.authentication

/**
 * Provides the ability to authenticate the user, while providing details about the currently
 * authenticated user.
 */
interface IProvideAuthentication
{
    /**
     * Given Twitter credentials, is responsible for authenticating the user with Twitter.
     *
     * @param userToken the Twitter user token to authenticate with.
     * @param userSecret the Twitter user secret to authenticate with.
     * @param callback is executed on authentication completion, with the success of the authentication
     * attempt being passed in.
     */
    fun authenticateWithTwitterSession(
            userToken: String,
            userSecret: String,
            callback: (status: Boolean, errorMessage: String?) -> Unit)

    /**
     * Given Facebook credentials, is responsible for authenticating the user with Facebook.
     *
     * @param accessToken the Facebook access token to authenticate with.
     * @param callback is executed on authentication completion, with the success of the authentication
     * attempt being passed in.
     */
    fun authenticateWithFacebookSession(
            accessToken: String,
            callback: (status: Boolean, errorMessage: String?) -> Unit)

    /**
     * Given Google credentials, is responsible for authenticating the user with Google.
     *
     * @param accessToken the Google access token to authenticate with.
     * @param callback is executed on authentication completion, with the success of the authentication
     * attempt being passed in.
     */
    fun authenticateWithGoogleSession(
            accessToken: String,
            callback: (status: Boolean, errorMessage: String?) -> Unit)

    /**
     * Responsible for logging the user out of the application.
     *
     * @param callback executed on logout completion, with the success of the logout attempt being
     * passed in.
     */
    fun logout(callback: (status: Boolean, errorMessage: String?) -> Unit)

    /**
     * Responsible for stating whether there is a currently authenticated user logged in.
     */
    val userLoggedIn: Boolean

    /**
     * Responsible for stating the currently logged in users unique ID, if user not logged in returns null.
     */
    val userUniqueId: String?
}