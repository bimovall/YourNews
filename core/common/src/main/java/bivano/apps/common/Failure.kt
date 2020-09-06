package bivano.apps.common

data class Failure (
    val status : String,
    val message: String?,
    val code: Int?
)