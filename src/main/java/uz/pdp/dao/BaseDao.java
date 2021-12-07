package uz.pdp.dao;

import java.util.List;
import java.util.Objects;

public interface BaseDao <T>{
   void add(T obj);
   void delete(int index);
   List<T> list();
   void reset(int index, T obj);

}
