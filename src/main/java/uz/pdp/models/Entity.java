package uz.pdp.models;

import lombok.*;
import uz.pdp.utils.BaseUtils;
import uz.pdp.utils.Print;

import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public abstract class Entity {
    private String id = BaseUtils.generateUniqueId();
    private String createdBy;
    private String updatedBy;
    private String createAt = BaseUtils.getDate();
    private Date updateAt;

    public Entity(String createdBy) {
        this.createdBy = createdBy;
    }
}
