package dao.interfaces;

import java.util.List;

public interface BasicCrud <T>{

    public T getById(Integer id);
    public List<T> getAll();
    public Integer deleteById(Integer id); //return id of deleted record

    public Integer insert(T data);//return the id of the inserted obj

    public Integer update(Object ...data);//return id of updated obj


}
