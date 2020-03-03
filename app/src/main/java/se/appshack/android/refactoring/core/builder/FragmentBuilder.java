package se.appshack.android.refactoring.core.builder;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import se.appshack.android.refactoring.main.presentation.ui.fragment.PokemonDetailsFragment;
import se.appshack.android.refactoring.main.presentation.ui.fragment.PokemonListFragment;

@Module
public interface FragmentBuilder {

    @ContributesAndroidInjector
    PokemonDetailsFragment getPokemonDetailsFragment();

    @ContributesAndroidInjector
    PokemonListFragment getPokemonListFragment();
}
