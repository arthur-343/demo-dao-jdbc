import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Departament;
import model.entities.Seller;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {


        Departament obj = new Departament(1,"aaa");
        Seller se = new Seller(1, "ars","ars@", LocalDate.of(2002,12,22), 3000.0, obj);
        System.out.println(se);
        SellerDao sellerDao = DaoFactory.createSellerDao();
        Seller seller = sellerDao.findById(5);
        System.out.println(seller);
    }
}