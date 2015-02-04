package ftpclient;



import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import it.sauronsoftware.ftp4j.FTPAbortedException;
import it.sauronsoftware.ftp4j.FTPClient;
import it.sauronsoftware.ftp4j.FTPDataTransferException;
import it.sauronsoftware.ftp4j.FTPException;
import it.sauronsoftware.ftp4j.FTPFile;
import it.sauronsoftware.ftp4j.FTPIllegalReplyException;
import it.sauronsoftware.ftp4j.FTPListParseException;

public class FtpWork {

	private final static String FTP_ADDRESS = "ftp.mozilla.org";
	private final static String LOGIN = "anonymous";
	private final static String PASSWORD = "ftp4j";
	private final static long NOOPTIME = 5000;
	private final static String UPCOMMAND = "up";
	private final static String EXITCOMMAND = "exit";

	private static FTPClient ftpClient = new FTPClient();
	private static FTPFile[] list;
	private static ArrayList<FtpUnit> unitList = new ArrayList<FtpUnit>();

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		connectToFtp();
		ftpFolderList();
		listOutput(unitList);
		processUserInput(in);
		in.close();
		System.out.println("Work ended.");
		ftpClient.setAutoNoopTimeout(0);
	}

	public static void connectToFtp() {
		try {
			
			ftpClient.connect(FTP_ADDRESS);
			ftpClient.login(LOGIN, PASSWORD);
			
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (FTPIllegalReplyException e) {
			e.printStackTrace();
		} catch (FTPException e) {
			e.printStackTrace();
		}
		
		ftpClient.setAutoNoopTimeout(NOOPTIME);
	}

	public static void processUserInput(Scanner in) {
		
		String input;

		while (true) {
			input = in.nextLine();
			switch (input) {
			case EXITCOMMAND:
				return;
			case UPCOMMAND:
				try {
					ftpClient.changeDirectoryUp();
				} catch (IllegalStateException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (FTPIllegalReplyException e) {
					e.printStackTrace();
				} catch (FTPException e) {
					e.printStackTrace();
				}
				ftpFolderList();
				break;
			default:
				ftpProcessing(input);
			}
			listOutput(unitList);
		}
	}

	public static void ftpFolderList() {
		try {
			list = ftpClient.list();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (FTPIllegalReplyException e) {
			e.printStackTrace();
		} catch (FTPException e) {
			e.printStackTrace();
		} catch (FTPDataTransferException e) {
			e.printStackTrace();
		} catch (FTPAbortedException e) {
			e.printStackTrace();
		} catch (FTPListParseException e) {
			e.printStackTrace();
		}
		for (int i = 0; i < list.length; i++) {
			FtpUnit unit = new FtpUnit(list[i].getName(),
					unitType(list[i].getType()));
			unitList.add(unit);
		}
		System.gc();
	}

	public static void ftpProcessing(String input) {

		if (unitList.contains(new FtpUnit(input, "Directory"))
				|| unitList.contains(new FtpUnit(input, "Link"))) {
			try {
				ftpClient.changeDirectory(input);
				ftpFolderList();
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (FTPIllegalReplyException e) {
				e.printStackTrace();
			} catch (FTPException e) {
				e.printStackTrace();
			}
		}

		else {
			if (unitList.contains(new FtpUnit(input, "File"))) {
				fileDownload(input);

			} else
				System.out.println("Wrong input.");
		}

	}

	public static void fileDownload(String filename) {
		System.out.println("Downloading file: " + filename);
		try {
			ftpClient.download(filename, new java.io.File(filename));
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (FTPIllegalReplyException e) {
			e.printStackTrace();
		} catch (FTPException e) {
			e.printStackTrace();
		} catch (FTPDataTransferException e) {
			e.printStackTrace();
		} catch (FTPAbortedException e) {
			e.printStackTrace();
		}
		System.out.println("Download finished.");
	}

	public static String unitType(int type) {

		switch (type) {
		case 0:
			return "File";
		case 1:
			return "Directory";
		case 2:
			return "Link";
		default:
			return "Unrecognized type";
		}
	}

	public static void listOutput(ArrayList<FtpUnit> unitList) {
		for (int i = 0; i < unitList.size(); i++) {
			System.out.println(unitList.get(i).toString());
		}

	}
}