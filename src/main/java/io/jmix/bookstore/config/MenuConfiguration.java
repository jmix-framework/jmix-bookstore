package io.jmix.bookstore.config;

import io.jmix.ui.menu.MenuConfig;
import io.jmix.ui.menu.MenuItem;
import org.dom4j.Element;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@Configuration
public class MenuConfiguration {

    @Primary
    @Bean
    @ConditionalOnProperty(prefix = "bookstore.misc", name = "hide-sensitive-menu-screens", havingValue = "true")
    MenuConfig extendedMenuConfig() {
        return new ExtendedMenuConfig(getForbiddenScreens());
    }

    protected Collection<String> getForbiddenScreens() {
        return List.of("ui_JmxConsoleScreen");
    }

    public static class ExtendedMenuConfig extends MenuConfig {

        private final Set<String> forbiddenScreens;

        public ExtendedMenuConfig(Collection<String> forbiddenScreens) {
            this.forbiddenScreens = forbiddenScreens == null ? Collections.emptySet() : Set.copyOf(forbiddenScreens);
        }

        @Nullable
        @Override
        protected MenuItem createMenuItem(Element element, @Nullable MenuItem currentParentItem) {
            if (isForbiddenScreen(element)) {
                return null;
            }

            return super.createMenuItem(element, currentParentItem);
        }

        protected boolean isForbiddenScreen(Element element) {
            String screen = element.attributeValue("screen");
            return screen != null && forbiddenScreens.contains(screen);
        }
    }
}
