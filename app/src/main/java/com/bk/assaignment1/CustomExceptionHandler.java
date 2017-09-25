package com.bk.assaignment1;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.os.Process;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
/**
 * Created by Saki Rezwana on 9/25/2017
 *
 * @author Biswajit Karmakar
 * @mail bkarmak@gmail.com
 * @version 1.0
 */
public class CustomExceptionHandler implements Thread.UncaughtExceptionHandler {

	public static Context context;

	public static final String CRASH_FILE = "CrashReport.txt";

	private final Class<?> activityClass;

	public CustomExceptionHandler(Context ctx, Class<?> c) {

		context = ctx;
		activityClass = c;
	}

	public void uncaughtException(Thread thread, Throwable exception) {

		StringWriter stackTrace = new StringWriter();
		exception.printStackTrace(new PrintWriter(stackTrace));
		System.err.println(stackTrace);// You can use LogCat too
		Intent intent = new Intent(context, activityClass);
		String s = stackTrace.toString();
		saveCrashReport(s);
		saveReportInDB(s);
		//you can use this String to know what caused the exception and in which Activity
		intent.putExtra("uncaughtException",
				"Exception is: " + stackTrace.toString());
		intent.putExtra("stacktrace", s);
		//context.startActivity(intent);
		//for restarting the Activity
		Process.killProcess(Process.myPid());
		System.exit(0);
	}

	private void saveReportInDB(String report) {
		// TODO Auto-generated method stub

		//Errorlog attributes
		String timeStamp = "";
		String errorSummary = "";
		String className = "";
		String errorAtline = "";
		String errorDetail = "";
		String isDeleted = "0";

		String[] inputSplitNewLine =report.split("\\n");

		timeStamp = Long.toString(System.currentTimeMillis());

		// Get main cause of error from stackstrace
		errorSummary = inputSplitNewLine[0];

		String packagePrefix = "com.bk";

		// Filtering errors which are thrown from classes under project specific package 
		for(int i=1;i<inputSplitNewLine.length;i++){

			String str = inputSplitNewLine[i];

			if(str.contains(packagePrefix))
				errorDetail += str + "\n";
		}

		//Get only error class name from braces
		int startingIndex = errorDetail.indexOf("(");
		int endingIndex = errorDetail.indexOf(")");

		className = errorDetail.substring(startingIndex+1, endingIndex);

		errorAtline = className.substring(className.indexOf(":")+1 , className.length());
		className = className.substring(0, className.indexOf(":"));

		//Build a custom error report

	}

	private void saveCrashReport(String report){

		// TODO Auto-generated method stub
		File logFile = new File(Environment.getExternalStorageDirectory(), "Error_Log.txt");
		try {
			if(!logFile.exists() ){

				logFile.createNewFile();
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy, HH:mm:ss  ");
		String currentDateandTime = sdf.format(new Date()).concat(":::  ");

		FileOutputStream fos;
		byte[] data = concat(currentDateandTime.getBytes() , report.concat("\n###\n").getBytes());

		try {
			fos = new FileOutputStream(logFile, true);
			try {
				fos.write(data);
				fos.flush();
				fos.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}


		try {
			OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput(CRASH_FILE, Context.MODE_PRIVATE));
			outputStreamWriter.write("");
			outputStreamWriter.close();
		}
		catch (IOException e) {
			Log.e("Exception", "File write failed: " + e.toString());
		}
	}

	byte[] concat(byte[]...arrays)
	{
		// Determine the length of the result array
		int totalLength = 0;
		for (int i = 0; i < arrays.length; i++)
		{
			totalLength += arrays[i].length;
		}

		// create the result array
		byte[] result = new byte[totalLength];

		// copy the source arrays into the result array
		int currentIndex = 0;
		for (int i = 0; i < arrays.length; i++)
		{
			System.arraycopy(arrays[i], 0, result, currentIndex, arrays[i].length);
			currentIndex += arrays[i].length;
		}

		return result;
	}

}
