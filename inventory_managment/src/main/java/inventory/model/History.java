package inventory.model;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Time;

@Entity
public class History {
    private short historyId;
    private String actionName;
    private boolean type;
    private long qty;
    private long price;
    private boolean activeFlag;
    private Time createDate;
    private Time updateDate;

    @Id
    @Column(name = "HISTORY_ID")
    public short getHistoryId() {
        return historyId;
    }

    public void setHistoryId(short historyId) {
        this.historyId = historyId;
    }

    @Basic
    @Column(name = "ACTION_NAME")
    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    @Basic
    @Column(name = "TYPE")
    public boolean isType() {
        return type;
    }

    public void setType(boolean type) {
        this.type = type;
    }

    @Basic
    @Column(name = "QTY")
    public long getQty() {
        return qty;
    }

    public void setQty(long qty) {
        this.qty = qty;
    }

    @Basic
    @Column(name = "PRICE")
    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
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

        History history = (History) o;

        if (historyId != history.historyId) return false;
        if (type != history.type) return false;
        if (qty != history.qty) return false;
        if (price != history.price) return false;
        if (activeFlag != history.activeFlag) return false;
        if (actionName != null ? !actionName.equals(history.actionName) : history.actionName != null) return false;
        if (createDate != null ? !createDate.equals(history.createDate) : history.createDate != null) return false;
        if (updateDate != null ? !updateDate.equals(history.updateDate) : history.updateDate != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) historyId;
        result = 31 * result + (actionName != null ? actionName.hashCode() : 0);
        result = 31 * result + (type ? 1 : 0);
        result = 31 * result + (int) (qty ^ (qty >>> 32));
        result = 31 * result + (int) (price ^ (price >>> 32));
        result = 31 * result + (activeFlag ? 1 : 0);
        result = 31 * result + (createDate != null ? createDate.hashCode() : 0);
        result = 31 * result + (updateDate != null ? updateDate.hashCode() : 0);
        return result;
    }
}
