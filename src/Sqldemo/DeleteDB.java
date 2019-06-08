package Sqldemo;

import java.io.File;

public class DeleteDB {
	// Java program to delete a file
	public static void main(String[] args)
	{
		File file1 = new File("/DyeCenter/idea.db.mv.db");
		File file2 = new File("/DyeCenter/idea.db.trace.db");

		if(file1.delete())
		{
			System.out.println("File deleted successfully");
		}
		else
		{
			System.out.println("Failed to delete the file");
		}
		if(file2.delete())
		{
			System.out.println("File deleted successfully");
		}
		else
		{
			System.out.println("Failed to delete the file");
		}
	}
}

