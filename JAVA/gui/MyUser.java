package gui;

import java.awt.Frame;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import javax.sound.sampled.ReverbType;

public class MyUser {
	private MyFrame F;
	private HashMap<String, Integer> portfolio=new HashMap<>();
	private HashMap<String, String> creds=new HashMap<>();
	private double funds;
	private Stock currentStock =new Stock();
	private ArrayList<Transaction> Tlist=new ArrayList<Transaction>();
	private String name;
	private String pass;
	private boolean ready;
	public MyFrame getF() {
		return F;
	}


	public void setF(MyFrame f) {
		F = f;
	}
	public void calcPortfolio() {
		portfolio=new HashMap<>();
		for (int i = 0; i < Tlist.size(); i++) {
			String S=Tlist.get(i).getID();
			int h=Tlist.get(i).getVolume();
			String split=S.substring(0,1);
			String s=Tlist.get(i).getTicker();
			if(split.equals("B")) {
				if(portfolio.containsKey(s)) {
					int br=portfolio.get(s);
					portfolio.put(s,br+h);
					
				}else {
					portfolio.put(s,h);
					
				}
				
			}else {
				int br=portfolio.get(s);
				portfolio.put(s,br-h);
				
			}
			
		}
	}
	
	public HashMap<String, String> getCreds() {
		return creds;
	}


	public void setCreds(HashMap<String, String> creds) {
		this.creds = creds;
	}
	public boolean isReady() {
		return ready;
	}

	public void setReady(boolean ready) {
		this.ready = ready;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getFunds() {
		return funds;
	}

	public void setFunds(double funds) {
		this.funds = funds;
	}

	public Stock getCurrentStock() {
		return currentStock;
	}

	public void setCurrentStock(Stock currentStock) {
		this.currentStock = currentStock;
	}

	public MyUser(String n,double d) {
		name =new String(n);
		funds=d;
		
	}
	
	public void Buy(String name,int vol) {
		
		long currentT=Instant.now().getEpochSecond();
		long end =currentT-(7*24*3600);
		Date d1 = new Date(currentT * 1000);
		Date d2 = new Date(end * 1000);
		StringBuilder sb1= new StringBuilder();
		sb1.append(d1.getDate());
		sb1.append(".");
		sb1.append(d1.getMonth()+1);
		sb1.append(".");
		sb1.append(d1.getYear()+1900);
		sb1.append(".");
		StringBuilder sb2= new StringBuilder();
		//System.out.println(sb1.toString());
		sb2.append(d2.getDate());
		sb2.append(".");
		sb2.append(d2.getMonth()+1);
		sb2.append(".");
		sb2.append(d2.getYear()+1900);
		sb2.append(".");
		//System.out.println(sb2.toString());
		System.out.println(sb1.toString());
		System.out.println(sb2.toString());
		Crawler C=new Crawler(name,sb2.toString(),sb1.toString());
		Stock S=C.parse();
		Double lastprice=S.getArrCandles().get(S.getArrCandles().size()-1).getClose();
		Double ukupna=lastprice*vol;
		if(ukupna<=this.getFunds()) {
			double d=this.getFunds();
			String ID="B"+currentT+this.getName();
			this.getTlist().add(new Transaction(ID, name, vol, lastprice));
			Double p=d-ukupna;
			this.setFunds(p);
			
			F.setL();
		}
		this.calcPortfolio();
		F.writeData();
	}
	
	public HashMap<String, Integer> getPortfolio() {
		return portfolio;
	}


	public void setPortfolio(HashMap<String, Integer> portfolio) {
		this.portfolio = portfolio;
	}


	public void Sell(String name,int vol) {
		long currentT=Instant.now().getEpochSecond();
		long end =currentT-(7*24*3600);
		Date d1 = new Date(currentT * 1000);
		Date d2 = new Date(end * 1000);
		StringBuilder sb1= new StringBuilder();
		sb1.append(d1.getDate());
		sb1.append(".");
		sb1.append(d1.getMonth()+1);
		sb1.append(".");
		sb1.append(d1.getYear()+1900);
		sb1.append(".");
		StringBuilder sb2= new StringBuilder();
		sb2.append(d2.getDate());
		sb2.append(".");
		sb2.append(d2.getMonth()+1);
		sb2.append(".");
		sb2.append(d2.getYear()+1900);
		sb2.append(".");
		Crawler C=new Crawler(name,sb2.toString(),sb1.toString());
		Stock S=C.parse();
		Double lastprice=S.getArrCandles().get(S.getArrCandles().size()-1).getClose();
		Double ukupna=lastprice*vol;
		if(portfolio.containsKey(name)) {
			if(vol<=portfolio.get(name)) {
				double d=this.getFunds();
				String ID="S"+currentT+this.getName();
				this.getTlist().add(new Transaction(ID, name, vol, lastprice));
				Double p=d+ukupna;
				this.setFunds(p);
				
				
				F.setL();
				
			}
	
		}
		this.calcPortfolio();
		F.writeData();
	}
	public void addT(Transaction T) {
		this.getTlist().add(T);
		
		
	}
	public void ldhm() {
		try {
			FileInputStream fstream;
			fstream = new FileInputStream("src/user_login.txt");
			BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
			String strLine;
			while ((strLine = br.readLine()) != null) {
				String s=new String(strLine);
				
				String []up=s.split(",");
				getCreds().put(up[0], up[1]);
			}

		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	}
	public ArrayList<Transaction> getTlist() {
		return Tlist;
	}
	public void setTlist(ArrayList<Transaction> tlist) {
		Tlist = tlist;
	}
}
