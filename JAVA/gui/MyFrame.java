package gui;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.io.*;
import java.security.Policy;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;

import javax.swing.*;
import javax.swing.JScrollPane;



public class MyFrame extends Frame {
	
	private MyUser User=new MyUser("anon", 0);
	

	
	private MyCanvas myc;
	

	private JScrollPane sp=new JScrollPane();
	private Panel MainPanel;
	private Panel RMenu;
	private Panel StockInfo;
	private Label xz = new Label("Xzoom:");
	private Label yz = new Label("Yzoom:");
	private TextField stockName=new TextField(15);
	private TextField startDate=new TextField(15);
	private TextField endDate=new TextField(15);
	private Checkbox MA= new Checkbox("MA",false);
	private Checkbox EMA= new Checkbox("EMA",false);
	private Button showInd=new Button("Show");
	private Button gatherData;
	private TextField time=new TextField(15);
	private TextField tick=new TextField(15);
	private TextField quant=new TextField(15);
	private Label l2=new Label("",Label.LEFT);
	private Label l3=new Label("Funds:",Label.LEFT);
	private Label l4=new Label("",Label.LEFT);
	private CheckboxGroup bsgroup = new CheckboxGroup();
	private Checkbox Buy= new Checkbox("Buy",true,bsgroup);
	private Checkbox Sell= new Checkbox("Sell",false,bsgroup);
	private Button confirm=new Button("Confirm");
	private TextArea Port=new TextArea("",10,50,TextArea.SCROLLBARS_VERTICAL_ONLY);
	private TextArea history=new TextArea("",10,50,TextArea.SCROLLBARS_VERTICAL_ONLY);
	
	
	public void setL() {
		double d=User.getFunds();
		NumberFormat nf=NumberFormat.getPercentInstance();
		DecimalFormat numberFormat = new DecimalFormat("#.00");
		l4.setText(numberFormat.format(d));
		revalidate();
		
	}
	
	private class loginDialog extends Dialog{
		//private MyUser current=new MyUser("anon", 0);
		private TextField username=new TextField(20);
		private TextField password=new TextField(20);
		private TextField money=new TextField(20);
		Label text= new Label("Welcome! Choose option!");
		private Button login=new Button("Login");
		private Button register=new Button("Register");
		private Button cancel=new Button("Cancel");
		
