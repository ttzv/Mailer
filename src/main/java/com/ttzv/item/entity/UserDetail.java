package com.ttzv.item.entity;

public class UserDetail implements DynamicEntityCompatible{

    private DynamicEntity userDetailEntity;



    private String guid;
    private String position;
    private String initMailPass;
    private String notes;

    public UserDetail(String guid) {
        this.userDetailEntity = new DynamicEntity();
        this.setGuid(guid);
    }

    public UserDetail(String guid, String position, String initMailPass, String notes) {
        this.userDetailEntity = new DynamicEntity();
        this.setGuid(guid);
        this.setPosition(position);
        this.setInitMailPass(initMailPass);
        this.setNotes(notes);
    }

    public UserDetail(DynamicEntity entity) {
        this.userDetailEntity = entity;
    }

    @Override
    public String getUniqueIdentifier() {
        return getGuid();
    }

    @Override
    public DynamicEntity getEntity() {
        return this.userDetailEntity;
    }

    public String getGuid() {
        return this.userDetailEntity.getValue(UserDetailData.guid.toString()); }

    public void setGuid(String guid) {
        if(!this.userDetailEntity.setValue(UserDetailData.guid.toString(), guid)){
            this.userDetailEntity.add(UserDetailData.guid.toString(), guid);
        }
    }

    public String getPosition() {
        return this.userDetailEntity.getValue(UserDetailData.position.toString());
    }

    public void setPosition(String position) {
        if(!this.userDetailEntity.setValue(UserDetailData.position.toString(), position)){
            this.userDetailEntity.add(UserDetailData.position.toString(), position);
        }
    }

    public String getInitMailPass() {
        return this.userDetailEntity.getValue(UserDetailData.initMailPass.toString());
    }

    public void setInitMailPass(String initMailPass) {
        if(!this.userDetailEntity.setValue(UserDetailData.initMailPass.toString(), initMailPass)){
            this.userDetailEntity.add(UserDetailData.initMailPass.toString(), initMailPass);
        }
    }

    public String getNotes() {
        return this.userDetailEntity.getValue(UserDetailData.notes.toString());
    }

    public void setNotes(String notes) {
        if(!this.userDetailEntity.setValue(UserDetailData.notes.toString(), notes)){
            this.userDetailEntity.add(UserDetailData.notes.toString(), notes);
        }
    }

    @Override
    public String toString() {
        return this.userDetailEntity.getList().toString();
    }
}
