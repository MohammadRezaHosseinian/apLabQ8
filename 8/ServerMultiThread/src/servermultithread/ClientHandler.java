/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servermultithread;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.text.*;
import java.util.*;

class ClientHandler extends Thread {

	DateFormat fordate = new SimpleDateFormat("yyyy/MM/dd");
	DateFormat fortime = new SimpleDateFormat("hh:mm:ss");
	final DataInputStream dis;
	final DataOutputStream dos;
	final Socket socketMultiThread;

	// Constructor
	public ClientHandler(Socket s, DataInputStream dis, DataOutputStream dos) {
		this.socketMultiThread = s;
		this.dis = dis;
		this.dos = dos;
	}

	@Override
	public void run() {
		String received;
		String toreturn;
		while (true) {
			try {

				// Ask user what he wants
				dos.writeUTF("What do you want?[Date | Time]..\n"
						+ "Type Exit to terminate connection.");

				// receive the answer from client
				received = dis.readUTF();

				if (received.equals("Exit")) {
					System.out.println("Client " + this.socketMultiThread + " sends exit...");
					System.out.println("Closing this connection.");
					this.socketMultiThread.close();
					System.out.println("Connection closed");
					break;
				}

				// creating Date object
				Date date = new Date();

				// write on output stream based on the
				// answer from the client
				switch (received) {

					case "Date":
						toreturn = fordate.format(date);
						dos.writeUTF(toreturn);
						break;

					case "Time":
						toreturn = fortime.format(date);
						dos.writeUTF(toreturn);
						break;

					default:
						dos.writeUTF("Invalid input");
						break;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		try {
			// closing resources
			this.dis.close();
			this.dos.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
