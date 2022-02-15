package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class MyCanvas extends JPanel{
	private int width, height;
	private int canWidth;
	private Stock S;
	private Stock fS;
	private Color color = Color.black;
    private JScrollPane scrollPane;
    private boolean ema=false;
    private boolean ma=false;
    double Xzoom=1.0;
	double Yzoom=1.0;
	public double getXzoom() {
		return Xzoom;
	}

	public void setXzoom(double xzoom) {
		Xzoom = xzoom;
	}

	public double getYzoom() {
		return Yzoom;
	}

	public void setYzoom(double yzoom) {
		Yzoom = yzoom;
	}
    private int t;
	public int getT() {
		return t;
	}
	public void setT(int t) {
		this.t = t;
	}
	public MyCanvas(int x, int y,Stock s) {
		this.setPreferredSize(new Dimension(x , y ));

        this.width = x;
        this.height = y;
        this.S=s;
		this.fS=s;
	}
	private void updateScrollPane() {
        //scrollPane.repaint();
    }

    void setScrollPane(JScrollPane scrollPane) {
        this.scrollPane = scrollPane;
    }
    
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);	
		
		if(!S.equals(null)) {
			height= scrollPane.getViewport().getHeight();
			//width= scrollPane.getViewport().getWidth();
			width=(scrollPane.getViewport().getWidth()/50)*S.getArrCandles().size();
			g.setColor(Color.DARK_GRAY);
			if(Yzoom>1.0) {
				this.setPreferredSize(new Dimension((int)(width*Xzoom),(int)(height*Yzoom)));
				g.fillRect(0, 0,(int)( width*Xzoom),(int)( height*Yzoom));
				g.setColor(Color.WHITE);
				g.drawLine(0,(int)( height*Yzoom)/2, (int)( width*Xzoom), (int)( height*Yzoom)/2);
			}else {
				this.setPreferredSize(new Dimension((int)(width*Xzoom),(int)(height)));
				g.fillRect(0, 0,(int)( width*Xzoom),(int)( height));
				g.setColor(Color.WHITE);
				g.drawLine(0,(int)( height)/2, (int)( width*Xzoom), (int)( height)/2);
			}
			
			
			int canWid= (int) ((scrollPane.getViewport().getWidth()/50)*Xzoom);
			//canWid=(int) (canWid*Xzoom);
			//System.out.println(canWid);
			
			for (int i = S.getArrCandles().size()-1; i >=0 ; i--) {
				double vis=this.height;
				double deset=this.height*0.1;
				double o=S.getArrCandles().get(i).getOpen();
				double c=S.getArrCandles().get(i).getClose();
				double l=S.getArrCandles().get(i).getLow();
				double h=S.getArrCandles().get(i).getHigh();
				if(Yzoom>1.0) {
					if(c>=o) {
						double br=S.getMax()/c;
						br=Math.pow(br, -1)*this.height*0.5;
						//br=br*this.getHeight()*0.9;
						double br2=S.getMax()/o;
						br2=Math.pow(br2, -1)*this.height*0.5;
						//br2=br2*this.getHeight()*0.9;
						double zbir1= br+br2;
						int j=(int)(this.height-br);
						int k=(int)(br-br2);
						double br3=S.getMax()/l;
						br3=Math.pow(br3, -1)*this.height*0.5;
						//br=br*this.getHeight()*0.9;
						double br4=S.getMax()/h;
						br4=Math.pow(br4, -1)*this.height*0.5;
						double zbir2= br3+br4;
						int x=(int)(this.height-br3);
						int y=(int)(this.height-br4);
						j=(int) (j*Yzoom);
						k=(int) (k*Yzoom);
						x=(int) (x*Yzoom);
						y=(int) (y*Yzoom);
						g.setColor(Color.GREEN);
						g.fillRect(i*canWid, j, canWid, k);
						g.drawLine(i*canWid+canWid/2, y, i*canWid+canWid/2, x);
						//System.out.println(i*22 +" "+ j+" " + 20+" " + k);
					}else {
						double br=S.getMax()/c;
						br=Math.pow(br, -1)*this.height*0.5;
						//br=br*this.getHeight()*0.9;
						double br2=S.getMax()/o;
						br2=Math.pow(br2, -1)*this.height*0.5;
						//br2=br2*this.getHeight()*0.9;
						double zbir1= br+br2;
						int j=(int)(this.height-br2);
						int k=(int)(br2-br);
						double br3=S.getMax()/l;
						br3=Math.pow(br3, -1)*this.height*0.5;
						//br=br*this.getHeight()*0.9;
						double br4=S.getMax()/h;
						br4=Math.pow(br4, -1)*this.height*0.5;
						double zbir2= br3+br4;
						int x=(int)(this.height-br3);
						int y=(int)(this.height-br4);
						j=(int) (j*Yzoom);
						k=(int) (k*Yzoom);
						x=(int) (x*Yzoom);
						y=(int) (y*Yzoom);
						g.setColor(Color.RED);
						g.fillRect(i*canWid, j, canWid, k);
						g.drawLine(i*canWid+canWid/2, x, i*canWid+canWid/2, y);
						//System.out.println(i*22 +" "+ j+" " + 20+" " + k);
					}
					ArrayList<Integer> vals=new ArrayList<>();
					//System.out.println(S.getArrCandles().size());
					if(ema==true) {
						g.setColor(Color.CYAN);
						//TO DO docrtaj ove linije
						EMA x= new EMA(t);
						x.calculate(S.getArrCandles());
						for (int j = 0; j < S.getArrCandles().size(); j++) {
							double br4=S.getMax()/x.getValues().get(j);
							br4=Math.pow(br4, -1)*this.getHeight()*0.5;
							//System.out.println(x.getValues().get(j));
							int y=(int)((this.getHeight()-br4));
							//System.out.println(y);
							vals.add(y);
							
							if(j==0) {
								
							}else {
								int a= (int) (((j-1)*canWid+canWid/2));
								int b=	(int) ((j*canWid+canWid/2));
								int aa=(int) (vals.get(j-1)*Yzoom);
								int bb=(int) (vals.get(j)*Yzoom);
								g.drawLine(a, aa, b, bb);
								//this.repaint();
								this.revalidate();
								
							}
							
						}
						//System.out.println(x.getValues().size());
					}
					//System.out.println(vals.size());
					vals.clear();
					if(ma==true) {
						g.setColor(Color.PINK);
						//to do docrtaj ove linije
						MA x= new MA(t);
						x.calculate(S.getArrCandles());
						for (int j = 0; j < S.getArrCandles().size(); j++) {
							double br4=S.getMax()/x.getValues().get(j);
							br4=Math.pow(br4, -1)*this.getHeight()*0.5;
							//System.out.println(x.getValues().get(j));
							int y=(int)((this.getHeight()-br4));
							//System.out.println(y);
							vals.add(y);
							
							if(j==0) {
								
							}else {
								int a= (int) (((j-1)*canWid+canWid/2));
								int b=	(int) ((j*canWid+canWid/2));
								int aa=(int) (vals.get(j-1)*Yzoom);
								int bb=(int) (vals.get(j)*Yzoom);
								g.drawLine(a, aa, b, bb);
								//this.repaint();
								this.revalidate();
								
							}
							
						}
					}
				}else {
					if(c>=o) {
						double br=S.getMax()/c;
						br=Math.pow(br, -1)*this.height*0.5*Yzoom;
						//br=br*this.getHeight()*0.9;
						double br2=S.getMax()/o;
						br2=Math.pow(br2, -1)*this.height*0.5*Yzoom;
						//br2=br2*this.getHeight()*0.9;
						double zbir1= br+br2;
						int j=(int)(this.height-br);
						int k=(int)(br-br2);
						double br3=S.getMax()/l;
						br3=Math.pow(br3, -1)*this.height*0.5*Yzoom;
						//br=br*this.getHeight()*0.9;
						double br4=S.getMax()/h;
						br4=Math.pow(br4, -1)*this.height*0.5*Yzoom;
						double zbir2= br3+br4;
						int x=(int)(this.height-br3);
						int y=(int)(this.height-br4);
						//j=(int) (j*Yzoom);
						//k=(int) (k*Yzoom);
						//x=(int) (x*Yzoom);
						//y=(int) (y*Yzoom);
						g.setColor(Color.GREEN);
						g.fillRect(i*canWid, j, canWid, k);
						g.drawLine(i*canWid+canWid/2, y, i*canWid+canWid/2, x);
						//System.out.println(i*22 +" "+ j+" " + 20+" " + k);
					}else {
						double br=S.getMax()/c;
						br=Math.pow(br, -1)*this.height*0.5*Yzoom;
						//br=br*this.getHeight()*0.9;
						double br2=S.getMax()/o;
						br2=Math.pow(br2, -1)*this.height*0.5*Yzoom;
						//br2=br2*this.getHeight()*0.9;
						double zbir1= br+br2;
						int j=(int)(this.height-br2);
						int k=(int)(br2-br);
						double br3=S.getMax()/l;
						br3=Math.pow(br3, -1)*this.height*0.5*Yzoom;
						//br=br*this.getHeight()*0.9;
						double br4=S.getMax()/h;
						br4=Math.pow(br4, -1)*this.height*0.5*Yzoom;
						double zbir2= br3+br4;
						int x=(int)(this.height-br3);
						int y=(int)(this.height-br4);
						//j=(int) (j*Yzoom);
						//k=(int) (k*Yzoom);
						//x=(int) (x*Yzoom);
						//y=(int) (y*Yzoom);
						g.setColor(Color.RED);
						g.fillRect(i*canWid, j, canWid, k);
						g.drawLine(i*canWid+canWid/2, x, i*canWid+canWid/2, y);
						//System.out.println(i*22 +" "+ j+" " + 20+" " + k);
					}
					
					ArrayList<Integer> vals=new ArrayList<>();
					//System.out.println(S.getArrCandles().size());
					if(ema==true) {
						g.setColor(Color.CYAN);
						//TO DO docrtaj ove linije
						EMA x= new EMA(t);
						x.calculate(S.getArrCandles());
						for (int j = 0; j < S.getArrCandles().size(); j++) {
							double br4=S.getMax()/x.getValues().get(j);
							br4=Math.pow(br4, -1)*this.getHeight()*0.5*Yzoom;
							//System.out.println(x.getValues().get(j));
							int y=(int)((this.getHeight()-br4));
							//System.out.println(y);
							vals.add(y);
							
							if(j==0) {
								
							}else {
								int a= (int) (((j-1)*canWid+canWid/2));
								int b=	(int) ((j*canWid+canWid/2));
								int aa=(int) (vals.get(j-1));
								int bb=(int) (vals.get(j));
								g.drawLine(a, aa, b, bb);
								//this.repaint();
								this.revalidate();
								
							}
							
						}
						//System.out.println(x.getValues().size());
					}
					//System.out.println(vals.size());
					vals.clear();
					if(ma==true) {
						g.setColor(Color.PINK);
						//to do docrtaj ove linije
						MA x= new MA(t);
						x.calculate(S.getArrCandles());
						for (int j = 0; j < S.getArrCandles().size(); j++) {
							double br4=S.getMax()/x.getValues().get(j);
							br4=Math.pow(br4, -1)*this.getHeight()*0.5*Yzoom;
							//System.out.println(x.getValues().get(j));
							int y=(int)((this.getHeight()-br4));
							//System.out.println(y);
							vals.add(y);
							
							if(j==0) {
								
							}else {
								int a= (int) (((j-1)*canWid+canWid/2));
								int b=	(int) ((j*canWid+canWid/2));
								int aa=(int) (vals.get(j-1));
								int bb=(int) (vals.get(j));
								g.drawLine(a, aa, b, bb);
								//this.repaint();
								this.revalidate();
								
							}
							
						}
					}
					
					
				}
				//System.out.println(this.height);
			}
			
		}else {
			height= scrollPane.getViewport().getHeight();
			width= scrollPane.getViewport().getWidth();
			g.setColor(Color.DARK_GRAY);
			g.fillRect(0, 0,(int)( width*Xzoom),(int)( height*Yzoom));
			g.setColor(Color.WHITE);
			g.drawLine(0,(int)( height*Yzoom)/2, (int)( width*Xzoom), (int)( height*Yzoom)/2);
			double razlika=S.getMax()-S.getMin();
			double procenat=S.getMax()/100;
			
			
		}
		revalidate();
		updateScrollPane();
	    }
	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public Stock getS() {
		return S;
	}

	public void setS(Stock s) {
		S = s;
	}

	public boolean isEma() {
		return ema;
	}
	public void setEma(boolean ema) {
		this.ema = ema;
	}
	public boolean isMa() {
		return ma;
	}
	public void setMa(boolean ma) {
		this.ma = ma;
	}

	
}
