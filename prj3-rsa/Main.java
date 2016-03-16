//Paul Nguyen
//Dhrumin Patel
//University of Illinois in Chicago
//March/15/2016
//Project 3: RSA Encryption/Decryption

import javax.swing.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.awt.*;
import java.awt.event.*;
import java.util.Scanner;
import java.io.*;
import java.util.Random;
import java.util.*;

public class Main extends JFrame implements ActionListener
{
	JPanel control;

	//List of prime numbers
	DynamicArray prime;
	
	//Store the user's key
	Key storage;
	
	//Store blocking information
	int bSize;
	String bName;
	
	//Store encryption information
	String eName;
	
	//Store decryption information
	String dName;
	
	
	//For the menu
	JMenuBar menu;
	JMenu command;
	JMenuItem decrypt, encrypt, keyCreation, msgBlocking;
	
	public Main()
	{	
		super("RSA Encryption/Decryption");
		JFrame.setDefaultLookAndFeelDecorated(true);
		setSize(300, 300);
		setMenu();
		
		prime = new DynamicArray();
		
		getPrimeList();
		
		storage = new Key();
	}
	
	//Set up the menu bar for GUI
	public void setMenu()
	{
		menu = new JMenuBar();
		
		command = new JMenu("Command");
		command.setMnemonic(KeyEvent.VK_C);
		
		keyCreation = new JMenuItem("Key Creation");
		keyCreation.setMnemonic(KeyEvent.VK_K);
		keyCreation.addActionListener(this);
		
		msgBlocking = new JMenuItem("Message Blocking");
		msgBlocking.setMnemonic(KeyEvent.VK_B);
		msgBlocking.addActionListener(this);
		
		encrypt = new JMenuItem("Encrypt");
		encrypt.setMnemonic(KeyEvent.VK_E);
		encrypt.addActionListener(this);

		decrypt = new JMenuItem("Decrypt");
		decrypt.setMnemonic(KeyEvent.VK_D);
		decrypt.addActionListener(this);
		
		command.add(keyCreation);
		command.add(msgBlocking);
		command.add(encrypt);
		command.add(decrypt);
		
		menu.add(command);
		
		this.setJMenuBar(menu);
	}
	
	public static void main(String[] args)
	{
		
		Main n = new Main();
		n.setVisible(true);

	}
	
