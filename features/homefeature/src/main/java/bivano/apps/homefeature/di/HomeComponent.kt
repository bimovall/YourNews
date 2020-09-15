package bivano.apps.homefeature.di

import bivano.apps.homefeature.HomeFragment
import bivano.apps.homefeature.list.ListHeadlineFragment
import bivano.apps.yournews.di.DynamicModuleDependencies
import dagger.Component

@Component(
    dependencies = [DynamicModuleDependencies::class],
    modules = [HomeModule::class]
)
interface HomeComponent {

    fun inject(fragment: HomeFragment)

    fun inject(fragment: ListHeadlineFragment)

    /*@Component.Builder
    interface Builder {
        fun context(@BindsInstance context: Context): Builder
        fun appDependecies(dynamicModuleDependencies: DynamicModuleDependencies): Builder
        fun build(): HomeComponent
    }*/

    @Component.Factory
    interface Factory {
        fun create(
            dependencies: DynamicModuleDependencies
        ): HomeComponent
    }

}