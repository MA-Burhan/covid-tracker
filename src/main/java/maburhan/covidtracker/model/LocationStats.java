package maburhan.covidtracker.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@Builder
@ToString
public class LocationStats {

    private String state;
    private String country;
    private int totalNumOfCasesLatest;
    private int totalNumOfCasesPrevDay;

    public int getNumOfNewCases(){
        return totalNumOfCasesLatest - totalNumOfCasesPrevDay;
    }

}
