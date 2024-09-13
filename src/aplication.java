import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.dao.impl.DepartmentDaoJDBC;
import model.entities.Department;

import java.util.ArrayList;
import java.util.List;

public class aplication {


//    DepartmentDao departmentDao = DaoFactory.createDepartmentDao();
public static void main(String[] args) {
//    DepartmentDao departmentDao = DaoFactory.createDepartmentDao();
//    Department de1 = departmentDao.findById(1);
//    System.out.println(de1);
//    Department dep = new Department(null, "aaaaa");
//    DepartmentDao departmentDao1 = DaoFactory.createDepartmentDao();
////    Department de2 = departmentDao1.insert(dep);
//        departmentDao1.insert(dep);
//    System.out.println(dep);
//    DepartmentDao departmentDao = DaoFactory.createDepartmentDao();
//    departmentDao.deleteById(6);
//
//    DepartmentDao departmentDao = DaoFactory.createDepartmentDao();
//    Department deep = departmentDao.findById(7);
//    if (deep != null) {
//        System.out.println("Before update: " + deep);
//        deep.setName("ars23");
//        departmentDao.update(deep);
//        Department updatedDeep = departmentDao.findById(7);
//        System.out.println("After update: " + updatedDeep);
//    } else {
//        System.out.println("Department not found.");
//    }

    DepartmentDao departmentDao = DaoFactory.createDepartmentDao();
    List<Department> list = new ArrayList<>();
    list = departmentDao.findAll();
    for (Department item : list) {
        System.out.println(item);
    }
    }
}


