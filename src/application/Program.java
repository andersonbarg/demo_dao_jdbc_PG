package application;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class Program {

	public static void main(String[] args) throws ParseException {

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		
		SellerDao sellerDao = DaoFactory.createSellerDao();

		System.out.println("=== TEST1: Seller findById ===");
		Seller seller = sellerDao.findById(3);
		System.out.println(seller);
		
		System.out.println("\n=== TEST2: Seller findByDepartment ===");
		Department department = new Department(2, null);
		List<Seller> list = sellerDao.findByDepartment(department);
		for (Seller obj : list) {
			System.out.println(obj);
		}
		
		System.out.println("\n=== TEST3: Seller findAll ===");
		list = sellerDao.findAll();
		for (Seller obj : list) {
			System.out.println(obj);
		}
		
		System.out.println("\n=== TEST4: Seller insert ===");
		Seller newSeller = new Seller(null, "Greg", "greg@gmail.com", sdf.parse("14/06/1982"), 4000.0, department);
		sellerDao.insert(newSeller);
		System.out.println("Inserted! New id = " + newSeller.getId());
	
		System.out.println("\n=== TEST5: Seller update ===");
		seller = sellerDao.findById(1);
		seller.setName("Martha Waine");
		seller.setEmail("martha@hotmail.com");
		seller.setBirthDate(sdf.parse("18/02/1978"));
		seller.setBaseSalary(3520.0);
		sellerDao.update(seller);
		System.out.println("Update completed!");
		System.out.println("===========================");
		list = sellerDao.findAll();
		for (Seller obj : list) {
			System.out.println(obj);
		}

		/*
		 * 
		 * 
		 */
	}
}
