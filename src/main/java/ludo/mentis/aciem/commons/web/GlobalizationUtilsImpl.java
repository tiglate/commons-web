package ludo.mentis.aciem.commons.web;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.LocaleResolver;

@Component
public final class GlobalizationUtilsImpl implements GlobalizationUtils {

    private final MessageSource messageSource;
    private final LocaleResolver localeResolver;

    public GlobalizationUtilsImpl(final MessageSource messageSource, final LocaleResolver localeResolver) {
        this.messageSource = messageSource;
        this.localeResolver = localeResolver;
    }

    @Override
    public String getMessage(final String code, final Object... args) {
        return messageSource.getMessage(code, args, code, resolveCurrentLocale());
    }

    private java.util.Locale resolveCurrentLocale() {
        return localeResolver.resolveLocale(WebUtils.getRequest());
    }
}
