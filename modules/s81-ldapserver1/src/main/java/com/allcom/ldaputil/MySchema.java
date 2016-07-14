package com.allcom.ldaputil;

import org.apache.directory.api.ldap.model.schema.SchemaObject;
import org.apache.directory.api.ldap.model.schema.SchemaObjectType;

import java.util.List;
import java.util.Map;

/**
 * Created by ljy on 16/7/14.
 * ok
 */
public class MySchema implements SchemaObject{
    private String oid;
    private List<String> names;
    private String name;
    private String description;
    private String specification;
    private boolean isEnabled;
    private boolean isReadOnly;
    private boolean isObsolete;
    private String schemaName;

    public MySchema(){

    }

    @Override
    public String getOid() {
        return this.oid;
    }

    @Override
    public void setOid(String s) {
        this.oid = s;
    }

    @Override
    public List<String> getNames() {
        return this.names;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void addName(String... strings) {
        for(String name:strings){
            this.names.add(name);
        }
    }

    @Override
    public void setNames(List<String> list) {
        this.names = list;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public void setDescription(String s) {
        this.description = s;
    }

    @Override
    public String getSpecification() {
        return this.specification;
    }

    @Override
    public void setSpecification(String s) {
        this.specification = s;
    }

    @Override
    public boolean isEnabled() {
        return this.isEnabled;
    }

    @Override
    public boolean isDisabled() {
        return false;
    }

    @Override
    public void setEnabled(boolean b) {
        this.isEnabled = b;
    }

    @Override
    public boolean isReadOnly() {
        return this.isReadOnly;
    }

    @Override
    public void setReadOnly(boolean b) {
        this.isReadOnly = b;
    }

    @Override
    public boolean isObsolete() {
        return this.isObsolete;
    }

    @Override
    public void setObsolete(boolean b) {
        this.isObsolete = b;
    }

    @Override
    public Map<String, List<String>> getExtensions() {
        return null;
    }

    @Override
    public boolean hasExtension(String s) {
        return false;
    }

    @Override
    public List<String> getExtension(String s) {
        return null;
    }

    @Override
    public void addExtension(String s, String... strings) {

    }

    @Override
    public void addExtension(String s, List<String> list) {

    }

    @Override
    public void setExtensions(Map<String, List<String>> map) {

    }

    @Override
    public SchemaObjectType getObjectType() {
        return null;
    }

    @Override
    public String getSchemaName() {
        return this.schemaName;
    }

    @Override
    public void setSchemaName(String s) {
        this.schemaName = s;
    }

    @Override
    public SchemaObject copy() {
        return null;
    }

    @Override
    public SchemaObject copy(SchemaObject schemaObject) {
        return null;
    }

    @Override
    public void clear() {

    }

    @Override
    public void lock() {

    }
}