	/******************ACTION EVENT************************/
	public void actionPerformed(ActionEvent e)
	{
		Object source = e.getSource();
		
		if (source == keyCreation)
		{
			//Initialize a new key
			storage = new Key();
			
			//Get two prime numbers for p and q
			storage.setP(primeInput());
			storage.setQ(primeInput());
			
			DynamicArray temp = new DynamicArray();
			temp = storage.p.add(storage.q.getArr());
			//temp.print();
			temp.reverse();
			//temp.print();
			
			DynamicArray temp2 = new DynamicArray();
			temp2 = storage.p.sub(storage.q.getArr());
			//temp2.print();
			temp2.reverse();
			//temp2.print();

			DynamicArray temp3 = new DynamicArray();
			temp3 = storage.p.mult(storage.q.getArr());
			//temp3.print();
			temp3.reverse();
			//temp3.print();

			DynamicArray temp4 = new DynamicArray();
			temp4 = storage.p.div(storage.q.getArr());
			//temp3.print();
			temp4.reverse();
			//temp3.print();

			DynamicArray temp5 = new DynamicArray();
			temp5 = storage.p.mod(storage.q.getArr());
			//temp3.print();
			temp5.reverse();
			//temp3.print();
			
			
			storage.generateN();
			storage.generateM();
					
			storage.generateE();
			storage.generateD();	
			
			storage.makeXML();

			DynamicArray temp6 = new DynamicArray();
			temp6 = storage.e.gcd(storage.p.getArr(), storage.q.getArr());			
			temp6.reverse();

			DynamicArray temp7 = new DynamicArray();
			temp7 = storage.p.pow(storage.q.getArr());
			//temp3.print();
			temp7.reverse();
			//temp3.print();
			
			JOptionPane.showMessageDialog(keyCreation, "p = " + storage.getP() +"\n"
					+ "q = " + storage.getQ() + "\n"
					+ "m = " + storage.getM() + "\n"
					+ "n = " + storage.getN() + "\n"
					+ "e = " + storage.getE() + "\n"
					+ "d = " + storage.getD() + "\n"
					+ "Addition of p + q: " + temp.toString() + "\n"
					+ "Subtraction of p - q: " + temp2.toString() + "\n"
					+ "Multiplication of p * q: " + temp3.toString() + "\n"
					+ "Division of the p / q: " + temp4.toString() + "\n"
					+ "Modulus of the p % q: " + temp5.toString() + "\n"
					+ "gcd of the p and q: " + temp6.toString() + "\n"
					+ "gcd of the p ^ q: " + temp7.toString(), "Generated Output", JOptionPane.PLAIN_MESSAGE);
		}
		
		if (source == msgBlocking)
		{
			blockInput();
			blockOperation();
			JOptionPane.showMessageDialog(this,"Finished blocking file.", "Blocking", JOptionPane.PLAIN_MESSAGE);
			
		}
		if (source == encrypt)
		{
			String eTxt = null;
			String nTxt = null;
			//Attempt to read XML file
			try
			{
				File file = new File("pubkey.xml");
				DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
				Document doc = dBuilder.parse(file);
				
				doc.getDocumentElement().normalize();
				
				NodeList nList = doc.getElementsByTagName("rsakey");
				
				for (int i = 0; i < nList.getLength(); i++)
				{
					Node nNode = nList.item(i);
				
					if (nNode.getNodeType() == Node.ELEMENT_NODE)
					{
						Element eElement = (Element) nNode;

						eTxt = eElement.getElementsByTagName("evalue").item(0).getTextContent();
						nTxt = eElement.getElementsByTagName("nvalue").item(0).getTextContent(); 
						
						
						
						//DEBUG INFORMATION
						//System.out.println("eValue: " + eElement.getElementsByTagName("evalue").item(0).getTextContent());
						//System.out.println("nValue: " + eElement.getElementsByTagName("nvalue").item(0).getTextContent());
					}
				}
			}
			catch (Exception e1)
			{
				System.out.println("ERROR: Public key file does not exist. Please generate one first.");
				return;
			}

			storage.e.toArray(eTxt);
			//storage.e.getArr().reverse();
			

			storage.n.toArray(nTxt);
			//storage.n.getArr().reverse();
			LargeInt[] blockFile;
			
			encryptInput();
			//Perform the encryption operation
			//C = M^(e) mod n

			//Convert string into large integer
			
			
			//create an array of largeInt
			
			blockFile = getBlockFile(eName);
			int row = blockFile.length;
			for (int i = 0; i < row; i++)
			{
				blockFile[i].encrypt(storage.e.getArr(), storage.n.getArr());
			}
			
			String[] blockInfo = new String[row];
			for (int i = 0; i < row; i++)
			{
				blockInfo[i] = "";
			}
			
			for (int i = 0; i < row; i++)
			{
				for (int j = 0; j < blockFile[i].getArr().getSize(); j++)
				{
					blockInfo[i] += blockFile[i].getArr().getIthInt(blockFile[i].getArr().getSize() - j - 1);
				}
			}
			
			FileWriter fw;
			try 
			{
				fw = new FileWriter(eName + ".ef");
				for(int i = 0; i < row; i++)
				{
					fw.write(blockInfo[i]);
					fw.write(System.getProperty("line.separator"));
				}
				fw.close();
			} 
			catch (IOException e1) 
			{
				System.out.println("ERROR: Fail in creating ef file.");
			}
			JOptionPane.showMessageDialog(this,"Finished encrypted.", "Encryption", JOptionPane.PLAIN_MESSAGE);
			
			
		}
	
		if (source == decrypt)
		{
			String dTxt = null;
			String nTxt = null;
			//Attempt to read XML file
			try
			{
				File file = new File("prikey.xml");
				DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
				Document doc = dBuilder.parse(file);
				
				doc.getDocumentElement().normalize();
				
				NodeList nList = doc.getElementsByTagName("rsakey");
				
				for (int i = 0; i < nList.getLength(); i++)
				{
					Node nNode = nList.item(i);
				
					if (nNode.getNodeType() == Node.ELEMENT_NODE)
					{
						Element eElement = (Element) nNode;

						dTxt = eElement.getElementsByTagName("dvalue").item(0).getTextContent();
						nTxt = eElement.getElementsByTagName("nvalue").item(0).getTextContent(); 
						
						
						
						//DEBUG INFORMATION
						//System.out.println("eValue: " + eElement.getElementsByTagName("evalue").item(0).getTextContent());
						//System.out.println("nValue: " + eElement.getElementsByTagName("nvalue").item(0).getTextContent());
					}
				}
			}
			catch (Exception e1)
			{
				System.out.println("ERROR: Private key file does not exist. Please generate one first.");
				return;
			}

			storage.d.toArray(dTxt);
			//storage.e.getArr().reverse();
			

			storage.n.toArray(nTxt);
			//storage.n.getArr().reverse();
			LargeInt[] blockFile;
			
			decryptInput();
			//Perform the encryption operation
			//C = M^(e) mod n

			//Convert string into large integer
			
			
			//create an array of largeInt
			FileWriter fw;
			try 
			{
					
				blockFile = getEncryptedFile(dName);
				int row = blockFile.length;
				
				//DEBUG INFORMATION
				//storage.d.getArr().print();
				//storage.n.getArr().print();
				//blockFile[0].getArr().print();
				for (int i = 0; i < row; i++)
				{	
					blockFile[i].encrypt(storage.d.getArr(), storage.n.getArr());
				}
				
				String[] blockInfo = new String[row];
				for (int i = 0; i < row; i++)
				{
					blockInfo[i] = "";
				}
				
				for (int i = 0; i < row; i++)
				{
					for (int j = 0; j < blockFile[i].getArr().getSize(); j++)
					{
						blockInfo[i] += blockFile[i].getArr().getIthInt(blockFile[i].getArr().getSize() - j - 1);
					}
				}
				String ascii = "";
				for (int i = 0; i < row; i++)
				{
					ascii += blockInfo[i];
				}
				
				//Covert encryption back to acii value
				StringBuilder output = new StringBuilder();
			    for (int i = 0; i < ascii.length(); i+=2) 
			    {
			        String str = (ascii.substring(i, i+2) + 27);
			        output.append((char)Integer.parseInt(str, 16));
			    }
			    String original = output.toString();
			    
				fw = new FileWriter(dName + ".txt");
				//for(int i = 0; i < row; i++)
				//{
					fw.write(ascii);
					//fw.write(original);
					//fw.write(System.getProperty("line.separator"));
				//}
				fw.close();
			} 
			catch (IOException e1) 
			{
				System.out.println("ERROR: Fail in creating XML file.");
			}
			
			JOptionPane.showMessageDialog(this,"Finished decrypted.", "Decryption", JOptionPane.PLAIN_MESSAGE);
			
		}
	
	}

