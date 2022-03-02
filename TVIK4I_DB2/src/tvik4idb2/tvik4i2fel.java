package tvik4idb2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Scanner;

public class tvik4i2fel {
	
    static Connection conn = null;
    static Statement s = null;
    static Scanner sc = new Scanner(System.in);
    static PreparedStatement ps = null;
    static String user;
	
	public static void main(String[] args) {
		Connect();
		//DriverReg();
		//StatikusTablaLetrehozas();
		//StatikusTablaModositas();
		//StatikusAdatfelvitel();
		DinamikusAdatfelvitel();
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

        String url = "jdbc:oracle:thin:@193.6.5.58:1521:XE";
        String user = "H22_tvik4i";
        String pwd = "TVIK4I";

        try {
            conn = DriverManager.getConnection(url, user, pwd);
            System.out.println("Sikeres kapcsolódás\n");
        } catch(Exception e) {
            System.err.println(e.getMessage());
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
        String sqlp_auto = "create table auto ( rsz char(6) primary key, " +
                "tipus char(10) not null, szin char(10) default 'feher', " +
                "evjarat number(4), ar number(8) check(ar>0) )";
        String sqlp_tulaj = "create table tulaj(id number(3) primary key, " +
                "nev char(20) not null, cim char(20), szuldatum date)";
        
         if (conn != null) {
        	try {
        		//String sqlp = "alter table auto add(tulaj_id references tulaj)";
        		s = conn.createStatement();
        		s.executeUpdate(sqlp_auto);
        		System.out.println("Autó tábla létrejött\n");
        		s.executeUpdate(sqlp_tulaj);
        		System.out.println("Tulajdonsos tábla létrejött\n");
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
        		String sqlp = "alter table auto add(tulaj_id references tulaj)";
        		s = conn.createStatement();
        		s.executeUpdate(sqlp);
        		System.out.println("Autó tábla módosítva\n");
        		s.close();
        	} catch(Exception ex) {
        		System.err.println(ex.getMessage());
        	}
        }
    }
    
    public static void StatikusAdatfelvitel() {
    	if (conn != null) {
    		String sqlp_tul = "insert into tulaj values(1, 'Tóth Máté', " +
    				" 'Miskolc', to_date(1980.05.12', 'yyyy.mm.dd'))";
    		String[] sqlp = {
    				"insert into auto values('aaa111', 'opel', 'piros', 2014, 1650000, 1)",
    				"insert into auto values('bbb222', 'mazda', null, 2016, 2800000, 1)",
    				"insert into auto (rsz, tipus, evjarat, ar) values ('ccc333', 'ford', 2009, 15000000)"
    		};
    		try {
    			s = conn.createStatement();
    			s.executeUpdate(sqlp_tul);
    			System.out.println("Tulaj féléve\n");
    			s.close();
    		} catch(Exception ex) {
    			System.out.println(ex.getMessage());
    		}
    		for (int i=0; i<sqlp.length; i++) {
    			try {
    				s = conn.createStatement();
    				s.executeUpdate(sqlp[i]);
    				System.out.println("Auto felvéve\n");
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
    		String sqlp = "insert into auto (rsz, tipus, szin, evjarat, ar, tulaj_id)" +
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
    	System.out.println("Törlendő autó: ");
    	String rsz = sc.next();
        //Az sql parancsban a ? helyére kerülnek a paraméterek
        String sqlp = "delete from " + user + ".AUTO" + " where rsz=?";
        if (conn != null) {
        	try {
        		ps = conn.prepareStatement(sqlp);
        		ps.setString(1, rsz);
        		ps.executeUpdate();
        		ps.close();
        		System.out.println(rsz + " rendszámú autó törölve\n");
        	} catch(Exception ex) {
        		System.err.println(ex.getMessage());
        	}
        }
    }

}

