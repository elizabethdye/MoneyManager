package Networking;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;

import Model.ServerRequest;
import Model.ServerRequestResult;

public class ClientRequestThread extends Thread {
	private ServerRequest request;
	private String host;
	private int port;
	private ArrayBlockingQueue<ServerRequestResult> channel;
	private boolean going;
	
	public ClientRequestThread(ServerRequest request, String host, int port, ArrayBlockingQueue<ServerRequestResult> channel) {
		this.request = request;
		this.host = host;
		this.port = port;
		this.channel = channel;
		going = true;
	}
	
	public synchronized boolean isGoing() {
		return going;
	}

	@Override
	public void run() {
		Socket s = null;
		try {
			s = new Socket(host, port);
			ObjectOutputStream toServerStream = new ObjectOutputStream(s.getOutputStream());
			ObjectInputStream fromServerStream = new ObjectInputStream(s.getInputStream());
			toServerStream.writeObject(request);
			ServerRequestResult result = (ServerRequestResult) fromServerStream.readObject();
			channel.put(result);
			System.out.println("ClientRequestThread put result into channel...");
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
            try {
                if (s != null) {s.close();}
            } catch (IOException ioe) {
                System.out.println("error closing socket");
            }
        }
	}
	
	public synchronized void halt() {
		going = false;
	}
}