		public loginDialog(Frame owner)  {
			super(owner);
			username.setText("lazarm");
			password.setText("1234");
			setResizable(false);
			setModalityType(ModalityType.APPLICATION_MODAL);
				
				
				login.addActionListener((ae)->{
					User=new MyUser(username.getText(), 0);
					if(User.equals(null)) {
						System.out.println("Err");
						
					}else {
						
						User.ldhm();
					}
					
					if(User.getCreds().containsKey(username.getText())) {
						if(User.getCreds().get(username.getText()).equals(password.getText())) {
						//User=new MyUser(username.getText(), 0);
						User.setName(username.getText());
						try {
							
							File myf= new File("src/users/"+username.getText()+".txt");
							FileInputStream fstream = new FileInputStream(myf);
							BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
							String strLine;
							int num=1;
							while ((strLine = br.readLine()) != null) {
								if(num==1) {
									User.setFunds(Double.parseDouble(strLine));
								}else {
									String s=new String(strLine);
									//System.out.println(s);
									String []up=s.split(",");
									User.addT(new Transaction(up[0], up[1],Integer.parseInt(up[2]), Double.parseDouble(up[3])));
								}
								num++;
							}
						
							
							l2.setText(username.getText());
							
							double d=User.getFunds();
							NumberFormat nf=NumberFormat.getPercentInstance();
							DecimalFormat numberFormat = new DecimalFormat("#.00");
							
							//l3.setPreferredSize(new Dimension(80,20));
							//l4=new Label("",Label.LEFT);
							//l4.setPreferredSize(new Dimension(100,20));
							l4.setText(numberFormat.format(d));
							
							System.out.println(d);
							User.setReady(true);
							User.setF((MyFrame) owner);
							revalidate();
							User.calcPortfolio();
							this.dispose();
						} catch (FileNotFoundException e1) {
							
							e1.printStackTrace();
						} catch (IOException e1) {
							
							e1.printStackTrace();
						}
						
						
						}
					}else {
						text.setText("No user with suck credentials");
						this.dispose();
						
					}				
				});
				register.addActionListener((ae)->{
						//User=new MyUser(username.getText(), 0);
						
						if(User.equals(null)) {
							System.out.println("Err");
							
						}else {
							
							User.ldhm();
						}
						
						if(!User.getCreds().containsKey(username.getText())) {
							text.setText("Welcome");
							//User=new MyUser(username.getText(), Double.parseDouble(money.getText()));
							User.setName(username.getText());
							User.setFunds(Double.parseDouble(money.getText()));
							User.getCreds().put(username.getText(),password.getText());
							
							l2.setText(username.getText());
							double d=User.getFunds();
							NumberFormat nf=NumberFormat.getPercentInstance();
							DecimalFormat numberFormat = new DecimalFormat("#.00");
							l4.setText(numberFormat.format(d));
							User.setReady(true);
							User.setF((MyFrame) owner);
							revalidate();
							User.calcPortfolio();
							this.dispose();
						}else {
							
							this.dispose();
						}
						
					
				});
				
				setTitle("LogIn");
				setBounds(owner.getX()+owner.getWidth()/2-100,owner.getY()+owner.getHeight()/2-100,300,250);
				setResizable(false);
				setModalityType(ModalityType.APPLICATION_MODAL);
				Panel pnl=new Panel(new BorderLayout());
				Panel buttons=new Panel(new GridLayout(7,1));
				
				
				cancel.addActionListener((ae)->{
					User.setReady(true);
					MyFrame.this.dispose();
				});
				password.setEchoChar('*');
				
				buttons.add(text);
				buttons.add(username);
				buttons.add(password);
				buttons.add(money);
				buttons.add(login);
				buttons.add(register);
				buttons.add(cancel);
				add(buttons,BorderLayout.NORTH);
				addWindowListener(new WindowAdapter() {
					public void windowClosing(WindowEvent e) {
						User.setReady(true);
						MyFrame.this.dispose();
						
					}
				});
				setVisible(true);
				
			
			
			
			
		}
	}
	
	private class quitDialog extends Dialog{
		private Button ok=new Button("Ok");
		private Button cancel=new Button("Cancel");
		private Label areYouSure =new Label("Are you sure you want to quit?");
		public quitDialog(Frame owner) {
			super(owner);
			setTitle("Quit");
			setBounds(owner.getX()+owner.getWidth()/2,owner.getY()+owner.getHeight()/2,200,150);
			setResizable(false);
			setModalityType(ModalityType.APPLICATION_MODAL);
			Panel buttons=new Panel();
			ok.addActionListener((ae)->{
				try {
					//stores username and password
					String S="src/user_login.txt";
					BufferedWriter outputWriter = new BufferedWriter(new FileWriter(S));
					
					for (String tmp: User.getCreds().keySet()) {
						//System.out.println("1");
						StringBuilder sb= new StringBuilder();
						sb.append(tmp);
						sb.append(",");
						sb.append(User.getCreds().get(tmp));
						//sb.append("\n");
						//System.out.println(sb.toString());
						outputWriter.write(sb.toString());
					    outputWriter.newLine();
					}
					outputWriter.flush();  
					outputWriter.close();
					
					
					String temp=User.getName()+".txt";
					String path="src/users/"+User.getName()+".txt";
									
					
					outputWriter = new BufferedWriter(new FileWriter(path));
					outputWriter.write(Double.toString(User.getFunds()));
				    outputWriter.newLine();
				    
					for (int i = 0; i < User.getTlist().size(); i++) {
						StringBuilder sb= new StringBuilder();
						sb.append(User.getTlist().get(i).getID());
						sb.append(",");
						sb.append(User.getTlist().get(i).getTicker());
						sb.append(",");
						sb.append(User.getTlist().get(i).getVolume());
						sb.append(",");
						sb.append(User.getTlist().get(i).getPrice());
						outputWriter.write(sb.toString());
					    outputWriter.newLine();
					}
					outputWriter.flush();  
					outputWriter.close();
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				MyFrame.this.dispose();
			});
			
			cancel.addActionListener((ae)->{
				quitDialog.this.dispose();
			});
			
			buttons.add(ok);
			buttons.add(cancel);
			add(areYouSure,BorderLayout.CENTER);
			add(buttons,BorderLayout.SOUTH);
			
			addWindowListener(new WindowAdapter() {
				public void windowClosing(WindowEvent e) {
					dispose();
				}
			});
			setVisible(true);
		}
	}
	
	
	
