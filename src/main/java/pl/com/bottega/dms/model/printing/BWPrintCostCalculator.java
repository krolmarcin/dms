package pl.com.bottega.dms.model.printing;

import org.springframework.stereotype.Component;
import pl.com.bottega.dms.model.Document;

import java.math.BigDecimal;

@Component
public class BWPrintCostCalculator implements PrintCostCalculator {

    public BigDecimal calculateCost(Document document) {
        return null;
    }

}
