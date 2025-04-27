package commons.modules;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.pages.MainPage;

public class GuiceModule extends AbstractModule {
  @Singleton
  @Provides
  public MainPage getMainPage() {
    return new MainPage();
  }
}