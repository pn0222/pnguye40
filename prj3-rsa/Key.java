//Paul Nguyen
//Dhrumin Patel
//University of Illinois in Chicago
//March/15/2016
//Project 3: RSA Encryption/Decryption

import java.io.*;
import java.util.Scanner;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

public class Key 
{
	//Instance variable of Key
	private String dString;
	private String eString;
	private String mString;
	private String nString;
	private String pString;
	private String qString;
	public LargeInt d;
	public LargeInt e;
	public LargeInt m;
	public LargeInt n;
	public LargeInt p;
	public LargeInt q;
	
	//Constructor for Key class
	public Key()
	{
		d = new LargeInt();
		e = new LargeInt();
		m = new LargeInt();
		n = new LargeInt();
		p = new LargeInt();
		q = new LargeInt();
		dString = null;
		eString = null;
		mString = null;
		nString = null;
		pString = null;
		qString = null;
		
		//Invalid key
		
		//When we generate a key, we request the value of p and q
		
	}

	public void makeXML()
	{
		FileWriter fw;
		try 
		{
			fw = new FileWriter("pubkey.xml");
			fw.write("<rsakey>\n"
					+ "\t<evalue>" + eString + "</evalue>\n"
					+ "\t<nvalue>" + nString + "</nvalue>\n"
				+ "</rsakey>\n");
			fw.close();
		} 
		catch (IOException e) 
		{
			System.out.println("ERROR: Fail in creating XML file.");
		}
		
		FileWriter fw2;
		try 
		{
			fw2 = new FileWriter("prikey.xml");
			fw2.write("<rsakey>\n"
					+ "\t<dvalue>" + dString + "</dvalue>\n"
					+ "\t<nvalue>" + nString + "</nvalue>\n"
				+ "</rsakey>\n");
			fw2.close();
		} 
		catch (IOException e) 
		{
			System.out.println("ERROR: Fail in creating XML file.");
		}
		
		
	}
	
	public void setD(String x)
	{
		dString = x;
		d.toArray(dString);
	}
	
	public void setE(String x)
	{
		eString = x;
		e.toArray(eString);
	}

	public void setN(String x)
	{
		nString = x;
		n.toArray(nString);
	}
	
	public void setP(String x)
	{
		pString = x;
		p.toArray(pString);
	}
	
	public void setQ(String x)
	{
		qString = x;
		q.toArray(qString);
	}
	
	public void generateD()
	{
		DynamicArray one = new DynamicArray();
		one.add(1);
		
		//n * m
		DynamicArray temp = n.mult(m.getArr());
		LargeInt temp2 = new LargeInt(temp.clone(temp));
		
		//(1 + mn)
		DynamicArray temp3 = temp2.add(one);
		LargeInt temp4 = new LargeInt(temp3.clone(temp3));
		
		//d = (1 + nm) / e
		d = new LargeInt (temp4.div(e.getArr()));
		dString = d.getArr().toString();
		
	}
	
	public void generateE()
	{
		DynamicArray temp = e.getCoPrime(m);
		e = new LargeInt(temp.clone(temp));
		eString = temp.toString();
		//temp.print();
		
	}
	
	public void generateM()
	{
		DynamicArray one = new DynamicArray();
		one.add(1);
		DynamicArray temp = p.sub(one);
		DynamicArray temp2 = q.sub(one);
		LargeInt t = new LargeInt(temp);
		DynamicArray temp3 = t.mult(temp2);
		
		m = new LargeInt(temp3.clone(temp3));
		temp3.reverse();
		mString = temp3.toString();
	}
	
	public void generateN()
	{
		DynamicArray qTemp =q.getArr().clone(q.getArr());
		DynamicArray temp = p.mult(qTemp);
		//temp.reverse();
		n = new LargeInt(temp.clone(temp));
		temp.reverse();
		nString = temp.toString();
	}
	
	public String getP()
	{
		return pString;
	}

	public String getQ()
	{
		return qString;
	}

	public String getM()
	{
		return mString;
	}
	
	public String getN()
	{
		return nString;
	}
	
	public String getD()
	{
		return dString;
	}
	
	public String getE()
	{
		return eString;
	}
	
	//Scanner the user input for (unknown);
	public String scanInput()
	{
		System.out.print("Input a valid prime number: ");
		Scanner sc = new Scanner(System.in);
		String x = null;
		
		while (sc.hasNext() && (x == null))
		{
			if (sc.hasNextInt())
			{
				LargeInt temp = new LargeInt();
				String t = sc.next();
				temp.toArray(t);
				if (temp.isPrime())
				{
					x = t;
					sc.close();
					return x;
				}
				else
				{
					System.out.println("The input \"" + t + "\" is not a valid prime number. Please try again.");
					System.out.print("Input a valid prime number: ");
				}
			}
			else
			{
				System.out.println("Invalid input. Please try again.");
				System.out.print("Input a valid prime number: ");
				//Move on to next input
				sc.next();
			}
		}
		sc.close();
		
		return x;
		
//		System.out.print("Please input a valid prime integer: ");
//		Scanner sc = new Scanner(System.in);
//		while (sc.hasNext())
//		{
//			if (sc.hasNextInt())
//			{
//				int x = sc.nextInt();
//				sc.close();
//				System.out.println(x);
//				return x;
//			}
//			else if (sc.next().equals("exit"))
//			{
//				System.exit(0);
//			}
//			else 
//			{
//				System.out.println("Invalid input... Try again.");
//				System.out.print("Please input a valid prime integer: ");
//				sc.next();			//Move onto the next input
//			}
//		}
//		sc.close();
//		return -1;					//Assuming we work with unsigned integer
	}
	/*TRANSFER OVER TO LARGEINT*/
//	public static boolean isPrime(int x)
//	{
//		for (int i = 2; i < x; i++)
//		{
//			if ((x % i) == 0)
//				return false;
//		}
//		return true;
//	}
}
