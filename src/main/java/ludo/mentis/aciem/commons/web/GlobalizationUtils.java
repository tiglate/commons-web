package ludo.mentis.aciem.commons.web;

/**
 * Interface for handling globalization and localization tasks.
 * <p>
 * Defines the contract for retrieving localized messages based on the active locale.
 * Implementations are responsible for resolving the current locale and sourcing messages.
 * </p>
 */
public interface GlobalizationUtils {

    /**
     * Retrieves a localized message based on the provided message code and arguments.
     *
     * @param code the message code to resolve
     * @param args optional arguments used to format the message
     * @return the localized message
     */
    String getMessage(final String code, final Object... args);
}
