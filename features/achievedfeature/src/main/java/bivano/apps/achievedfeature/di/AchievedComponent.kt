package bivano.apps.achievedfeature.di

import bivano.apps.achievedfeature.AchievedFragment
import bivano.apps.yournews.di.DynamicModuleDependencies
import dagger.Component

@Component(
    dependencies = [DynamicModuleDependencies::class],
    modules = [AchievedModule::class]
)
interface AchievedComponent {

    fun inject(achievedFragment: AchievedFragment)

    @Component.Factory
    interface Factory {
        fun create(dependencies: DynamicModuleDependencies): AchievedComponent
    }
}