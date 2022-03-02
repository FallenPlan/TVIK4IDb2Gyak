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
    

	
	public static void main(String[] args) {
		Connect();
		DriverReg();

		
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
    

}

