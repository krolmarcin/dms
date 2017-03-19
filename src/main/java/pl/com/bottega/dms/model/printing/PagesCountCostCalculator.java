package pl.com.bottega.dms.model.printing;

import pl.com.bottega.dms.model.Document;

import java.math.BigDecimal;

public class PagesCountCostCalculator implements PrintCostCalculator {

    private static final BigDecimal HIGH_PAGES_COUNT_COST = new BigDecimal(40);
    private static final int PAGES_COUNT_LIMIT = 100;

    private PrintCostCalculator printCostCalculator;

    public PagesCountCostCalculator(PrintCostCalculator printCostCalculator) {
        this.printCostCalculator = printCostCalculator;
    }

    @Override
    public BigDecimal calculateCost(Document document) {
        if (document.getPagesCount() > PAGES_COUNT_LIMIT)
            return calculateCost(document).multiply(HIGH_PAGES_COUNT_COST);
        else
            return BigDecimal.valueOf(0);
    }

}
