package service;

import MyException.MyException;

public interface GenericService<T> {
    String add(Long  hospitalId, T t) throws MyException;

    void removeById(Long id);

    String updateById(Long id, T t);
}
