package bivano.apps.common

import java.lang.Exception

sealed class Result<out T> {
    data class Success<out T : Any>(val data : T) : Result<T>()
    data class ResponseError(val failure: Failure) : Result<Nothing>()
    data class GeneralError(val exception: Exception) : Result<Nothing>()
    object Loading : Result<Nothing>()
}