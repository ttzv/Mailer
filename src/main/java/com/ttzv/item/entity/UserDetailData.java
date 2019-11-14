package com.ttzv.item.entity;

import java.sql.DatabaseMetaData;

public enum UserDetailData implements Mappable {
    guid("Guid"),
    position("Position"),
    initMailPass("InitialMailPassword"),
    notes("Notes");

    String name;

    UserDetailData(String name) {
        this.name = name;
    }

    @Override
    public String getDbKey(KeyMapper keyMapper) {
        return keyMapper.getCorrespondingMapping(this.toString(), KeyMapper.DBKEY);
    }

    @Override
    public String getLdapKey(KeyMapper keyMapper) {
        return keyMapper.getCorrespondingMapping(this.toString(), KeyMapper.LDAPKEY);
    }
}
