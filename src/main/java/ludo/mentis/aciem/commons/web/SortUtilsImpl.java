package ludo.mentis.aciem.commons.web;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriBuilder;

import java.util.Map;

@Component
public class SortUtilsImpl implements SortUtils {

    private static final String PARAM_SORT = "sort";
    private static final String PARAM_PAGE = "page";
    private static final String DIR_ASC = "asc";
    private static final String DIR_DESC = "desc";

    private UriBuilder uriBuilder;

    @SuppressWarnings("unused")
    public SortUtilsImpl() {
        this.uriBuilder = null;
    }

    /**
     * Constructor that uses the provided UriBuilder to build the sort links.
     *
     * @param uriComponentsBuilder the UriBuilder to use
     */
    public SortUtilsImpl(UriBuilder uriComponentsBuilder) {
        this.uriBuilder = uriComponentsBuilder;
    }

    @Override
    public Sort addSortAttributesToModel(Model model, String sort, Pageable pageable, Map<String, String> sortAttributes) {
        var sortOrder = getSortOrder(sort);
        sortAttributes.forEach((property, attributeName) -> {
            var sortLink = buildSortLink(property, sort, pageable);
            var sortDirection = getSortDirection(property, sort);
            model.addAttribute(attributeName + "Link", sortLink);
            model.addAttribute(attributeName + "Direction", sortDirection);
        });
        return sortOrder;
    }

    @Override
    public Sort getSortOrder(String sort) {
        if (sort != null && !sort.isBlank()) {
            var sortParts = sort.split(",", 2);
            var property = sortParts[0].trim();
            if (property.isEmpty()) {
                return Sort.unsorted();
            }
            var dirToken = (sortParts.length > 1 ? sortParts[1].trim() : DIR_ASC);
            var direction = DIR_ASC.equalsIgnoreCase(dirToken) ? Sort.Direction.ASC : Sort.Direction.DESC;
            return Sort.by(direction, property);
        }
        return Sort.unsorted();
    }

    @Override
    public String buildSortLink(String property, String currentSort, Pageable pageable) {
        var direction = nextDirection(property, currentSort);
        var builder = resolveUriBuilder();
        return builder
                .replaceQueryParam(PARAM_SORT, property + "," + direction)
                .replaceQueryParam(PARAM_PAGE, pageable.getPageNumber())
                .build()
                .toString();
    }

    @Override
    public String getSortDirection(String property, String currentSort) {
        return nextDirection(property, currentSort);
    }

    private UriBuilder resolveUriBuilder() {
        // Lazily resolve from the current request if not provided via constructor
        if (this.uriBuilder == null) {
            this.uriBuilder = ServletUriComponentsBuilder.fromCurrentRequest();
        }
        return this.uriBuilder;
    }

    private boolean isSortedByProperty(String property, String currentSort) {
        return currentSort != null && currentSort.startsWith(property + ",");
    }

    private String nextDirection(String property, String currentSort) {
        if (isSortedByProperty(property, currentSort)) {
            return currentSort.endsWith("," + DIR_ASC) ? DIR_DESC : DIR_ASC;
        }
        return DIR_ASC;
    }
}