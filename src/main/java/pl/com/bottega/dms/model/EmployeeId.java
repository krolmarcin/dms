package pl.com.bottega.dms.model;

import javax.persistence.Embeddable;

@Embeddable
public class EmployeeId {

    Long id;

    public EmployeeId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EmployeeId employeeId = (EmployeeId) o;

        return id.equals(employeeId.id);

    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

}
