package pl.com.bottega.dms.model.printing;

import pl.com.bottega.dms.model.Document;

import java.math.BigDecimal;

public class BWPrintCostCalculator implements PrintCostCalculator {

    public static final int BW_PAGE_COST = 1;

    public BigDecimal calculateCost(Document document) {
        return new BigDecimal(document.getPagesCount() * BW_PAGE_COST);
    }

}
