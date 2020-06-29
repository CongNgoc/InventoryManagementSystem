package inventory.model;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.awt.*;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Invoice {
    private short invoiceId;
    private boolean type;
    private long price;
    private boolean activeFlag;
    private Date createDate;
    private Date updateDate;
    private short userId;
    private Date toDate;
    private Date fromDate;
    private String code;
    private List<InvoiceDetail> invoiceDetails = new ArrayList<InvoiceDetail>();

    public List<InvoiceDetail> getInvoiceDetails() {
        return invoiceDetails;
    }

    public void setInvoiceDetails(List<InvoiceDetail> invoiceDetails) {
        this.invoiceDetails = invoiceDetails;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    @Id
    @Column(name = "INVOICE_ID", nullable = false, precision = 0)
    public short getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(short invoiceId) {
        this.invoiceId = invoiceId;
    }

    @Basic
    @Column(name = "TYPE", nullable = false, precision = 0)
    public boolean isType() {
        return type;
    }

    public void setType(boolean type) {
        this.type = type;
    }

    @Basic
    @Column(name = "PRICE", nullable = false, precision = 0)
    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
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
    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @Basic
    @Column(name = "UPDATE_DATE", nullable = false)
    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Invoice invoice = (Invoice) o;

        if (invoiceId != invoice.invoiceId) return false;
        if (type != invoice.type) return false;
        if (price != invoice.price) return false;
        if (activeFlag != invoice.activeFlag) return false;
        if (createDate != null ? !createDate.equals(invoice.createDate) : invoice.createDate != null) return false;
        if (updateDate != null ? !updateDate.equals(invoice.updateDate) : invoice.updateDate != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) invoiceId;
        result = 31 * result + (type ? 1 : 0);
        result = 31 * result + (int) (price ^ (price >>> 32));
        result = 31 * result + (activeFlag ? 1 : 0);
        result = 31 * result + (createDate != null ? createDate.hashCode() : 0);
        result = 31 * result + (updateDate != null ? updateDate.hashCode() : 0);
        return result;
    }

    @Basic
    @Column(name = "USER_ID", nullable = false, precision = 0)
    public short getUserId() {
        return userId;
    }

    public void setUserId(short userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "CODE", nullable = true, length = 30)
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
