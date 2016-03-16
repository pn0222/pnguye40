//Paul Nguyen
//Dhrumin Patel
//University of Illinois in Chicago
//March/15/2016
//Project 3: RSA Encryption/Decryption


public class DynamicArray 
{
	//Instance variable of dynamic array
	private int[] dArr;
	private int size;
	private int cap;
	
	//Default constructor
	public DynamicArray()
	{
		cap = 1;
		dArr = new int[cap];
		size = 0;
	}
	
	//Get the current size of the array
	public int getSize()
	{
		return size;
	}
	
	public String getIthString(int x)
	{
		//Check if element is in boundary of the array
		if (x >= size)
			return null;
		
		String temp = "" + dArr[x];
		return temp;
	}
	
	public int getIthInt(int x)
	{
		//Check if element is in boundary of the array
		if (x >= size || x < 0)
			return -1;
		
		return dArr[x];
	}
	
	//Add a single digit into the dynamic array
	public void add(int x)
	{
		//If we reach the cap, we increase the capacity of the array
		if (size == cap)
		{
			dArr = grow();
		}
		
		dArr[size] = x;
		size++;
	}
	
	
	//Doubles the capacity when we need to increase the array
	public int[] grow()
	{
		int oldCap = cap;
		int newCap = oldCap * 2;
		int[] newArr = new int[newCap];
		
		//Update new capacity
		cap = newCap;
		
		//Duplicate everything from old array;
		for (int i = 0; i < oldCap; i++)
		{
			newArr[i] = dArr[i];
		}
		
		return newArr;
	}
	
	//Check if this array contains this element
	public boolean contain(int x)
	{
		for (int i = 0; i < size; i++)
		{
			if (dArr[i] == x)
				return true;
		}
		return false;
	}
	
	//For debugging, display the number in array form
	public void print()
	{
		for (int i = 0; i < size; i++)
		{
			System.out.print("[" + dArr[i] + "]" + " ");
		}
		System.out.println("");
	}
	
	//Reverse the list
	public void reverse()
	{
		//Base case when there is only one element or less
		if (size < 2)
			return;
		
		int[] temp = new int [size];
		
		//Store each element in reverse
		for (int i = 0; i < size; i++)
		{
			temp[i] = dArr[size - i - 1];
		}
		
		//Store the reverse list in place
		dArr = temp;
	}
	
	//Return the string form of this array
	public String toString()
	{
		String temp = "";
		
		for (int i = 0; i < size; i++)
		{
			temp += dArr[i];
		}
		
		//DEBUG INFORMATION
		//System.out.println(temp);
		
		return temp;
	}
	
	public DynamicArray clone(DynamicArray arr2)
	{
		DynamicArray newArr = new DynamicArray();
		for (int i = 0; i < arr2.getSize(); i++)
		{
			newArr.add(arr2.getIthInt(i));
		}
		return newArr;
	}
	
	//Check if another dynamic array is equal
	public boolean isEqual(DynamicArray arr2)
	{
		if (size != arr2.getSize())
			return false;
		
		for (int i = 0; i < size; i++)
		{
			if (dArr[i] != arr2.getIthInt(i))
			{
				return false;
			}
		}
		
		return true;
	}
	
	//Remove extra padding of zero
	public void cleanZero()
	{
		//Base case for one element left
		if (size == 1)
			return;
		
		int lastElem = size - 1;

		//Remove last element is zero, repeat
		if (dArr[lastElem] == 0)
		{
			int[] temp = new int[lastElem];
			
			//Copy every element over except the last one
			for (int i = 0; i < lastElem; i++)
			{
				temp[i] = dArr[i];
			}
			dArr = temp;
			//Decrement the size
			size--;
			cleanZero();
		}
		
		//Until the last element is not zero
		else
			return;
	}
	
}
