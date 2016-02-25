import java.util.Scanner;

public class Rsa {
	
	int key;
	
	//Default constructor for RSA
	public Rsa()
	{
		key = 0;
	}
	
	public static void main(String[] args)
	{
		Scanner sc = new Scanner(System.in);
		System.out.print("Input Value : ");
		while (sc.hasNextInt())
		{
			
			int input = sc.nextInt();
			//If user inputs a negative number, exit the program
			if (input < 0)
			{
				System.exit(0);
			}
			
			//Check if the user input is a prime number
			if (isPrime(input))
				System.out.println("THIS NUMBER IS A PRIME!!");
			else
				System.out.println("This number is not a prime...");
			
			
		}
		sc.close();
		
	}
	
	//Check if value x is a prime number
	public static boolean isPrime(int x)
	{
		//Base Case: 
		//P(0), P(1), P(n) for all n < 0
		if (x <= 1)
			return false;
		
		//If any number is divisible from 2 - (x - 1) then number x is not a prime
		for (int i = 2; i < x; i++)
		{
			if ( (x % i) == 0 )
				return false;
		}

		//Pass the test
		return true;
	}
}
