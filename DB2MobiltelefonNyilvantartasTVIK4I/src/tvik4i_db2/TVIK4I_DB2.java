package tvik4i_db2;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class TVIK4I_DB2 {

	static Connection conn = null;
    static Statement s = null;
    static Scanner sc = new Scanner(System.in);
    static PreparedStatement ps = null;
	private static ResultSet rs;
	private static CallableStatement cs;
    
    static String url = "jdbc:oracle:thin:@193.6.5.58:1521:XE";
    static String user = "H22_tvik4i";
    static String pwd = "TVIK4I";
	
	public static void main(String[] args) {
		Connect();
		TablaTorlese();
		//DriverReg();
		StatikusTablaLetrehozas();
		StatikusTablaModositas();
		StatikusAdatfelvitel();
		DinamikusAdattorles();
		StatikusAdattorles();
		StatikusLekerdezes();
		ModosithatoKurzor();
		DinamikusLekerdezes();
		StatikusLekerdezes();
		
	}

    public static void DriverReg() {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            System.out.println("Sikeres driver regisztrálás\n");
        } catch (Exception e) {
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
    		String sqlp_webShop = "DROP TABLE webShop";
    		String sqlp_costumer = "DROP TABLE costumer";
    		String sqlp_phone = "DROP TABLE phone";
    		s = conn.createStatement();
    		s.executeUpdate(sqlp_webShop);
    		System.out.println("WebShop table deleted!\n");
    		s.executeUpdate(sqlp_costumer);
    		System.out.println("Costumer table deleted!\n");
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
        
        String sqlp_costumer = "create table costumer "
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
        		s.executeUpdate(sqlp_costumer);
        		System.out.println("Costumer table was created\n");
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
        		String sqlp = "alter table costumer add(id references phone)";
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
    		
    		String[] sqlp_costumer = {
    				"insert into costumer values(12345678, 'Kiss Béla', 'Budapest, Pöttyös utca 69', 1200000, to_date('1990.04.12', 'yyyy.mm.dd'), 1)",
    				"insert into costumer values(87654321, 'Nagy Ferenc', 'Miskolc, Kockás utca 420', 3400000, to_date('1994.12.05', 'yyyy.mm.dd'), 2)",
    				"insert into costumer values(12398746, 'Kovács Dániel', 'Debrecen, Hős utca 84', 5400000, to_date('1985.03.24', 'yyyy.mm.dd'), 3)",
    				"insert into costumer values(54637281, 'Horváth Bence', 'Gyál, Damjanich utca 10', 2300000, to_date('1997.07.17', 'yyyy.mm.dd'), 4)"
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
    		for (int i=0; i<sqlp_costumer.length; i++) {
    			try {
    				s = conn.createStatement();
    				s.executeUpdate(sqlp_costumer[i]);
    				System.out.println("Costumer recorded\n");
    				s.close();
    			} catch(Exception ex) {
    				System.out.println(ex.getMessage());
    			}
    		}
    	}
    }
    
    public static void DinamikusAdatfelvitel() {
    	if (conn != null) {
    		//Az SQL parancsban a ? helyére kerülnek a paraméterek
    		String sqlp = "insert into phone (rsz, tipus, szin, evjarat, ar, tulaj_id)" +
    		"values (?, ?, ?, ?, ?, ?)";
    		
    		System.out.println("Kérem a rendszámot: ");
    		String rsz = sc.next().trim();
    		System.out.println("Kérem a típust: ");
    		String tipus = sc.next().trim();
    		System.out.println("Kérem a színt: ");
    		String szin = sc.next().trim();
    		System.out.println("Kérem a évjáratot: ");
    		int evjarat = sc.nextInt();
    		System.out.println("Kérem a árat: ");
    		float ar = sc.nextFloat();
    		System.out.println("Kérem a tulajdonos azonosítóját: ");
    		int tulaj_id = sc.nextInt();
    		try {
    			ps = conn.prepareStatement(sqlp);
    			ps.setString(1, rsz);
    			ps.setString(2, tipus);
    			ps.setString(3, szin);
    			ps.setInt(4, evjarat);
    			ps.setFloat(5, ar);
    			ps.setInt(6, tulaj_id);
    			ps.executeUpdate();
    			ps.close();
    			System.out.println("Autó felvéve\n");
    		} catch(Exception ex) {
    			System.err.println(ex.getMessage());
    		}
    	}
    }
    
    public static void DinamikusAdattorles() {
    	System.out.println("Costumer to delete: ");
    	String id = sc.next();
        //Az sql parancsban a ? helyére kerülnek a paraméterek
        String sqlp = "delete from " + user + ".COSTUMER where id=?";
        if (conn != null) {
        	try {
        		ps = conn.prepareStatement(sqlp);
        		ps.setString(1, id);
        		ps.executeUpdate();
        		ps.close();
        		System.out.println("costumer with "+id+" id was deleted dynamically\n");
        	} catch(Exception ex) {
        		System.err.println(ex.getMessage());
        	}
        }
    }
    
    public static void StatikusAdattorles() {
    	System.out.println("Costumer to delete: ");
    	String id = sc.next();
    	String sqlp = "delete from costumer where id like'"+id+"'";
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
        		System.out.println("ID Type Color Release Price Condition");
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
        				int price = rs.getInt("price");
        				int condition = rs.getInt("condition");
        				System.out.println(id + "\t\t" + type +  "\t" + color + "\t" + 
        				release + "\t" + price + "\t" + condition);
        			}
        			rs.close();
        		} catch(Exception ex) {
        			System.err.println(ex.getMessage());
        		}
    		} else if(table.equals("costumer")) {
        		String  sqlp = "select * from costumer";
        		System.out.println("IdentityN Name Address Salary Birthday");
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
        		System.out.println("ID Name Address URL Foundation");
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
	}

	
	/*public static void FuggvenyHivas() {
		if(conn != null) {
			try {
				String sqlp = "create or replace function atlagarfv " + "(sz IN char) return number is " + "atl number(10,2);"+
								"begin "+ 
						"select avg(ar) into atl from auto where szin = sz; "+"return atl;"
						+"end;";
				System.out.println("Szín: ");
				String szin  = sc.next();
				s = conn.createStatement();
				s.executeUpdate(sqlp);
				System.out.println("Függvény létrejött\n");
				cs = conn.prepareCall("{? = call atlagarfv(?)}");
				cs.setString(1, szin);
				cs.registerOutParameter(1, java.sql.Types.FLOAT);
				cs.setString(2, szin);
				cs.execute();
				float atlag = cs.getFloat(1);
				System.out.println(szin  + "autók átlagára: "+atlag+"\n");
			}catch(Exception ex) {
				System.err.println(ex.getMessage());
			}
		}
	}*/
	
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
	
	/*public static void DinamikusModositas() {
		if(conn != null) {
			String sqlp = "update auto1 set ar=ar-?";
			System.out.println("Mennyivel csökkentsük az árat?");
			int arcsokk = sc.nextInt();
			try {
				conn.setAutoCommit(false);
				try {
					ps = conn.prepareStatement(sqlp);
					ps.setInt(1, arcsokk);
					ps.executeUpdate();
					conn.commit();
					System.out.println("Módosítás megtörtént!\n");
				}catch(Exception e) {
					System.err.println(e.getMessage());
					conn.rollback();
					System.out.println("Módosítás visszavonva!\n");
				}
				conn.setAutoCommit(true);
			}catch(Exception ex) {
				System.err.println(ex.getMessage());
			}
		}
	}*/

}
