package bivano.apps.articlefeature.di

import bivano.apps.articlefeature.ArticleFragment
import bivano.apps.yournews.di.DynamicModuleDependencies
import dagger.Component

@Component(
    dependencies = [DynamicModuleDependencies::class],
    modules = [ArticleModule::class]

)
interface ArticleComponent {

    fun inject(fragment: ArticleFragment)

    @Component.Factory
    interface Factory {
        fun create(
            dependencies: DynamicModuleDependencies
        ) : ArticleComponent
    }

}