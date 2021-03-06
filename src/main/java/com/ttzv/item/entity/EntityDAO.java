package com.ttzv.item.entity;

import javax.naming.NamingException;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.sql.SQLException;
import java.util.List;

public interface EntityDAO<T> {

    public List<T> getAllEntities() throws SQLException, IOException, NamingException, GeneralSecurityException;
    public T getEntity(String id) throws SQLException, IOException, NamingException;
    public boolean updateEntity(T entity) throws SQLException, IOException;
    public boolean deleteEntity(T entity) throws SQLException, IOException;
    public int[] syncDataSourceWith(EntityDAO<T> entityDAO) throws SQLException, NamingException, IOException, GeneralSecurityException;

}
