package gui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Crawler {
	private String raw;
	private String currency;
	private String t;
	private String h;
	private String l;
	private String o;
	private String c;
	private boolean ready;
	public boolean isReady() {
		return ready;
	}

	public void setReady(boolean ready) {
		this.ready = ready;
	}

	public Crawler(String name, String start, String end) {
		boolean flag = false;
		String s1=new String(start);
		String s2=new String(end);
		try {
			String []n1=s1.split("\\.");
			String []n2=s2.split("\\.");
			//System.out.println(n1[0]);
			int d =Integer.parseInt(n1[0]);
			int m=Integer.parseInt(n1[1]);
			int y=Integer.parseInt(n1[2]);
			if ((1000 <= y)&&( y<= 3000)) {
				if ((m == 1 || m == 3 || m == 5 || m == 7 || m == 8 || m == 10 || m == 12) && d > 0 && d <= 31) {
					flag = false;
				}
				else if (m == 4 || m == 6 || m == 9 || m == 11 && d > 0 && d <= 30) {
					flag = false;
				}
				else if (m== 2) {
					if ((y % 400 == 0 || (y % 100 != 0 && y % 4 == 0)) && d> 0 && d <= 29) {
						flag = false;
					}
					else if (d > 0 && d <= 28) {
						flag = false;
					}
					else flag = true;
				}
				else flag = true;
				
			}
			if(flag==true) {
				System.out.println("Err:Prvi datum nije adekvatan");
				
			}else {
				int d1 =Integer.parseInt(n2[0]);
				int m1=Integer.parseInt(n2[1]);
				int y1=Integer.parseInt(n2[2]);
				if ((1000 <= y1)&&( y1<= 3000)) {
					flag=false;
					if ((m1 == 1 || m1 == 3 || m1 == 5 || m1 == 7 || m1 == 8 || m1 == 10 || m1 == 12) && d1 > 0 && d1 <= 31) {
						flag = false;
					}
					else if (m1 == 4 || m1 == 6 || m1 == 9 || m1 == 11 && d1 > 0 && d1 <= 30) {
						flag = false;
					}
					else if (m1== 2) {
						if ((y1 % 400 == 0 || (y1 % 100 != 0 && y1 % 4 == 0)) && d1> 0 && d1 <= 29) {
							flag = false;
						}
						else if (d1 > 0 && d1 <= 28) {
							flag = false;
						}
						else flag = true;
					}
					else flag = true;
					
				}
				if(flag==true) {
					//nazanaka da je drugi loseg formata
					System.out.println("Err:Drugi datum nije adekvatan");
				}else {
					long  st=y*31556926+m*2629743+d*86400;
					long en=y1*31556926+m1*2629743+d1*86400;
					long raz=en-st;
					if(raz>0) {
						//provera da li je start pre enda
						StringBuilder sb= new StringBuilder();
						sb.append("path to exe ");
						sb.append(name + " ");
						sb.append(start + " ");
						sb.append(end);
						
						
						//System.out.println(s);
						Process p = Runtime.getRuntime().exec(new String(sb.toString()));
						
						BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
						//currency=input.readLine();
						t= input.readLine();
						l= input.readLine();
						h= input.readLine();
						o= input.readLine();
						c= input.readLine();
						p.waitFor();
						
						
						
						
						input.close();
						ready=true;
					}else {
						System.out.println("Err:Drugi datum je pre prvog");
						
					}
					
				}
				
			}
			
			
			
			
			
		} catch (IOException | InterruptedException e) {
			
			e.printStackTrace();
		}
		
	}
	
	public Stock parse() {
		
		NumberFormat nf = NumberFormat.getInstance(Locale.US);
		Stock s=new Stock();
		
		if(t.matches(".\\bnull\\b")) {
			System.out.println("TICKER ne postoji");
			return null;
			
		}
		String []ti=t.split(" ");
		String []lo=l.split(" ");
		String []hi=h.split(" ");
		String []op=o.split(" ");
		String []cl=c.split(" ");
		Object o =s.getArrCandles();
		int br=0;
		int nbr=0;
		//System.out.println(ti.length);
		if(ti[0].matches(".*\\bnull\\b.*")){
			System.out.println("TICKER ne postoji");
			ready=false;
			return null;
			
			
		}
		for (int j = 0; j < cl.length; j++) {
			
			
			
				try {
					s.addCD(new Candle(Integer.parseInt(ti[j]),nf.parse(lo[j]).doubleValue(), nf.parse(hi[j]).doubleValue(),nf.parse(op[j]).doubleValue(), nf.parse(cl[j]).doubleValue()));
				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				br++;
			
			}
		//System.out.println(br);
		
		return s;
	}
	
	public String getRaw() {
		return raw;
	}
	

	public void setRaw(String raw) {
		this.raw = raw;
	}


	public static void main(String[] args) {
		
		Crawler cr= new Crawler("TSLA", "1.02.2020.", "10.06.2021.");
		
		
		Stock ss=cr.parse();
		
		for (int i = 0; i < ss.getArrCandles().size(); i++) {
			//System.out.println(ss.getArrCandles().get(i));
		}
		
	}


	public String getT() {
		return t;
	}


	public void setT(String t) {
		this.t = t;
	}


	public String getH() {
		return h;
	}


	public void setH(String h) {
		this.h = h;
	}


	public String getL() {
		return l;
	}


	public void setL(String l) {
		this.l = l;
	}


	public String getO() {
		return o;
	}


	public void setO(String o) {
		this.o = o;
	}


	public String getC() {
		return c;
	}


	public void setC(String c) {
		this.c = c;
	}
}
