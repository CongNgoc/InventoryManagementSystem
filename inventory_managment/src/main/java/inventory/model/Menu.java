package inventory.model;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Time;

@Entity
public class Menu {
    private short menuId;
    private String menuName;
    private String url;
    private Integer menuIndex;
    private boolean activeFlag;
    private Time createDate;
    private Time updateDate;

    @Id
    @Column(name = "MENU_ID")
    public short getMenuId() {
        return menuId;
    }

    public void setMenuId(short menuId) {
        this.menuId = menuId;
    }

    @Basic
    @Column(name = "MENU_NAME")
    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    @Basic
    @Column(name = "URL")
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Basic
    @Column(name = "MENU_INDEX")
    public Integer getMenuIndex() {
        return menuIndex;
    }

    public void setMenuIndex(Integer menuIndex) {
        this.menuIndex = menuIndex;
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Menu menu = (Menu) o;

        if (menuId != menu.menuId) return false;
        if (activeFlag != menu.activeFlag) return false;
        if (menuName != null ? !menuName.equals(menu.menuName) : menu.menuName != null) return false;
        if (url != null ? !url.equals(menu.url) : menu.url != null) return false;
        if (menuIndex != null ? !menuIndex.equals(menu.menuIndex) : menu.menuIndex != null) return false;
        if (createDate != null ? !createDate.equals(menu.createDate) : menu.createDate != null) return false;
        if (updateDate != null ? !updateDate.equals(menu.updateDate) : menu.updateDate != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) menuId;
        result = 31 * result + (menuName != null ? menuName.hashCode() : 0);
        result = 31 * result + (url != null ? url.hashCode() : 0);
        result = 31 * result + (menuIndex != null ? menuIndex.hashCode() : 0);
        result = 31 * result + (activeFlag ? 1 : 0);
        result = 31 * result + (createDate != null ? createDate.hashCode() : 0);
        result = 31 * result + (updateDate != null ? updateDate.hashCode() : 0);
        return result;
    }
}
