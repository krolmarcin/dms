package pl.com.bottega.dms.model.printing;

import pl.com.bottega.dms.model.Document;
import pl.com.bottega.dms.model.DocumentType;

import java.math.BigDecimal;

public class ManualPrintCostCalculator implements PrintCostCalculator {

    private static final BigDecimal MANUAL_PAGES_PRINT_COST = new BigDecimal(1.3);

    private PrintCostCalculator printCostCalculator;

    public ManualPrintCostCalculator(PrintCostCalculator printCostCalculator) {
        this.printCostCalculator = printCostCalculator;
    }

    @Override
    public BigDecimal calculateCost(Document document) {
        if (document.getDocumentType().equals(DocumentType.MANUAL))
            return MANUAL_PAGES_PRINT_COST;
        else
            return BigDecimal.valueOf(0);
    }

}
