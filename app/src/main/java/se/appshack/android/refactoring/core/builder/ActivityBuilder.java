package se.appshack.android.refactoring.core.builder;


import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import se.appshack.android.refactoring.main.presentation.ui.activity.MainActivity;

@Module
public interface ActivityBuilder {

    @ContributesAndroidInjector
    MainActivity getMainActivity();

}
