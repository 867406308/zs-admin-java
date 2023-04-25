package generator.domain;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import javax.validation.constraints.NotNull;

import java.io.Serializable;

import org.hibernate.validator.constraints.Length;

/**
* 组织结构
* @TableName sys_org
*/
public class SysOrg implements Serializable {

    /**
    * 
    */
    @NotNull(message="[]不能为空")
    private Long sysOrgId;
    /**
    * 
    */
    private Long pid;
    /**
    * 
    */
    @Size(max= 255,message="编码长度不能超过255")
    @Length(max= 255,message="编码长度不能超过255")
    private String orgName;

    /**
    * 
    */
    private void setSysOrgId(Long sysOrgId){
    this.sysOrgId = sysOrgId;
    }

    /**
    * 
    */
    private void setPid(Long pid){
    this.pid = pid;
    }

    /**
    * 
    */
    private void setOrgName(String orgName){
    this.orgName = orgName;
    }


    /**
    * 
    */
    private Long getSysOrgId(){
    return this.sysOrgId;
    }

    /**
    * 
    */
    private Long getPid(){
    return this.pid;
    }

    /**
    * 
    */
    private String getOrgName(){
    return this.orgName;
    }

}
