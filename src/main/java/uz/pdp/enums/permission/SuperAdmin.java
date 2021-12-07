package uz.pdp.enums.permission;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SuperAdmin {
    CREAT_ORG(true,"creat.org"),
    DELETE_ORG(true,"delete.org"),
    UPDATE_ORG(true,"update.org"),
    LIST_ORG(true,"list.org"),
    BLOCK_ORG(true, "block.org"),
    UNBLOCK_ORG(true, "unblock.org"),
    ADD_PERMISSION_ADMIN(true,"add.permission.admin"),
    CREATE_ADMIN(true,"create.admin"),
    DELETE_ADMIN(true,"delete.admin"),
    LIST_ADMIN(true,"list.admin"),
    UPDATE_ADMIN(true,"update.admin"),
    BLOCK_ADMIN(true, "block.admin"),
    UNBLOCK_ADMIN(true, "UNblock.admin");
    private final boolean type;
    private final String propertyCode;
}