	public LargeInt[] getBlockFile(String name)
	{
		LargeInt[] blockFile;
		//See how many rows there are in the block file
		int row = 0;
		File file = new File(name + ".bf");
		try 
		{
			Scanner sc = new Scanner(file);
			while (sc.hasNext())
			{
				sc.nextLine();
				row++;
			}
			sc.close();
		} 
		catch (FileNotFoundException e) 
		{
			System.out.println("ERROR: " + name + ".bf does not exist.");
			return null;
		}
		
		//Empty block file
		if (row == 0)
			return null;
		
		blockFile = new LargeInt[row];
		for (int i = 0; i < row; i++)
		{
			blockFile[i] = new LargeInt();
		}
		
		File file2 = new File(name + ".bf");
		try 
		{
			Scanner sc = new Scanner(file2);
			int i = 0;
			while (sc.hasNext())
			{
				String temp = sc.nextLine();
				blockFile[i].toArray(temp);
				i++;
			}
			sc.close();
		} 
		catch (FileNotFoundException e) 
		{
			System.out.println("ERROR: " + name + ".bf does not exist.");
		}
		
		return blockFile;
		
	}
	
	public LargeInt[] getEncryptedFile(String name)
	{
		LargeInt[] blockFile;
		//See how many rows there are in the block file
		int row = 0;
		File file = new File(name + ".ef");
		try 
		{
			Scanner sc = new Scanner(file);
			while (sc.hasNext())
			{
				sc.nextLine();
				row++;
			}
			sc.close();
		} 
		catch (FileNotFoundException e) 
		{
			System.out.println("ERROR: " + name + ".ef does not exist.");
			return null;
		}
		
		//Empty block file
		if (row == 0)
			return null;
		
		blockFile = new LargeInt[row];
		for (int i = 0; i < row; i++)
		{
			blockFile[i] = new LargeInt();
		}
		
		File file2 = new File(name + ".ef");
		try 
		{
			Scanner sc = new Scanner(file2);
			int i = 0;
			while (sc.hasNext())
			{
				String temp = sc.nextLine();
				blockFile[i].toArray(temp);
				i++;
			}
			sc.close();
		} 
		catch (FileNotFoundException e) 
		{
			System.out.println("ERROR: " + name + ".ef does not exist.");
		}
		
		return blockFile;
		
	}
	