	public MyFrame() {
		new BorderLayout();
		setResizable(true);
		setBounds(50,50,1000,1200);
		setTitle("StockMarket");
		//close Myframe and dialog to confirm
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				//dispose();
				new quitDialog(MyFrame.this);
			}
		});
		populateWin();
		pack();
		setVisible(true);
		revalidate();
		

	}
	
	private void populateWin() {
		
		history.setEditable(true);
		Port.setEditable(true);
		MainPanel= new Panel(new BorderLayout());
		myc= new MyCanvas(1280,720,new Stock());
		sp=new JScrollPane();
		sp.setViewportView(myc);
		myc.setScrollPane(sp);
		
		
		RMenu=new Panel(new GridLayout(0,1));
		RMenu.setBackground(Color.LIGHT_GRAY);
		StockInfo=new Panel(new GridLayout(0,1));
		stockName.setText("GME");
		startDate.setText("01.01.2021.");
		endDate.setText("01.03.2021.");
		gatherData=new Button("Fetch data");
		gatherData.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Crawler C=new Crawler(stockName.getText(),startDate.getText(),endDate.getText());
				if(C.isReady()) {
					Stock S=C.parse();
					if(C.isReady()) {
					int i= S.getArrCandles().size()*22;
					//System.out.println(i);
					//myc.setS(S);
					//myc.setWidth(i);
					//myc.setHeight(720);
					myc=new MyCanvas(i,720,S);
					sp.setViewportView(myc);
					myc.setScrollPane(sp);
					add(sp,BorderLayout.CENTER);
					//myc.addKeyListener(myc);
					myc.setT(Integer.parseInt(time.getText()));
					if(EMA.getState())myc.setMa(true);
					if(MA.getState())myc.setEma(true);
					myc.addKeyListener(new KeyListener() {
						
						@Override
						public void keyTyped(KeyEvent e) {
							// TODO Auto-generated method stub
							
						}
						
						@Override
						public void keyReleased(KeyEvent e) {
							// TODO Auto-generated method stub
							
						}
						
						@Override
						public void keyPressed(KeyEvent e) {
							int c = e.getKeyCode ();
					        if (c==KeyEvent.VK_RIGHT) { 
					        	if(myc.Xzoom<2.0) {
					        		myc.Xzoom=myc.Xzoom+0.1;
					        		repaint();
					            }   
					        } else if(c==KeyEvent.VK_LEFT) {                
					        	if(myc.Xzoom>0.0) {
					        		myc.Xzoom=myc.Xzoom-0.1;
					        		repaint();
					        	}   
					        } else if(c==KeyEvent.VK_DOWN) {                
					        	if(myc.Yzoom>0.0) {
					        		myc.Yzoom=myc.Yzoom-0.1;
					        		repaint();
					        	}  
					        } else if(c==KeyEvent.VK_UP) {                
					        	if(myc.Yzoom<2.0) {
					        		myc.Yzoom=myc.Yzoom+0.1;
					        		repaint();
					        	}   
					        }
					        xz.setText("Xzoom:"+myc.Xzoom);
					        yz.setText("Yzoom:"+myc.Yzoom);
							//System.out.println(myc.Xzoom+" "+myc.Yzoom);
							revalidate();
						}
					});
					myc.setFocusable(true);
					myc.requestFocusInWindow();
					myc.repaint();
					myc.revalidate();
				}
				}
			}
		});
		
		
		//myc.addKeyListener(myc);
		myc.setFocusable(true);

		Panel p1=new Panel(new FlowLayout(FlowLayout.LEFT));
		Panel p2=new Panel(new FlowLayout(FlowLayout.LEFT));
		Panel p3=new Panel(new FlowLayout(FlowLayout.LEFT));
		Panel p4=new Panel(new FlowLayout(FlowLayout.LEFT));
		Panel p5=new Panel(new FlowLayout(FlowLayout.LEFT));
		Panel p6=new Panel(new FlowLayout(FlowLayout.LEFT));
		Panel p7=new Panel(new FlowLayout(FlowLayout.LEFT));
		Panel p8=new Panel(new FlowLayout(FlowLayout.LEFT));
		Panel p9=new Panel(new FlowLayout(FlowLayout.LEFT));
		Panel p10=new Panel(new FlowLayout(FlowLayout.LEFT));
		Panel p11=new Panel(new FlowLayout(FlowLayout.LEFT));
		Panel p12=new Panel(new FlowLayout(FlowLayout.LEFT));
		Panel p13=new Panel(new FlowLayout(FlowLayout.LEFT));
		Panel p14=new Panel(new FlowLayout(FlowLayout.LEFT));
		Panel p15=new Panel(new FlowLayout(FlowLayout.LEFT));
		Panel p16=new Panel(new FlowLayout(FlowLayout.LEFT));
		Panel p17=new Panel(new FlowLayout(FlowLayout.LEFT));
		Panel p18=new Panel(new FlowLayout(FlowLayout.LEFT));
		Panel p19=new Panel(new FlowLayout(FlowLayout.LEFT));
		Panel p20=new Panel(new FlowLayout(FlowLayout.LEFT));
		Panel a1=new Panel(new FlowLayout(FlowLayout.LEFT));
		Panel a2=new Panel(new FlowLayout(FlowLayout.LEFT));
		Panel b1=new Panel(new GridLayout(1,2));
		Panel b2=new Panel(new GridLayout(1,2));
		Panel b3=new Panel(new GridLayout(3,2));
		Label l1=new Label("Username:  ");
		
		
		b1.add(l1);
		b1.add(l2);
		b2.add(l3);
		b2.add(l4);
		a1.add(b1);
		a2.add(b2);
		p9.add(new Label(""));
		p1.add(stockName);
		p2.add(startDate);
		p3.add(endDate);
		p4.add(gatherData);
		p5.add(new Label(""));
		p6.add(MA);
		p7.add(EMA);
		p8.add(time);
		p17.add(showInd);
		p10.add(new Label(""));
		p11.add(new Label("Buy/Sell"));
		p12.add(Buy);
		p13.add(Sell);
		p14.add(tick);
		p16.add(quant);
		p15.add(confirm);
		p20.add(new Label(""));
		p18.add(xz);
		p19.add(yz);
		Panel pp= new Panel(new BorderLayout());
		StockInfo.add(a1);
		StockInfo.add(a2);
		StockInfo.add(p9);
		StockInfo.add(p1);
		StockInfo.add(p2);
		StockInfo.add(p3);
		StockInfo.add(p4);
		StockInfo.add(p5);
		StockInfo.add(p6);
		StockInfo.add(p7);
		StockInfo.add(p8);
		//StockInfo.add(p17);
		StockInfo.add(p10);
		StockInfo.add(p11);
		StockInfo.add(p12);
		StockInfo.add(p13);
		StockInfo.add(p14);
		StockInfo.add(p16);
		StockInfo.add(p15);
		StockInfo.add(p20);
		StockInfo.add(p18);
		StockInfo.add(p19);
		
		time.setText("10");
		tick.setText("AAPL");
		quant.setText("20");
		
		MA.addItemListener(new ItemListener() {  
             public void itemStateChanged(ItemEvent e) {               
            	 if(e.getStateChange()==1) {
                	   if(((Checkbox)e.getSource()).getState()) {
                	   myc.setT(Integer.parseInt(time.getText()));
                	   myc.setMa(true);
                	   //System.out.println("1");
                	   }else {
                		   
                		   
                		   myc.setMa(false);   
                	   }
                	   myc.repaint();
                	   myc.revalidate();
                   }else {
                	 myc.repaint();
                  	 myc.revalidate();
                 }
             }  
          });
		
		EMA.addItemListener(new ItemListener() {  
             public void itemStateChanged(ItemEvent e) { 
            	 if(e.getStateChange()==1) {
              	   if(((Checkbox)e.getSource()).getState()) {
              	   myc.setT(Integer.parseInt(time.getText()));
              	   myc.setEma(true);
              	   }else {
              		   
              		   
              		   myc.setEma(false);   
              	   }
              	 myc.repaint();
              	 myc.revalidate();
                 }else {
                	 myc.repaint();
                  	 myc.revalidate();
                 }
            	 
             }  
          });
		 
		confirm.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(Buy.getState()) {
					User.Buy(tick.getText(), Integer.parseInt(quant.getText()));
				}else {
					User.Sell(tick.getText(), Integer.parseInt(quant.getText()));
				}
			}
		});
		
		
		pp.add(StockInfo,BorderLayout.NORTH);;
		RMenu.add(pp);//adds elements to easter panel
		RMenu.setPreferredSize(new Dimension(250,0));
		
		//Add to south Transactions and Portfolio
		Panel pan=new Panel(new BorderLayout());
		Panel sPan=new Panel(new GridLayout(1,2));
		Panel smPan=new Panel(new GridLayout(1,2));
		Label l2l=new Label("     Portfolio");
		Label l3l=new Label("     Transactions");
		smPan.add(l2l);
		smPan.add(l3l);
		
		sPan.add(Port);
		sPan.add(history);
		pan.add(smPan,BorderLayout.NORTH);
		pan.add(sPan,BorderLayout.CENTER);
		//Add all to MyFrame
		
	    add(sp,BorderLayout.CENTER);  
	    add(pan,BorderLayout.SOUTH); 
		add(RMenu,BorderLayout.EAST);
	}
	public boolean  logIn() {
		new loginDialog(MyFrame.this);
		if (User.isReady()) {
			return true;
			
		}else return false;
	}
	
	public void  writeData() {
		Port.setText("");
		history.setText("");
		for (int i = 0; i < User.getTlist().size(); i++) {
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
			Transaction t= User.getTlist().get(i);
			Crawler C=new Crawler(t.getTicker(),sb2.toString(),sb1.toString());
			Stock S=C.parse();
			Double lastprice=S.getArrCandles().get(S.getArrCandles().size()-1).getClose();
			history.append(t.toString()+"   ");
			history.append(lastprice.toString()+"   ");
			Double d=lastprice-t.getPrice();
			history.append(d.toString()+"   ");
			d=(lastprice/t.getPrice())*100;
			history.append(d.toString()+"   ");
			history.append("\n");
		}
		
		for (String s: User.getPortfolio().keySet()) {
			StringBuilder sb=new StringBuilder(s);
			sb.append("   ");
			sb.append(User.getPortfolio().get(s));
			Port.append(sb.toString());
			Port.append("\n");
		}
	}
	
	
	public static void main(String[] args) {
		MyFrame mf=new MyFrame();
		boolean li=false;
		while (li==false) {
			li =mf.logIn();
			
			//System.out.println(li);
		}
		mf.writeData();
		mf.setSize(new Dimension(1500,970));
		
	}
}
