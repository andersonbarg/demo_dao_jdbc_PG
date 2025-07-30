package application;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import model.entities.Department;
import model.entities.Seller;

public class Program {

	public static void main(String[] args) throws ParseException {

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		
		Department dep = new Department(1, "Books");
		
		Seller seller = new Seller(21, "Bob", "bob@gmail.com", new Date(sdf.parse("18/04/1985").getTime()), 3000.0, dep);
		
		System.out.println(dep);
		
		System.out.println(seller);
		
		System.out.println(sdf.format(seller.getBirthDate()));

	}

}
