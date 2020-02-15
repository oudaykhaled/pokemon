package se.appshack.android.refactoring.core.builder;


import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import se.appshack.android.refactoring.main.presentation.ui.activity.MainActivity;
import se.appshack.android.refactoring.main.presentation.ui.activity.PokemonDetailsActivity;

@Module
public interface ActivityBuilder {

    @ContributesAndroidInjector
    MainActivity getMainActivity();

    @ContributesAndroidInjector
    PokemonDetailsActivity getPokemonDetailsActivity();

}
