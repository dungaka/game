package com.wyd.exchange.bean;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
/**
 * The persistent class for the tab_application database table.
 * 
 * @author BEA Workshop
 */
@Entity()
@Table(name = "tab_group_integral")
public class GroupIntegral implements Serializable {
    // default serial version id, required for serializable classes.
    private static final long serialVersionUID = 1L;
    private Integer           id;
    private String            services;             // 服务器id
    private String            remark;               // 组描述
    private String            info;         // 弹王挑战赛相关配置

    @Id()
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", unique = true, nullable = false, precision = 10)
    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic()
    @Column(name = "services", length = 32)
    public String getServices() {
        return services;
    }

    public void setServices(String services) {
        this.services = services;
    }
    
    @Basic()
    @Column(name = "remark", length = 255)
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Basic()
    @Column(name = "info", length = 255)
    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof GroupIntegral)) {
            return false;
        }
        GroupIntegral castOther = (GroupIntegral) other;
        return new EqualsBuilder().append(this.getId(), castOther.getId()).isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder().append(getId()).toHashCode();
    }

    public String toString() {
        return new ToStringBuilder(this).append("id", getId()).toString();
    }
}