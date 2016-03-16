//Paul Nguyen
//Dhrumin Patel
//University of Illinois in Chicago
//March/15/2016
//Project 3: RSA Encryption/Decryption

public class LargeInt
{
	private int n;
	private String num;
	private DynamicArray arr;
	
	//Constructor to initialize large integer
	public LargeInt()
	{
		n = -1;
		num = null;
		arr = new DynamicArray();
	}
	
	public LargeInt(DynamicArray x)
	{
		n = -1;
		num = null;
		arr = x;
	}
	
	public DynamicArray getArr()
	{
		return arr;
	}
	
	public DynamicArray gcd(DynamicArray arr1, DynamicArray arr2)
	{
		
		if ((arr2.getSize() == 1) && (arr2.getIthInt(0) == 0))
			return arr1;
		
		LargeInt temp = new LargeInt(arr1.clone(arr1));
		DynamicArray temp2 = temp.mod(arr2);
		return gcd(arr2, temp2);
	}
	
	public void encrypt(DynamicArray e, DynamicArray n)
	{
		//C = m^(e) + n
		
		//C = 1;
		//for ( int i = 0 ; i < e ; i++ )
		//C = ( C * M ) % n;
		
		//C is the encrypted version of this Large Int
		//m is this array
		//e is the from key
		//n is from key
		
		//Need a constant one to traverse through the array
		DynamicArray constOne = new DynamicArray();
		constOne.add(1);
		
		//Initialize C = 1
		DynamicArray newArr = new DynamicArray();
		newArr.add(1);
		
		//Initialize i = 0
		DynamicArray i = new DynamicArray();
		i.add(0);
		
		//Construct e into a large int
		LargeInt e_int = new LargeInt(e.clone(e));
		
		//e > i
		while (e_int.isGreater(i))
		{
			
			LargeInt newArr_temp = new LargeInt(newArr);
			DynamicArray mult = newArr_temp.mult(arr);
			LargeInt mult_temp = new LargeInt(mult.clone(mult));
			newArr = mult_temp.mod(n);
			
			//increment i
			LargeInt i_temp = new LargeInt(i.clone(i));
			i = i_temp.add(constOne);
		}
		
		arr = newArr;
		

	}
	
	public void decrypt(DynamicArray d, DynamicArray n)
	{
		//C = m^(d) + n
		
		//C = 1;
		//for ( int i = 0 ; i < e ; i++ )
		//C = ( C * M ) % n;
		
		//C is the encrypted version of this Large Int
		//m is this array
		//e is the from key
		//n is from key
		
		//Need a constant one to traverse through the array
		DynamicArray constOne = new DynamicArray();
		constOne.add(1);
		
		//Initialize C = 1
		DynamicArray newArr = new DynamicArray();
		newArr.add(1);
		
		//Initialize i = 0
		DynamicArray i = new DynamicArray();
		i.add(0);
		
		//Construct e into a large int
		LargeInt d_int = new LargeInt(d.clone(d));

		//e > i
		while (d_int.isGreater(i))
		{
			LargeInt newArr_temp = new LargeInt(newArr);
			DynamicArray mult = newArr_temp.mult(arr);
			LargeInt mult_temp = new LargeInt(mult.clone(mult));
			newArr = mult_temp.mod(n);
			
			//increment i
			LargeInt i_temp = new LargeInt(i.clone(i));
			i = i_temp.add(constOne);
		}
		
		arr = newArr;
		

	}
	
	
	//Assuming num is an integer
	public void toArray(String s)
	{
		if (s != null)
		{
			arr = new DynamicArray();
			int length = s.length();
			//n = Integer.parseInt(s);
			for (int i = 0; i < length; i++)
			{
				int x = Integer.parseInt( ("" + s.charAt(length - i - 1) ));
				arr.add(x);
			}
			
			//DEBUG INFORMATION
			//arr.print();
		}
	}
	
	public int getNum()
	{
		return n;
	}
	
