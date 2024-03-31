package dao;

import MyException.MyException;

public interface GenericDao <T> {
    String add(Long  hospitalId, T t) throws MyException;

    void removeById(Long id);

    String updateById(Long id, T t);
}
