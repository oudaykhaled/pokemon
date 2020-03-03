package se.appshack.android.refactoring.main.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import se.appshack.android.refactoring.core.di.modules.ViewModelKey
import se.appshack.android.refactoring.core.presentation.ViewModelFactory
import se.appshack.android.refactoring.main.presentation.viewmodel.PokemonViewModel

@Module
abstract class PokemonPresentationModule {

    @Binds
    abstract fun bindsViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(PokemonViewModel::class)
    abstract fun bindsViewModel(viewModel: PokemonViewModel): ViewModel

}
