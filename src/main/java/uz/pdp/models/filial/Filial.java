package uz.pdp.models.filial;

import lombok.*;
import uz.pdp.enums.status.Status;
import uz.pdp.models.Entity;
import uz.pdp.utils.Color;
import uz.pdp.utils.Print;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Filial extends Entity {
    private String name;
    private String longitude;
    private String latitude;
    private String bankId;
    private Status status = Status.ACTIVE;

    public Filial(String id,String createdBy, String name, String longitude, String latitude) {
        super( createdBy);
        this.bankId=id;
        this.name = name;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    @Override
    public String toString() {
        return "Filial--> Name: "+name+" | Status: "+status+" | Owner: "+getCreatedBy()+" | Created At: "+getCreateAt()+
                " | Update by"+(getUpdatedBy()==null?"_____":getUpdatedBy()+"Update time"+getUpdateAt());
    }
}
