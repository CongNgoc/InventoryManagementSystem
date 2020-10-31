package inventory.model;

import javax.persistence.*;
import java.sql.Time;
import java.util.Date;

@Entity
@Table(name = "PRODUCT_IN_STOCK", schema = "QLKHO_ADMIN", catalog = "")
public class ProductInStock {
    private short productStockId;
    private long quanity;
    private boolean activeFlag;
    private Date createDate;
    private Date updateDate;
    private short productId;
    private ProductInfo productInfo;

    public ProductInfo getProductInfo() {
        return productInfo;
    }

    public void setProductInfo(ProductInfo productInfo) {
        this.productInfo = productInfo;
    }

    @Id
    @Column(name = "PRODUCT_STOCK_ID", nullable = false, precision = 0)
    public short getProductStockId() {
        return productStockId;
    }

    public void setProductStockId(short productStockId) {
        this.productStockId = productStockId;
    }

    @Basic
    @Column(name = "QUANITY", nullable = false, precision = 0)
    public long getQuanity() {
        return quanity;
    }

    public void setQuanity(long quanity) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProductInStock that = (ProductInStock) o;

        if (productStockId != that.productStockId) return false;
        if (quanity != that.quanity) return false;
        if (activeFlag != that.activeFlag) return false;
        if (createDate != null ? !createDate.equals(that.createDate) : that.createDate != null) return false;
        if (updateDate != null ? !updateDate.equals(that.updateDate) : that.updateDate != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) productStockId;
        result = 31 * result + (int) (quanity ^ (quanity >>> 32));
        result = 31 * result + (activeFlag ? 1 : 0);
        result = 31 * result + (createDate != null ? createDate.hashCode() : 0);
        result = 31 * result + (updateDate != null ? updateDate.hashCode() : 0);
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
}
