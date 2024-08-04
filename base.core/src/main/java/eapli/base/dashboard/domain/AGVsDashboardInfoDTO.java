package eapli.base.dashboard.domain;

public class AGVsDashboardInfoDTO {
    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("AGVsDashboardInfoDTO{");
        sb.append("AGVDescription='").append(AGVDescription).append('\'');
        sb.append(", AGVStatus='").append(AGVStatus).append('\'');
        sb.append(", AGVPosition='").append(AGVPosition).append('\'');
        sb.append('}');
        return sb.toString();
    }

    public String AGVDescription;
    public String AGVStatus;
    public String AGVPosition;

    public AGVsDashboardInfoDTO(String AGVDescription, String AGVStatus, String AGVPosition) {
        this.AGVDescription = AGVDescription;
        this.AGVStatus = AGVStatus;
        this.AGVPosition = AGVPosition;
    }

}
