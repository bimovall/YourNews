package bivano.apps.domain.base

import bivano.apps.common.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

abstract class UseCase<in P, R>(private val coroutineDispatcher: CoroutineDispatcher) {

    abstract suspend fun execute(param: P) : Flow<Result<R>>

    @ExperimentalCoroutinesApi
    suspend operator fun invoke(param: P) : Flow<Result<R>> {
        return execute(param)
            .flowOn(coroutineDispatcher)
    }
}