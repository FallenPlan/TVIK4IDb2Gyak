package tvik4idb2;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class tvik4i2fel {
	
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
		//DinamikusAdatfelvetel();
		//DinamikusAdattorles();
		//StatikusAdattorles(); 
		//StatikusLekerdezes();
		//ModosithatoKurzor();
		//InEljarasHivas();
		//OutEljarasHivas();
		DinamikusTablaTorles();
		
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
    		String sqlp_auto = "DROP TABLE auto";
    		String sqlp_tulaj = "DROP TABLE tulaj";
    		s = conn.createStatement();
    		s.executeUpdate(sqlp_auto);
    		System.out.println("Autó tábla törölve!\n");
    		s.executeUpdate(sqlp_tulaj);
    		System.out.println("Tulaj tábls törölve!\n");
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
        String sqlp = "delete from " + user + ".AUTO where rsz=?";
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
    
    public static void StatikusAdattorles() {
    	System.out.println("Törlendő autó: ");
    	String rsz = sc.next();
    	String sqlp = "delete from auto where rsz like '"+rsz+"'";
    	if (conn != null) {
    		try {
    			s = conn.createStatement();
    			s.executeUpdate(sqlp);
    			s.close();
    			System.out.println(rsz + " rendszámú autó törölve\n");
    		} catch(Exception ex) {
    			System.err.println(ex.getMessage());
    		}
    	}
    }
    
    public static void StatikusLekerdezes() {
    	if (conn != null) {
    		String  sqlp = "select * from auto";
    		System.out.println("Rendszám Típus Szín Évjárat Ár Tulaj");
    		try {
    			s = conn.createStatement();
    			s.executeQuery(sqlp);
    			rs = s.getResultSet();
    			while(rs.next()) {
    				//A get metódusoknak a megfelelő típusú táblamezőket kell megadni
    				String rsz = rs.getString("rsz");
    				String tipus = rs.getString("tipus");
    				String szin = rs.getString("szin");
    				int evjarat = rs.getInt("evjarat");
    				int ar = rs.getInt("ar");
    				int tulaj_id = rs.getInt("tulaj_id");
    				System.out.println(rsz + "\t\t" + tipus +  "\t" + szin + "\t" + 
    				evjarat + "\t" + ar + "\t" + tulaj_id);
    			}
    			rs.close();
    		} catch(Exception ex) {
    			System.err.println(ex.getMessage());
    		}
    	}
    }
    
    public static void ModosithatoKurzor() {
		System.out.println("Szín: ");
		String szin = sc.next().trim();
		String sqlp = "select ar from auto where szin= '"+szin+"'";
		if(conn != null) {
			try {
				s=conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE);
				rs=s.executeQuery(sqlp);
				while(rs.next()) {
					int regiar = rs.getInt("ar");
					rs.updateInt("ar", (regiar*2));
					rs.updateRow();
				}
			}catch(Exception ex) {
				System.err.println(ex.getMessage());
			}
		}	
	}
	
	public static void InEljarasHivas() {
		if(conn != null) {
			try {
				String sqlp = "create or procedure arcsokkent " + "(kor IN number) is" + 
			"begin "+ "update auto set ar=ar*0.9 where"
						+"to_char (sysdate, 'yyyy' - evjarat > kor; "+ "end;";
				System.out.println("Kor: ");
				int kor  = sc.nextInt();
				s = conn.createStatement();
				s.executeUpdate(sqlp);
				System.out.println("Függvény létrejött\n");
				cs = conn.prepareCall("{call arcsokkent(?)}");
				cs.setInt(1, kor);
				cs.execute();
			}catch(Exception ex) {
				System.err.println(ex.getMessage());
			}
		}
	}
	
	public static void OutEljarasHivas() {
		if(conn != null) {
			try {
				String sqlp = "create or procedure atlagar " + "(sz IN char, atl OUT number) is " + 
			"begin "+ "select avg(ar) into atl from auto where szin = sz; "
						+"end;";
				System.out.println("Szín: ");
				String szin  = sc.next();
				s = conn.createStatement();
				s.executeUpdate(sqlp);
				System.out.println("Eljárás létrejött\n");
				cs = conn.prepareCall("{call atlagar(?, ?)}");
				cs.setString(1, szin);
				cs.registerOutParameter(2, java.sql.Types.FLOAT);
				cs.execute();
				float atlag = cs.getFloat(2);
				System.out.println(szin  + "autók átlagára: "+atlag+"\n");
			}catch(Exception ex) {
				System.err.println(ex.getMessage());
			}
		}
	}
	
	public static void FuggvenyHivas() {
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
	}
	
	public static void DinamikusTablaTorles() {
		
		String sqlp = "create or replace procedure tablatorles(nev IN char) is " + "begin "+ "execute immediate 'drop table' || nev; "	+"end;";
		
		System.out.println("Törlendõ tábla: ");
		String name   = sc.next().trim();
		if(conn != null) {
			try {
				s = conn.createStatement();
				s.executeUpdate(sqlp);
				cs = conn.prepareCall("{call tablatorles(?)}");
				cs.setString(1, name);
				cs.execute();
				System.out.println("Tábla törölve\n");
			}catch(Exception ex) {
				System.err.println(ex.getMessage());
			}
		}
	}
	
	public static void DinamikusModositas() {
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
	}
}