	//This operations needs to add two dynamic array together and
	//return a resulting dynamic array
	public DynamicArray add(DynamicArray arr2)
	{	
		DynamicArray newArr = new DynamicArray();
		int length;
		if (arr.getSize() > arr2.getSize())
		{
			length = arr.getSize();
		}
		else
		{
			length = arr2.getSize();
		}
		
		//Initialize a global carry for when its needed
		int carry = 0;
		
		for (int i = 0; i < length; i++)
		{
			int x = arr.getIthInt(i);
			int y = arr2.getIthInt(i);
			
			//If out of boundary, replace it with a zero
			if (x == -1)
				x = 0;

			//If out of boundary, replace it with a zero
			if (y == -1)
				y = 0;
			
			//DEBUG INFORMATION
			//System.out.println("x = " + x);
			//System.out.println("y = " + y);
			//System.out.println("carry = " + carry);
			
			int z = x + y + carry;
			
			if (z >= 10)
				carry = 1;
			else
				carry = 0;
			
			//Insert the value at position i
			z = z % 10;
			newArr.add(z);
		}
		
		//Insert another element if there still is a carry
		if (carry == 1)
			newArr.add(1);
		
		return newArr;
	}
	
	
	//This operations needs to subtract two dynamic array together and
	//return a resulting dynamic array
	public DynamicArray sub(DynamicArray arr2)
	{
		DynamicArray newArr = new DynamicArray();
		
		int length;
		
		//Get the max length to scan thru
		if (arr.getSize() > arr2.getSize())
		{
			length = arr.getSize();
		}
		else
		{
			length = arr2.getSize();
		}
		
		int carry = 0;
		
		for (int i = 0; i < length; i++)
		{
			int x = arr.getIthInt(i);
			int y = arr2.getIthInt(i);
			
			//If out of boundary, replace it with a zero
			if (x == -1)
				x = 0;

			//If out of boundary, replace it with a zero
			if (y == -1)
				y = 0;
			
			//DEBUG INFORMATION
			//System.out.println("x = " + x);
			//System.out.println("y = " + y);
			//System.out.println("carry = " + carry);
			
			//Perform subtract operation
			int z = x - y - carry;
			
			//Adjust for carry
			if (z < 0)
			{
				carry = 1;
				z = z + 10;
			}
			
			else
				carry = 0;
			
			//Insert the value at position i
			z = z % 10;
			newArr.add(z);
		}
		
		//Set new array to zero if there is a carry leftover since we can't have negative number
		if (carry == 1 || arr.isEqual(arr2))
		{
			newArr = new DynamicArray();
			newArr.add(0);
		}
		
		//Remove all padding zeros
		newArr.cleanZero();
		
		return newArr;
	}
	
	//This operations needs to multiply two dynamic array together and
	//return a resulting dynamic array
	public DynamicArray mult(DynamicArray arr2)
	{
		
		DynamicArray newArr = new DynamicArray();
		
		int carry = 0;
		int z = 0;
		for (int i = 0; i < arr2.getSize(); i++)
		{
			//Place holder to sum all the multiplication up
			DynamicArray temp = new DynamicArray();
			carry = 0;
			
			int k = 0;
			
			//Adjust padding for proper multiplication operation
			while (k < i)
			{
				temp.add(0);
				k++;
			}
			
			for (int j = 0; j < arr.getSize(); j++)
			{
				
				int x = arr2.getIthInt(i);
				int y = arr.getIthInt(j);
				
				//If out of boundary, replace it with a zero
				if (x == -1)
				{
					x = 0;
				}
				
				//If out of boundary, replace it with a zero
				if (y == -1)
				{
					y = 0;
				}
				
				//If out of boundary, replace it with a zero
				if (z == -1)
				{
					z = 0;
				}

				//Perform multiplication operation
				z = x * y + carry;
				
				//Adjust the carry and current position input
				if (z >= 10)
				{
					carry = z / 10;
					z = z % 10;
				}
					
				else
					carry = 0;
				
				//DEBUG INFORMATION
				//System.out.println("x: " + x);
				//System.out.println("y: " + y);
				//System.out.println("z: " + z);
				//System.out.println("carry: " + carry);				
				
				temp.add(z);
			}
			
			//If there is a remaining carry add to the end
			if (carry > 0)
				temp.add(carry);
			
			//Add each operations together to perform multiplication
			LargeInt l = new LargeInt(temp.clone(temp));
			newArr = l.add(newArr);
		}
		
		return newArr;
	}
	
