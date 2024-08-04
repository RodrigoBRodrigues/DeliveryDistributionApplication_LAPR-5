package eapli.base.warehousemanagement;

import eapli.base.warehousemanagement.domain.Begin;
import eapli.base.warehousemanagement.domain.Depth;
import eapli.base.warehousemanagement.domain.End;

import javax.persistence.*;

@Entity
public class AGVDocks {
    @Id
    private String Id;

    private Begin begin;

    private End end;

    private Depth depth;

    private String accessibility;


    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("AGVDocks{");
        sb.append("id=").append(Id);
        sb.append(", begin=").append(begin);
        sb.append(", end=").append(end);
        sb.append(", depth=").append(depth);
        sb.append(", accessibility='").append(accessibility).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
