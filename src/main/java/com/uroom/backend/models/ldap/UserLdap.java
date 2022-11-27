package com.uroom.backend.models.ldap;

import org.springframework.ldap.odm.annotations.Attribute;
import org.springframework.ldap.odm.annotations.Entry;
import org.springframework.ldap.odm.annotations.Id;

import javax.naming.Name;

@Entry(objectClasses = {"inetOrgPerson", "posixAccount", "top"})
public final class UserLdap {

    @Id
    private Name dn;

    @Attribute(name = "uid")
    private String uid;

    @Attribute(name = "gidNumber")
    private String gidNumber;

    @Attribute(name = "uidNumber")
    private String uidNumber;

    @Attribute(name = "homeDirectory")
    private String homeDirectory;

    @Attribute(name = "sn")
    private String sn;

    @Attribute(name = "cn")
    private String email;

    @Attribute(name = "userPassword")
    private String password;

    @Attribute(name = "telexNumber")
    private String age;

    @Attribute(name = "telephoneNumber")
    private String cellphone;

    public Name getDn() {
        return dn;
    }

    public void setDn(Name dn) {
        this.dn = dn;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUidNumber() {
        return uidNumber;
    }

    public String getGidNumber() {
        return gidNumber;
    }

    public void setGidNumber(String gidNumber) {
        this.gidNumber = gidNumber;
    }

    public void setUidNumber(String uidNumber) {
        this.uidNumber = uidNumber;
    }

    public String getHomeDirectory() {
        return homeDirectory;
    }

    public void setHomeDirectory(String homeDirectory) {
        this.homeDirectory = homeDirectory;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getCellphone() {
        return cellphone;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }
}
