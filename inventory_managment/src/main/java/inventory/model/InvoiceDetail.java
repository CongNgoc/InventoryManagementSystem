package inventory.model;

import javax.persistence.*;
import java.sql.Time;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Entity
@Table(name = "INVOICE_DETAIL", schema = "QLKHO_ADMIN", catalog = "")
public class InvoiceDetail {
    private short quanity;
    private boolean activeFlag;
    private Date createDate;
    private Date updateDate;
    private short inDeId;
    private short productId;
    private short invoiceId;
    private ProductInfo productInfo;
    private Long amount;
    static Map<Short, InvoiceDetail> mapQuantityForProduct = new HashMap<>();

    public static Map<Short, InvoiceDetail> getMapQuantityForProduct() {
        return mapQuantityForProduct;
    }

    public static void setMapQuantityForProduct(Map<Short, InvoiceDetail> mapQuantityForProduct) {
        InvoiceDetail.mapQuantityForProduct = mapQuantityForProduct;
    }

    public ProductInfo getProductInfo() {
        return productInfo;
    }

    public void setProductInfo(ProductInfo productInfo) {
        this.productInfo = productInfo;
    }

    public Long getAmount() {
        if(productInfo != null && productInfo.getProductInfoId() != 0 && quanity >= 0) {
            this.amount = this.quanity * productInfo.getPrice();
        }
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    @Basic
    @Column(name = "QUANITY", nullable = false, precision = 0)
    public short getQuanity() {
        return quanity;
    }

    public void setQuanity(short quanity) {
        this.quanity = quanity;
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

    @Id
    @Column(name = "IN_DE_ID", nullable = false, precision = 0)
    public short getInDeId() {
        return inDeId;
    }

    public void setInDeId(short inDeId) {
        this.inDeId = inDeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        InvoiceDetail that = (InvoiceDetail) o;

        if (quanity != that.quanity) return false;
        if (activeFlag != that.activeFlag) return false;
        if (inDeId != that.inDeId) return false;
        if (createDate != null ? !createDate.equals(that.createDate) : that.createDate != null) return false;
        if (updateDate != null ? !updateDate.equals(that.updateDate) : that.updateDate != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) quanity;
        result = 31 * result + (activeFlag ? 1 : 0);
        result = 31 * result + (createDate != null ? createDate.hashCode() : 0);
        result = 31 * result + (updateDate != null ? updateDate.hashCode() : 0);
        result = 31 * result + (int) inDeId;
        return result;
    }

    @Basic
    @Column(name = "PRODUCT_ID", nullable = false, precision = 0)
    public short getProductId() {
        return productId;
    }

    public void setProductId(short productId) {
        this.productId = productId;
    }

    @Basic
    @Column(name = "INVOICE_ID", nullable = false, precision = 0)
    public short getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(short invoiceId) {
        this.invoiceId = invoiceId;
    }
}
