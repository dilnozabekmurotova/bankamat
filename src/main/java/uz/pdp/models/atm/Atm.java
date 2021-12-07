package uz.pdp.models.atm;

import lombok.*;
import uz.pdp.enums.status.Status;
import uz.pdp.models.casatte.Cassette;

import java.util.List;
import java.util.Map;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Atm {
    private String code;
    private String orgId;
    private String orgName;
    private String filId;
    private String filName;
    private String longitude;
    private String latitude;
    private Status status;
    private String limitCount;
    private Map<Cassette, String> mapCassettes;

    public Atm(String code, String orgId, String orgName, String filId, String filName,
               String longitude, String latitude, Status status, String limitCount) {
        this.code = code;
        this.orgId = orgId;
        this.orgName = orgName;
        this.filId = filId;
        this.filName = filName;
        this.longitude = longitude;
        this.latitude = latitude;
        this.status = status;
        this.limitCount = limitCount;
    }
}
