package ludo.mentis.aciem.commons.web;

import ludo.mentis.aciem.commons.web.model.PaginationModel;
import org.springframework.data.domain.Page;

/**
 * Contract for building pagination artifacts from a Spring Data {@link org.springframework.data.domain.Page}.
 * <p>
 * Implementations are expected to:
 * <ul>
 *   <li>Translate paging information into a {@link ludo.mentis.aciem.commons.web.model.PaginationModel}
 *       suitable for rendering pagination controls.</li>
 *   <li>Provide navigation URLs for specific target pages while preserving relevant paging context.</li>
 * </ul>
 * </p>
 *
 * <p>
 * Note: Implementations may decide to return {@code null} when pagination is not applicable
 * (e.g., when the provided {@code Page} is {@code null}, empty, or unpaged).
 * </p>
 *
 * @see ludo.mentis.aciem.commons.web.model.PaginationModel
 * @see org.springframework.data.domain.Page
 */
public interface PaginationUtils {

    /**
     * Build a {@link PaginationModel} representing the pagination controls for the given page.
     * <p>
     * The resulting model typically contains:
     * <ul>
     *   <li>Steps (previous/next and numbered page links) with active/disabled states</li>
     *   <li>A human-readable summary of the currently visible element range</li>
     * </ul>
     * </p>
     *
     * @param page the current {@link Page} instance describing the slice to render; may be {@code null}
     * @return a {@link PaginationModel} ready for rendering, or {@code null} if pagination is not applicable
     */
    PaginationModel getPaginationModel(Page<?> page);

    /**
     * Compute a navigation URL for moving from the current {@link Page} to the specified target page index.
     * <p>
     * Implementations commonly preserve relevant query parameters (such as page size and filters)
     * to ensure consistent navigation behavior.
     * </p>
     *
     * @param page       the current {@link Page} used to derive paging context (e.g., size); may be {@code null}
     * @param targetPage the zero-based index of the page to navigate to
     * @return a URL (or query string) that points to the requested {@code targetPage}
     */
    String getStepUrl(Page<?> page, int targetPage);
}