	//This operations needs to division two dynamic array together and
	//return a resulting dynamic array
	public DynamicArray div(DynamicArray arr2)
	{
		DynamicArray newArr = new DynamicArray();
		DynamicArray remainder = arr;
		LargeInt temp = new LargeInt(remainder.clone(remainder));
		
		//If numerator is smaller then return 0
		if (isLesser(arr2))
		{
			newArr.add(0);
			return newArr;
		}
		
		//If numerator and denominator are equal return 1
		else if (isEqual(arr2))
		{
			newArr.add(1);
			return newArr;
		}
		
		//Otherwise attempt to do divide operation
		else
		{
			for (int i = 0; i < arr.getSize(); i++)
			{
				DynamicArray powTemp = powTen(arr2, (arr.getSize() - i - 1));
				LargeInt check = new LargeInt(powTemp.clone(powTemp));
				int j = 0;
				
				while (check.isLesserOrEqual(remainder))
				{
					remainder = temp.sub(powTemp);
					temp = new LargeInt(remainder.clone(remainder));
					j++;
				}
				
				newArr.add(j);
			}
			
			//Clean up and return
			newArr.reverse();
			newArr.cleanZero();
			newArr.reverse();
			return newArr;
		}
	}
	
	public DynamicArray pow(DynamicArray arr2)
	{
		DynamicArray newArr = new DynamicArray();
		
		//Anything power to the 0 is 1
		if (arr2.getSize() == 1 && (arr2.getIthInt(0) == 0))
		{
			newArr.add(1);
			return newArr;
		}

		//Initialize counter to 0
		DynamicArray zero = new DynamicArray();
		zero.add(0);
		LargeInt counter = new LargeInt(zero.clone(zero));
		
		//Initialize the number 1 to add
		DynamicArray one = new DynamicArray();
		one.add(1);
		
		//By default everything above one is at least one
		DynamicArray total = new DynamicArray();
		total.add(1);
		
		//Multiply until we reach the length of array 2
		while (counter.isLesser(arr2))
		{
			total = mult(total);
			
			//Increment counter
			DynamicArray temp = counter.add(one);
			counter = new LargeInt(temp.clone(temp));
		}
		return total;
	}
	
	public DynamicArray powTen(DynamicArray arr2, int x)
	{
		if (arr2 == null)
			return null;
		
		DynamicArray temp = new DynamicArray();
		
		//Pad the front with the proper amount of zero
		for (int i = 0; i < x; i++)
		{
			temp.add(0);
		}
		
		//Add the rest of the array
		for (int j = 0; j < arr2.getSize(); j++)
		{
			temp.add(arr2.getIthInt(j));
		}
		
		return temp;
	}
	
	
	//This operations needs to modulus two dynamic array together and
	//return a resulting dynamic array
	public DynamicArray mod(DynamicArray arr2)
	{
		DynamicArray newArr = new DynamicArray();
		DynamicArray remainder = arr;
		LargeInt temp = new LargeInt(remainder.clone(remainder));
		
		//If numerator is smaller then return 0
		if (isLesser(arr2))
		{
			return arr;
		}
		
		//If numerator and denominator are equal return 0
		else if (isEqual(arr2))
		{
			newArr.add(0);
			return newArr;
		}
		
		//Otherwise attempt to do divide operation
		else
		{
			for (int i = 0; i < arr.getSize(); i++)
			{
				DynamicArray powTemp = powTen(arr2, (arr.getSize() - i - 1));
				LargeInt check = new LargeInt(powTemp.clone(powTemp));
				
				while (check.isLesserOrEqual(remainder))
				{
					remainder = temp.sub(powTemp);
					temp = new LargeInt(remainder.clone(remainder));
				}
				
			}
			
			return remainder;
		}
	}

	//This operations compares the two dynamic array and see if they are equal
	public boolean isEqual(DynamicArray arr2)
	{
		if (arr.getSize() != arr2.getSize())
			return false;
		
		else
			for (int i = 0; i < arr.getSize(); i++)
			{
				if (arr.getIthInt(arr.getSize() - i - 1) != arr2.getIthInt(arr2.getSize() - i - 1))
					return false;
			}
		return true;
	}
	
	public DynamicArray getCoPrime(LargeInt m)
	{
		DynamicArray var = new DynamicArray();
		var.add(2);
		LargeInt li_var = new LargeInt(var.clone(var));
		
		DynamicArray constOne = new DynamicArray();
		constOne.add(1);
		
		while (m.isGreaterOrEqual(var))
		{
			DynamicArray check = new DynamicArray();
			check = m.gcd(m.getArr(), var);
			LargeInt temp = new LargeInt(check.clone(check));
			if (temp.isEqual(constOne))
			{
				return var;
			}
			
			var = li_var.add(constOne);
			li_var = new LargeInt(var.clone(var));
		}
		return null;
	}
	

