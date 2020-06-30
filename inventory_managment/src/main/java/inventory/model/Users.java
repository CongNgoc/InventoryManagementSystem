package inventory.model;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Users {
    private short userId;
    private String firstName;
    private String lastName;
    private Date birthday;
    private String userName;
    private String password;
    private String email;
    private String phone;
    private boolean activeFlag;
    private Date createDate;
    private Date updateDate;

    @Id
    @Column(name = "USER_ID", nullable = false, precision = 0)
    public short getUserId() {
        return userId;
    }

    public void setUserId(short userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "FIRST_NAME", nullable = false, length = 25)
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Basic
    @Column(name = "LAST_NAME", nullable = false, length = 25)
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Basic
    @Column(name = "BIRTHDAY", nullable = false)
    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    @Basic
    @Column(name = "USER_NAME", nullable = false, length = 30)
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Basic
    @Column(name = "PASSWORD", nullable = false, length = 30)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Basic
    @Column(name = "EMAIL", nullable = true, length = 50)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Basic
    @Column(name = "PHONE", nullable = true, length = 12)
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

        Users users = (Users) o;

        if (userId != users.userId) return false;
        if (activeFlag != users.activeFlag) return false;
        if (firstName != null ? !firstName.equals(users.firstName) : users.firstName != null) return false;
        if (lastName != null ? !lastName.equals(users.lastName) : users.lastName != null) return false;
        if (birthday != null ? !birthday.equals(users.birthday) : users.birthday != null) return false;
        if (userName != null ? !userName.equals(users.userName) : users.userName != null) return false;
        if (password != null ? !password.equals(users.password) : users.password != null) return false;
        if (email != null ? !email.equals(users.email) : users.email != null) return false;
        if (phone != null ? !phone.equals(users.phone) : users.phone != null) return false;
        if (createDate != null ? !createDate.equals(users.createDate) : users.createDate != null) return false;
        if (updateDate != null ? !updateDate.equals(users.updateDate) : users.updateDate != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) userId;
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (birthday != null ? birthday.hashCode() : 0);
        result = 31 * result + (userName != null ? userName.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        result = 31 * result + (activeFlag ? 1 : 0);
        result = 31 * result + (createDate != null ? createDate.hashCode() : 0);
        result = 31 * result + (updateDate != null ? updateDate.hashCode() : 0);
        return result;
    }
}
