package inventory.model;

import javax.persistence.*;
import java.sql.Time;

@Entity
@Table(name = "USER_ROLE", schema = "QLKHO_ADMIN", catalog = "")
public class UserRole {
    private boolean activeFlag;
    private Time createDate;
    private Time updateDate;
    private short userRoleId;

    @Basic
    @Column(name = "ACTIVE_FLAG")
    public boolean isActiveFlag() {
        return activeFlag;
    }

    public void setActiveFlag(boolean activeFlag) {
        this.activeFlag = activeFlag;
    }

    @Basic
    @Column(name = "CREATE_DATE")
    public Time getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Time createDate) {
        this.createDate = createDate;
    }

    @Basic
    @Column(name = "UPDATE_DATE")
    public Time getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Time updateDate) {
        this.updateDate = updateDate;
    }

    @Id
    @Column(name = "USER_ROLE_ID")
    public short getUserRoleId() {
        return userRoleId;
    }

    public void setUserRoleId(short userRoleId) {
        this.userRoleId = userRoleId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserRole userRole = (UserRole) o;

        if (activeFlag != userRole.activeFlag) return false;
        if (userRoleId != userRole.userRoleId) return false;
        if (createDate != null ? !createDate.equals(userRole.createDate) : userRole.createDate != null) return false;
        if (updateDate != null ? !updateDate.equals(userRole.updateDate) : userRole.updateDate != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (activeFlag ? 1 : 0);
        result = 31 * result + (createDate != null ? createDate.hashCode() : 0);
        result = 31 * result + (updateDate != null ? updateDate.hashCode() : 0);
        result = 31 * result + (int) userRoleId;
        return result;
    }
}
