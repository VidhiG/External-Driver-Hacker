import java.io.*;
class Hack
{
	private File extDrive;
	private String content[];
	private File toBeHacked;

	public void readDrive()
	{
		File arr[] = File.listRoots();
		extDrive = arr[arr.length-1];
		content=extDrive.list();
	}

	public void surfDrive()
	{
		int i,ch;
		String path = extDrive.getPath();
		do
		{
			i=1;
			try
			{
				System.out.println("---------------------------Contents-----------------------------");
				for(String s:content)
				{
					System.out.println(i+"."+s);
					i++;			
				}
				System.out.println("----------------------------------------------------------------");
				System.out.println("You can choose file or enter 0 to proceed towards hacking");
				System.out.println("----------------------------------------------------------------");
				ch=Integer.parseInt(System.console().readLine("Enter your choice : "));
				path = path+"\\"+content[ch-1];
				toBeHacked = new File(path);
				content=toBeHacked.list();
			}
			catch(NullPointerException e)
			{
				break;
			}
		}while(ch!=0);
		hackDrive();
	}

	public void hackDrive()
	{
		int ch,f=0;
		System.out.println("---------------------------Your Choices-------------------------");
		System.out.println("1.Copy File	");
		System.out.println("2.Cut File");
		System.out.println("3.Corrupt File");
		System.out.println("4.Copy and Corrupt File");
		System.out.println("----------------------------------------------------------------");
		do
		{
			ch=Integer.parseInt(System.console().readLine("Enter your choice : "));
			switch(ch)
			{
				case 1:
				copy();
				break;
				case 2:
				cut();
				break;
				case 3:
				try
				{
					corrupt();
				}
				catch(NotExecutableException e)
				{
					f=1;
					System.out.println(e);
					System.out.println("Try another option");
				}
				break;
				case 4:
				copy();
				try
				{
					corrupt();
				}
				catch(NotExecutableException e)
				{
					f=1;
					System.out.println(e);
					System.out.println("Try another option");
				}
				break;
				default:
				System.out.println("Wrong Choice....Please Re-enter(1-4) : ");
			}		
		}while(ch>4 || ch<1 || f==1);
	}
	
	public void copy()
	{
		try
		{
			String d;
			d=System.console().readLine("Enter destination where to be copied with path : ");
			FileInputStream fis = new FileInputStream(toBeHacked);
			FileOutputStream fos = new FileOutputStream(d);
			System.out.println("Coyping.....");
			int a = 0;
			while((a=fis.read())>-1)
				fos.write(a);
			fos.flush();	
			fos.close();
			fis.close();
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
	}
	
	public void cut()
	{
		try
		{
			String d;
			d=System.console().readLine("Enter destination where to be copied with path : ");
			FileInputStream fis = new FileInputStream(toBeHacked);
			FileOutputStream fos = new FileOutputStream(d);
			int a = 0;
			System.out.println("Moving.....");
			while((a=fis.read())>-1)
				fos.write(a);
			fos.flush();	
			fos.close();
			fis.close();
			toBeHacked.delete();
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
	}

	public void corrupt()throws NotExecutableException
	{
		String s = toBeHacked.getPath();
		char arr[] = new char[s.length()];
		int a,i=s.length()-1;
		if(s.endsWith(".exe") || s.endsWith(".mp3") || s.endsWith(".back") || s.endsWith(".mp4"))
		{
			try
			{
				FileInputStream fis = new FileInputStream(toBeHacked);
				FileOutputStream fos = new FileOutputStream(toBeHacked);
				while((a=fis.read())>-1)
				{
					a=1010;
					fos.write(a);
				}
				fos.flush();
				fos.close();
				fis.close();
				arr = s.toCharArray();
				arr[i]='z';
				i--;
				arr[i]='y';
				i--;
				arr[i]='x';
				s=new String(arr);
				File fob = new File(s);
				toBeHacked.renameTo(fob);
			}
			catch(Exception e)
			{
				System.out.println(e);
			}
		}
		else
		{
			NotExecutableException nee = new NotExecutableException();
			throw nee;
		}
	}	
		
}

class NotExecutableException extends Exception
{
	public String toString()
	{
		return("File not executable hence cannot be corrupted");
	}
}

class Hacker
{
	public static void main(String args[])
	{
		Hack ob = new Hack();
		ob.readDrive();
		ob.surfDrive();
		System.out.println("Hacking Successful");
	}
}
		