package tvik4i_db2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class TVIK4I_DB2 {

	static Connection conn = null;
    static Statement s = null;
    static Scanner sc = new Scanner(System.in);
    static PreparedStatement ps = null;
	private static ResultSet rs;
    
    static String url = "jdbc:oracle:thin:@193.6.5.58:1521:XE";
    static String user = "H22_tvik4i";
    static String pwd = "TVIK4I";
	
	public static void main(String[] args) throws ParseException {
		//Connect();
		Bejelentkezes();
		DriverReg();
		
		TablaTorlese();
		
		StatikusTablaLetrehozas();
		StatikusTablaModositas();
		StatikusAdatfelvitel();
		DinamikusAdatfelvitel();
		//DinamikusErtekfelvetel();
		DinamikusAdattorles();
		StatikusAdattorles();
		StatikusLekerdezes();
		ModosithatoKurzor();
		DinamikusLekerdezes();
		StatikusLekerdezes();
		
		//LeKapcs();
		
	}

    public static void DriverReg() {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            System.out.println("Sikeres driver regisztrálás\n");
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
    
    public static void Bejelentkezes() {
        try {
        	System.out.println("Kérem az url-t: ");
        	String url1 = sc.next().trim();
        	System.out.println("Kérem az user-t: ");
        	String user1 = sc.next().trim();
        	System.out.println("Kérem a pwd-t: ");
        	String pwd1 = sc.next().trim();
            conn = DriverManager.getConnection(url1, user1, pwd1);
            System.out.println("Sikeres kapcsolódás\n");
        } catch(Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public static void Connect() {

        try {
            conn = DriverManager.getConnection(url, user, pwd);
            System.out.println("Sikeres kapcsolódás\n");
        } catch(Exception e) {
            System.err.println(e.getMessage());
        }
    }
    
    public static void TablaTorlese() {
    	try {
    		/*String sqlp = "DROP TABLE phone";
    		s = conn.createStatement();
    		s.executeUpdate(sqlp);*/
    		
    		String sqlp_webShop = "DROP TABLE webShop";
    		String sqlp_customer = "DROP TABLE customer";
    		String sqlp_phone = "DROP TABLE phone";
    		s = conn.createStatement();
    		s.executeUpdate(sqlp_webShop);
    		System.out.println("WebShop table deleted!\n");
    		s.executeUpdate(sqlp_customer);
    		System.out.println("customer table deleted!\n");
    		s.executeUpdate(sqlp_phone);
    		System.out.println("Phone table deleted!\n");
    		s.close();
    	} catch(Exception ex) {
    		System.err.println(ex.getMessage());
    	}
    }

    public static void LeKapcs() {
        if (conn != null) {
            try {
                conn.close();
                System.out.println("Sikeres lekapcsolódás\n");
            } catch(Exception ex) {
                System.err.println(ex.getMessage());
            }
        }
    }

    public static void StatikusTablaLetrehozas() {
        String sqlp_phone = "create table phone "
        		+ "("
        		+ "id int not null, "
        		+ "type char(10) not null, "
        		+ "color char(10) default 'white', "
                + "release date, "
        		+ "price integer not null, "
        		+ "condition char(10), "
                + "primary key (id)"
        		+ ")";
        
        String sqlp_webShop = "create table webShop "
        		+ "(" 
        		+ "id numeric(3) not null, "
                + "name char(30) not null, "
        		+ "address char(30), "
        		+ "url char(40), "
                + "foundation date, "
        		+ "primary key (id)"
                + ")";
        
        String sqlp_customer = "create table customer "
        		+ "("
        		+ "identityN int not null, "
                + "name char(30) not null, "
        		+ "address char(30), "
                + "salary int, "
                + "birthday date, "
        		+ "primary key (identityN)"
                + ")";
        
         if (conn != null) {
        	try {
        		s = conn.createStatement();
        		s.executeUpdate(sqlp_phone);
        		System.out.println("Phone table was created\n");
        		s.executeUpdate(sqlp_webShop);
        		System.out.println("WebShop table was created\n");
        		s.executeUpdate(sqlp_customer);
        		System.out.println("customer table was created\n");
        		s.close(); //erőforrás felszabadítása
        	} catch(Exception ex) {
        		System.err.println(ex.getMessage());
        	}
        }
    }
    
    public static void StatikusTablaModositas() {
        //eltárol
        if (conn != null) {
        	try {
        		String sqlp = "alter table customer add(id references phone)";
        		s = conn.createStatement();
        		s.executeUpdate(sqlp);
        		System.out.println("Phone table modified\n");
        		s.close();
        	} catch(Exception ex) {
        		System.err.println(ex.getMessage());
        	}
        }
    }
    
    public static void StatikusAdatfelvitel() {
    	if (conn != null) {
    		String[] sqlp_ws = {
    				"insert into webShop values(123, 'Phone Shop', '1st street', 'https://www.phoneshop.com', to_date('2010.07.10', 'yyyy.mm.dd'))",
    				"insert into webShop values(456, 'Mobile Buys', '2nd street', 'https://www.mobilebuys.com', to_date('2014.04.14', 'yyyy.mm.dd'))"	
    		};
    		
    		String[] sqlp_phone = {
    				"insert into phone values(1, 'Samsung', 'red', to_date('2020.01.01', 'yyyy.mm.dd'), 165000, 'new')",
    				"insert into phone values(2, 'IPhone', 'black', to_date('2016.10.21', 'yyyy.mm.dd'), 280000, 'used')",
    				"insert into phone values(3, 'Huawei', 'white', to_date('2021.05.17', 'yyyy.mm.dd'), 150000, 'new')",
    				"insert into phone values(4, 'LG', 'blue', to_date('2022.08.10', 'yyyy.mm.dd'), 300000, 'new')"
    		};
    		
    		String[] sqlp_customer = {
    				"insert into customer values(12345678, 'Kiss Béla', 'Budapest, Pöttyös utca 69', 1200000, to_date('1990.04.12', 'yyyy.mm.dd'), 1)",
    				"insert into customer values(87654321, 'Nagy Ferenc', 'Miskolc, Kockás utca 420', 3400000, to_date('1994.12.05', 'yyyy.mm.dd'), 2)",
    				"insert into customer values(12398746, 'Kovács Dániel', 'Debrecen, Hős utca 84', 5400000, to_date('1985.03.24', 'yyyy.mm.dd'), 3)",
    				"insert into customer values(54637281, 'Horváth Bence', 'Gyál, Damjanich utca 10', 2300000, to_date('1997.07.17', 'yyyy.mm.dd'), 4)"
    		};
    		
    		for (int i=0; i<sqlp_ws.length; i++) {
        		try {
        			s = conn.createStatement();
        			s.executeUpdate(sqlp_ws[i]);
        			System.out.println("webShop recorded\n");
        			s.close();
        		} catch(Exception ex) {
        			System.out.println(ex.getMessage());
        		}
    		}
    		for (int i=0; i<sqlp_phone.length; i++) {
    			try {
    				s = conn.createStatement();
    				s.executeUpdate(sqlp_phone[i]);
    				System.out.println("Phone recorded\n");
    				s.close();
    			} catch(Exception ex) {
    				System.out.println(ex.getMessage());
    			}
    		}
    		for (int i=0; i<sqlp_customer.length; i++) {
    			try {
    				s = conn.createStatement();
    				s.executeUpdate(sqlp_customer[i]);
    				System.out.println("customer recorded\n");
    				s.close();
    			} catch(Exception ex) {
    				System.out.println(ex.getMessage());
    			}
    		}
    	}
    }
    
    public static void DinamikusAdattorles() {
    	System.out.println("customer to delete: ");
    	String id = sc.next();
        //Az sql parancsban a ? helyére kerülnek a paraméterek
        String sqlp = "delete from " + user + ".customer where id=?";
        if (conn != null) {
        	try {
        		ps = conn.prepareStatement(sqlp);
        		ps.setString(1, id);
        		ps.executeUpdate();
        		ps.close();
        		System.out.println("customer with "+id+" id was deleted dynamically\n");
        	} catch(Exception ex) {
        		System.err.println(ex.getMessage());
        	}
        }
    }
    
    public static void StatikusAdattorles() {
    	System.out.println("customer to delete: ");
    	String id = sc.next();
    	String sqlp = "delete from customer where id like'"+id+"'";
    	if (conn != null) {
    		try {
    			s = conn.createStatement();
    			s.executeUpdate(sqlp);
    			s.close();
    			System.out.println("Cosumer with " + id + " id was deleted\n");
    		} catch(Exception ex) {
    			System.err.println(ex.getMessage());
    		}
    	}
    }
    
    public static void StatikusLekerdezes() {
    	System.out.println("Melyik tábla adatait szeretnéd látni? ");
    	String table = sc.next().trim();
    	if (conn != null) {
    		if(table.equals("phone")) {
        		String  sqlp = "select * from phone";
        		System.out.println("ID"+  "\t\t" +" Type"+"\t\t"+" Color"+"\t\t"+" Release"+"\t\t"+" Price"+"\t\t"+" Condition");
        		try {
        			s = conn.createStatement();
        			s.executeQuery(sqlp);
        			rs = s.getResultSet();
        			while(rs.next()) {
        				//A get metódusoknak a megfelelő típusú táblamezőket kell megadni
        				int id = rs.getInt("id");
        				String type = rs.getString("type");
        				String color = rs.getString("color");
        				String release = rs.getString("release");
        				Integer price = rs.getInt("price");
        				String condition = rs.getString("condition");
        				System.out.println(id + "\t\t" + type +  "\t" + color + "\t" + 
        				release + "\t" + price + "\t\t" + condition);
        			}
        			rs.close();
        		} catch(Exception ex) {
        			System.err.println(ex.getMessage());
        		}
    		} else if(table.equals("customer")) {
        		String  sqlp = "select * from customer";
        		System.out.println("IdentityN"+  "\t\t" +" Name"+"\t\t\t\t"+" Address"+"\t\t\t"+" Salary"+"\t\t"+" Birthday");
        		try {
        			s = conn.createStatement();
        			s.executeQuery(sqlp);
        			rs = s.getResultSet();
        			while(rs.next()) {
        				//A get metódusoknak a megfelelő típusú táblamezőket kell megadni
        				int identityN = rs.getInt("identityN");
        				String name = rs.getString("name");
        				String address = rs.getString("address");
        				int salary = rs.getInt("salary");
        				String birthday = rs.getString("birthday");
        				System.out.println(identityN + "\t\t" + name +  "\t" + address + "\t" + 
        						salary + "\t" + birthday);
        			}
        			rs.close();
        		} catch(Exception ex) {
        			System.err.println(ex.getMessage());
        		}
    		} else if(table.equals("webShop")) {
        		String  sqlp = "select * from webShop";
        		System.out.println("ID"+  "\t\t" +" Name"+"\t\t"+" Address"+"\t\t"+" URL"+"\t\t"+" Foundation");
        		try {
        			s = conn.createStatement();
        			s.executeQuery(sqlp);
        			rs = s.getResultSet();
        			while(rs.next()) {
        				//A get metódusoknak a megfelelő típusú táblamezőket kell megadni
        				long id = rs.getLong("id");
        				String name = rs.getString("name");
        				String address = rs.getString("address");
        				String url = rs.getString("url");
        				String foundation = rs.getString("foundation");
        				System.out.println(id + "\t\t" + name +  "\t" + address + "\t" + 
        						url + "\t" + foundation);
        			}
        			rs.close();
        		} catch(Exception ex) {
        			System.err.println(ex.getMessage());
        		}
    		} else {
    			System.out.println("Ilyen tábla nincs!");
    			StatikusLekerdezes();
    		}
    	}
    }
    
    public static Date StringToDate(String release) throws ParseException {
        //Instantiating the SimpleDateFormat class
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd");
        //Parsing the given String to Date object
        Date releaseDate = formatter.parse(release);
        System.out.println("Date object value: " + releaseDate);
        return releaseDate;
     }
    
    public static void DinamikusAdatfelvitel() throws ParseException {
    	if (conn != null) {
    		//Az SQL parancsban a ? helyére kerülnek a paraméterek
    		String sqlp = "insert into phone (id, type, color, release, price, condition)" +
    		"values (?, ?, ?, ?, ?, ?)";
    		
    		System.out.println("Kérem az id-t: ");
    		String id = sc.next().trim();
    		System.out.println("Kérem a típust: ");
    		String type = sc.next().trim();
    		System.out.println("Kérem a színt: ");
    		String color = sc.next().trim();
    		System.out.println("Kérem a megjelenési dátumot: (yyyy.mm.dd) ");
    		String release = sc.next().trim();
    		Date releaseDate = TVIK4I_DB2.StringToDate(release);
    		java.sql.Date sqlDate = new java.sql.Date(releaseDate.getTime());
    		System.out.println("Kérem a árat: ");
    		float price = sc.nextFloat();
    		System.out.println("Kérem az állapotát: ");
    		String condition = sc.next().trim();
    		try {
    			ps = conn.prepareStatement(sqlp);
    			ps.setString(1, id);
    			ps.setString(2, type);
    			ps.setString(3, color);
    			ps.setDate(4, sqlDate);
    			ps.setFloat(5, price);
    			ps.setString(6, condition);
    			ps.executeUpdate();
    			ps.close();
    			System.out.println("Telefon felvéve\n");
    		} catch(Exception ex) {
    			System.err.println(ex.getMessage());
    			System.out.println("Az ID létezik, adjon meg mást!");
    			DinamikusAdatfelvitel();
    		}
    	}
    }
    
    public static void ModosithatoKurzor() {
		System.out.println("Color: ");
		String color = sc.next().trim();
		String sqlp = "select price from phone where color= '"+color+"'";
		if(conn != null) {
			try {
				s=conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE);
				rs=s.executeQuery(sqlp);
				while(rs.next()) {
					int oldPrice = rs.getInt("price");
					rs.updateInt("price", (oldPrice*2));
					rs.updateRow();
				}
			}catch(Exception ex) {
				System.err.println(ex.getMessage());
			}
		}
		System.out.println("Phone price with "+color+" color was doubled");
	}
	
	public static void DinamikusLekerdezes() {
		System.out.println("WebShop ID-je: ");
		String id = sc.next().trim();
		String sqlp = "select name from webShop where id like '"+id+"'";
		if(conn != null) {
			try {
				s=conn.createStatement();
				s.executeQuery(sqlp);
				rs=s.getResultSet();
				while(rs.next()) {
						String name = rs.getString("name");
						
						System.out.println("Name: "+name);
				}
				rs.close();
			}catch(Exception ex) {
				System.out.println(ex.getMessage());
			}
		}
	}


}
