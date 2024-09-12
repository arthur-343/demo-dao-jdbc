import model.dao.DaoFactory;
import model.dao.SellerDao;

import model.entities.Department;
import model.entities.Seller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public class Main {
    public static void main(String[] args) {


        Department obj = new Department(1,"aaa");
        Seller se = new Seller(1, "ars","ars@", LocalDate.of(2002,12,22), 3000.0, obj);
        System.out.println(se);
        SellerDao sellerDao = DaoFactory.createSellerDao();
        Seller seller = sellerDao.findById(5);
        System.out.println(seller);
        System.out.println("----------------------------------------------------------------------------");
        Department dep = new Department(1, null);
        List<Seller> sellerList = sellerDao.findByDep(dep);
        for (Seller seller1 : sellerList){
            System.out.println(seller1);
        }

    System.out.println("----------------------------------------------------------------------------");
         sellerList = sellerDao.findAll();
        for (Seller seller1 : sellerList){
        System.out.println(seller1);
    }
        System.out.println("----------------------------------------------------------------------------");
//        Seller seller1 = new Seller(null, "ars", "ars@", LocalDate.of(2002, 12, 22),2000.0, dep);
//        sellerDao.insert(seller1);
//        System.out.println(seller1);

        System.out.println("----------------------------------------------------------------------------");
        se = sellerDao.findById(1);
        se.setName("Bruce Wayne");
        sellerDao.update(se);
        System.out.println("update complete");

        System.out.println("----------------------------------------------------------------------------");
        sellerDao.deleteById(16);
        System.out.println("delete complete");
    }}