package bivano.apps.domain.usecase

import bivano.apps.common.Result
import bivano.apps.common.model.Article
import bivano.apps.domain.DataManager
import bivano.apps.domain.base.UseCase
import bivano.apps.domain.di.DefaultDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LoadHeadlinesUseCase
@Inject constructor(
    private val dataManager: DataManager,
    @DefaultDispatcher coroutineDispatcher: CoroutineDispatcher
) : UseCase<LoadHeadlinesUseCase.Params, List<Article>>(coroutineDispatcher) {
    override suspend fun execute(param: Params): Flow<Result<List<Article>>> {
        return dataManager.loadHeadline(param.category, param.page)
    }

    data class Params(val category: String?, val page: Int)

}