	//Read the private key XML file
	public void readPri()
	{
		try
		{
			File file = new File("prikey.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(file);
			
			doc.getDocumentElement().normalize();
			
			NodeList nList = doc.getElementsByTagName("rsakey");
			
			for (int i = 0; i < nList.getLength(); i++)
			{
				Node nNode = nList.item(i);
				
				if (nNode.getNodeType() == Node.ELEMENT_NODE)
				{
					Element eElement = (Element) nNode;
					
					String d = eElement.getElementsByTagName("dvalue").item(0).getTextContent();
					String n = eElement.getElementsByTagName("nvalue").item(0).getTextContent(); 
					storage.setD(d);
					storage.setN(n);
					
					
					//DEBUG INFORMATION
					//System.out.println("dValue: " + eElement.getElementsByTagName("dvalue").item(0).getTextContent());
					//System.out.println("nValue: " + eElement.getElementsByTagName("nvalue").item(0).getTextContent());
				}
			}
		}
		catch (Exception e)
		{
			System.out.println("ERROR: Private key file does not exist. Please generate one first.");
		}
	}
	
	//MINIMUM PRIME NUMBER NEED IMPLEMENTATION*****
	//Get an input of a prime number
	public String primeInput()
	{
		
		JFrame frame = new JFrame();
		String input = JOptionPane.showInputDialog(frame, "Please input a valid prime number. Enter \"random\" to generate a random prime number.", "Key Creation", JOptionPane.PLAIN_MESSAGE);
		
		//If user input "random" we generate their prime numbers
		if (input.equals("random"))
		{
			int length = prime.getSize();
			Random r = new Random();
			int i = r.nextInt(length);
			return prime.getIthString(i);
		}
		
		while (!isPrime(input))
		{
			input = JOptionPane.showInputDialog(frame, "Invalid Input. Please input a valid prime number. Enter \"random\" to generate a random prime number.", "Key Creation", JOptionPane.PLAIN_MESSAGE);
			
			//If user input "random" we generate their prime numbers
			if (input.equals("random"))
			{
				int length = prime.getSize();
				Random r = new Random();
				int i = r.nextInt(length);
				return prime.getIthString(i);
			}
		}
		
		//DEBUG INFORMATION
		//System.out.println("The final input: " + input);
		
		return input;
	}
	
	//Get an input for blocking
	public void blockInput()
	{
		JFrame frame = new JFrame();
		String input = JOptionPane.showInputDialog(frame, "Please give a valid block size.", "Blocking", JOptionPane.PLAIN_MESSAGE);
		
		//If value is not a number or less than 0, then keep asking for proper input
		while(!isNumeric(input))
		{
			input = JOptionPane.showInputDialog(frame, "Invalid Input. Please give a valid block size.", "Blocking", JOptionPane.PLAIN_MESSAGE);
		}
		
		String name = JOptionPane.showInputDialog(frame, "Open which file?", "Blocking", JOptionPane.PLAIN_MESSAGE);
		
		while(name.length() == 0)
		{
			name = JOptionPane.showInputDialog(frame, "Invalid Input. Open which file? Do not include \".txt\" in the name", "Blocking", JOptionPane.PLAIN_MESSAGE);	
		}

		//Convert input to integer
		bSize = Integer.parseInt(input);
		bName = name;
	}
	
	
	//Get the user input of the blocking file to encrypt
	public void encryptInput()
	{
		JFrame frame = new JFrame();
		
		String name = JOptionPane.showInputDialog(frame, "Open which blocking file? Do not include \".bf\" in the name.", "Encryption", JOptionPane.PLAIN_MESSAGE);
		
		while(name.length() == 0)
		{
			name = JOptionPane.showInputDialog(frame, "Invalid Input. Open which blocking file? Do not include \".bf\" in the name", "Encryption", JOptionPane.PLAIN_MESSAGE);	
		}

		//Transfer name
		eName = name;	
	}
	
	public void decryptInput()
	{
		JFrame frame = new JFrame();
		
		String name = JOptionPane.showInputDialog(frame, "Open which encrypted file? Do not include \".ef\" in the name.", "Decryption", JOptionPane.PLAIN_MESSAGE);
		
		while(name.length() == 0)
		{
			name = JOptionPane.showInputDialog(frame, "Invalid Input. Open which encrypted file? Do not include \".ef\" in the name", "Decryption", JOptionPane.PLAIN_MESSAGE);	
		}

		//Transfer name
		dName = name;	
	}
	
	public int getBlockSize(String name)
	{
		File file = new File(name + ".bf");
		int size = 0;
		String temp = null;
		try 
		{
			Scanner sc = new Scanner(file);
			if (sc.hasNext())
			{
				temp = sc.nextLine();
			}
			sc.close();
			
		} 
		catch (FileNotFoundException e) 
		{
			System.out.println("ERROR: " + name + ".ef file does not exist.");
			return 0;
		}
		size = temp.length() / 2;
		return size;
		
	}

	
	public void blockOperation()//String file)
	{
		//Attempt to open the file that the user inputed
		File f = new File(bName + ".txt");
		  
		if (!f.exists())
		{
			JOptionPane.showMessageDialog(this, bName + ".txt does not exist. Please try again.", "Error", JOptionPane.PLAIN_MESSAGE);
			return;
		}
		
		String file = "";
		try 
		{
			Scanner sc = new Scanner(f);
			while (sc.hasNext())
			{
				file += sc.nextLine();
				if (sc.hasNext())
					file += '\n';
					
			}
		} 
		catch (FileNotFoundException e1) 
		{
			System.out.println("ERROR: Scanning file failed.");
		}
		
		//DEBUG INFORMATION
		//System.out.println(file);
		
		int length = file.length();
		int row = (length / bSize) + 1;
		
		//Initialize each block with nothing
		String[] blocked = new String[row];
		for (int i = 0; i < row; i++)
			blocked[i] = "";
		
		//DEBUG INFOMRATION
		//String ascii = "";
		//for (int i = 0; i < length; i++)
		//{
		//	int temp = file.charAt(length - i - 1) - 27;
		//	ascii += temp;
		//}
		//System.out.println(ascii);
		
		//Block the message properly
		for (int i = 0; i < row; i++)
		{
			for (int j = 0; j < bSize; j++)
			{
				//Check boundaries of the string
				if ((((bSize * (1 + i)) - 1) - j < length) && (((bSize * (1 + i)) - 1) - j) >= 0)
				{
					int temp = file.charAt(((bSize * (1 + i)) - 1) - j) - 27;
					if (temp < 10)
						blocked[i]+= '0';
					blocked[i] += temp;
				}
				
				//Pad the rest with extra zeros if needed
				else
					blocked[i] += "00";
			}
		}
		
		//DEBUG INFORMATION
		//for (int i = 0; i < row; i++)
		//{
		//	System.out.println(blocked[i]);
		//}
		
		FileWriter fw;
		try 
		{
			fw = new FileWriter(bName + ".bf");
			for(int i = 0; i < row; i++)
			{
				fw.write(blocked[i]);
				fw.write(System.getProperty("line.separator"));
			}
			fw.close();
		} 
		catch (IOException e) 
		{
			System.out.println("ERROR: Fail in creating XML file.");
		}
		
	}
	
	//Check if a string is a valid number and greater than 0
	public boolean isNumeric(String x)
	{
		int n;
		try
		{
			n = Integer.parseInt(x);
		}
		catch(NumberFormatException e)
		{
			return false;
		}
		
			if (n > 0)
				return true;
			else
				return false;
	}
	
	//Check if the string is a valid prime number
	public boolean isPrime(String x)
	{
		try
		{
			int n = Integer.parseInt(x);
			if (n <= 1)
				return false;
			
			for (int i = 2; i < n; i++)
			{
				if (n % i == 0)
					return false;
			}
		}
		catch(NumberFormatException e)
		{
			return false;
		}
		return true;
	}

	//Check if the integer is a valid prime number
	public boolean isPrime(int x)
	{
		if (x <= 0)
			return false;
		for (int i = 2; i < x; i++)
		{
			if (x % i == 0)
				return false;
		}
		return true;
	}
	
	//Access a given list of prime numbers
	public void getPrimeList()
	{
		//Attempt to read the file
		File file = new File("primeNumbers.rsc");
		
		try
		{
			Scanner sc = new Scanner(file);
	
			while (sc.hasNext())
			{
				if (sc.hasNextInt())
				{
					int x = sc.nextInt();
			
					//If the input is a prime and not a duplicate, store it into our prime array
					if (isPrime(x))
					{
						if (!prime.contain(x))
						{
							prime.add(x);
							
							//DEBUG INFORMATION
							//System.out.println(x);
						}
					}
				}
				//Invalid input, move onto the next item
				else
				{
					sc.next();
				}
			}
			
			sc.close();
		}
		
		//If the file doesn't exist, produce error message
		catch (IOException e)
		{
			System.out.println("\"primeNumbers.rsc\" does not exist.");
		}
			
	}
	
	
}
