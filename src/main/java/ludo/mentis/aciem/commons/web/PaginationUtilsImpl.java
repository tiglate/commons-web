package ludo.mentis.aciem.commons.web;

import ludo.mentis.aciem.commons.web.model.PaginationModel;
import ludo.mentis.aciem.commons.web.model.PaginationStep;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
class PaginationUtilsImpl implements PaginationUtils {

    private static final int PAGE_WINDOW = 5;
    private static final String LABEL_PREVIOUS = "Previous";
    private static final String LABEL_NEXT = "Next";

    @Override
    public PaginationModel getPaginationModel(final Page<?> page) {
        if (page == null || page.isEmpty()) {
            return null;
        }

        final var previousPageNumber = page.getPageable().isUnpaged()
                ? 0
                : page.previousOrFirstPageable().getPageNumber();
        final var nextPageNumber = page.getPageable().isUnpaged()
                ? 0
                : page.nextOrLastPageable().getPageNumber();

        final var steps = new ArrayList<PaginationStep>();

        final var previousStep = createNavStep(page, previousPageNumber, !page.hasPrevious(), LABEL_PREVIOUS);
        steps.add(previousStep);

        addPageSteps(page, steps);

        final var nextStep = createNavStep(page, nextPageNumber, !page.hasNext(), LABEL_NEXT);
        steps.add(nextStep);

        final var paginationModel = new PaginationModel();
        paginationModel.setSteps(steps);
        paginationModel.setElements(buildElementsLabel(page));
        return paginationModel;
    }

    private PaginationStep createNavStep(final Page<?> page, final int targetPage, final boolean disabled, final String label) {
        final var step = new PaginationStep();
        step.setDisabled(disabled);
        step.setLabel(label);
        step.setUrl(getStepUrl(page, targetPage));
        return step;
    }

    private void addPageSteps(final Page<?> page, final List<PaginationStep> steps) {
        // find a range of up to PAGE_WINDOW pages around the current active page
        final var startIndex = Math.max(0, Math.min(page.getNumber() - (PAGE_WINDOW / 2), page.getTotalPages() - PAGE_WINDOW));
        final var endIndex = Math.min(startIndex + PAGE_WINDOW, page.getTotalPages());

        for (var i = startIndex; i < endIndex; i++) {
            final var step = new PaginationStep();
            step.setActive(i == page.getNumber());
            step.setLabel(Integer.toString(i + 1));
            step.setUrl(getStepUrl(page, i));
            steps.add(step);
        }
    }

    private String buildElementsLabel(final Page<?> page) {
        final var rangeStart = (long) page.getNumber() * page.getSize() + 1L;
        final var rangeEnd = Math.min(rangeStart + page.getSize() - 1, page.getTotalElements());
        final var range = (rangeStart == rangeEnd) ? Long.toString(rangeStart) : (rangeStart + " - " + rangeEnd);
        return String.format("Item %s of %d", range, page.getTotalElements());
    }

    @Override
    public String getStepUrl(final Page<?> page, final int targetPage) {
        final var request = WebUtils.getRequest();
        final var sort = request.getParameter("sort");
        final var filter = request.getParameter("filter");

        final var sb = new StringBuilder()
                .append("?page=").append(targetPage)
                .append("&size=").append(page.getSize());

        appendParam(sb, "sort", sort);
        appendParam(sb, "filter", filter);
        return sb.toString();
    }

    private void appendParam(final StringBuilder sb, final String name, final String value) {
        if (value != null && !value.isBlank()) {
            sb.append("&").append(name).append("=").append(value);
        }
    }
}
