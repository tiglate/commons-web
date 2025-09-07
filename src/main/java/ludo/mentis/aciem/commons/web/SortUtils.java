package ludo.mentis.aciem.commons.web;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.ui.Model;

import java.util.Map;

/**
 * Interface for handling sort links.
 * <p>
 * This interface defines operations to add sort attributes to a model and to build sort links.
 * The sort attributes are used to display sort links in the view, and the sort links
 * are used to sort the data in the controller.
 * </p>
 * <p>
 * Implementations are responsible for the actual link construction strategy.
 * </p>
 */
public interface SortUtils {

    /**
     * Adds sort attributes to the model for each property in the sortAttributes map.
     *
     * @param model          the model to add the sort attributes to
     * @param sort           the current sort order
     * @param pageable       the current pageable
     * @param sortAttributes a map of property names to attribute names
     * @return the sort order
     */
    Sort addSortAttributesToModel(Model model, String sort, Pageable pageable, Map<String, String> sortAttributes);

    /**
     * Gets the sort order from the sort string.
     *
     * @param sort the sort string
     * @return the sort order
     */
    Sort getSortOrder(String sort);

    /**
     * Builds a sort link for the given property.
     *
     * @param property    the property to sort by
     * @param currentSort the current sort order
     * @param pageable    the current pageable
     * @return the sort link
     */
    String buildSortLink(String property, String currentSort, Pageable pageable);

    /**
     * Gets the sort direction for the given property.
     *
     * @param property    the property to get the sort direction for
     * @param currentSort the current sort order
     * @return the sort direction
     */
    String getSortDirection(String property, String currentSort);
}
