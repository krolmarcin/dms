package pl.com.bottega.dms.model.numbers;

import pl.com.bottega.dms.model.DocumentNumber;

public class DemoNumberGenerator implements NumberGenerator {

    private NumberGenerator decorated;

    public DemoNumberGenerator(NumberGenerator decorated) {
        this.decorated = decorated;
    }

    @Override
    public DocumentNumber generate() {
        DocumentNumber documentNumber = decorated.generate();

        String nr = documentNumber.getNumber();
        return new DocumentNumber("DEMO-" + nr);
    }

}
