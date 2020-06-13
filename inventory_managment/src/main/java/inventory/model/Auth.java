package inventory.model;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Time;

@Entity
public class Auth {
    private boolean permission;
    private boolean activeFlag;
    private Time createDate;
    private Time updateDate;
    private short authId;
    private short roleId;
    private short menuId;

    @Basic
    @Column(name = "PERMISSION", nullable = false, precision = 0)
    public boolean isPermission() {
        return permission;
    }

    public void setPermission(boolean permission) {
        this.permission = permission;
    }

    @Basic
    @Column(name = "ACTIVE_FLAG", nullable = false, precision = 0)
    public boolean isActiveFlag() {
        return activeFlag;
    }

    public void setActiveFlag(boolean activeFlag) {
        this.activeFlag = activeFlag;
    }

    @Basic
    @Column(name = "CREATE_DATE", nullable = false)
    public Time getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Time createDate) {
        this.createDate = createDate;
    }

    @Basic
    @Column(name = "UPDATE_DATE", nullable = false)
    public Time getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Time updateDate) {
        this.updateDate = updateDate;
    }

    @Id
    @Column(name = "AUTH_ID", nullable = false, precision = 0)
    public short getAuthId() {
        return authId;
    }

    public void setAuthId(short authId) {
        this.authId = authId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Auth auth = (Auth) o;

        if (permission != auth.permission) return false;
        if (activeFlag != auth.activeFlag) return false;
        if (authId != auth.authId) return false;
        if (createDate != null ? !createDate.equals(auth.createDate) : auth.createDate != null) return false;
        if (updateDate != null ? !updateDate.equals(auth.updateDate) : auth.updateDate != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (permission ? 1 : 0);
        result = 31 * result + (activeFlag ? 1 : 0);
        result = 31 * result + (createDate != null ? createDate.hashCode() : 0);
        result = 31 * result + (updateDate != null ? updateDate.hashCode() : 0);
        result = 31 * result + (int) authId;
        return result;
    }

    @Basic
    @Column(name = "ROLE_ID", nullable = false, precision = 0)
    public short getRoleId() {
        return roleId;
    }

    public void setRoleId(short roleId) {
        this.roleId = roleId;
    }

    @Basic
    @Column(name = "MENU_ID", nullable = false, precision = 0)
    public short getMenuId() {
        return menuId;
    }

    public void setMenuId(short menuId) {
        this.menuId = menuId;
    }
}