	//This operations compares the two dynamic array and see if they are not equal
	public boolean isNotEqual(DynamicArray arr2)
	{
		if (arr.getSize() != arr2.getSize())
			return true;
		
		else
			for (int i = 0; i < arr.getSize(); i++)
			{
				if (arr.getIthInt(i) != arr2.getIthInt(i))
					return true;
			}
		return false;
	}
	
	//This operations compares the two dynamic array and see if array 1 is greater than array 2
	public boolean isGreater(DynamicArray arr2)
	{
		//If this value's length is less than array 2, return false
		if (arr.getSize() < arr2.getSize())
			return false;
		
		else if (arr.getSize() > arr2.getSize())
			return true;
		
		else //if(arr.getSize() == arr2.getSize())
		{
			for (int i = 0; i < arr.getSize(); i++)
			{
				if (arr.getIthInt(arr.getSize() - i - 1) == arr2.getIthInt(arr2.getSize() - i - 1))
				{
					;//Move onto the next element to check again
				}
				
				//If element is less than array 2
				else if (arr.getIthInt(arr.getSize() - i - 1) < arr2.getIthInt(arr2.getSize() - i - 1))
				{
					return false;
				}
				
				//Otherwise return false
				else
				{
					return true;
				}
			}
			
			//If we reach here they are equal
			return false;
		}
	}

	//This operations compares the two dynamic array and see if array 1 is greater than or equal array 2
	public boolean isGreaterOrEqual(DynamicArray arr2)
	{
		//If this value's length is less than array 2, return false
		if (arr.getSize() < arr2.getSize())
			return false;
		
		else if (arr.getSize() > arr2.getSize())
			return true;
		
		else //if(arr.getSize() == arr2.getSize())
		{
			for (int i = 0; i < arr.getSize(); i++)
			{
				if (arr.getIthInt(arr.getSize() - i - 1) == arr2.getIthInt(arr2.getSize() - i - 1))
				{
					;//Move onto the next element to check again
				}
				
				//If element is less than array 2
				else if (arr.getIthInt(arr.getSize() - i - 1) < arr2.getIthInt(arr2.getSize() - i - 1))
				{
					return false;
				}
				
				//Otherwise return false
				else
				{
					return true;
				}
			}
			
			//If we reach here they are equal
			return true;
		}
	}
	

	//This operations compares the two dynamic array and see if array 1 is lesser than array 2
	public boolean isLesser(DynamicArray arr2)
	{
		//If this value's length is less than array 2, return false
		if (arr.getSize() < arr2.getSize())
			return true;
		
		else if (arr.getSize() > arr2.getSize())
			return false;
		
		else //if(arr.getSize() == arr2.getSize())
		{
			for (int i = 0; i < arr.getSize(); i++)
			{
				if (arr.getIthInt(arr.getSize() - i - 1) == arr2.getIthInt(arr2.getSize() - i - 1))
				{
					;//Move onto the next element to check again
				}
				
				//If element is less than array 2
				else if (arr.getIthInt(arr.getSize() - i - 1) < arr2.getIthInt(arr2.getSize() - i - 1))
				{
					return true;
				}
				
				//Otherwise return false
				else
				{
					return false;
				}
			}
			
			//If we reach here they are equal
			return false;
		}
	}
	
	//This operations compares the two dynamic array and see if array 1 is lesser than or equal array 2
	public boolean isLesserOrEqual(DynamicArray arr2)
	{
		//If this value's length is less than array 2, return false
		if (arr.getSize() < arr2.getSize())
			return true;
		
		else if (arr.getSize() > arr2.getSize())
			return false;
		
		else //if(arr.getSize() == arr2.getSize())
		{
			for (int i = 0; i < arr.getSize(); i++)
			{
				if (arr.getIthInt(arr.getSize() - i - 1) == arr2.getIthInt(arr2.getSize() - i - 1))
				{
					;//Move onto the next element to check again
				}
				
				//If element is less than array 2
				else if (arr.getIthInt(arr.getSize() - i - 1) < arr2.getIthInt(arr2.getSize() - i - 1))
				{
					return true;
				}
				
				//Otherwise return false
				else
				{
					return false;
				}
			}
			
			//If we reach here they are equal
			return true;
		}
	}
	
	//This functions check if this integer is 
	public boolean isPrime()
	{
		for (int i = 2; i < n; i++)
		{
			if ((n % i) == 0)
				return false;
		}
		return true;
	}
	
}
